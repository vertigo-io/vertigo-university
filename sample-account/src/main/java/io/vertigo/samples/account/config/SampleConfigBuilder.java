package io.vertigo.samples.account.config;

import io.vertigo.account.AccountFeatures;
import io.vertigo.account.authorization.AuthorizationManager;
import io.vertigo.account.impl.authorization.AuthorizationManagerImpl;
import io.vertigo.commons.CommonsFeatures;
import io.vertigo.connectors.javalin.JavalinFeatures;
import io.vertigo.core.node.config.BootConfig;
import io.vertigo.core.node.config.DefinitionProviderConfig;
import io.vertigo.core.node.config.ModuleConfig;
import io.vertigo.core.node.config.NodeConfig;
import io.vertigo.core.node.config.NodeConfigBuilder;
import io.vertigo.core.param.Param;
import io.vertigo.core.plugins.resource.classpath.ClassPathResourceResolverPlugin;
import io.vertigo.core.plugins.resource.local.LocalResourceResolverPlugin;
import io.vertigo.database.DatabaseFeatures;
import io.vertigo.database.impl.sql.vendor.h2.H2DataBase;
import io.vertigo.datamodel.DataModelFeatures;
import io.vertigo.datamodel.impl.smarttype.ModelDefinitionProvider;
import io.vertigo.datastore.DataStoreFeatures;
import io.vertigo.samples.account.domain.DtDefinitions;
import io.vertigo.samples.account.webservices.TestUserSession;
import io.vertigo.vega.VegaFeatures;

public class SampleConfigBuilder {

	public static NodeConfigBuilder createNodeConfigBuilder() {
		final NodeConfigBuilder nodeConfigBuilder = NodeConfig.builder()
				.withBoot(BootConfig.builder().withLocales("fr_FR")
						.addPlugin(ClassPathResourceResolverPlugin.class)
						.addPlugin(LocalResourceResolverPlugin.class)
						.build())
				.addModule(new JavalinFeatures().withEmbeddedServer(Param.of("port", "8081")).build())
				.addModule(new CommonsFeatures()
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
				.addModule(new DataModelFeatures().build())
				.addModule(new DataStoreFeatures()
						.withCache()
						.withMemoryCache()
						.withEntityStore()
						.withSqlEntityStore()
						.withFileStore()
						.build())
				//----Definitions
				.addModule(ModuleConfig.builder("ressources")
						.addDefinitionProvider(DefinitionProviderConfig.builder(ModelDefinitionProvider.class)
								.addDefinitionResource("smarttypes", SampleAccountSmartTypes.class.getCanonicalName())
								.addDefinitionResource("dtobjects", DtDefinitions.class.getCanonicalName())
								.build())
						.build())
				//---- Account
				.addModule(new AccountFeatures()
						.withSecurity(Param.of("userSessionClassName", TestUserSession.class.getName()))
						.withAccount()
						.withStoreAccount(
								Param.of("userIdentityEntity", "DtUser"),
								Param.of("groupIdentityEntity", "DtUserGroup"),
								Param.of("userAuthField", "login"),
								Param.of("userToAccountMapping", "id:login, displayName:name, email:email, authToken:login"),
								Param.of("groupToGroupAccountMapping", "id:name, displayName:name"))
						.withAuthentication()
						.withTextAuthentication(
								Param.of("filePath", "authentication/identities.txt"))
						.build())
				.addModule(ModuleConfig.builder("authorization")
						.addComponent(AuthorizationManager.class, AuthorizationManagerImpl.class)
						.build())
				.addModule(new VegaFeatures()
						.withWebServices()
						.withWebServicesSecurity()
						.build());

		return nodeConfigBuilder;
	}

}
