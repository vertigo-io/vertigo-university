package io.vertigo.pandora.boot;

import org.h2.Driver;

import io.vertigo.account.AccountFeatures;
import io.vertigo.account.plugins.authentication.mock.MockAuthenticationPlugin;
import io.vertigo.app.config.DefinitionProviderConfig;
import io.vertigo.app.config.LogConfig;
import io.vertigo.app.config.ModuleConfig;
import io.vertigo.app.config.NodeConfig;
import io.vertigo.app.config.NodeConfigBuilder;
import io.vertigo.commons.CommonsFeatures;
import io.vertigo.commons.plugins.analytics.log.SocketLoggerAnalyticsConnectorPlugin;
import io.vertigo.core.param.Param;
import io.vertigo.core.plugins.resource.classpath.ClassPathResourceResolverPlugin;
import io.vertigo.core.plugins.resource.local.LocalResourceResolverPlugin;
import io.vertigo.database.DatabaseFeatures;
import io.vertigo.database.impl.sql.vendor.h2.H2DataBase;
import io.vertigo.dynamo.DynamoFeatures;
import io.vertigo.dynamo.plugins.environment.DynamoDefinitionProvider;
import io.vertigo.pandora.dao.movies.MovieDAO;
import io.vertigo.pandora.dao.movies.MoviesPAO;
import io.vertigo.pandora.dao.persons.ActorRoleDAO;
import io.vertigo.pandora.dao.persons.PersonDAO;
import io.vertigo.pandora.dao.persons.PersonsPAO;
import io.vertigo.pandora.domain.DtDefinitions;
import io.vertigo.pandora.search.movies.MovieSearchClient;
import io.vertigo.pandora.search.persons.PersonSearchClient;
import io.vertigo.pandora.services.common.CommonServices;
import io.vertigo.pandora.services.common.CommonServicesImpl;
import io.vertigo.pandora.services.masterdata.MasterdataServices;
import io.vertigo.pandora.services.masterdata.MasterdataServicesImpl;
import io.vertigo.pandora.services.movies.MovieSearchLoader;
import io.vertigo.pandora.services.movies.MovieServices;
import io.vertigo.pandora.services.movies.MovieServicesImpl;
import io.vertigo.pandora.services.persons.PersonSearchLoader;
import io.vertigo.pandora.services.persons.PersonServices;
import io.vertigo.pandora.services.persons.PersonServicesImpl;
import io.vertigo.pandora.user.LollipopUserSession;
import io.vertigo.pandora.webservices.common.CommonWebServices;
import io.vertigo.pandora.webservices.masterdata.MasterdataWebservices;
import io.vertigo.pandora.webservices.movies.MovieWebServices;
import io.vertigo.pandora.webservices.persons.PersonWebServices;
import io.vertigo.social.SocialFeatures;
import io.vertigo.social.webservices.notification.NotificationWebServices;
import io.vertigo.studio.StudioFeatures;
import io.vertigo.studio.plugins.mda.webservice.WsTsGeneratorPlugin;
import io.vertigo.vega.VegaFeatures;

