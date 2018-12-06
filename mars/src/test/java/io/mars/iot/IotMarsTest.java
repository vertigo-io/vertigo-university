/**
 * vertigo - simple java starter
 *
 * Copyright (C) 2013-2018, KleeGroup, direction.technique@kleegroup.com (http://www.kleegroup.com)
 * KleeGroup, Centre d'affaire la Boursidiere - BP 159 - 92357 Le Plessis Robinson Cedex - France
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.mars.iot;

import java.time.Instant;
import java.util.Collections;

import javax.inject.Inject;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
//import lib to use mqtt paho
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.junit.jupiter.api.Test;

import io.vertigo.AbstractTestCaseJU5;
import io.vertigo.database.timeseries.DataFilter;
import io.vertigo.database.timeseries.Measure;
import io.vertigo.database.timeseries.TimeFilter;
import io.vertigo.database.timeseries.TimeSeriesDataBaseManager;

/**
 * Test of the IoT services for mars.
 *
 * @author mlaroche
 */
public final class IotMarsTest extends AbstractTestCaseJU5 {

	@Inject
	private TimeSeriesDataBaseManager timeSeriesDataBaseManager;

	@Test
	public void testMqttClient() {

		final String mytopicsub = "alpha/#";
		final String mytopicpub = "alpha/MQTT";
		final String mytopicpub2 = "alpha/Toto";
		final String content = "Message from MqttPublishSample";
		final int qos = 0;
		final String broker = "tcp://mars.dev.klee.lan.net:1883";
		final String clientId = "JavaSample";
		final MemoryPersistence persistence = new MemoryPersistence();
		final MqttCallback callback = new MqttCallback() {
			@Override
			public void connectionLost(final Throwable cause) {
				//
			}

			@Override
			public void messageArrived(final String topic, final MqttMessage message) throws Exception {
				handleMessage(message, topic);
				System.out.println("message from: " + topic);
				System.out.println("Message: " + message.toString());

			}

			@Override
			public void deliveryComplete(final IMqttDeliveryToken token) {
				// TODO
			}
		};
		try {
			final MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
			final MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(true);
			System.out.println("Connecting to broker: " + broker);
			sampleClient.connect(connOpts);
			sampleClient.setCallback(callback);
			System.out.println("Connected");
			sampleClient.subscribe(mytopicsub);
			System.out.println("Publishing message: " + content);
			final MqttMessage message = new MqttMessage(content.getBytes());
			message.setQos(qos);
			sampleClient.publish(mytopicpub, message);
			sampleClient.publish(mytopicpub2, message);
			System.out.println("Message published");
			sampleClient.subscribe(mytopicsub);
			sampleClient.disconnect();
			System.out.println("Disconnected");
		} catch (final MqttException me) {
			System.out.println("reason " + me.getReasonCode());
			System.out.println("msg " + me.getMessage());
			System.out.println("loc " + me.getLocalizedMessage());
			System.out.println("cause " + me.getCause());
			System.out.println("excep " + me);
			me.printStackTrace();
		}
	}

	private static String[] parseMqttMessage(final MqttMessage message) {
		final String[] dataParsed = message.toString().split(" ");
		return dataParsed;
	}

	private static String[] parseTopic(final String topic) {
		final String[] dataParsed = topic.split("/");
		return dataParsed;
	}

	@Test
	public void testInsertMeasure() {
		final Measure measure = Measure.builder("test")
				.time(Instant.now())
				.addField("temp", 12)
				.build();
		timeSeriesDataBaseManager.insertMeasure("mars-test", measure);
	}

	private static String getTypeOfDataMeasured(final String[] topicParsed) {
		if (topicParsed[1] == "temperature") {
			return "temperature";
		} else if (topicParsed[1] == "moisture") {
			return "moisture";
		} else if (topicParsed[1] == "temperature") {
			return "temperature";
		} else if (topicParsed[1] == "soil_moisture") {
			return "soil_moisture";
		} else if (topicParsed[-1] == "temperature") {
			return "temperature";
		} else {
			return null;
		}
	}

	private void handleMessage(final MqttMessage message, final String topic) {
		///
		final String[] data = parseMqttMessage(message);
		final String[] location = parseTopic(topic);
		final Measure mes = Measure.builder(location[1])
				.time(Instant.now())
				.addField(getTypeOfDataMeasured(location), data[1])
				.build();
		timeSeriesDataBaseManager.insertMeasure("mars-test", mes);
	}

	@Test
	public void testReadMeasures() {
		// read some values
		timeSeriesDataBaseManager.getTimeSeries(
				"mars-test",
				Collections.singletonList("temp:mean"),
				DataFilter.builder("test").build(),
				TimeFilter.builder("now() - 1h", "now()").withTimeDim("1m").build());
	}
}
