package io.vertigo.samples.dao.run;

import javax.inject.Inject;

import io.vertigo.app.AutoCloseableApp;
import io.vertigo.app.config.AppConfigBuilder;
import io.vertigo.core.component.di.injector.Injector;
import io.vertigo.samples.dao.config.SampleConfigBuilder;
import io.vertigo.samples.dao.dao.MyMovieDAO;
import io.vertigo.samples.dao.domain.MyMovie;
import io.vertigo.samples.dao.services.MovieServices;
import io.vertigo.samples.dao.services.MovieServicesImpl;

public class DaoSample {

	@Inject
	private MovieServices movieServices;

	public static void main(final String[] args) {
		final AppConfigBuilder appConfigBuilder = SampleConfigBuilder.createAppConfigBuilder();
		appConfigBuilder.beginModule("mineDAO")
				.withNoAPI()
				.addComponent(MyMovieDAO.class)
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
		final MyMovie newMovie = new MyMovie();
		newMovie.setName("My Film");
		newMovie.setYear(2016);
		movieServices.saveMyMovie(newMovie);
	}

}
