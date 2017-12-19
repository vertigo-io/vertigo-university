package io.vertigo.samples.crystal.run;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;

import io.vertigo.app.AutoCloseableApp;
import io.vertigo.app.config.AppConfigBuilder;
import io.vertigo.app.config.ModuleConfig;
import io.vertigo.core.component.di.injector.DIInjector;
import io.vertigo.samples.SamplesPAO;
import io.vertigo.samples.crystal.config.SampleConfigBuilder;
import io.vertigo.samples.crystal.dao.ActorDAO;
import io.vertigo.samples.crystal.dao.MovieDAO;
import io.vertigo.samples.crystal.dao.RoleDAO;
import io.vertigo.samples.crystal.services.MovieServices;
import io.vertigo.samples.crystal.services.MovieServicesImpl;

public class Level1 {

	@Inject
	private MovieServices movieServices;

	public static void main(final String[] args) {
		final AppConfigBuilder appConfigBuilder = SampleConfigBuilder.createAppConfigBuilderWithoutCrebase();
		appConfigBuilder.addModule(ModuleConfig.builder("mineDAO")
				.build())
				.addModule(ModuleConfig.builder("services")
						.addComponent(MovieServices.class, MovieServicesImpl.class)
						.addComponent(MovieDAO.class)
						.addComponent(ActorDAO.class)
						.addComponent(RoleDAO.class)
						.addComponent(SamplesPAO.class)
						.build());
		try (final AutoCloseableApp app = new AutoCloseableApp(appConfigBuilder.build())) {
			final Level1 sample = new Level1();
			DIInjector.injectMembers(sample, app.getComponentSpace());
			//-----
			sample.step1();
			sample.step2();
		}
	}

	void step1() {
		LogManager.getLogger(this.getClass()).info(movieServices.getActorsIdsByMovie(3678598L));
	}

	void step2() {
		final List<Long> countryIds = Arrays.asList(1223L, 1119L, 1075L, 1207L);
		LogManager.getLogger(this.getClass()).info(movieServices.getMoviesInCountries(countryIds));
	}

}
