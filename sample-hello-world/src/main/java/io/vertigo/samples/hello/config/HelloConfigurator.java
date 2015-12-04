package io.vertigo.samples.hello.config;

import io.vertigo.app.config.AppConfig;
import io.vertigo.app.config.AppConfigBuilder;
import io.vertigo.samples.hello.webservices.HelloWebServices;
import io.vertigo.vega.VegaFeatures;

public final class HelloConfigurator {
	public static AppConfig config(final int port) {
		return new AppConfigBuilder()
				.beginModule(VegaFeatures.class)
				.withEmbeddedServer(port)
				.endModule()
				//-----Declaration of a module named 'Hello' which contains a webservice component. 
				.beginModule("Hello")
				.withNoAPI()
				.addComponent(HelloWebServices.class)
				.endModule()
				.build();
	}
}
