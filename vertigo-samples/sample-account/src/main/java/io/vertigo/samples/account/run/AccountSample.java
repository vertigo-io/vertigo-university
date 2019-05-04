package io.vertigo.samples.account.run;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;

import io.vertigo.account.account.Account;
import io.vertigo.account.authorization.AuthorizationManager;
import io.vertigo.account.plugins.authorization.loaders.JsonSecurityDefinitionProvider;
import io.vertigo.account.security.UserSession;
import io.vertigo.account.security.VSecurityManager;
import io.vertigo.app.AutoCloseableApp;
import io.vertigo.app.config.DefinitionProviderConfig;
import io.vertigo.app.config.ModuleConfig;
import io.vertigo.app.config.NodeConfigBuilder;
import io.vertigo.core.component.di.DIInjector;
import io.vertigo.samples.account.authorization.SecuredEntities.MovieOperations;
import io.vertigo.samples.account.config.SampleConfigBuilder;
import io.vertigo.samples.account.dao.ActorDAO;
import io.vertigo.samples.account.dao.MovieDAO;
import io.vertigo.samples.account.dao.RoleDAO;
import io.vertigo.samples.account.domain.Movie;
import io.vertigo.samples.account.services.UserServices;
import io.vertigo.samples.account.services.UserServicesImpl;
import io.vertigo.samples.account.webservices.TestUserSession;

public class AccountSample {

	@Inject
	private UserServices userServices;
	@Inject
	private AuthorizationManager authorizationManager;

	public static void main(final String[] args) {
		final NodeConfigBuilder nodeConfigBuilder = SampleConfigBuilder.createNodeConfigBuilder();
		nodeConfigBuilder
				.addModule(ModuleConfig.builder("stepDao")
						.addComponent(ActorDAO.class)
						.addComponent(RoleDAO.class)
						.addDefinitionProvider(DefinitionProviderConfig.builder(JsonSecurityDefinitionProvider.class)
								.addDefinitionResource("security", "auth-config.json")
								.build())
						.build())
				.addModule(defaultSampleModule());
		try (final AutoCloseableApp app = new AutoCloseableApp(nodeConfigBuilder.build())) {
			final AccountSample sample = new AccountSample();
			DIInjector.injectMembers(sample, app.getComponentSpace());
			//-----
			final VSecurityManager mySecurityManager = app.getComponentSpace().resolve(VSecurityManager.class);
			final UserSession userSession = mySecurityManager.<TestUserSession> createUserSession();
			try {
				mySecurityManager.startCurrentUserSession(userSession);
				//---
				sample.login();
				sample.testSecurity();
			} finally {
				mySecurityManager.stopCurrentUserSession();
			}

		}
	}

	private static ModuleConfig defaultSampleModule() {
		return ModuleConfig.builder("Sample")
				.addComponent(MovieDAO.class)
				.addComponent(UserServices.class, UserServicesImpl.class)
				.build();
	}

	void login() {
		final Account account = userServices.login("admin", "v3rt1g0");
		LogManager.getLogger(this.getClass()).info("account: " + account.getDisplayName());
	}

	void testSecurity() {
		final String query = authorizationManager.getSearchSecurity(Movie.class, MovieOperations.read);
		LogManager.getLogger(this.getClass()).info("query: " + query);

	}

}
