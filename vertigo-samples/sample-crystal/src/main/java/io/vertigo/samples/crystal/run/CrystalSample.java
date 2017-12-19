package io.vertigo.samples.crystal.run;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;

import io.vertigo.app.AutoCloseableApp;
import io.vertigo.app.config.AppConfigBuilder;
import io.vertigo.app.config.ModuleConfig;
import io.vertigo.core.component.di.injector.DIInjector;
import io.vertigo.samples.crystal.CrystalPAO;
import io.vertigo.samples.crystal.config.SampleConfigBuilder;
import io.vertigo.samples.crystal.dao.MovieDAO;
import io.vertigo.samples.crystal.services.MovieSearchLoader;
import io.vertigo.samples.crystal.services.MovieServices;
import io.vertigo.samples.crystal.services.MovieServicesImpl;

public class CrystalSample {

	@Inject
	private MovieServices movieServices;

	public static void main(final String[] args) {
		final AppConfigBuilder appConfigBuilder = SampleConfigBuilder.createAppConfigBuilder();
		appConfigBuilder
				.addModule(ModuleConfig.builder("DAO")
						.addComponent(MovieDAO.class)
						.addComponent(CrystalPAO.class)
						.build())
				.addModule(ModuleConfig.builder("Services")
						.addComponent(MovieServices.class, MovieServicesImpl.class)
						.addComponent(MovieSearchLoader.class)
						.build());

		try (final AutoCloseableApp app = new AutoCloseableApp(appConfigBuilder.build())) {
			final CrystalSample sample = new CrystalSample();
			DIInjector.injectMembers(sample, app.getComponentSpace());
			//-----
			sample.step1();
		}
	}

	void step1() {
		LogManager.getLogger(this.getClass()).info(movieServices.getMovieById(3678598L));
	}

}
