package io.vertigo.samples.dao.config;

import javax.inject.Inject;

import io.vertigo.app.AutoCloseableApp;
import io.vertigo.app.config.AppConfigBuilder;
import io.vertigo.core.component.di.injector.Injector;
import io.vertigo.samples.dao.dao.MovieDAO;
import io.vertigo.samples.dao.domain.Movie;
import io.vertigo.samples.dao.sevices.MovieServices;
import io.vertigo.samples.dao.sevices.MovieServicesImpl;

public class DaoSample {

	@Inject
	private MovieServices movieServices;

	public static void main(final String[] args) {
		final AppConfigBuilder appConfigBuilder = SampleConfigBuilder.createAppConfigBuilder();
		appConfigBuilder.beginModule("mineDAO")
				.withNoAPI()
				.addComponent(MovieDAO.class)
				.endModule()
				.beginModule("mineServices")
				.addComponent(MovieServices.class, MovieServicesImpl.class)
				.endModule();
		try (final AutoCloseableApp app = new AutoCloseableApp(appConfigBuilder.build())) {
			final DaoSample sample = new DaoSample();
			Injector.injectMembers(sample, app.getComponentSpace());
			//-----
			sample.step1();
		}
	}

	void step1() {
		final Movie newMovie = new Movie();
		newMovie.setName("My Film");
		newMovie.setYear(2016);
		movieServices.saveMovie(newMovie);
	}
}
