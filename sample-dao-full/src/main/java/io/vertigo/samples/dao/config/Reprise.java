package io.vertigo.samples.dao.config;

import javax.inject.Inject;

import io.vertigo.app.AutoCloseableApp;
import io.vertigo.app.config.AppConfigBuilder;
import io.vertigo.core.component.di.injector.Injector;
import io.vertigo.samples.dao.dao.ActorDAO;
import io.vertigo.samples.dao.dao.CountryDAO;
import io.vertigo.samples.dao.dao.MovieDAO;
import io.vertigo.samples.dao.dao.MyActorDAO;
import io.vertigo.samples.dao.dao.MyCountryDAO;
import io.vertigo.samples.dao.dao.MyMovieDAO;
import io.vertigo.samples.dao.dao.MyRoleDAO;
import io.vertigo.samples.dao.dao.RoleDAO;
import io.vertigo.samples.dao.sevices.ActorServices;
import io.vertigo.samples.dao.sevices.ActorServicesImpl;
import io.vertigo.samples.dao.sevices.MovieServices;
import io.vertigo.samples.dao.sevices.MovieServicesImpl;
import io.vertigo.samples.dao.sevices.RepriseServices;
import io.vertigo.samples.dao.sevices.RepriseServicesImpl;
import io.vertigo.samples.dao.sevices.RoleServices;
import io.vertigo.samples.dao.sevices.RoleServicesImpl;
import io.vertigo.samples.reprise.ReprisePAO;

public class Reprise {

	@Inject
	private RepriseServices repriseServices;

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
				.endModule()
				.beginModule("mineServices")
				.addComponent(MovieServices.class, MovieServicesImpl.class)
				.addComponent(ActorServices.class, ActorServicesImpl.class)
				.addComponent(RoleServices.class, RoleServicesImpl.class)
				.addComponent(RepriseServices.class, RepriseServicesImpl.class)
				.endModule();
		try (final AutoCloseableApp app = new AutoCloseableApp(appConfigBuilder.build())) {
			final Reprise sample = new Reprise();
			Injector.injectMembers(sample, app.getComponentSpace());
			//-----
			sample.step1();
			sample.step2();
			sample.step3();
			sample.step4();
		}
	}

	void step1() {
		repriseServices.fillCountries();
	}

	void step2() {
		final long chunksize = 1000L;
		final long chunkCounts = (long) (Math.floor(repriseServices.countActors() / chunksize) + 1);
		for (int i = 0; i < chunkCounts; i++) {
			repriseServices.fillActors(chunksize, i * chunksize);

		}
	}

	void step3() {
		final long minMovie = repriseServices.minMovie();
		final long maxMovie = repriseServices.maxMovie();
		final long chunksize = 1000L;
		final long chunkCounts = (long) (Math.floor(repriseServices.countMovies() / chunksize) + 1);
		for (int i = 0; i < chunkCounts; i++) {
			repriseServices.fillMovies(chunksize, i * chunksize, minMovie, maxMovie);

		}
	}

	void step4() {
		final long minMovie = repriseServices.minMovie();
		final long maxMovie = repriseServices.maxMovie();
		final long chunksize = 10000L;
		final long chunkCounts = (long) (Math.floor(repriseServices.countRoles() / chunksize) + 1);
		for (int i = 0; i < chunkCounts; i++) {
			repriseServices.fillRoles(chunksize, i * chunksize, minMovie, maxMovie);

		}
	}

}
