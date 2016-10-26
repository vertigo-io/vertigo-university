package io.vertigo.samples.dao.config;

import io.vertigo.app.config.AppConfigBuilder;
import io.vertigo.commons.impl.CommonsFeatures;
import io.vertigo.commons.plugins.cache.memory.MemoryCachePlugin;
import io.vertigo.core.plugins.resource.classpath.ClassPathResourceResolverPlugin;
import io.vertigo.dynamo.database.SqlDataBaseManager;
import io.vertigo.dynamo.impl.DynamoFeatures;
import io.vertigo.dynamo.impl.database.SqlDataBaseManagerImpl;
import io.vertigo.dynamo.impl.database.vendor.h2.H2Database;
import io.vertigo.dynamo.impl.database.vendor.postgresql.PostgreSqlDataBase;
import io.vertigo.dynamo.plugins.database.connection.c3p0.C3p0ConnectionProviderPlugin;
import io.vertigo.dynamo.plugins.environment.loaders.java.AnnotationLoaderPlugin;
import io.vertigo.dynamo.plugins.environment.loaders.kpr.KprLoaderPlugin;
import io.vertigo.dynamo.plugins.environment.registries.domain.DomainDynamicRegistryPlugin;
import io.vertigo.dynamo.plugins.environment.registries.task.TaskDynamicRegistryPlugin;
import io.vertigo.dynamo.plugins.store.datastore.postgresql.PostgreSqlDataStorePlugin;
import io.vertigo.samples.dao.boot.DataBaseInitializer;

public class SampleConfigBuilder {
	public static AppConfigBuilder createAppConfigBuilder() {
		// @formatter:off
				return new AppConfigBuilder().beginBootModule("fr_FR")
						.addPlugin(ClassPathResourceResolverPlugin.class)
						.addPlugin(KprLoaderPlugin.class)
						.addPlugin(AnnotationLoaderPlugin.class)
						.addPlugin(DomainDynamicRegistryPlugin.class)
						.addPlugin(TaskDynamicRegistryPlugin.class)
					.endModule()
					.beginBoot()
						.silently()
					.endBoot()
					.beginModule(CommonsFeatures.class)
						.withCache(MemoryCachePlugin.class)
						.withScript()
					.endModule()
					.beginModule(DynamoFeatures.class)
						.withStore()
						.getModuleConfigBuilder()
						.addComponent(SqlDataBaseManager.class, SqlDataBaseManagerImpl.class)
						.beginPlugin(C3p0ConnectionProviderPlugin.class)
							.addParam("dataBaseClass", H2Database.class.getName())
							.addParam("jdbcDriver", org.h2.Driver.class.getName())
							.addParam("jdbcUrl", "jdbc:h2:mem:database")
						.endPlugin()
						.beginPlugin(PostgreSqlDataStorePlugin.class)
							.addParam("sequencePrefix","SEQ_")
						.endPlugin()
					.endModule()
					//----Definitions
					.beginModule("ressources")
						.addDefinitionResource("kpr", "application.kpr")
					.endModule()
					.addInitializer(DataBaseInitializer.class);
		// @formatter:on
	}

	public static AppConfigBuilder createAppConfigBuilderRemoteDb() {
		// @formatter:off
				return new AppConfigBuilder().beginBootModule("fr_FR")
						.addPlugin(ClassPathResourceResolverPlugin.class)
						.addPlugin(KprLoaderPlugin.class)
						.addPlugin(AnnotationLoaderPlugin.class)
						.addPlugin(DomainDynamicRegistryPlugin.class)
						.addPlugin(TaskDynamicRegistryPlugin.class)
					.endModule()
					.beginBoot()
						.silently()
					.endBoot()
					.beginModule(CommonsFeatures.class)
						.withCache(MemoryCachePlugin.class)
						.withScript()
					.endModule()
					.beginModule(DynamoFeatures.class)
						.withStore()
						.getModuleConfigBuilder()
						.addComponent(SqlDataBaseManager.class, SqlDataBaseManagerImpl.class)
						.beginPlugin(C3p0ConnectionProviderPlugin.class)
							.addParam("dataBaseClass", PostgreSqlDataBase.class.getName())
							.addParam("jdbcDriver", org.postgresql.Driver.class.getName())
							.addParam("jdbcUrl", "jdbc:postgresql://localhost:5432/formation?user=formation&password=formation")
						.endPlugin()
						.beginPlugin(PostgreSqlDataStorePlugin.class)
							.addParam("sequencePrefix","SEQ_")
						.endPlugin()
					.endModule()
					//----Definitions
					.beginModule("ressources")
						.addDefinitionResource("kpr", "application.kpr")
					.endModule();
		// @formatter:on
	}
}
