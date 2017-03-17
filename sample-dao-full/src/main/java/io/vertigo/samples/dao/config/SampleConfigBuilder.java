package io.vertigo.samples.dao.config;

import io.vertigo.app.config.AppConfigBuilder;
import io.vertigo.app.config.DefinitionProviderConfigBuilder;
import io.vertigo.app.config.ModuleConfigBuilder;
import io.vertigo.commons.impl.CommonsFeatures;
import io.vertigo.commons.plugins.cache.memory.MemoryCachePlugin;
import io.vertigo.core.param.Param;
import io.vertigo.core.plugins.resource.classpath.ClassPathResourceResolverPlugin;
import io.vertigo.dynamo.impl.DynamoFeatures;
import io.vertigo.dynamo.impl.database.vendor.h2.H2DataBase;
import io.vertigo.dynamo.plugins.database.connection.c3p0.C3p0ConnectionProviderPlugin;
import io.vertigo.dynamo.plugins.environment.DynamoDefinitionProvider;
import io.vertigo.dynamo.plugins.store.datastore.sql.SqlDataStorePlugin;
import io.vertigo.samples.dao.aspect.SupervisionAspect;
import io.vertigo.samples.dao.boot.DataBaseInitializer;

public class SampleConfigBuilder {
	public static AppConfigBuilder createAppConfigBuilder() {
		// @formatter:off
				return createAppConfigBuilderWithoutCrebase()
					.addInitializer(DataBaseInitializer.class);
		// @formatter:on
	}

	public static AppConfigBuilder createAppConfigBuilderWithoutCrebase() {
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
						.addModule(new DynamoFeatures()
								.withStore()
								.withSqlDataBase()
								.addSqlConnectionProviderPlugin(C3p0ConnectionProviderPlugin.class,
										Param.create("dataBaseClass", H2DataBase.class.getName()),
										Param.create("jdbcDriver", org.h2.Driver.class.getName()),
										Param.create("jdbcUrl", "jdbc:h2:D:/atelier/database/formation_loaded"))
								.addSqlConnectionProviderPlugin(C3p0ConnectionProviderPlugin.class,
										Param.create("dataBaseClass", H2DataBase.class.getName()),
										Param.create("jdbcDriver", org.h2.Driver.class.getName()),
										Param.create("jdbcUrl", "jdbc:h2:D:/atelier/database/formation_mine"))
								.addDataStorePlugin(SqlDataStorePlugin.class,
										Param.create("sequencePrefix","SEQ_"))
								.addDataStorePlugin(SqlDataStorePlugin.class,
										Param.create("name","mine"),
										Param.create("connectionName","mine"),
										Param.create("sequencePrefix","SEQ_"))
								.build())
						//----Definitions
						.addModule(new ModuleConfigBuilder("ressources")
								.addDefinitionProvider(new DefinitionProviderConfigBuilder(DynamoDefinitionProvider.class)
										.addDefinitionResource("kpr", "application.kpr")
										.build())
								.build())
						.addModule(new ModuleConfigBuilder("aspect")
							.addAspect(SupervisionAspect.class)
							.build());
		// @formatter:on
	}

	public static AppConfigBuilder createAppConfigBuilderRemoteDb() {
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
					.addModule(new DynamoFeatures()
							.withStore()
							.withSqlDataBase()
							.addSqlConnectionProviderPlugin(C3p0ConnectionProviderPlugin.class,
									Param.create("dataBaseClass", H2DataBase.class.getName()),
									Param.create("jdbcDriver", org.h2.Driver.class.getName()),
									Param.create("jdbcUrl", "jdbc:postgresql://localhost:5432/formation?user=formation&password=formation"))
							.addSqlConnectionProviderPlugin(C3p0ConnectionProviderPlugin.class,
									Param.create("dataBaseClass", H2DataBase.class.getName()),
									Param.create("jdbcDriver", org.h2.Driver.class.getName()),
									Param.create("jdbcUrl", "jdbc:h2:D:/atelier/database/formation_mine"))
							.addDataStorePlugin(SqlDataStorePlugin.class,
									Param.create("sequencePrefix", "SEQ_"))
							.addDataStorePlugin(SqlDataStorePlugin.class,
									Param.create("name", "mine"),
									Param.create("connectionName", "mine"),
									Param.create("sequencePrefix", "SEQ_"))
							.build())
					//----Definitions
					.addModule(new ModuleConfigBuilder("ressources")
							.addDefinitionProvider(new DefinitionProviderConfigBuilder(DynamoDefinitionProvider.class)
									.addDefinitionResource("kpr", "application.kpr")
									.build())
							.build());
		// @formatter:on

	}
}