public final class PandoraConfigurator {
	public static NodeConfig config(final boolean activeStudio) {
		final String pandoraHome = System.getProperty("pandora.home", "d:/pandora");
		final String pandoraPort = System.getProperty("pandora.port", "8080");

		final NodeConfigBuilder nodeConfigBuilder = NodeConfig.builder()
				.withAppName("pandora")
				.beginBoot()
				.withLocales("fr")
				.addPlugin(ClassPathResourceResolverPlugin.class)
				.addPlugin(LocalResourceResolverPlugin.class)
				.withLogConfig(new LogConfig("/log4j.xml"))
				.endBoot();

		if (!activeStudio) {
			nodeConfigBuilder
					.addModule(new CommonsFeatures()
							.withCache()
							.withScript()
							.withMemoryCache()
							.withJaninoScript()
							.addAnalyticsConnectorPlugin(SocketLoggerAnalyticsConnectorPlugin.class)
							.build())
					.addModule(new DatabaseFeatures()
							.withSqlDataBase()
							.withC3p0(
									Param.of("dataBaseClass", H2DataBase.class.getName()),
									Param.of("jdbcDriver", Driver.class.getName()),
									Param.of("jdbcUrl", "jdbc:h2:" + pandoraHome + "/data/demo"))
							.build())
					.addModule(new DynamoFeatures()
							.withStore()
							.withSqlStore()
							.withESEmbedded(
									Param.of("home", pandoraHome + "/search"), //usage d'url impropre
									Param.of("envIndex", "test"),
									Param.of("rowsPerQuery", "50"),
									Param.of("config.file", "io/vertigo/pandora/boot/elasticsearch.yml"))
							/*.addComponent(KVStoreManager.class, KVStoreManagerImpl.class)
							.beginPlugin(BerkeleyKVStorePlugin.class)
								.addParam("collections", "allocine-movies,allocine-persons,allocine-queue-persons,allocine-queue-movies")
								.addParam("dbFilePath", pandoraHome+"/data")
							.endPlugin()*/
							.build())

					.addModule(new VegaFeatures()
							.withWebServicesSecurity()
							//.withTokens("vega")
							.withWebServicesRateLimiting()
							.withWebServicesEmbeddedServer(Param.of("port", pandoraPort))
							.build())
					.addModule(ModuleConfig.builder("identities")
							.addComponent(MockIdentities.class)
							.build())
					.addModule(new AccountFeatures()
							.withAuthentication()
							.withSecurity(Param.of("userSessionClassName", LollipopUserSession.class.getName()))
							.addPlugin(MockAuthenticationPlugin.class)
							.withLoaderAccount(
									Param.of("accountLoaderName", "MockIdentities"),
									Param.of("groupLoaderName", "MockIdentities"))
							.build())
					.addModule(new SocialFeatures()
							.withMemoryNotifications()
							.build())
					//-----
					.addModule(ModuleConfig.builder("pandora-dao")
							.addComponent(MovieDAO.class)
							.addComponent(MovieSearchClient.class)
							.addComponent(MoviesPAO.class)
							.addComponent(PersonDAO.class)
							.addComponent(PersonSearchClient.class)
							.addComponent(PersonsPAO.class)
							.addComponent(ActorRoleDAO.class)
							.build())
					.addModule(ModuleConfig.builder("pandora-services")
							.addComponent(MasterdataServices.class, MasterdataServicesImpl.class)
							.addComponent(MovieServices.class, MovieServicesImpl.class)
							.addComponent(PersonServices.class, PersonServicesImpl.class)
							.addComponent(CommonServices.class, CommonServicesImpl.class)
							.build())

					.addModule(ModuleConfig.builder("pandora-webservices")
							.addComponent(MasterdataWebservices.class)
							.addComponent(MovieWebServices.class)
							.addComponent(PersonWebServices.class)
							.addComponent(CommonWebServices.class)
							.addComponent(NotificationWebServices.class)
							.build())
					.addModule(ModuleConfig.builder("pandora-searchloader")
							.addComponent(MovieSearchLoader.class)
							.addComponent(PersonSearchLoader.class)
							.build())
					.addModule(ModuleConfig.builder("myApp")
							.addDefinitionProvider(DefinitionProviderConfig.builder(DynamoDefinitionProvider.class)
									.addDefinitionResource("classes", ProvidedDtDefinitions.class.getName())
									.addDefinitionResource("classes", DtDefinitions.class.getName())
									.addDefinitionResource("kpr", "io/vertigo/pandora/boot/application.kpr")
									.build())
							.build());
		} else {
			nodeConfigBuilder
					.addModule(new StudioFeatures()
							.withMasterData()
							.withMda(
									Param.of("projectPackageName", "io.vertigo.pandora"))
							.withJavaDomainGenerator(
									Param.of("generateDtResources", "false"))
							.withTaskGenerator()
							.withSearchGenerator()
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
									.addParam(Param.of("constFieldName", "true"))
									.addDefinitionResource("kpr", "io/vertigo/pandora/boot/application.kpr")
									.addDefinitionResource("oom", "io/vertigo/pandora/mda/pandora.oom")
									.addDefinitionResource("kpr", "io/vertigo/pandora/mda/generation.kpr")
									.build())
							.build());
		}
		return nodeConfigBuilder.build();
	}
}
