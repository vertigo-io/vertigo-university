package io.vertigo.samples.crystal.run;

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
import io.vertigo.samples.crystal.webservices.MovieWebServices;

public class CrystalSearchSample {

	public static void main(final String[] args) {
		final AppConfigBuilder appConfigBuilder = SampleConfigBuilder.createAppConfigBuilder(true, true);
		appConfigBuilder
				.addModule(ModuleConfig.builder("stepDao")
						.build())
				.addModule(defaultSampleModule());

		try (final AutoCloseableApp app = new AutoCloseableApp(appConfigBuilder.build())) {
			final CrystalSearchSample sample = new CrystalSearchSample();
			DIInjector.injectMembers(sample, app.getComponentSpace());
			//-----
			sample.step1();
		}
	}

	private static ModuleConfig defaultSampleModule() {
		return ModuleConfig.builder("Sample")
				.addComponent(MovieDAO.class)
				.addComponent(CrystalPAO.class)
				.addComponent(MovieServices.class, MovieServicesImpl.class)
				.addComponent(MovieSearchLoader.class)
				.addComponent(MovieWebServices.class)
				.build();
	}

	void step1() {
		try {
			Thread.currentThread().join();
		} catch (final InterruptedException e) {
			//nothing
		}
	}

}
