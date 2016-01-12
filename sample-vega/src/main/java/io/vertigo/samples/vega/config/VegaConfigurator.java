package io.vertigo.samples.vega.config;

import io.vertigo.app.config.AppConfig;
import io.vertigo.app.config.AppConfigBuilder;
import io.vertigo.samples.vega.webservices.VegaWebServices;
import io.vertigo.vega.VegaFeatures;

public final class VegaConfigurator {
	public static AppConfig config(final int port) {
		// @formatter:off
		return new AppConfigBuilder()
		//		.beginModule("context")
//			.withNoAPI()
//			.addComponent(AnalyticsManagerImpl.class)
//			.addComponent(VSecurityManagerImpl.class)
//		.endModule()
//		.beginModule(PersonaFeatures.class)
//			.withUserSession(UserSession.class)
//		.endModule()
		.beginModule(VegaFeatures.class)
			.withEmbeddedServer(port)
		.endModule()
		//-----Declaration of a module named 'Vega' which contains a webservice component. 
		.beginModule("VegaSamples")
			.withNoAPI()
			.addComponent(VegaWebServices.class)
		.endModule()
		.build();
		// @formatter:on

	}
}
