package io.vertigo.samples.dao.run;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import io.vertigo.app.AutoCloseableApp;
import io.vertigo.app.config.AppConfigBuilder;
import io.vertigo.core.component.di.injector.Injector;
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
import io.vertigo.samples.dao.services.ActorServices;
import io.vertigo.samples.dao.services.ActorServicesImpl;
import io.vertigo.samples.dao.services.MovieServices;
import io.vertigo.samples.dao.services.MovieServicesImpl;
import io.vertigo.samples.dao.services.RepriseServices;
import io.vertigo.samples.dao.services.RepriseServicesImpl;
import io.vertigo.samples.reprise.ReprisePAO;

public class Level4 {

	private final Logger LOGGER = Logger.getLogger(this.getClass());

	private static final Long STARWARS_ID = 3678598L;

	@Inject
	private MovieServices movieServices;

	public static void main(final String[] args) {
		final AppConfigBuilder appConfigBuilder = SampleConfigBuilder.createAppConfigBuilderWithoutCrebase();
		appConfigBuilder.beginModule("mineDAO")
				.withNoAPI()
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
				.endModule()
				.beginModule("mineServices")
				.addComponent(MovieServices.class, MovieServicesImpl.class)
				.addComponent(ActorServices.class, ActorServicesImpl.class)
				.addComponent(RepriseServices.class, RepriseServicesImpl.class)
				.endModule();
		try (final AutoCloseableApp app = new AutoCloseableApp(appConfigBuilder.build())) {
			final Level4 level4 = new Level4();
			Injector.injectMembers(level4, app.getComponentSpace());
			//-----
			level4.step1();
			level4.step2();
			level4.step3();
			level4.step4();
			level4.step5();
		}
	}

	void step1() {
		LOGGER.info(movieServices.getActorsByMovie2(STARWARS_ID));
	}

	void step2() {
		LOGGER.info(movieServices.getMoviesWith100Actors());
	}

	void step3() {
		// nothing
	}

	void step4() {
		LOGGER.info(movieServices.getMovieDisplay());
	}

	void step5() {
		LOGGER.info(movieServices.getMoviesByDate());
	}

}
