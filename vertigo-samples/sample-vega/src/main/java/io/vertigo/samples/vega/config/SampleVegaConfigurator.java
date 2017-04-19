package io.vertigo.samples.vega.config;

import io.vertigo.app.config.AppConfig;
import io.vertigo.app.config.AppConfigBuilder;
import io.vertigo.app.config.DefinitionProviderConfigBuilder;
import io.vertigo.app.config.ModuleConfigBuilder;
import io.vertigo.commons.impl.CommonsFeatures;
import io.vertigo.dynamo.impl.DynamoFeatures;
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
				.beginBoot()
				.withLocales(locales)
				.endBoot()
				.addModule(new CommonsFeatures().build())
				.addModule(new DynamoFeatures().build())
				.addModule(new PersonaFeatures()
						.withUserSession(SampleSession.class)
						.build())
				.addModule(new ModuleConfigBuilder("dependencies")
						.addComponent(KVStoreManager.class, io.vertigo.dynamo.impl.kvstore.KVStoreManagerImpl.class)
						.build())
				.addModule(new VegaFeatures()
						.withEmbeddedServer(port)
						.withTokens("security-token")
						.build())
				//-----Declaration of a module named 'Vega' which contains a webservice component.
				.addModule(new ModuleConfigBuilder("Samples")
					.withNoAPI()
					.addComponent(HelloWebServices.class)
					.addComponent(MovieWebServices.class)
					.addComponent(TokenWebServices.class)
					.addDefinitionProvider(new DefinitionProviderConfigBuilder(VegaDefinitionProvider.class).build())
					.build())
				.build();
		// @formatter:on
	}
}
