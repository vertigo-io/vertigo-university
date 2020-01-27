package io.vertigo.samples.dao.config;

import io.vertigo.commons.CommonsFeatures;
import io.vertigo.core.node.config.DefinitionProviderConfig;
import io.vertigo.core.node.config.ModuleConfig;
import io.vertigo.core.node.config.NodeConfig;
import io.vertigo.core.node.config.NodeConfigBuilder;
import io.vertigo.core.param.Param;
import io.vertigo.core.plugins.resource.classpath.ClassPathResourceResolverPlugin;
import io.vertigo.database.DatabaseFeatures;
import io.vertigo.database.impl.sql.vendor.h2.H2DataBase;
import io.vertigo.datamodel.DataModelFeatures;
import io.vertigo.datamodel.plugins.environment.ModelDefinitionProvider;
import io.vertigo.datastore.DataStoreFeatures;
import io.vertigo.samples.dao.aspect.SupervisionAspect;
import io.vertigo.samples.dao.boot.DataBaseInitializer;

public class SampleConfigBuilder {
	public static NodeConfigBuilder createNodeConfigBuilder() {
		// @formatter:off
				return createNodeConfigBuilderWithoutCrebase()
					.addInitializer(DataBaseInitializer.class);
		// @formatter:on
	}

	public static NodeConfigBuilder createNodeConfigBuilderWithoutCrebase() {
		// @formatter:off
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
						.addModule(new DatabaseFeatures()
								.withSqlDataBase()
								.withC3p0(
										Param.of("dataBaseClass", H2DataBase.class.getName()),
										Param.of("jdbcDriver", org.h2.Driver.class.getName()),
										Param.of("jdbcUrl", "jdbc:h2:D:/atelier/database/formation_loaded"))
								.withC3p0(
										Param.of("name", "mine"),
										Param.of("dataBaseClass", H2DataBase.class.getName()),
										Param.of("jdbcDriver", org.h2.Driver.class.getName()),
										Param.of("jdbcUrl", "jdbc:h2:D:/atelier/database/formation_mine"))
								.build())
						.addModule(new DataModelFeatures().build())
						.addModule(new DataStoreFeatures()
								.withEntityStore()
								.withSqlEntityStore()
								.withSqlEntityStore(
										Param.of("dataSpace","mine"),
										Param.of("connectionName","mine"),
										Param.of("sequencePrefix","SEQ_"))
								.build())
						//----Definitions
						.addModule( ModuleConfig.builder("ressources")
								.addDefinitionProvider( DefinitionProviderConfig.builder(ModelDefinitionProvider.class)
										.addDefinitionResource("kpr", "model.kpr")
										.addDefinitionResource("kpr", "task.kpr")
										.build())
								.build())
						.addModule(ModuleConfig.builder("aspect")
							.addAspect(SupervisionAspect.class)
							.build());
		// @formatter:on
	}

	public static NodeConfigBuilder createNodeConfigBuilderRemoteDb() {
		// @formatter:off
			return  NodeConfig.builder()
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
					.addModule(new DatabaseFeatures()
							.withSqlDataBase()
							.withC3p0(
									Param.of("dataBaseClass", H2DataBase.class.getName()),
									Param.of("jdbcDriver", org.h2.Driver.class.getName()),
									Param.of("jdbcUrl", "jdbc:postgresql://localhost:5432/formation?user=formation&password=formation"))
							.withC3p0(
									Param.of("dataBaseClass", H2DataBase.class.getName()),
									Param.of("jdbcDriver", org.h2.Driver.class.getName()),
									Param.of("jdbcUrl", "jdbc:h2:D:/atelier/database/formation_mine"))
							.build())
					.addModule(new DataStoreFeatures()
							.withEntityStore()
							.withSqlEntityStore()
							.withSqlEntityStore(
									Param.of("name", "mine"),
									Param.of("connectionName", "mine"),
									Param.of("sequencePrefix", "SEQ_"))
							.build())
					//----Definitions
					.addModule( ModuleConfig.builder("ressources")
							.addDefinitionProvider( DefinitionProviderConfig.builder(ModelDefinitionProvider.class)
									.addDefinitionResource("kpr", "application.kpr")
									.build())
							.build());
		// @formatter:on

	}
}
