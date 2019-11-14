package io.vertigo.samples.dao.run;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.vertigo.commons.transaction.VTransactionManager;
import io.vertigo.commons.transaction.VTransactionWritable;
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
import io.vertigo.samples.dao.services.CountryServices;
import io.vertigo.samples.dao.services.CountryServicesImpl;
import io.vertigo.samples.dao.services.MovieServices;
import io.vertigo.samples.dao.services.MovieServicesImpl;
import io.vertigo.samples.dao.services.RepriseServices;
import io.vertigo.samples.dao.services.RepriseServicesImpl;
import io.vertigo.samples.reprise.ReprisePAO;

public class Level6 {

	private final Logger LOGGER = LogManager.getLogger(this.getClass());

	@Inject
	private ActorServices actorServices;

	@Inject
	private VTransactionManager transactionManager;

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
						.addComponent(CountryServices.class, CountryServicesImpl.class)
						.addComponent(MovieServices.class, MovieServicesImpl.class)
						.addComponent(ActorServices.class, ActorServicesImpl.class)
						.addComponent(RepriseServices.class, RepriseServicesImpl.class)
						.build());
		try (final AutoCloseableApp app = new AutoCloseableApp(nodeConfigBuilder.build())) {
			final Level6 level6 = new Level6();
			InjectorUtil.injectMembers(level6);
			//-----
			level6.step1();
			level6.step2();
		}
	}

	void step1() {
		LOGGER.info("\n\nStep1");
		final Actor actor1 = createActor("Brad", "F");
		final Actor actor2 = createActor("GwazaCrud", "M");
		try {
			testUpdate(actor1, actor2);
		} catch (final Exception e) {
			LOGGER.info(e);
		}
		LOGGER.info(actorServices.getActorById(actor1.getActId()));

	}

	void step2() {
		LOGGER.info("\n\nStep2");
		final Actor actor1 = createActor("Brad", "F");
		final Actor actor2 = createActor("GwazaCrud", "M");
		try {
			try (VTransactionWritable transaction = transactionManager.createCurrentTransaction()) {
				testUpdate(actor1, actor2);
			}
		} catch (final Exception e) {
			LOGGER.info(e);
		}
		LOGGER.info(actorServices.getActorById(actor1.getActId()));
	}

	private void testUpdate(final Actor actor1, final Actor actor2) {
		updateActor(actor1, "M");
		updateActor(actor2, "Beaucoup trop grand pour tenir dans ce champ texte, voir même beaucoup trop grand pour tenir dans ce champ texte");
	}

	private Actor createActor(final String name, final String sexe) {
		final Actor actor = new Actor();
		actor.setName(name);
		actor.setSexe(sexe);

		actorServices.saveActor(actor);
		return actor;
	}

	private void updateActor(final Actor actor, final String sexe) {
		actor.setSexe(sexe);
		actorServices.saveActor(actor);
	}
}
