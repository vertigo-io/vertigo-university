package io.vertigo.samples.quarto.config;

import io.vertigo.commons.CommonsFeatures;
import io.vertigo.core.node.config.DefinitionProviderConfig;
import io.vertigo.core.node.config.ModuleConfig;
import io.vertigo.core.node.config.NodeConfig;
import io.vertigo.core.plugins.resource.classpath.ClassPathResourceResolverPlugin;
import io.vertigo.datamodel.plugins.environment.ModelDefinitionProvider;
import io.vertigo.datastore.DataStoreFeatures;
import io.vertigo.quarto.QuartoFeatures;
import io.vertigo.samples.quarto.services.ThemeProvider;

public final class SampleQuartoConfigBuilder {

	public static NodeConfig config() {
		final String locales = "fr_FR";
		// @formatter:off
		return  NodeConfig.builder()
				.beginBoot()
				.addPlugin(ClassPathResourceResolverPlugin.class)
				.withLocales(locales)
				.endBoot()
				.addModule(new CommonsFeatures()
						.withScript()
						.withJaninoScript()
						.build())
				.addModule(new DataStoreFeatures().build())
				.addModule(new QuartoFeatures()
						.withPublisher()
						.withDOCXPublisher()
						.build())
				//-----Declaration of a module named 'Vega' which contains a webservice component.
				.addModule( ModuleConfig.builder("Samples")
					.addDefinitionProvider( DefinitionProviderConfig.builder(QuartoDefinitionProvider.class).build())
					.addDefinitionProvider(DefinitionProviderConfig.builder(ModelDefinitionProvider.class)
							.addDefinitionResource("classes", "io.vertigo.samples.quarto.domain*")
							.build())
					.addDefinitionProvider(PublisherDefinitionProvider.class)
					.addComponent(ThemeProvider.class)
					.build())
				.build();
		// @formatter:on
	}
}
