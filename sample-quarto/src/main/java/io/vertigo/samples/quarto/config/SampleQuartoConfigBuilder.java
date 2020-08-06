package io.vertigo.samples.quarto.config;

import io.vertigo.commons.CommonsFeatures;
import io.vertigo.core.node.config.BootConfig;
import io.vertigo.core.node.config.DefinitionProviderConfig;
import io.vertigo.core.node.config.ModuleConfig;
import io.vertigo.core.node.config.NodeConfig;
import io.vertigo.core.plugins.resource.classpath.ClassPathResourceResolverPlugin;
import io.vertigo.datamodel.DataModelFeatures;
import io.vertigo.datamodel.impl.smarttype.ModelDefinitionProvider;
import io.vertigo.datastore.DataStoreFeatures;
import io.vertigo.quarto.QuartoFeatures;
import io.vertigo.samples.quarto.services.ThemeProvider;

public final class SampleQuartoConfigBuilder {

	public static NodeConfig config() {
		final String locales = "fr_FR";
		return NodeConfig.builder()
				.withBoot(BootConfig.builder()
						.withLocales(locales)
						.addPlugin(ClassPathResourceResolverPlugin.class)
						.build())
				.addModule(new CommonsFeatures()
						.withScript()
						.withJaninoScript()
						.build())
				.addModule(new DataModelFeatures().build())
				.addModule(new DataStoreFeatures().build())
				.addModule(new QuartoFeatures()
						.withPublisher()
						.withDOCXPublisher()
						.build())
				//-----Declaration of a module named 'Vega' which contains a webservice component.
				.addModule(ModuleConfig.builder("Samples")
						.addDefinitionProvider(DefinitionProviderConfig.builder(ModelDefinitionProvider.class)
								.addDefinitionResource("smarttypes", SampleQuartoSmartTypes.class.getCanonicalName())
								.addDefinitionResource("dtobjects", "io.vertigo.samples.quarto.domain*")
								.build())
						.addDefinitionProvider(PublisherDefinitionProvider.class)
						.addComponent(ThemeProvider.class)
						.build())
				.build();
	}
}
