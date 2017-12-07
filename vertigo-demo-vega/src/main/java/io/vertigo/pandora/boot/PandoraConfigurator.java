package io.vertigo.pandora.boot;

import org.h2.Driver;

import io.vertigo.account.AccountFeatures;
import io.vertigo.account.plugins.authentication.mock.MockAuthenticatingRealmPlugin;
import io.vertigo.account.plugins.identity.memory.MemoryAccountStorePlugin;
import io.vertigo.app.config.AppConfig;
import io.vertigo.app.config.AppConfigBuilder;
import io.vertigo.app.config.DefinitionProviderConfig;
import io.vertigo.app.config.LogConfig;
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
import io.vertigo.pandora.dao.movies.MovieDAO;
import io.vertigo.pandora.dao.movies.MoviesPAO;
import io.vertigo.pandora.dao.persons.ActorRoleDAO;
import io.vertigo.pandora.dao.persons.PersonDAO;
import io.vertigo.pandora.dao.persons.PersonsPAO;
import io.vertigo.pandora.domain.DtDefinitions;
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
import io.vertigo.persona.impl.security.PersonaFeatures;
import io.vertigo.social.SocialFeatures;
import io.vertigo.social.webservices.notification.NotificationWebServices;
import io.vertigo.studio.impl.mda.MdaManagerImpl;
import io.vertigo.studio.mda.MdaManager;
import io.vertigo.studio.plugins.mda.domain.java.DomainGeneratorPlugin;
import io.vertigo.studio.plugins.mda.domain.sql.SqlGeneratorPlugin;
import io.vertigo.studio.plugins.mda.domain.ts.TSGeneratorPlugin;
import io.vertigo.studio.plugins.mda.task.TaskGeneratorPlugin;
import io.vertigo.studio.plugins.mda.webservice.WsTsGeneratorPlugin;
import io.vertigo.vega.VegaFeatures;

public final class PandoraConfigurator {
	public static AppConfig config(final boolean activeStudio) {
		final String pandoraHome = System.getProperty("pandora.home", "d:/pandora");
		final int pandoraPort = Integer.parseInt(System.getProperty("pandora.port", "8080"));

		final AppConfigBuilder appConfigBuilder = AppConfig.builder()
				.beginBoot()
				.withLocales("fr")
				.addPlugin(ClassPathResourceResolverPlugin.class)
				.addPlugin(LocalResourceResolverPlugin.class)
				.withLogConfig(new LogConfig("/log4j.xml"))
				.endBoot();

		if (!activeStudio) {
			appConfigBuilder
					.addModule(new PersonaFeatures()
							.withUserSession(LollipopUserSession.class).build())
					.addModule(new CommonsFeatures().withCache(MemoryCachePlugin.class).withScript().build())
					.addModule(new DatabaseFeatures()
							.withSqlDataBase()
							.addSqlConnectionProviderPlugin(C3p0ConnectionProviderPlugin.class,
									Param.of("dataBaseClass", H2DataBase.class.getName()),
									Param.of("jdbcDriver", Driver.class.getName()),
									Param.of("jdbcUrl", "jdbc:h2:" + pandoraHome + "/data/demo"))
							.build())
					.addModule(new DynamoFeatures()
							.withStore()
							.addDataStorePlugin(SqlDataStorePlugin.class,
									Param.of("sequencePrefix", "SEQ_"))
							.withSearch(ESEmbeddedSearchServicesPlugin.class,
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
							.withSecurity()
							//.withTokens("vega")
							.withMisc()
							.withEmbeddedServer(pandoraPort)
							.build())
					.addModule(new AccountFeatures()
							.withAccountStorePlugin(MemoryAccountStorePlugin.class)
							.withAuthentificationRealm(MockAuthenticatingRealmPlugin.class)
							.build())
					.addModule(new SocialFeatures()
							.withMemoryNotifications()
							.build())
					//-----
					.addModule(ModuleConfig.builder("pandora-dao")
							.addComponent(MovieDAO.class)
							.addComponent(MoviesPAO.class)
							.addComponent(PersonDAO.class)
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
			appConfigBuilder
					.addModule(ModuleConfig.builder("studio")
							.addComponent(MdaManager.class, MdaManagerImpl.class,
									Param.of("projectPackageName", "io.vertigo.pandora"),
									Param.of("targetGenDir", "src/main/"),
									Param.of("encoding", "utf8"))
							.addPlugin(DomainGeneratorPlugin.class,
									Param.of("targetSubDir", "javagen"),
									Param.of("generateDtResources", "false"),
									Param.of("generateJpaAnnotations", "false"),
									Param.of("generateDtDefinitions", "true"),
									Param.of("generateDtObject", "true"))
							.addPlugin(TaskGeneratorPlugin.class,
									Param.of("targetSubDir", "javagen"))
							.addPlugin(SqlGeneratorPlugin.class,
									Param.of("targetSubDir", "sqlgen"),
									Param.of("generateDrop", "false"),
									Param.of("baseCible", "H2"))
							.addPlugin(TSGeneratorPlugin.class,
									Param.of("targetSubDir", "tsgen"),
									Param.of("generateDtResourcesTS", "false"),
									Param.of("generateTsDtDefinitions", "true"))
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
		}
		return appConfigBuilder.build();
	}
}
