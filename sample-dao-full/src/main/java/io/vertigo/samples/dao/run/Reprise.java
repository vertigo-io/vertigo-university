package io.vertigo.samples.dao.run;

import java.util.Optional;

import javax.inject.Inject;

import io.vertigo.app.AutoCloseableApp;
import io.vertigo.app.config.ModuleConfig;
import io.vertigo.app.config.NodeConfigBuilder;
import io.vertigo.samples.SamplesPAO;
import io.vertigo.samples.dao.config.SampleConfigBuilder;
import io.vertigo.samples.dao.dao.ActorDAO;
import io.vertigo.samples.dao.dao.CountryDAO;
import io.vertigo.samples.dao.dao.MovieDAO;
import io.vertigo.samples.dao.dao.MyActorDAO;
import io.vertigo.samples.dao.dao.MyCountryDAO;
import io.vertigo.samples.dao.dao.MyMovieDAO;
import io.vertigo.samples.dao.dao.MyRoleDAO;
import io.vertigo.samples.dao.dao.RoleDAO;
import io.vertigo.samples.dao.services.ActorServices;
import io.vertigo.samples.dao.services.ActorServicesImpl;
import io.vertigo.samples.dao.services.MovieServices;
import io.vertigo.samples.dao.services.MovieServicesImpl;
import io.vertigo.samples.dao.services.RepriseServices;
import io.vertigo.samples.dao.services.RepriseServicesImpl;
import io.vertigo.samples.reprise.ReprisePAO;
import io.vertigo.util.InjectorUtil;

public class Reprise {

	@Inject
	private RepriseServices repriseServices;

	public static void main(final String[] args) {
		final NodeConfigBuilder nodeConfigBuilder = SampleConfigBuilder.createNodeConfigBuilderWithoutCrebase();
		nodeConfigBuilder.addModule(ModuleConfig.builder("mineDAO")
				.addComponent(MyMovieDAO.class)
				.addComponent(MyActorDAO.class)
				.addComponent(MyRoleDAO.class)
				.addComponent(MyCountryDAO.class)
				.addComponent(MovieDAO.class)
				.addComponent(ActorDAO.class)
				.addComponent(RoleDAO.class)
				.addComponent(CountryDAO.class)
				.addComponent(ReprisePAO.class)
				.addComponent(SamplesPAO.class)
				.build())
				.addModule(ModuleConfig.builder("mineServices")
						.addComponent(MovieServices.class, MovieServicesImpl.class)
						.addComponent(ActorServices.class, ActorServicesImpl.class)
						.addComponent(RepriseServices.class, RepriseServicesImpl.class)
						.build());

		try (final AutoCloseableApp app = new AutoCloseableApp(nodeConfigBuilder.build())) {
			final Reprise sample = new Reprise();
			InjectorUtil.injectMembers(sample);
			//-----
			sample.step1();
			sample.step2();
			sample.step3();
			sample.step4();
		}
	}

	void step1() {
		repriseServices.fillCountries();
	}

	void step2() {
		final long chunksize = 1000L;
		Optional<Long> offset = Optional.of(0L);
		while (offset.isPresent()) {
			offset = repriseServices.fillActors(chunksize, offset.get());

		}
	}

	void step3() {
		final long chunksize = 1000L;
		Optional<Long> offset = Optional.of(0L);
		while (offset.isPresent()) {
			offset = repriseServices.fillMovies(chunksize, offset.get());

		}
	}

	void step4() {
		final long chunksize = 1000L;
		Optional<Long> offset = Optional.of(0L);
		while (offset.isPresent()) {
			offset = repriseServices.fillRoles(chunksize, offset.get());

		}
	}

}
