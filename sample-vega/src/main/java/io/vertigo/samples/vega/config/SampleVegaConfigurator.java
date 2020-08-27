package io.vertigo.samples.vega.config;

import io.vertigo.account.AccountFeatures;
import io.vertigo.commons.CommonsFeatures;
import io.vertigo.connectors.javalin.JavalinFeatures;
import io.vertigo.core.node.config.BootConfig;
import io.vertigo.core.node.config.DefinitionProviderConfig;
import io.vertigo.core.node.config.ModuleConfig;
import io.vertigo.core.node.config.NodeConfig;
import io.vertigo.core.param.Param;
import io.vertigo.datamodel.DataModelFeatures;
import io.vertigo.datastore.DataStoreFeatures;
import io.vertigo.datastore.kvstore.KVStoreManager;
import io.vertigo.samples.vega.domain.SampleSession;
import io.vertigo.samples.vega.domain.VegaDefinitionProvider;
import io.vertigo.samples.vega.webservices.HelloWebServices;
import io.vertigo.samples.vega.webservices.MovieWebServices;
import io.vertigo.samples.vega.webservices.TokenWebServices;
import io.vertigo.vega.VegaFeatures;

public final class SampleVegaConfigurator {
	public static NodeConfig config(final int port) {
		final String locales = "fr_FR, en , de_DE";
		return NodeConfig.builder()
				.withBoot(BootConfig.builder()
						.withLocales(locales)
						.build())
				.addModule(new JavalinFeatures().withEmbeddedServer(Param.of("port", port)).build())
				.addModule(new CommonsFeatures().build())
				.addModule(new DataStoreFeatures().build())
				.addModule(new AccountFeatures()
						.withSecurity(Param.of("userSessionClassName", SampleSession.class.getName()))
						.build())
				.addModule(new DataModelFeatures().build())
				.addModule(ModuleConfig.builder("dependencies")
						.addComponent(KVStoreManager.class, io.vertigo.datastore.impl.kvstore.KVStoreManagerImpl.class)
						.build())
				.addModule(new VegaFeatures()
						.withWebServices()
						.withJavalinWebServerPlugin()
						.withWebServicesTokens(Param.of("tokens", "security-token"))
						.build())
				//-----Declaration of a module named 'Vega' which contains a webservice component.
				.addModule(ModuleConfig.builder("Samples")
						.addComponent(HelloWebServices.class)
						.addComponent(MovieWebServices.class)
						.addComponent(TokenWebServices.class)
						.addDefinitionProvider(DefinitionProviderConfig.builder(VegaDefinitionProvider.class).build())
						.build())
				.build();
	}
}
