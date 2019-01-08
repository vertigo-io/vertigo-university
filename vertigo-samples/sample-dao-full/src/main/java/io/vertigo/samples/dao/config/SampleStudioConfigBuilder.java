package io.vertigo.samples.dao.config;

import io.vertigo.app.config.AppConfig;
import io.vertigo.app.config.DefinitionProviderConfig;
import io.vertigo.app.config.ModuleConfig;
import io.vertigo.commons.CommonsFeatures;
import io.vertigo.core.param.Param;
import io.vertigo.core.plugins.resource.classpath.ClassPathResourceResolverPlugin;
import io.vertigo.dynamo.DynamoFeatures;
import io.vertigo.dynamo.plugins.environment.DynamoDefinitionProvider;
import io.vertigo.studio.impl.masterdata.MasterDataManagerImpl;
import io.vertigo.studio.impl.mda.MdaManagerImpl;
import io.vertigo.studio.masterdata.MasterDataManager;
import io.vertigo.studio.mda.MdaManager;
import io.vertigo.studio.plugins.mda.domain.java.DomainGeneratorPlugin;
import io.vertigo.studio.plugins.mda.domain.sql.SqlGeneratorPlugin;
import io.vertigo.studio.plugins.mda.file.FileInfoGeneratorPlugin;
import io.vertigo.studio.plugins.mda.task.TaskGeneratorPlugin;

public class SampleStudioConfigBuilder {
	public AppConfig build() {
		// @formatter:off
		return  AppConfig.builder()
				.beginBoot()
				.withLocales("fr_FR")
				.addPlugin(ClassPathResourceResolverPlugin.class)
				.endBoot()
				.addModule(new CommonsFeatures()
						.withCache()
						.withMemoryCache()
						.withScript()
						.build())
				.addModule(new DynamoFeatures().build())
				//----Definitions
				.addModule(ModuleConfig.builder("ressources")
						.addDefinitionProvider(DefinitionProviderConfig.builder(DynamoDefinitionProvider.class)
								.addDefinitionResource("kpr", "application.kpr")
								.build())
						.build())
				// ---StudioFeature
				.addModule( ModuleConfig.builder("studio")
					.addComponent(MdaManager.class, MdaManagerImpl.class,
							Param.of("targetGenDir", "src/main/javagen/"),
							Param.of("encoding", "UTF-8"),
							Param.of("projectPackageName", "io.vertigo.samples.dao"))

					.addPlugin(DomainGeneratorPlugin.class,
							Param.of("targetSubDir", "."),
							Param.of("generateDtResources", "false"),
							Param.of("generateDtDefinitions", "true"),
							Param.of("generateDtObject", "true"),
							Param.of("generateJpaAnnotations", "false"))
					.addPlugin(TaskGeneratorPlugin.class,
						Param.of("targetSubDir", "."))

					.addPlugin(FileInfoGeneratorPlugin.class,
							Param.of("targetSubDir", "."))
					.addPlugin(SqlGeneratorPlugin.class,
							Param.of("targetSubDir", "sqlgen"),
							Param.of("baseCible", "PostgreSql"),
							Param.of("generateDrop", "false"))
					.addComponent(MasterDataManager.class, MasterDataManagerImpl.class)
					.build())
				.build();
		// @formatter:on

	}

}
