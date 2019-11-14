package io.vertigo.samples.crystal.config;

import io.vertigo.core.node.config.DefinitionProviderConfig;
import io.vertigo.core.node.config.ModuleConfig;
import io.vertigo.core.node.config.NodeConfig;
import io.vertigo.core.param.Param;
import io.vertigo.core.plugins.resource.classpath.ClassPathResourceResolverPlugin;
import io.vertigo.dynamo.plugins.environment.DynamoDefinitionProvider;
import io.vertigo.studio.StudioFeatures;

public class SampleStudioConfigBuilder {
	public NodeConfig build() {
		return NodeConfig.builder()
		// @formatter:off
				.beginBoot()
					.withLocales("fr_FR")
					.addPlugin(ClassPathResourceResolverPlugin.class)
				.endBoot()
				// ---StudioFeature
				.addModule(new StudioFeatures()
						.withMasterData()
						.withMda(
								Param.of("projectPackageName", "io.vertigo.samples.crystal"))
						.withJavaDomainGenerator(
								Param.of("generateDtResources", "false"))
						.withTaskGenerator()
						.withSearchGenerator()
						.withAuthorizationGenerator()
						.withSqlDomainGenerator(
								Param.of("targetSubDir", "javagen"),
								Param.of("generateDrop", "false"),
								Param.of("baseCible", "H2"))
						.withFileGenerator()
						.build())
				//----Definitions
				.addModule(ModuleConfig.builder("ressources")
						.addDefinitionProvider(DefinitionProviderConfig.builder(DynamoDefinitionProvider.class)
								.addDefinitionResource("kpr", "application.kpr")
								.build())
						.build())
				.build();
		// @formatter:on

	}

}
