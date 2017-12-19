package io.vertigo.samples.crystal.run;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;

import io.vertigo.app.AutoCloseableApp;
import io.vertigo.app.config.AppConfigBuilder;
import io.vertigo.app.config.ModuleConfig;
import io.vertigo.core.component.di.injector.DIInjector;
import io.vertigo.samples.SamplesPAO;
import io.vertigo.samples.crystal.CrystalPAO;
import io.vertigo.samples.crystal.config.SampleConfigBuilder;
import io.vertigo.samples.crystal.dao.ActorDAO;
import io.vertigo.samples.crystal.dao.MovieDAO;
import io.vertigo.samples.crystal.dao.MovieProxyDAO;
import io.vertigo.samples.crystal.dao.RoleDAO;
import io.vertigo.samples.crystal.services.MovieSearchLoader;
import io.vertigo.samples.crystal.services.MovieServices;
import io.vertigo.samples.crystal.services.MovieServicesImpl;
import io.vertigo.samples.crystal.webservices.MovieWebServices;
import io.vertigo.vega.impl.webservice.catalog.SwaggerWebServices;

public class Level3 {

	@Inject
	private MovieServices movieServices;

	public static void main(final String[] args) {
		final AppConfigBuilder appConfigBuilder = SampleConfigBuilder.createAppConfigBuilderWithoutCrebase();
		appConfigBuilder
				.addModule(ModuleConfig.builder("stepDao")
						.addComponent(ActorDAO.class)
						.addComponent(RoleDAO.class)
						.addComponent(SamplesPAO.class)
						.addProxy(MovieProxyDAO.class)
						.build())
				.addModule(defaultSampleModule());
		try (final AutoCloseableApp app = new AutoCloseableApp(appConfigBuilder.build())) {
			final Level3 sample = new Level3();
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
				.addComponent(SwaggerWebServices.class)
				.build();
	}

	void step1() {
		LogManager.getLogger(this.getClass()).info("male actors : " + movieServices.countMaleActorsInMovie(3678598L));
	}

}
