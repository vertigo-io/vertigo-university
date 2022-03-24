package io.vertigo.samples.vui.controllers2;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;

import io.vertigo.core.node.AutoCloseableNode;
import io.vertigo.core.node.config.ModuleConfig;
import io.vertigo.core.node.config.NodeConfigBuilder;
import io.vertigo.core.util.InjectorUtil;
import io.vertigo.samples.SamplesPAO;
import io.vertigo.samples.vui.VuiPAO;
import io.vertigo.samples.vui.config.SampleConfigBuilder;
import io.vertigo.samples.vui.dao.ActorDAO;
import io.vertigo.samples.vui.dao.MovieDAO;
import io.vertigo.samples.vui.dao.RoleDAO;
import io.vertigo.samples.vui.search.MovieIndexSearchClient;
import io.vertigo.samples.vui.services.MovieSearchLoader;
import io.vertigo.samples.vui.services.MovieServices;
import io.vertigo.samples.vui.services.MovieServicesImpl;
import io.vertigo.samples.vui.webservices.MovieWebServices;

public class VuiSample {

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
		try (final AutoCloseableNode node = new AutoCloseableNode(nodeConfigBuilder.build())) {
			final VuiSample sample = new VuiSample();
			InjectorUtil.injectMembers(sample);
			//-----
			sample.step1();
		}
	}

	private static ModuleConfig defaultSampleModule() {
		return ModuleConfig.builder("Sample")
				.addComponent(MovieDAO.class)
				.addComponent(VuiPAO.class)
				.addComponent(MovieIndexSearchClient.class)
				.addComponent(MovieServices.class, MovieServicesImpl.class)
				.addComponent(MovieSearchLoader.class)
				.addComponent(MovieWebServices.class)
				.build();
	}

	void step1() {
		LogManager.getLogger(this.getClass()).info(movieServices.getMovieById(3678598L));
	}

}
