package io.vertigo.samples.dao.sevices;

import javax.inject.Inject;

import io.vertigo.dynamo.transaction.Transactional;
import io.vertigo.lang.Assertion;
import io.vertigo.samples.dao.dao.MyActorDAO;
import io.vertigo.samples.dao.domain.MyActor;

@Transactional
public class ActorServicesImpl implements ActorServices {

	@Inject
	private MyActorDAO myActorDAO;

	@Override
	public void saveMyActor(final MyActor myActor) {
		Assertion.checkNotNull(myActor);
		// ---
		myActorDAO.save(myActor);

	}

	@Override
	public MyActor getMyActorById(final Long actId) {
		Assertion.checkNotNull(actId);
		// ---
		return myActorDAO.get(actId);
	}

	@Override
	public void deleteMyActor(final MyActor myActor) {
		Assertion.checkNotNull(myActor);
		Assertion.checkNotNull(myActor.getActId());
		// ---
		myActorDAO.delete(myActor.getActId());

	}

}
