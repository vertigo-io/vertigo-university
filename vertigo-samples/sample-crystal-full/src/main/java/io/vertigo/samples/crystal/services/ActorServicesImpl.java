package io.vertigo.samples.crystal.services;

import javax.inject.Inject;

import io.vertigo.commons.transaction.Transactional;
import io.vertigo.lang.Assertion;
import io.vertigo.samples.crystal.dao.ActorDAO;
import io.vertigo.samples.crystal.domain.Actor;

@Transactional
public class ActorServicesImpl implements ActorServices {

	@Inject
	private ActorDAO actorDAO;

	@Override
	public void saveActor(final Actor actor) {
		Assertion.checkNotNull(actor);
		// ---
		actorDAO.save(actor);

	}

	@Override
	public Actor getActorById(final Long actId) {
		Assertion.checkNotNull(actId);
		//---
		return actorDAO.get(actId);
	}

	@Override
	public void deleteMyActor(final Long actId) {
		Assertion.checkNotNull(actId);
		//---
		actorDAO.delete(actId);
	}

}
