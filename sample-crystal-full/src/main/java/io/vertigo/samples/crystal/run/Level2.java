package io.vertigo.samples.crystal.run;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;

import io.vertigo.core.node.AutoCloseableNode;
import io.vertigo.core.node.config.ModuleConfig;
import io.vertigo.core.node.config.NodeConfigBuilder;
import io.vertigo.core.util.InjectorUtil;
import io.vertigo.samples.crystal.CrystalPAO;
import io.vertigo.samples.crystal.config.SampleConfigBuilder;
import io.vertigo.samples.crystal.dao.ActorDAO;
import io.vertigo.samples.crystal.dao.MovieDAO;
import io.vertigo.samples.crystal.dao.RoleDAO;
import io.vertigo.samples.crystal.domain.Movie;
import io.vertigo.samples.crystal.search.MovieIndexSearchClient;
import io.vertigo.samples.crystal.services.MovieSearchLoader;
import io.vertigo.samples.crystal.services.MovieServices;
import io.vertigo.samples.crystal.services.MovieServicesImpl;
import io.vertigo.samples.crystal.webservices.MovieWebServices;

public class Level2 {

	//demo data, created by sql/initdata.sql
	private static final Long FORREST_GUMP_ID = 1000L;

	@Inject
	private MovieServices movieServices;

	public static void main(final String[] args) {
		final NodeConfigBuilder nodeConfigBuilder = SampleConfigBuilder.createNodeConfigBuilder(true, false, false);
		nodeConfigBuilder
				.addModule(ModuleConfig.builder("stepDao")
						.addComponent(ActorDAO.class)
						.addComponent(RoleDAO.class)
						.build())
				.addModule(defaultSampleModule());
		try (final AutoCloseableNode node = new AutoCloseableNode(nodeConfigBuilder.build())) {
			final Level2 sample = new Level2();
			InjectorUtil.injectMembers(sample);
			//-----
			sample.step1();
			sample.step2();
			sample.step3();
		}
	}

	private static ModuleConfig defaultSampleModule() {
		return ModuleConfig.builder("Sample")
				.addComponent(MovieDAO.class)
				.addComponent(CrystalPAO.class)
				.addComponent(MovieIndexSearchClient.class)
				.addComponent(MovieServices.class, MovieServicesImpl.class)
				.addComponent(MovieSearchLoader.class)
				.addComponent(MovieWebServices.class)
				.build();
	}

	void step1() {
		movieServices.manipulateAccessors(FORREST_GUMP_ID);
	}

	void step2() {
		final Movie movie = movieServices.loadMovieWithRoles(FORREST_GUMP_ID);
		LogManager.getLogger(this.getClass()).info("movie roles : {}", movie.role().get());

		final Movie movieReset = movieServices.loadMovieWithRolesAndReset(FORREST_GUMP_ID);
		LogManager.getLogger(this.getClass()).info("movie roles reset (isLoaded) : {}", movieReset.role().isLoaded());
	}

	void step3() {
		LogManager.getLogger(this.getClass()).info(movieServices.getRolesByMovie(FORREST_GUMP_ID));
	}

}
