package io.vertigo.samples.crystal.config;

import io.vertigo.app.config.AppConfig;
import io.vertigo.app.config.AppConfigBuilder;
import io.vertigo.app.config.DefinitionProviderConfig;
import io.vertigo.app.config.ModuleConfig;
import io.vertigo.commons.impl.CommonsFeatures;
import io.vertigo.commons.plugins.cache.memory.MemoryCachePlugin;
import io.vertigo.core.param.Param;
import io.vertigo.core.plugins.resource.classpath.ClassPathResourceResolverPlugin;
import io.vertigo.core.plugins.resource.local.LocalResourceResolverPlugin;
import io.vertigo.database.DatabaseFeatures;
import io.vertigo.database.impl.sql.vendor.h2.H2DataBase;
import io.vertigo.database.plugins.sql.connection.c3p0.C3p0ConnectionProviderPlugin;
import io.vertigo.dynamo.impl.DynamoFeatures;
import io.vertigo.dynamo.plugins.environment.DynamoDefinitionProvider;
import io.vertigo.dynamo.plugins.search.elasticsearch.embedded.ESEmbeddedSearchServicesPlugin;
import io.vertigo.dynamo.plugins.store.datastore.sql.SqlDataStorePlugin;
import io.vertigo.vega.VegaFeatures;

public class SampleConfigBuilder {

	public static AppConfigBuilder createAppConfigBuilder(final boolean withSearch, final boolean withVega) {
		final DynamoFeatures dynamoFeatures = new DynamoFeatures()
				.withStore()
				.addDataStorePlugin(SqlDataStorePlugin.class,
						Param.of("sequencePrefix", "SEQ_"));
		if (withSearch) {
			dynamoFeatures.withSearch(ESEmbeddedSearchServicesPlugin.class,
					Param.of("home", "D:/atelier/search"), //usage d'url impropre
					Param.of("envIndex", "crystal-test"),
					Param.of("rowsPerQuery", "50"),
					Param.of("config.file", "elasticsearch.yml"));
		}

		final AppConfigBuilder appConfigBuilder = AppConfig.builder()
				.beginBoot()
				.withLocales("fr_FR")
				.addPlugin(ClassPathResourceResolverPlugin.class)
				.addPlugin(LocalResourceResolverPlugin.class)
				.endBoot()
				.addModule(new CommonsFeatures()
						.withCache(MemoryCachePlugin.class)
						.withScript()
						.build())
				.addModule(new DatabaseFeatures()
						.withSqlDataBase()
						.addSqlConnectionProviderPlugin(C3p0ConnectionProviderPlugin.class,
								Param.of("dataBaseClass", H2DataBase.class.getName()),
								Param.of("jdbcDriver", org.h2.Driver.class.getName()),
								Param.of("jdbcUrl", "jdbc:h2:D:/atelier/database/formation_loaded"))
						.build())
				.addModule(dynamoFeatures.build())
				.addModule(ModuleConfig.builder("ressources")
						.addDefinitionProvider(DefinitionProviderConfig.builder(DynamoDefinitionProvider.class)
								.addDefinitionResource("kpr", "application.kpr")
								.build())
						.build());

		if (withVega) {
			appConfigBuilder.addModule(new VegaFeatures()
					.withEmbeddedServer(8081)
					.build());
		}

		return appConfigBuilder;
	}

}
