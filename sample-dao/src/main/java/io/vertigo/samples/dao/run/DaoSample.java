package io.vertigo.samples.dao.run;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;

import io.vertigo.core.node.AutoCloseableApp;
import io.vertigo.core.node.config.ModuleConfig;
import io.vertigo.core.node.config.NodeConfigBuilder;
import io.vertigo.core.util.InjectorUtil;
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
			InjectorUtil.injectMembers(sample);
			//-----
			sample.step1();
		}
	}

	void step1() {
		LogManager.getLogger(this.getClass()).info(movieServices.getMovieById(3678598L));
	}

}
