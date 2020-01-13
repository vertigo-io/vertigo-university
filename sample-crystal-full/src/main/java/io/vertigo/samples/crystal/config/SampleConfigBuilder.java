package io.vertigo.samples.crystal.config;

import io.vertigo.account.AccountFeatures;
import io.vertigo.account.authorization.AuthorizationManager;
import io.vertigo.account.impl.authorization.AuthorizationManagerImpl;
import io.vertigo.commons.CommonsFeatures;
import io.vertigo.connectors.elasticsearch.ElasticSearchFeatures;
import io.vertigo.core.node.config.ComponentConfig;
import io.vertigo.core.node.config.DefinitionProviderConfig;
import io.vertigo.core.node.config.ModuleConfig;
import io.vertigo.core.node.config.NodeConfig;
import io.vertigo.core.node.config.NodeConfigBuilder;
import io.vertigo.core.param.Param;
import io.vertigo.core.plugins.resource.classpath.ClassPathResourceResolverPlugin;
import io.vertigo.core.plugins.resource.local.LocalResourceResolverPlugin;
import io.vertigo.database.DatabaseFeatures;
import io.vertigo.database.impl.sql.vendor.h2.H2DataBase;
import io.vertigo.datafactory.DataFactoryFeatures;
import io.vertigo.datafactory.impl.search.grammar.SearchDefinitionProvider;
import io.vertigo.datastore.DataStoreFeatures;
import io.vertigo.dynamo.DataModelFeatures;
import io.vertigo.dynamo.impl.task.proxy.TaskProxyMethod;
import io.vertigo.dynamo.plugins.environment.ModelDefinitionProvider;
import io.vertigo.samples.crystal.webservices.TestUserSession;
import io.vertigo.vega.VegaFeatures;

public class SampleConfigBuilder {

	public static NodeConfigBuilder createNodeConfigBuilder(final boolean withSearch, final boolean withVega, final boolean withAccount) {
		final DataStoreFeatures dynamoFeatures = new DataStoreFeatures()
				.withEntityStore()
				.withSqlEntityStore();

		final NodeConfigBuilder nodeConfigBuilder = NodeConfig.builder()
				.beginBoot()
				.withLocales("fr_FR")
				.addPlugin(ClassPathResourceResolverPlugin.class)
				.addPlugin(LocalResourceResolverPlugin.class)
				.endBoot();

		nodeConfigBuilder
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
				.addModule(new DataModelFeatures().build());

		if (withSearch) {
			nodeConfigBuilder
					.addModule(new ElasticSearchFeatures()
							.withEmbeddedServer(Param.of("home", "D:/atelier/search"))//usage d'url impropre
							.withRestHL(Param.of("servers.names", "localhost:9200"))
							.build())
					.addModule(new DataFactoryFeatures()
							.withSearch()
							.withESHL(Param.of("envIndex", "crystal-test_"),
									Param.of("rowsPerQuery", "50"),
									Param.of("config.file", "elasticsearch.yml"))
							.build());
		}
		//----Definitions
		nodeConfigBuilder.addModule(ModuleConfig.builder("ressources")
				.addDefinitionProvider(DefinitionProviderConfig.builder(ModelDefinitionProvider.class)
						.addDefinitionResource("kpr", "model.kpr")
						.addDefinitionResource("kpr", "task.kpr")
						.build())
				.addDefinitionProvider(DefinitionProviderConfig.builder(SearchDefinitionProvider.class)
						.addDefinitionResource("kpr", "search.kpr")
						.build())
				.build());
		if (withVega) {
			nodeConfigBuilder.addModule(new VegaFeatures()
					.withWebServices()
					.withWebServicesEmbeddedServer(Param.of("port", "8081"))
					.build());
		}

		//---- proxies (Level4)
		nodeConfigBuilder.addModule(ModuleConfig.builder("proxies")
				.addProxyMethod(TaskProxyMethod.class)
				.build());

		//---- Account (Level6)
		if (withAccount) {
			nodeConfigBuilder.addModule(new AccountFeatures()
					.withSecurity(Param.of("userSessionClassName", TestUserSession.class.getName()))
					.withAccount()
					.withStoreAccount(
							Param.of("userIdentityEntity", "DT_USER"),
							Param.of("groupIdentityEntity", "DT_USER_GROUP"),
							Param.of("userAuthField", "LOGIN"),
							Param.of("userToAccountMapping", "id:LOGIN, displayName:NAME, email:EMAIL, authToken:LOGIN"),
							Param.of("groupToGroupAccountMapping", "id:NAME, displayName:NAME"))
					.withAuthentication()
					.withLdapAuthentication(
							Param.of("userLoginTemplate", "cn={0},dc=vertigo,dc=io"),
							Param.of("ldapServerHost", "docker-vertigo.part.klee.lan.net"),
							Param.of("ldapServerPort", "389"))
					.build());
			nodeConfigBuilder.addModule(ModuleConfig.builder("authorization")
					.addComponent(ComponentConfig.builder()
							.withApi(AuthorizationManager.class)
							.withImpl(AuthorizationManagerImpl.class)
							.build())
					.build());
		}

		return nodeConfigBuilder;
	}

}
