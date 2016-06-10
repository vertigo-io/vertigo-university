package io.vertigo.samples.vega.config;

import io.vertigo.app.config.AppConfig;
import io.vertigo.app.config.AppConfigBuilder;
import io.vertigo.commons.impl.CommonsFeatures;
import io.vertigo.dynamo.kvstore.KVStoreManager;
import io.vertigo.persona.impl.security.PersonaFeatures;
import io.vertigo.samples.vega.domain.SampleSession;
import io.vertigo.samples.vega.domain.VegaDefinitionProvider;
import io.vertigo.samples.vega.webservices.HelloWebServices;
import io.vertigo.samples.vega.webservices.MovieWebServices;
import io.vertigo.samples.vega.webservices.TokenWebServices;
import io.vertigo.vega.VegaFeatures;

public final class SampleVegaConfigurator {
	public static AppConfig config(final int port) {
		final String locales = "fr_FR, en , de_DE";
		// @formatter:off
		return new AppConfigBuilder()
		.beginBootModule(locales).endModule()
		.beginModule(CommonsFeatures.class).endModule()
		.beginModule(PersonaFeatures.class)
			.withUserSession(SampleSession.class)
		.endModule()
		.beginModule("dependencies")
			.addComponent(KVStoreManager.class, io.vertigo.dynamo.impl.kvstore.KVStoreManagerImpl.class)
		.endModule()
		.beginModule(VegaFeatures.class)
			.withEmbeddedServer(port)
			.withTokens("security-token")
		.endModule()
		//-----Declaration of a module named 'Vega' which contains a webservice component.
		.beginModule("Samples")
			.withNoAPI()
			.addComponent(HelloWebServices.class)
			.addComponent(MovieWebServices.class)
			.addComponent(TokenWebServices.class)
			.addDefinitionProvider(VegaDefinitionProvider.class)
		.endModule()
		.build();
		// @formatter:on
	}
}
