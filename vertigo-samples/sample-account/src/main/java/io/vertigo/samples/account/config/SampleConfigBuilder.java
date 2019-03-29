package io.vertigo.samples.account.config;

import io.vertigo.account.AccountFeatures;
import io.vertigo.account.authorization.AuthorizationManager;
import io.vertigo.account.impl.authorization.AuthorizationManagerImpl;
import io.vertigo.app.config.ComponentConfig;
import io.vertigo.app.config.DefinitionProviderConfig;
import io.vertigo.app.config.ModuleConfig;
import io.vertigo.app.config.NodeConfig;
import io.vertigo.app.config.NodeConfigBuilder;
import io.vertigo.commons.CommonsFeatures;
import io.vertigo.core.param.Param;
import io.vertigo.core.plugins.resource.classpath.ClassPathResourceResolverPlugin;
import io.vertigo.core.plugins.resource.local.LocalResourceResolverPlugin;
import io.vertigo.database.DatabaseFeatures;
import io.vertigo.database.impl.sql.vendor.h2.H2DataBase;
import io.vertigo.dynamo.DynamoFeatures;
import io.vertigo.dynamo.plugins.environment.DynamoDefinitionProvider;
import io.vertigo.samples.account.webservices.TestUserSession;
import io.vertigo.vega.VegaFeatures;

public class SampleConfigBuilder {

	public static NodeConfigBuilder createNodeConfigBuilder() {
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
								Param.of("jdbcUrl", "jdbc:h2:~/atelier/database/sample_account"))
						.build())
				.addModule(new DynamoFeatures()
						.withStore()
						.withSqlStore()
						.build())
				//----Definitions
				.addModule(ModuleConfig.builder("ressources")
						.addDefinitionProvider(DefinitionProviderConfig.builder(DynamoDefinitionProvider.class)
								.addDefinitionResource("kpr", "application.kpr")
								.build())
						.build())
				//---- Account
				.addModule(new AccountFeatures()
						.withSecurity(Param.of("userSessionClassName", TestUserSession.class.getName()))
						.withAccount()
						.withStoreAccount(
								Param.of("userIdentityEntity", "DT_USER"),
								Param.of("groupIdentityEntity", "DT_USER_GROUP"),
								Param.of("userAuthField", "login"),
								Param.of("userToAccountMapping", "id:login, displayName:name, email:email, authToken:login"),
								Param.of("groupToGroupAccountMapping", "id:name, displayName:name"))
						.withAuthentication()
						.withTextAuthentication(
								Param.of("filePath", "authentication/identities.txt"))
						.build())
				.addModule(ModuleConfig.builder("authorization")
						.addComponent(ComponentConfig.builder()
								.withApi(AuthorizationManager.class)
								.withImpl(AuthorizationManagerImpl.class)
								.build())
						.build())
				.addModule(new VegaFeatures()
						.withWebServices()
						.withWebServicesSecurity()
						.withWebServicesEmbeddedServer(Param.of("port", "8081"))
						.build());

		return nodeConfigBuilder;
	}

}
