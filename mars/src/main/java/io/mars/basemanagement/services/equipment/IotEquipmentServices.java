package io.mars.basemanagement.services.equipment;

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
import io.vertigo.core.component.Activeable;
import io.vertigo.core.component.Component;
import io.vertigo.database.timeseries.Measure;
import io.vertigo.database.timeseries.TimeSeriesDataBaseManager;
import io.vertigo.lang.Assertion;
import io.vertigo.lang.WrappedException;

@NotDiscoverable
public class IotEquipmentServices implements Component, Activeable {

	private final Logger logger = LogManager.getLogger(this.getClass());

	@Inject
	private TimeSeriesDataBaseManager timeSeriesDataBaseManager;

	private final MqttClient mqttClient;

	final MqttCallback callback = new MqttCallback() {
		@Override
		public void connectionLost(final Throwable cause) {
			//
		}

		@Override
		public void messageArrived(final String topic, final MqttMessage message) throws Exception {
			Assertion.checkNotNull(topic);
			Assertion.checkNotNull(message);
			//
			handleMessage(message, topic);
			logger.info("message from: " + topic);
			logger.info("Message: " + message.toString());

		}

		@Override
		public void deliveryComplete(final IMqttDeliveryToken token) {
			// TODO
		}
	};

	@Inject
	public IotEquipmentServices() {
		final String BROKER = "tcp://mars.dev.klee.lan.net:1883";
		final String CLIENT_ID = "JavaSample";

		final MemoryPersistence persistence = new MemoryPersistence();
		try {
			final MqttClient sampleClient = new MqttClient(BROKER, CLIENT_ID, persistence);
			final MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(true);

			// Connect to broker
			logger.info("Connecting to broker: " + BROKER);
			sampleClient.connect(connOpts);
			sampleClient.setCallback(callback);
			logger.info("Connected");

			mqttClient = sampleClient;
		} catch (final MqttException me) {
			throw WrappedException.wrap(me);
		}
	}

	@Override
	public void start() {
		//final String topicPub = "alpha/MQTT";
		final String topicSub = "#";
		//		try {
		//			// Subscribing to topics
		//			mqttClient.subscribe(topicSub);
		//		} catch (final MqttException me) {
		//			throw WrappedException.wrap(me);
		//		}
	}

	@Override
	public void stop() {
		try {
			mqttClient.disconnect();
			logger.info("Disconnected");
		} catch (final MqttException me) {
			throw WrappedException.wrap(me);
		}
	}

	public void handleMessage(final MqttMessage message, final String topic) {
		Assertion.checkNotNull(message);
		Assertion.checkNotNull(topic);
		///
		final String[] data = parseMqttMessage(message);
		final String[] location = parseTopic(topic);
		final Measure mes = Measure.builder(location[1])
				.time(Instant.now())
				.addField("value", Integer.parseInt(data[1]))
				.build();
		timeSeriesDataBaseManager.insertMeasure("mars-test", mes);
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

}
