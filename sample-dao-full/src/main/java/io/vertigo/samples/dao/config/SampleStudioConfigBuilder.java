package io.vertigo.samples.dao.config;

import io.vertigo.app.config.AppConfig;
import io.vertigo.app.config.AppConfigBuilder;
import io.vertigo.core.plugins.resource.classpath.ClassPathResourceResolverPlugin;
import io.vertigo.core.plugins.resource.url.URLResourceResolverPlugin;
import io.vertigo.dynamo.collections.CollectionsManager;
import io.vertigo.dynamo.impl.collections.CollectionsManagerImpl;
import io.vertigo.dynamo.plugins.environment.loaders.kpr.KprLoaderPlugin;
import io.vertigo.dynamo.plugins.environment.registries.domain.DomainDynamicRegistryPlugin;
import io.vertigo.dynamo.plugins.environment.registries.file.FileDynamicRegistryPlugin;
import io.vertigo.dynamo.plugins.environment.registries.task.TaskDynamicRegistryPlugin;
import io.vertigo.studio.impl.mda.MdaManagerImpl;
import io.vertigo.studio.mda.MdaManager;
import io.vertigo.studio.plugins.mda.domain.DomainGeneratorPlugin;
import io.vertigo.studio.plugins.mda.domain.SqlGeneratorPlugin;
import io.vertigo.studio.plugins.mda.file.FileInfoGeneratorPlugin;
import io.vertigo.studio.plugins.mda.task.TaskGeneratorPlugin;

public class SampleStudioConfigBuilder {
	public AppConfig build() {
		return new AppConfigBuilder()
			// @formatter:off
				.beginBootModule("fr_FR")
					.addPlugin(ClassPathResourceResolverPlugin.class)
					.addPlugin(URLResourceResolverPlugin.class)
					.beginPlugin(KprLoaderPlugin.class)
						.addParam("encoding", "UTF-8")
					.endPlugin()
					.addPlugin(DomainDynamicRegistryPlugin.class)
					.addPlugin(TaskDynamicRegistryPlugin.class)
					.addPlugin(FileDynamicRegistryPlugin.class)
				.endModule()
				.beginBoot()
					.silently()
				.endBoot()
				.beginModule("dynamo")
					.addComponent(CollectionsManager.class, CollectionsManagerImpl.class)
				.endModule()
				//----Definitions
				.beginModule("ressources")
					.addDefinitionResource("kpr", "application.kpr")
				.endModule()
				// ---StudioFeature
				.beginModule("studio")
					.beginComponent(MdaManager.class, MdaManagerImpl.class)
						.addParam("targetGenDir", "src/main/javagen/")
						.addParam("encoding", "UTF-8")
						.addParam("projectPackageName", "io.vertigo.samples.dao")
					.endComponent()
					.beginPlugin(DomainGeneratorPlugin.class)
						.addParam("targetSubDir", ".")
						.addParam("generateDtResources", "false")
						.addParam("generateDtDefinitions", "false")
						.addParam("generateDtObject", "true")
						.addParam("generateJpaAnnotations", "false")
					.endPlugin()
					.beginPlugin(TaskGeneratorPlugin.class)
						.addParam("targetSubDir", ".")
					.endPlugin()
					.beginPlugin(FileInfoGeneratorPlugin.class)
						.addParam("targetSubDir", ".")
					.endPlugin()
					.beginPlugin(SqlGeneratorPlugin.class)
						.addParam("targetSubDir", "sqlgen")
						.addParam("baseCible", "PostgreSql")
						.addParam("generateDrop", "false")
					.endPlugin()
				.endModule()
				.build();
		// @formatter:on

	}

}
