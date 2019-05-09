package io.vertigo.samples.crystal.run;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;

import io.vertigo.app.AutoCloseableApp;
import io.vertigo.app.config.ModuleConfig;
import io.vertigo.app.config.NodeConfigBuilder;
import io.vertigo.samples.SamplesPAO;
import io.vertigo.samples.crystal.CrystalPAO;
import io.vertigo.samples.crystal.config.SampleConfigBuilder;
import io.vertigo.samples.crystal.dao.ActorDAO;
import io.vertigo.samples.crystal.dao.MovieDAO;
import io.vertigo.samples.crystal.dao.RoleDAO;
import io.vertigo.samples.crystal.services.MovieSearchLoader;
import io.vertigo.samples.crystal.services.MovieServices;
import io.vertigo.samples.crystal.services.MovieServicesImpl;
import io.vertigo.samples.crystal.webservices.MovieWebServices;
import io.vertigo.util.InjectorUtil;

public class Level1 {

	@Inject
	private MovieServices movieServices;

	public static void main(final String[] args) {
		final NodeConfigBuilder nodeConfigBuilder = SampleConfigBuilder.createNodeConfigBuilder(true, false, false);
		nodeConfigBuilder
				.addModule(ModuleConfig.builder("stepDao")
						.addComponent(ActorDAO.class)
						.addComponent(RoleDAO.class)
						.addComponent(SamplesPAO.class)
						.build())
				.addModule(defaultSampleModule());
		try (final AutoCloseableApp app = new AutoCloseableApp(nodeConfigBuilder.build())) {
			final Level1 sample = new Level1();
			InjectorUtil.injectMembers(sample);
			//-----
			sample.step1();
			sample.step2();
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
		LogManager.getLogger(this.getClass()).info(movieServices.getActorsIdsByMovie(3678598L));
	}

	void step2() {
		final List<Long> countryIds = Arrays.asList(1223L, 1119L, 1075L, 1207L);
		LogManager.getLogger(this.getClass()).info(movieServices.getMoviesInCountries(countryIds));
	}

}
