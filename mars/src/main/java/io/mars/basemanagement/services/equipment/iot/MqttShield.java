package io.mars.basemanagement.services.equipment.iot;

import java.time.Instant;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import io.vertigo.app.config.discovery.NotDiscoverable;
import io.vertigo.commons.eventbus.EventBusManager;
import io.vertigo.commons.eventbus.EventBusSubscribed;
import io.vertigo.core.component.Activeable;
import io.vertigo.core.component.Component;
import io.vertigo.database.timeseries.Measure;
import io.vertigo.lang.Assertion;
import io.vertigo.lang.WrappedException;

@NotDiscoverable
public class MqttShield implements Component, Activeable {
	private static final String BROKER = "tcp://mars.dev.klee.lan.net:1883";
	private static final String CLIENT_ID = "JavaSample";
	private static final String CLIENT_ID_PUB = "MarsPublisher";

	private static final Logger LOGGER = LogManager.getLogger(MqttShield.class);

	@Inject
	private EventBusManager eventBusManager;

	private final MqttCallback callback = new MqttCallback() {
		@Override
		public void connectionLost(final Throwable cause) {
			LOGGER.info("Connection lost");
			//			try {
			//				mqttClient.connect();
			//				mqttClient.setCallback(callback);
			//				logger.info("Reconnected");
			//				start();
			//			} catch (final MqttException me) {
			//				throw WrappedException.wrap(me);
			//			}
		}

		@Override
		public void messageArrived(final String topic, final MqttMessage message) throws Exception {
			Assertion.checkNotNull(topic);
			Assertion.checkNotNull(message);
			//
			handleMessage(message, topic);
			LOGGER.info("Message from: " + topic);
			LOGGER.info("Message: " + message.toString());

		}

		@Override
		public void deliveryComplete(final IMqttDeliveryToken token) {
			// TODO
		}
	};

	private MqttClient mqttClient;
	private MqttClient mqttClientPub;

	@Override
	public void start() {
		try {
			connect();
			subscribe();
		} catch (final MqttException me) {
			throw WrappedException.wrap(me);
		}
	}

	@Override
	public void stop() {
		try {
			//unsubscribe is automatically done when disconnecting
			disconnect();
		} catch (final MqttException me) {
			throw WrappedException.wrap(me);
		}
	}

	private void subscribe() throws MqttException {
		//final String topicPub = "alpha/MQTT";
		final String topicSub = "#";
		// Subscribing to topics
		mqttClient.subscribe(topicSub, 0);
		LOGGER.info("Subscribed to channel: " + topicSub);

	}

	private void connect() throws MqttException {
		final MqttClient sampleClient = new MqttClient(BROKER, CLIENT_ID, new MemoryPersistence());
		final MqttClient samplePublisher = new MqttClient(BROKER, CLIENT_ID_PUB);
		final MqttConnectOptions connOpts = new MqttConnectOptions();
		connOpts.setCleanSession(true);

		// Connect to broker
		LOGGER.info("Connecting to broker: " + BROKER);
		sampleClient.connect(connOpts);
		connOpts.setCleanSession(true);
		samplePublisher.connect();
		sampleClient.setCallback(callback);
		LOGGER.info("Connected");

		mqttClient = sampleClient;
		mqttClientPub = samplePublisher;
	}

	private void disconnect() throws MqttException {
		if (mqttClient != null) {
			mqttClient.disconnect();
			LOGGER.info("Disconnected");
		}
		if (mqttClientPub != null) {
			mqttClientPub.disconnect();
			LOGGER.info("Disconnected");
		}
	}

	private void handleMessage(final MqttMessage message, final String topic) {
		Assertion.checkNotNull(message);
		Assertion.checkNotNull(topic);
		//---
		LOGGER.info("Receive message " + message + " from " + topic);
		final String[] data = parseMqttMessage(message);
		LOGGER.info("data parsed");
		final String[] parsedTopic = parseTopic(topic);
		LOGGER.info("topic parsed");
		if (parsedTopic[1].equals("fireAlarm")) {
			LOGGER.info("Message from alarm dectected");
			final InputEvent actuatorEvent = createActuatorEvent(message, parsedTopic[0] + "/" + "relay");
			eventBusManager.post(actuatorEvent);
		}
		LOGGER.info("data that will be stored " + data[1] + " from " + parsedTopic[1]);
		LOGGER.info("value of data: " + Double.parseDouble(data[1]));
		final Measure measure = Measure.builder(parsedTopic[1])
				.time(Instant.now())
				.addField("equipment", parsedTopic[0])
				.addField("value", Double.parseDouble(data[1]))
				.build();
		eventBusManager.post(new MeasureEvent(measure));
	}

	private static String[] parseMqttMessage(final MqttMessage message) {
		Assertion.checkNotNull(message);
		//
		final String[] dataParsed = message.toString().split(" ");
		return dataParsed;
	}

	private static String[] parseTopic(final String topic) {
		Assertion.checkNotNull(topic);
		//
		final String[] dataParsed = topic.split("/");
		return dataParsed;
	}

	private static InputEvent createActuatorEvent(final MqttMessage message, final String topic) {
		Assertion.checkNotNull(message);
		//---
		final Integer actuatorValue = Integer.parseInt(parseMqttMessage(message)[1]);
		return new InputEvent(InputEvent.Type.of(actuatorValue), topic);
	}

	@EventBusSubscribed
	public void onOutput(final OutputEvent outputEvent) throws MqttException {
		Assertion.checkNotNull(outputEvent);
		//---
		mqttClientPub.publish(outputEvent.getTopic(), createMessage(outputEvent.getValue()));
	}

	private static MqttMessage createMessage(final String message) {
		return new MqttMessage((Instant.now() + " " + message).getBytes());
	}
}
