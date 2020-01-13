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
import io.vertigo.datastore.DataStoreFeatures;
import io.vertigo.dynamo.DataModelFeatures;
import io.vertigo.dynamo.plugins.environment.ModelDefinitionProvider;
import io.vertigo.samples.dao.aspect.SupervisionAspect;

public class SampleConfigBuilder {
	public static NodeConfigBuilder createNodeConfigBuilder() {
		// @formatter:off
				return createNodeConfigBuilderCore();
		// @formatter:on
	}

	public static NodeConfigBuilder createNodeConfigBuilderCore() {
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
						.build())
				.addModule(new DataModelFeatures().build())
				.addModule(new DataStoreFeatures()
					.withEntityStore()
					.withSqlEntityStore()
					.build())
				.addModule(ModuleConfig.builder("ressources")
						.addDefinitionProvider(DefinitionProviderConfig.builder(ModelDefinitionProvider.class)
								.addDefinitionResource("kpr", "application.kpr")
								.build())
						.build())
				.addModule(ModuleConfig.builder("aspect")
						.addAspect(SupervisionAspect.class)
						.build());
		// @formatter:on
	}

}
