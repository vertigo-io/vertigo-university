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

import java.util.Collections;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import lib to use mqtt paho
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import io.vertigo.AbstractTestCaseJU5;
import io.vertigo.app.config.NodeConfig;
import io.vertigo.database.timeseries.DataFilter;
import io.vertigo.database.timeseries.TabularDatas;
import io.vertigo.database.timeseries.TimeFilter;
import io.vertigo.database.timeseries.TimeSeriesDataBaseManager;
import io.vertigo.lang.WrappedException;

/**
 * Test of the IoT services for mars.
 *
 * @author mlaroche
 */
public final class IotMarsTest extends AbstractTestCaseJU5 {

	private final Logger logger = LogManager.getLogger(this.getClass());

	private static final String TOPIC_PUB = "e1441/temperature";
	private static final String BROKER = "tcp://mars.dev.klee.lan.net:1883";
	private static final String CLIENT_ID = "MarsTest";
	private static final int QOS = 0;
	private static final String CONTENT = "aaa 32";

	@Override
	protected NodeConfig buildNodeConfig() {
		return IotMarsTestConfig.config();
	}

	@Inject
	private TimeSeriesDataBaseManager timeSeriesDataBaseManager;

	@Test
	@Disabled
	public void server() {
		while (!Thread.interrupted()) {
			try {
				Thread.sleep(10 * 1000);
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	@Disabled
	public void testMqttClient() throws InterruptedException {

		final MemoryPersistence persistence = new MemoryPersistence();
		try {
			final MqttClient sampleClient = new MqttClient(BROKER, CLIENT_ID, persistence);
			final MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(true);
			// Connect to broker
			logger.info("Connecting to broker: " + BROKER);
			sampleClient.connect(connOpts);
			// Publishing message to two topics
			final MqttMessage message = new MqttMessage(CONTENT.getBytes());
			message.setQos(QOS);
			logger.info("Publishing message: " + CONTENT + " to topic " + TOPIC_PUB);
			sampleClient.publish(TOPIC_PUB, message);
			logger.info("Message published :" + CONTENT + " to topic " + TOPIC_PUB);
			//sampleClient.subscribe(TOPIC_SUB);
			sampleClient.disconnect();
			logger.info("Disconnected");

		} catch (final MqttException me) {
			throw WrappedException.wrap(me);
		}
		//---
		Thread.sleep(500L);
		//---
		final TabularDatas lastTemperature = timeSeriesDataBaseManager.getTabularData(
				"mars-test",
				Collections.singletonList("value:last"),
				DataFilter.builder("temperature").build(),
				TimeFilter.builder("now() - 1h", "now() + 1h").build());
		Assertions.assertEquals(32.0, lastTemperature.getTabularDataSeries().get(0).getValues().get("value:last"));
	}

}
