package io.vertigo.samples.dao.run;

import javax.inject.Inject;

import io.vertigo.core.node.AutoCloseableApp;
import io.vertigo.core.node.config.ModuleConfig;
import io.vertigo.core.node.config.NodeConfigBuilder;
import io.vertigo.core.util.InjectorUtil;
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
import io.vertigo.samples.dao.domain.Actor;
import io.vertigo.samples.dao.services.ActorServices;
import io.vertigo.samples.dao.services.ActorServicesImpl;
import io.vertigo.samples.dao.services.MovieServices;
import io.vertigo.samples.dao.services.MovieServicesImpl;
import io.vertigo.samples.dao.services.RepriseServices;
import io.vertigo.samples.dao.services.RepriseServicesImpl;
import io.vertigo.samples.reprise.ReprisePAO;

public class Level2 {

	@Inject
	private ActorServices actorServices;

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
				.addComponent(SamplesPAO.class)
				.addComponent(ReprisePAO.class)
				.build())
				.addModule(ModuleConfig.builder("mineServices")
						.addComponent(MovieServices.class, MovieServicesImpl.class)
						.addComponent(ActorServices.class, ActorServicesImpl.class)
						.addComponent(RepriseServices.class, RepriseServicesImpl.class)
						.build());
		try (final AutoCloseableApp app = new AutoCloseableApp(nodeConfigBuilder.build())) {
			final Level2 level2 = new Level2();
			InjectorUtil.injectMembers(level2);
			//-----
			level2.step1();
		}
	}

	void step1() {
		final Actor actor = new Actor();
		actor.setName("Direction Technique");
		actor.setSexe("M");

		actorServices.saveActor(actor);
		actorServices.getActorById(actor.getActId());
		actorServices.deleteMyActor(actor.getActId());
	}

}
