package io.vertigo.samples.dao.run;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;

import io.vertigo.app.AutoCloseableApp;
import io.vertigo.app.config.ModuleConfig;
import io.vertigo.app.config.NodeConfigBuilder;
import io.vertigo.core.component.di.DIInjector;
import io.vertigo.samples.dao.config.SampleConfigBuilder;
import io.vertigo.samples.dao.dao.MovieDAO;
import io.vertigo.samples.dao.services.MovieServices;
import io.vertigo.samples.dao.services.MovieServicesImpl;

public class DaoSample {

	@Inject
	private MovieServices movieServices;

	public static void main(final String[] args) {
		final NodeConfigBuilder nodeConfigBuilder = SampleConfigBuilder.createNodeConfigBuilder();
		nodeConfigBuilder
				.addModule(ModuleConfig.builder("DAO")
						.addComponent(MovieDAO.class)
						.build())
				.addModule(ModuleConfig.builder("Services")
						.addComponent(MovieServices.class, MovieServicesImpl.class)
						.build());

		try (final AutoCloseableApp app = new AutoCloseableApp(nodeConfigBuilder.build())) {
			final DaoSample sample = new DaoSample();
			DIInjector.injectMembers(sample, app.getComponentSpace());
			//-----
			sample.step1();
		}
	}

	void step1() {
		LogManager.getLogger(this.getClass()).info(movieServices.getMovieById(3678598L));
	}

}
