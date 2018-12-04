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

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.vertigo.app.AutoCloseableApp;
import io.vertigo.app.Home;
import io.vertigo.core.component.di.injector.DIInjector;
import io.vertigo.database.timeseries.DataFilter;
import io.vertigo.database.timeseries.Measure;
import io.vertigo.database.timeseries.TimeFilter;
import io.vertigo.database.timeseries.TimeSeriesDataBaseManager;

//import lib to use mqtt paho
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;

/**
 * Test of the IoT services for mars.
 *
 * @author mlaroche
 */
public final class IotMarsTest {

	private static AutoCloseableApp app;

	@BeforeAll
	public static final void setUp() throws Exception {
		app = new AutoCloseableApp(IotMarsTestConfig.config());
	}

	@AfterAll
	public static final void tearDown() throws Exception {
		if (app != null) {
			app.close();
		}
	}

	public final void setUpInjection() throws Exception {
		if (app != null) {
			DIInjector.injectMembers(this, Home.getApp().getComponentSpace());
		}
	}

	@BeforeEach
	public void doSetUp() throws Exception {
		setUpInjection();
	}

	@Inject
	private TimeSeriesDataBaseManager timeSeriesDataBaseManager;

	@Test
	public void testMqttClient() {
		String mytopic = "MQTT";
		String content = "Message from MqttPublishSample";
		int qos = 0;
		String broker = "tcp://mars.dev.klee.lan.net:1883";
		String clientId = "JavaSample";
		MemoryPersistence persistence = new MemoryPersistence();
		MqttCallback callback = new MqttCallback() {
			public void connectionLost(Throwable cause) {
			}

			public void messageArrived(String topic, MqttMessage message) throws Exception {
				System.out.println("Message: " + message.toString());
				
			}

			public void deliveryComplete(IMqttDeliveryToken token) {
			}
		};
		try {
			MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
			MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(true);
			System.out.println("Connecting to broker: " + broker);
			sampleClient.connect(connOpts);
			sampleClient.setCallback(callback);
			System.out.println("Connected");
			sampleClient.subscribe(mytopic);
			System.out.println("Publishing message: " + content);
			MqttMessage message = new MqttMessage(content.getBytes());
			message.setQos(qos);
			sampleClient.publish(mytopic, message);
			System.out.println("Message published");
			sampleClient.subscribe(mytopic);
			sampleClient.disconnect();
			System.out.println("Disconnected");
		} catch (MqttException me) {
			System.out.println("reason " + me.getReasonCode());
			System.out.println("msg " + me.getMessage());
			System.out.println("loc " + me.getLocalizedMessage());
			System.out.println("cause " + me.getCause());
			System.out.println("excep " + me);
			me.printStackTrace();
		}
	}

	@Test
	public void testInsertMeasure() {
		final Measure measure = Measure.builder("test")
				.time(Instant.now())
				.addField("temp", 12)
				.build();
		timeSeriesDataBaseManager.insertMeasure("mars-test", measure);
	}

	private void handleMessage(final MqttMessage message) {
		///
		//timeSeriesDataBaseManager.insertMeasure("mars-test", measure);
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
