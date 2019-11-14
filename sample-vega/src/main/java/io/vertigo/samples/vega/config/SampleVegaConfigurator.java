package io.vertigo.samples.vega.config;

import io.vertigo.account.AccountFeatures;
import io.vertigo.commons.CommonsFeatures;
import io.vertigo.core.node.config.DefinitionProviderConfig;
import io.vertigo.core.node.config.ModuleConfig;
import io.vertigo.core.node.config.NodeConfig;
import io.vertigo.core.param.Param;
import io.vertigo.dynamo.DynamoFeatures;
import io.vertigo.dynamo.kvstore.KVStoreManager;
import io.vertigo.samples.vega.domain.SampleSession;
import io.vertigo.samples.vega.domain.VegaDefinitionProvider;
import io.vertigo.samples.vega.webservices.HelloWebServices;
import io.vertigo.samples.vega.webservices.MovieWebServices;
import io.vertigo.samples.vega.webservices.TokenWebServices;
import io.vertigo.vega.VegaFeatures;

public final class SampleVegaConfigurator {
	public static NodeConfig config(final int port) {
		final String locales = "fr_FR, en , de_DE";
		// @formatter:off
		return  NodeConfig.builder()
				.beginBoot()
				.withLocales(locales)
				.endBoot()
				.addModule(new CommonsFeatures().build())
				.addModule(new DynamoFeatures().build())
				.addModule(new AccountFeatures()
						.withSecurity(Param.of("userSessionClassName", SampleSession.class.getName()))
						.build())
				.addModule( ModuleConfig.builder("dependencies")
						.addComponent(KVStoreManager.class, io.vertigo.dynamo.impl.kvstore.KVStoreManagerImpl.class)
						.build())
				.addModule(new VegaFeatures()
						.withWebServices()
						.withWebServicesEmbeddedServer(Param.of("port", Integer.toString(port)))
						.withWebServicesTokens(Param.of("tokens", "security-token"))
						.build())
				//-----Declaration of a module named 'Vega' which contains a webservice component.
				.addModule( ModuleConfig.builder("Samples")
					.addComponent(HelloWebServices.class)
					.addComponent(MovieWebServices.class)
					.addComponent(TokenWebServices.class)
					.addDefinitionProvider( DefinitionProviderConfig.builder(VegaDefinitionProvider.class).build())
					.build())
				.build();
		// @formatter:on
	}
}
