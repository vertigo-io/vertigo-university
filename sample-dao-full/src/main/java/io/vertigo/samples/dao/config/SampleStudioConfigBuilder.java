package io.vertigo.samples.dao.config;

import io.vertigo.app.config.AppConfig;
import io.vertigo.app.config.AppConfigBuilder;
import io.vertigo.app.config.DefinitionProviderConfigBuilder;
import io.vertigo.app.config.ModuleConfigBuilder;
import io.vertigo.commons.impl.CommonsFeatures;
import io.vertigo.commons.plugins.cache.memory.MemoryCachePlugin;
import io.vertigo.core.param.Param;
import io.vertigo.core.plugins.resource.classpath.ClassPathResourceResolverPlugin;
import io.vertigo.dynamo.impl.DynamoFeatures;
import io.vertigo.dynamo.plugins.environment.DynamoDefinitionProvider;
import io.vertigo.studio.impl.mda.MdaManagerImpl;
import io.vertigo.studio.mda.MdaManager;
import io.vertigo.studio.plugins.mda.domain.DomainGeneratorPlugin;
import io.vertigo.studio.plugins.mda.domain.SqlGeneratorPlugin;
import io.vertigo.studio.plugins.mda.file.FileInfoGeneratorPlugin;
import io.vertigo.studio.plugins.mda.task.TaskGeneratorPlugin;

public class SampleStudioConfigBuilder {
	public AppConfig build() {
		// @formatter:off
		return new AppConfigBuilder()
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
				.addModule(new ModuleConfigBuilder("ressources")
						.addDefinitionProvider(new DefinitionProviderConfigBuilder(DynamoDefinitionProvider.class)
								.addDefinitionResource("kpr", "application.kpr")
								.build())
						.build())
				// ---StudioFeature
				.addModule(new ModuleConfigBuilder("studio")
					.addComponent(MdaManager.class, MdaManagerImpl.class,
							Param.create("targetGenDir", "src/main/javagen/"),
							Param.create("encoding", "UTF-8"),
							Param.create("projectPackageName", "io.vertigo.samples.dao"))

					.addPlugin(DomainGeneratorPlugin.class,
							Param.create("targetSubDir", "."),
							Param.create("generateDtResources", "false"),
							Param.create("generateDtDefinitions", "true"),
							Param.create("generateDtObject", "true"),
							Param.create("generateJpaAnnotations", "false"))
					.addPlugin(TaskGeneratorPlugin.class,
						Param.create("targetSubDir", "."))

					.addPlugin(FileInfoGeneratorPlugin.class,
							Param.create("targetSubDir", "."))
					.addPlugin(SqlGeneratorPlugin.class,
							Param.create("targetSubDir", "sqlgen"),
							Param.create("baseCible", "PostgreSql"),
							Param.create("generateDrop", "false"))
					.build())
				.build();
		// @formatter:on

	}

}
