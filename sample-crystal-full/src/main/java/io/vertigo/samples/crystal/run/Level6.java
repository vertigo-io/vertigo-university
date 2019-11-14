package io.vertigo.samples.crystal.run;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;

import io.vertigo.account.account.Account;
import io.vertigo.account.plugins.authorization.loaders.JsonSecurityDefinitionProvider;
import io.vertigo.account.security.UserSession;
import io.vertigo.account.security.VSecurityManager;
import io.vertigo.core.node.AutoCloseableApp;
import io.vertigo.core.node.config.DefinitionProviderConfig;
import io.vertigo.core.node.config.ModuleConfig;
import io.vertigo.core.node.config.NodeConfigBuilder;
import io.vertigo.core.util.InjectorUtil;
import io.vertigo.samples.SamplesPAO;
import io.vertigo.samples.crystal.CrystalPAO;
import io.vertigo.samples.crystal.config.SampleConfigBuilder;
import io.vertigo.samples.crystal.dao.ActorDAO;
import io.vertigo.samples.crystal.dao.MovieDAO;
import io.vertigo.samples.crystal.dao.RoleDAO;
import io.vertigo.samples.crystal.services.LoginServices;
import io.vertigo.samples.crystal.services.LoginServicesImpl;
import io.vertigo.samples.crystal.services.MovieSearchLoader;
import io.vertigo.samples.crystal.services.MovieServices;
import io.vertigo.samples.crystal.services.MovieServicesImpl;
import io.vertigo.samples.crystal.webservices.MovieWebServices;
import io.vertigo.samples.crystal.webservices.TestUserSession;

public class Level6 {

	@Inject
	private LoginServices loginServices;
	@Inject
	private VSecurityManager securityManager;

	public static void main(final String[] args) {
		final NodeConfigBuilder nodeConfigBuilder = SampleConfigBuilder.createNodeConfigBuilder(true, true, true);
		nodeConfigBuilder
				.addModule(ModuleConfig.builder("stepDao")
						.addComponent(ActorDAO.class)
						.addComponent(RoleDAO.class)
						.addComponent(SamplesPAO.class)
						.addDefinitionProvider(DefinitionProviderConfig.builder(JsonSecurityDefinitionProvider.class)
								.addDefinitionResource("security", "auth-config.json")
								.build())
						.build())
				.addModule(defaultSampleModule());
		try (final AutoCloseableApp app = new AutoCloseableApp(nodeConfigBuilder.build())) {
			final Level6 sample = new Level6();
			InjectorUtil.injectMembers(sample);
			//-----
			sample.step1();
		}
	}

	private static ModuleConfig defaultSampleModule() {
		return ModuleConfig.builder("Sample")
				.addComponent(MovieDAO.class)
				.addComponent(CrystalPAO.class)
				.addComponent(MovieServices.class, MovieServicesImpl.class)
				.addComponent(LoginServices.class, LoginServicesImpl.class)
				.addComponent(MovieSearchLoader.class)
				.addComponent(MovieWebServices.class)
				.build();
	}

	void step1() {
		final UserSession userSession = securityManager.<TestUserSession> createUserSession();
		try {
			securityManager.startCurrentUserSession(userSession);
			final Account account = loginServices.login("admin", "v3rt1g0");
			LogManager.getLogger(this.getClass()).info("account: " + account.getDisplayName());
		} finally {
			securityManager.stopCurrentUserSession();
		}
	}

}
