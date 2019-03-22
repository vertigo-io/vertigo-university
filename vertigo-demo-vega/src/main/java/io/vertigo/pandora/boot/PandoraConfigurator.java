package io.vertigo.pandora.boot;

import io.vertigo.app.config.NodeConfig;
import io.vertigo.app.config.NodeConfigBuilder;
import io.vertigo.app.config.DefinitionProviderConfig;
import io.vertigo.app.config.LogConfig;
import io.vertigo.app.config.ModuleConfig;
import io.vertigo.core.param.Param;
import io.vertigo.core.plugins.resource.classpath.ClassPathResourceResolverPlugin;
import io.vertigo.core.plugins.resource.local.LocalResourceResolverPlugin;
import io.vertigo.dynamo.plugins.environment.DynamoDefinitionProvider;
import io.vertigo.studio.StudioFeatures;
import io.vertigo.studio.plugins.mda.webservice.WsTsGeneratorPlugin;

public final class PandoraConfigurator {
	public static NodeConfig config(final boolean activeStudio) {
		final String pandoraHome = System.getProperty("pandora.home", "d:/pandora");
		final int pandoraPort = Integer.parseInt(System.getProperty("pandora.port", "8080"));

		final NodeConfigBuilder nodeConfigBuilder = NodeConfig.builder()
				.beginBoot()
				.withLocales("fr")
				.addPlugin(ClassPathResourceResolverPlugin.class)
				.addPlugin(LocalResourceResolverPlugin.class)
				.withLogConfig(new LogConfig("/log4j.xml"))
				.endBoot();

		//		if (!activeStudio) {
		//			nodeConfigBuilder
		//					.addModule(new PersonaFeatures()
		//							.withUserSession(LollipopUserSession.class).build())
		//					.addModule(new CommonsFeatures()
		//							.withCache(MemoryCachePlugin.class)
		//							.withScript()
		//							.addAnalyticsConnectorPlugin(SocketLoggerAnalyticsConnectorPlugin.class)
		//							.build())
		//					.addModule(new DatabaseFeatures()
		//							.withSqlDataBase()
		//							.addSqlConnectionProviderPlugin(C3p0ConnectionProviderPlugin.class,
		//									Param.of("dataBaseClass", H2DataBase.class.getName()),
		//									Param.of("jdbcDriver", Driver.class.getName()),
		//									Param.of("jdbcUrl", "jdbc:h2:" + pandoraHome + "/data/demo"))
		//							.build())
		//					.addModule(new DynamoFeatures()
		//							.withStore()
		//							.addDataStorePlugin(SqlDataStorePlugin.class,
		//									Param.of("sequencePrefix", "SEQ_"))
		//							.withSearch(ESEmbeddedSearchServicesPlugin.class,
		//									Param.of("home", pandoraHome + "/search"), //usage d'url impropre
		//									Param.of("envIndex", "test"),
		//									Param.of("rowsPerQuery", "50"),
		//									Param.of("config.file", "io/vertigo/pandora/boot/elasticsearch.yml"))
		//							/*.addComponent(KVStoreManager.class, KVStoreManagerImpl.class)
		//							.beginPlugin(BerkeleyKVStorePlugin.class)
		//								.addParam("collections", "allocine-movies,allocine-persons,allocine-queue-persons,allocine-queue-movies")
		//								.addParam("dbFilePath", pandoraHome+"/data")
		//							.endPlugin()*/
		//							.build())
		//
		//					.addModule(new VegaFeatures()
		//							.withSecurity()
		//							//.withTokens("vega")
		//							.withMisc()
		//							.withEmbeddedServer(pandoraPort)
		//							.build())
		//					.addModule(ModuleConfig.builder("identities")
		//							.addComponent(MockIdentities.class)
		//							.build())
		//					.addModule(new AccountFeatures()
		//							.withAuthentication(MockAuthenticationPlugin.class)
		//							.withAccountStorePlugin(LoaderAccountStorePlugin.class,
		//									Param.of("accountLoaderName", "MockIdentities"),
		//									Param.of("groupLoaderName", "MockIdentities"))
		//							.build())
		//					.addModule(new SocialFeatures()
		//							.withMemoryNotifications()
		//							.build())
		//					//-----
		//					.addModule(ModuleConfig.builder("pandora-dao")
		//							.addComponent(MovieDAO.class)
		//							.addComponent(MoviesPAO.class)
		//							.addComponent(PersonDAO.class)
		//							.addComponent(PersonsPAO.class)
		//							.addComponent(ActorRoleDAO.class)
		//							.build())
		//					.addModule(ModuleConfig.builder("pandora-services")
		//							.addComponent(MasterdataServices.class, MasterdataServicesImpl.class)
		//							.addComponent(MovieServices.class, MovieServicesImpl.class)
		//							.addComponent(PersonServices.class, PersonServicesImpl.class)
		//							.addComponent(CommonServices.class, CommonServicesImpl.class)
		//							.build())
		//
		//					.addModule(ModuleConfig.builder("pandora-webservices")
		//							.addComponent(MasterdataWebservices.class)
		//							.addComponent(MovieWebServices.class)
		//							.addComponent(PersonWebServices.class)
		//							.addComponent(CommonWebServices.class)
		//							.addComponent(NotificationWebServices.class)
		//							.build())
		//					.addModule(ModuleConfig.builder("pandora-searchloader")
		//							.addComponent(MovieSearchLoader.class)
		//							.addComponent(PersonSearchLoader.class)
		//							.build())
		//					.addModule(ModuleConfig.builder("myApp")
		//							.addDefinitionProvider(DefinitionProviderConfig.builder(DynamoDefinitionProvider.class)
		//									.addDefinitionResource("classes", ProvidedDtDefinitions.class.getName())
		//									.addDefinitionResource("classes", DtDefinitions.class.getName())
		//									.addDefinitionResource("kpr", "io/vertigo/pandora/boot/application.kpr")
		//									.build())
		//							.build())
		//					.addModule(new DashboardFeatures()
		//							.withInfluxDb("http://analytica.part.klee.lan.net:8086", "analytica", "kleeklee")
		//							.build())
		//					.addModule(ModuleConfig.builder("metrics-provider")
		//							.addComponent(DomainMetricsProvider.class)
		//							.build())
		//					.addInitializer(DashboardInitializer.class);
		//			nodeConfigBuilder.withNodeConfig(
		//					NodeConfig.builder()
		//							.withAppName("pandora")
		//							.build());
		//		} else {
		nodeConfigBuilder
				.addModule(new StudioFeatures()
						.withMasterData()
						.withMda(
								Param.of("projectPackageName", "io.vertigo.pandora"))
						.withJavaDomainGenerator(
								Param.of("generateDtResources", "false"))
						.withTaskGenerator()
						.withSqlDomainGenerator(
								Param.of("generateDrop", "false"),
								Param.of("baseCible", "H2"))
						.withTsDomainGenerator(
								Param.of("generateDtResourcesTS", "false"))
						.addPlugin(WsTsGeneratorPlugin.class,
								Param.of("targetSubDir", "wstsgen"))
						.build())
				.addModule(ModuleConfig.builder("myAppGen")
						.addDefinitionProvider(DefinitionProviderConfig.builder(DynamoDefinitionProvider.class)
								.addDefinitionResource("kpr", "io/vertigo/pandora/boot/application.kpr")
								.addDefinitionResource("oom", "io/vertigo/pandora/mda/pandora.oom")
								.addDefinitionResource("kpr", "io/vertigo/pandora/mda/generation.kpr")
								.build())
						.build());
		//	}
		return nodeConfigBuilder.build();
	}
}
