package io.vertigo.samples.dao.services;

import javax.inject.Inject;

import io.vertigo.commons.transaction.Transactional;
import io.vertigo.core.lang.Assertion;
import io.vertigo.samples.dao.aspect.Supervision;
import io.vertigo.samples.dao.dao.ActorDAO;
import io.vertigo.samples.dao.domain.Actor;

@Transactional
@Supervision
public class ActorServicesImpl implements ActorServices {

	@Inject
	private ActorDAO actorDAO;

	@Override
	public void saveActor(final Actor actor) {
		Assertion.check().isNotNull(actor);
		// ---
		actorDAO.save(actor);

	}

	@Override
	public Actor getActorById(final Long actId) {
		Assertion.check().isNotNull(actId);
		//---
		return actorDAO.get(actId);
	}

	@Override
	public void deleteMyActor(final Long actId) {
		Assertion.check().isNotNull(actId);
		//---
		actorDAO.delete(actId);
	}

}
