package io.vertigo.samples.account.config;

import io.vertigo.account.AccountFeatures;
import io.vertigo.account.authorization.AuthorizationManager;
import io.vertigo.account.impl.authorization.AuthorizationManagerImpl;
import io.vertigo.account.plugins.account.store.datastore.StoreAccountStorePlugin;
import io.vertigo.account.plugins.authentication.text.TextAuthenticationPlugin;
import io.vertigo.app.config.AppConfig;
import io.vertigo.app.config.AppConfigBuilder;
import io.vertigo.app.config.ComponentConfig;
import io.vertigo.app.config.DefinitionProviderConfig;
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
import io.vertigo.dynamo.plugins.store.datastore.sql.SqlDataStorePlugin;
import io.vertigo.samples.account.webservices.TestUserSession;
import io.vertigo.vega.VegaFeatures;

public class SampleConfigBuilder {

	public static AppConfigBuilder createAppConfigBuilder() {
		final AppConfigBuilder appConfigBuilder = AppConfig.builder()
				.beginBoot()
				.withLocales("fr_FR")
				.addPlugin(ClassPathResourceResolverPlugin.class)
				.addPlugin(LocalResourceResolverPlugin.class)
				.endBoot()
				.addModule(new CommonsFeatures()
						.withCache(MemoryCachePlugin.class)
						.withScript()
						.build())
				.addModule(new DatabaseFeatures()
						.withSqlDataBase()
						.addSqlConnectionProviderPlugin(C3p0ConnectionProviderPlugin.class,
								Param.of("dataBaseClass", H2DataBase.class.getName()),
								Param.of("jdbcDriver", org.h2.Driver.class.getName()),
								Param.of("jdbcUrl", "jdbc:h2:~/atelier/database/sample_account"))
						.build())
				.addModule(new DynamoFeatures()
						.withStore()
						.addDataStorePlugin(SqlDataStorePlugin.class,
								Param.of("sequencePrefix", "SEQ_"))
						.build())
				//----Definitions
				.addModule(ModuleConfig.builder("ressources")
						.addDefinitionProvider(DefinitionProviderConfig.builder(DynamoDefinitionProvider.class)
								.addDefinitionResource("kpr", "application.kpr")
								.build())
						.build())
				//---- Account
				.addModule(new AccountFeatures()
						.withUserSession(TestUserSession.class)
						.withAccountStorePlugin(StoreAccountStorePlugin.class,
								Param.of("userIdentityEntity", "DT_USER"),
								Param.of("groupIdentityEntity", "DT_USER_GROUP"),
								Param.of("userAuthField", "LOGIN"),
								Param.of("userToAccountMapping", "id:LOGIN, displayName:NAME, email:EMAIL, authToken:LOGIN"),
								Param.of("groupToGroupAccountMapping", "id:NAME, displayName:NAME"))
						.withAuthentication(TextAuthenticationPlugin.class,
								Param.of("filePath", "authentication/identities.txt"))
						.build())
				.addModule(ModuleConfig.builder("authorization")
						.addComponent(ComponentConfig.builder()
								.withApi(AuthorizationManager.class)
								.withImpl(AuthorizationManagerImpl.class)
								.build())
						.build())
				.addModule(new VegaFeatures()
						.withSecurity()
						.withEmbeddedServer(8081)
						.build());

		return appConfigBuilder;
	}

}
