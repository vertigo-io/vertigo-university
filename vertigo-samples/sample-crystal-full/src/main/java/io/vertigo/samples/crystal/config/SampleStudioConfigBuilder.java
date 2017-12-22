package io.vertigo.samples.crystal.config;

import io.vertigo.account.plugins.authorization.loaders.JsonSecurityDefinitionProvider;
import io.vertigo.app.config.AppConfig;
import io.vertigo.app.config.DefinitionProviderConfig;
import io.vertigo.app.config.ModuleConfig;
import io.vertigo.commons.impl.CommonsFeatures;
import io.vertigo.commons.plugins.cache.memory.MemoryCachePlugin;
import io.vertigo.core.param.Param;
import io.vertigo.core.plugins.resource.classpath.ClassPathResourceResolverPlugin;
import io.vertigo.dynamo.impl.DynamoFeatures;
import io.vertigo.dynamo.plugins.environment.DynamoDefinitionProvider;
import io.vertigo.studio.impl.masterdata.MasterDataManagerImpl;
import io.vertigo.studio.impl.mda.MdaManagerImpl;
import io.vertigo.studio.masterdata.MasterDataManager;
import io.vertigo.studio.mda.MdaManager;
import io.vertigo.studio.plugins.masterdata.json.JsonMasterDataValueProvider;
import io.vertigo.studio.plugins.mda.authorization.AuthorizationGeneratorPlugin;
import io.vertigo.studio.plugins.mda.domain.java.DomainGeneratorPlugin;
import io.vertigo.studio.plugins.mda.domain.sql.SqlGeneratorPlugin;
import io.vertigo.studio.plugins.mda.file.FileInfoGeneratorPlugin;
import io.vertigo.studio.plugins.mda.task.TaskGeneratorPlugin;

public class SampleStudioConfigBuilder {

	public AppConfig build() {
		return AppConfig.builder()
				.beginBoot()
				.withLocales("fr_FR")
				.addPlugin(ClassPathResourceResolverPlugin.class)
				.endBoot()
				.addModule(new CommonsFeatures()
						.withCache(MemoryCachePlugin.class)
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
				.addModule(ModuleConfig.builder("studio")
						.addComponent(MdaManager.class, MdaManagerImpl.class,
								Param.of("targetGenDir", "src/main/javagen/"),
								Param.of("encoding", "UTF-8"),
								Param.of("projectPackageName", "io.vertigo.samples.crystal"))

						.addPlugin(DomainGeneratorPlugin.class,
								Param.of("targetSubDir", "."),
								Param.of("generateDtResources", "false"),
								Param.of("generateDtDefinitions", "true"),
								Param.of("generateDtObject", "true"),
								Param.of("generateJpaAnnotations", "false"))
						.addPlugin(TaskGeneratorPlugin.class,
								Param.of("targetSubDir", "."))
						.addPlugin(AuthorizationGeneratorPlugin.class,
								Param.of("targetSubDir", "."))
						.addPlugin(FileInfoGeneratorPlugin.class,
								Param.of("targetSubDir", "."))
						.addPlugin(SqlGeneratorPlugin.class,
								Param.of("targetSubDir", "sqlgen"),
								Param.of("baseCible", "PostgreSql"),
								Param.of("generateDrop", "false"),
								//------ StaticMasterData-------
								Param.of("generateMasterData", "true"))

						.addComponent(MasterDataManager.class, MasterDataManagerImpl.class)
						.addPlugin(JsonMasterDataValueProvider.class,
								Param.of("fileName", "masterDataValues.json"))
						//level6
						.addDefinitionProvider(DefinitionProviderConfig.builder(JsonSecurityDefinitionProvider.class)
								.addDefinitionResource("security", "auth-config.json")
								.build())
						.build())
				.build();
	}

}
