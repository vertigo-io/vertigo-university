package io.vertigo.samples.crystal.config;

import io.vertigo.app.config.NodeConfig;
import io.vertigo.app.config.NodeConfigBuilder;
import io.vertigo.app.config.DefinitionProviderConfig;
import io.vertigo.app.config.ModuleConfig;
import io.vertigo.commons.CommonsFeatures;
import io.vertigo.core.param.Param;
import io.vertigo.core.plugins.resource.classpath.ClassPathResourceResolverPlugin;
import io.vertigo.core.plugins.resource.local.LocalResourceResolverPlugin;
import io.vertigo.database.DatabaseFeatures;
import io.vertigo.database.impl.sql.vendor.h2.H2DataBase;
import io.vertigo.dynamo.DynamoFeatures;
import io.vertigo.dynamo.plugins.environment.DynamoDefinitionProvider;
import io.vertigo.vega.VegaFeatures;

public class SampleConfigBuilder {

	public static NodeConfigBuilder createNodeConfigBuilder(final boolean withSearch, final boolean withVega) {
		final DynamoFeatures dynamoFeatures = new DynamoFeatures()
				.withStore()
				.withSqlStore();
		if (withSearch) {
			dynamoFeatures.withSearch()
					.withESEmbedded(
							Param.of("home", "D:/atelier/search"), //usage d'url impropre
							Param.of("envIndex", "crystal-test"),
							Param.of("rowsPerQuery", "50"),
							Param.of("config.file", "elasticsearch.yml"));
		}

		final NodeConfigBuilder nodeConfigBuilder = NodeConfig.builder()
				.beginBoot()
				.withLocales("fr_FR")
				.addPlugin(ClassPathResourceResolverPlugin.class)
				.addPlugin(LocalResourceResolverPlugin.class)
				.endBoot()
				.addModule(new CommonsFeatures()
						.withCache()
						.withMemoryCache()
						.withScript()
						.withJaninoScript()
						.build())
				.addModule(new DatabaseFeatures()
						.withSqlDataBase()
						.withC3p0(
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
			nodeConfigBuilder.addModule(new VegaFeatures()
					.withWebServices()
					.withWebServicesEmbeddedServer(Param.of("port", "8081"))
					.build());
		}

		return nodeConfigBuilder;
	}

}
