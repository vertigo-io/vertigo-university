package io.vertigo.samples.dao.config;

import io.vertigo.commons.CommonsFeatures;
import io.vertigo.core.node.config.BootConfig;
import io.vertigo.core.node.config.DefinitionProviderConfig;
import io.vertigo.core.node.config.ModuleConfig;
import io.vertigo.core.node.config.NodeConfig;
import io.vertigo.core.node.config.NodeConfigBuilder;
import io.vertigo.core.param.Param;
import io.vertigo.core.plugins.resource.classpath.ClassPathResourceResolverPlugin;
import io.vertigo.database.DatabaseFeatures;
import io.vertigo.database.impl.sql.vendor.h2.H2DataBase;
import io.vertigo.datamodel.DataModelFeatures;
import io.vertigo.datamodel.impl.smarttype.ModelDefinitionProvider;
import io.vertigo.datastore.DataStoreFeatures;
import io.vertigo.samples.dao.aspect.SupervisionAspect;
import io.vertigo.samples.dao.boot.DataBaseInitializer;
import io.vertigo.samples.dao.domain.DtDefinitions;

public class SampleConfigBuilder {

	/**
	 * Two in-memory H2 databases, created at each start by the DataBaseInitializer :
	 * the main one holds the demo data (movies, actors, roles, countries), the "mine" one is the target of the Reprise.
	 * @return the node config builder of the sample
	 */
	public static NodeConfigBuilder createNodeConfigBuilder() {
		// @formatter:off
				return NodeConfig.builder()
						.withBoot(BootConfig.builder()
							.withLocales("fr_FR")
							.addPlugin(ClassPathResourceResolverPlugin.class)
						.build())
						.addModule(new CommonsFeatures()
								.withScript()
								.withJaninoScript()
								.build())
						.addModule(new DatabaseFeatures()
								.withSqlDataBase()
								.withC3p0(
										Param.of("dataBaseClass", H2DataBase.class.getName()),
										Param.of("jdbcDriver", org.h2.Driver.class.getName()),
										Param.of("jdbcUrl", "jdbc:h2:mem:formation_loaded;DB_CLOSE_DELAY=-1;NON_KEYWORDS=YEAR,KEY,USER"))
								.withC3p0(
										Param.of("name", "mine"),
										Param.of("dataBaseClass", H2DataBase.class.getName()),
										Param.of("jdbcDriver", org.h2.Driver.class.getName()),
										Param.of("jdbcUrl", "jdbc:h2:mem:formation_mine;DB_CLOSE_DELAY=-1;NON_KEYWORDS=YEAR,KEY,USER"))
								.build())
						.addModule(new DataModelFeatures().build())
						.addModule(new DataStoreFeatures()
								.withCache()
								.withMemoryCache()
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
										.addDefinitionResource("smarttypes", SampleDaoSmartTypes.class.getCanonicalName())
										.addDefinitionResource("dtobjects", DtDefinitions.class.getCanonicalName())
										.build())
								.build())
						.addModule(ModuleConfig.builder("aspect")
							.addAspect(SupervisionAspect.class)
							.build())
						.addInitializer(DataBaseInitializer.class);
		// @formatter:on
	}
}
