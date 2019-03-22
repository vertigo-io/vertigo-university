package io.vertigo.samples.account.config;

import io.vertigo.account.plugins.authorization.loaders.JsonSecurityDefinitionProvider;
import io.vertigo.app.config.NodeConfig;
import io.vertigo.app.config.DefinitionProviderConfig;
import io.vertigo.app.config.ModuleConfig;
import io.vertigo.core.param.Param;
import io.vertigo.core.plugins.resource.classpath.ClassPathResourceResolverPlugin;
import io.vertigo.dynamo.plugins.environment.DynamoDefinitionProvider;
import io.vertigo.studio.StudioFeatures;

public class SampleStudioConfigBuilder {

	public NodeConfig build() {
		return NodeConfig.builder()
				.beginBoot()
				.withLocales("fr_FR")
				.addPlugin(ClassPathResourceResolverPlugin.class)
				.endBoot()
				// ---StudioFeature
				.addModule(new StudioFeatures()
						.withMasterData()
						.withMda(
								Param.of("projectPackageName", "io.vertigo.samples.account"))
						.withJavaDomainGenerator(
								Param.of("generateDtResources", "false"))
						.withTaskGenerator()
						.withAuthorizationGenerator()
						.withSqlDomainGenerator(
								Param.of("targetSubDir", "javagen/sqlgen"),
								Param.of("generateDrop", "false"),
								Param.of("baseCible", "H2"))
						.withFileGenerator()
						.build())
				//----Definitions
				.addModule(ModuleConfig.builder("myApp")
						.addDefinitionProvider(DefinitionProviderConfig.builder(DynamoDefinitionProvider.class)
								.addDefinitionResource("kpr", "application.kpr")
								.build())
						.addDefinitionProvider(DefinitionProviderConfig.builder(JsonSecurityDefinitionProvider.class)
								.addDefinitionResource("security", "auth-config.json")
								.build())
						.build())
				.build();
	}

}
