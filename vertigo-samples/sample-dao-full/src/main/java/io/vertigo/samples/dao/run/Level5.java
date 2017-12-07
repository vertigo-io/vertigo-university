package io.vertigo.samples.dao.run;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.vertigo.app.AutoCloseableApp;
import io.vertigo.app.config.AppConfigBuilder;
import io.vertigo.app.config.ModuleConfig;
import io.vertigo.core.component.di.injector.DIInjector;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.samples.SamplesPAO;
import io.vertigo.samples.dao.config.SampleConfigBuilder;
import io.vertigo.samples.dao.dao.ActorDAO;
import io.vertigo.samples.dao.dao.CountryDAO;
import io.vertigo.samples.dao.dao.MovieDAO;
import io.vertigo.samples.dao.dao.MyActorDAO;
import io.vertigo.samples.dao.dao.MyCountryDAO;
import io.vertigo.samples.dao.dao.MyMovieDAO;
import io.vertigo.samples.dao.dao.MyRoleDAO;
import io.vertigo.samples.dao.dao.RoleDAO;
import io.vertigo.samples.dao.domain.Country;
import io.vertigo.samples.dao.services.ActorServices;
import io.vertigo.samples.dao.services.ActorServicesImpl;
import io.vertigo.samples.dao.services.CountryServices;
import io.vertigo.samples.dao.services.CountryServicesImpl;
import io.vertigo.samples.dao.services.MovieServices;
import io.vertigo.samples.dao.services.MovieServicesImpl;
import io.vertigo.samples.dao.services.RepriseServices;
import io.vertigo.samples.dao.services.RepriseServicesImpl;
import io.vertigo.samples.reprise.ReprisePAO;

public class Level5 {

	private final Logger LOGGER = LogManager.getLogger(this.getClass());

	@Inject
	private MovieServices movieServices;
	@Inject
	private CountryServices countryServices;

	public static void main(final String[] args) {
		final AppConfigBuilder appConfigBuilder = SampleConfigBuilder.createAppConfigBuilderWithoutCrebase();
		appConfigBuilder.addModule(ModuleConfig.builder("mineDAO")
				.addComponent(MyMovieDAO.class)
				.addComponent(MyActorDAO.class)
				.addComponent(MyRoleDAO.class)
				.addComponent(MyCountryDAO.class)
				.addComponent(MovieDAO.class)
				.addComponent(ActorDAO.class)
				.addComponent(RoleDAO.class)
				.addComponent(CountryDAO.class)
				.addComponent(ReprisePAO.class)
				.addComponent(SamplesPAO.class)
				.build())
				.addModule(ModuleConfig.builder("mineServices")
						.addComponent(CountryServices.class, CountryServicesImpl.class)
						.addComponent(MovieServices.class, MovieServicesImpl.class)
						.addComponent(ActorServices.class, ActorServicesImpl.class)
						.addComponent(RepriseServices.class, RepriseServicesImpl.class)
						.build());
		try (final AutoCloseableApp app = new AutoCloseableApp(appConfigBuilder.build())) {
			final Level5 level5 = new Level5();
			DIInjector.injectMembers(level5, app.getComponentSpace());
			//-----
			level5.step1();
			level5.step2();
		}
	}

	void step1() {
		LOGGER.info(movieServices.findMoviesByKsp("Star Wars", 1977));
	}

	void step2() {
		final DtList<Country> countries = countryServices.getCountriesByName("U");
		LOGGER.info(movieServices.findMoviesByKspWhereIn("Star Wars", null, countries));
	}

}
