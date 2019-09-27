package io.mars.support.mda;

import javax.inject.Inject;

import io.vertigo.app.AutoCloseableApp;
import io.vertigo.app.config.DefinitionProviderConfig;
import io.vertigo.app.config.ModuleConfig;
import io.vertigo.app.config.NodeConfig;
import io.vertigo.commons.CommonsFeatures;
import io.vertigo.core.param.Param;
import io.vertigo.core.plugins.resource.classpath.ClassPathResourceResolverPlugin;
import io.vertigo.dynamo.DynamoFeatures;
import io.vertigo.dynamo.plugins.environment.DynamoDefinitionProvider;
import io.vertigo.studio.StudioFeatures;
import io.vertigo.studio.mda.MdaManager;
import io.vertigo.util.InjectorUtil;

public class Studio {

	private static NodeConfig buildNodeConfig() {
		return NodeConfig.builder()
				.beginBoot()
				.withLocales("fr_FR")
				.addPlugin(ClassPathResourceResolverPlugin.class)
				.endBoot()
				.addModule(new CommonsFeatures()
						.withCache()
						.withMemoryCache()
						.withScript()
						.withJaninoScript()
						.build())
				.addModule(new DynamoFeatures().build())
				//----Definitions
				.addModule(ModuleConfig.builder("ressources")
						.addDefinitionProvider(DefinitionProviderConfig.builder(DynamoDefinitionProvider.class)
								.addDefinitionResource("kpr", "io/mars/gen.kpr")
								.build())
						.build())
				// ---StudioFeature
				.addModule(new StudioFeatures()
						.withMasterData()
						.withMda(
								Param.of("projectPackageName", "io.mars"))
						.withJavaDomainGenerator(
								Param.of("generateDtResources", "false"))
						.withTaskGenerator()
						.withSearchGenerator()
						.withFileGenerator()
						.withSqlDomainGenerator(
								Param.of("targetSubDir", "javagen/sqlgen"),
								Param.of("baseCible", "H2"),
								Param.of("generateDrop", "true"),
								Param.of("generateMasterData", "true"))
						.withJsonMasterDataValuesProvider(
								Param.of("fileName", "io/mars/support/masterDataValues.json"))

						.build())
				.build();

	}

	@Inject
	private MdaManager mdaManager;

	public static void main(final String[] args) {
		try (final AutoCloseableApp app = new AutoCloseableApp(buildNodeConfig())) {
			final Studio sample = new Studio();
			InjectorUtil.injectMembers(sample);
			//-----
			sample.cleanGenerate();
		}
	}

	void cleanGenerate() {
		mdaManager.clean();
		mdaManager.generate().displayResultMessage(System.out);
	}
}
