package io.vertigo.samples.crystal.config;

import io.vertigo.commons.CommonsFeatures;
import io.vertigo.connectors.elasticsearch.ElasticSearchFeatures;
import io.vertigo.core.node.config.DefinitionProviderConfig;
import io.vertigo.core.node.config.ModuleConfig;
import io.vertigo.core.node.config.NodeConfig;
import io.vertigo.core.node.config.NodeConfigBuilder;
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

		final NodeConfigBuilder nodeConfigBuilder = NodeConfig.builder()
				.beginBoot()
				.withLocales("fr_FR")
				.addPlugin(ClassPathResourceResolverPlugin.class)
				.addPlugin(LocalResourceResolverPlugin.class)
				.endBoot();

		if (withSearch) {
			nodeConfigBuilder.addModule(new ElasticSearchFeatures()
					.withEmbedded(Param.of("home", "D:/atelier/search"))//usage d'url impropre
					.build());
			dynamoFeatures
					.withSearch()
					.withES(
							Param.of("envIndex", "CrystalTest"),
							Param.of("rowsPerQuery", "50"),
							Param.of("config.file", "elasticsearch.yml"));
		}

		nodeConfigBuilder.addModule(new CommonsFeatures()
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
