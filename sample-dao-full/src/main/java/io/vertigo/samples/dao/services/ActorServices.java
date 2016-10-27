package io.vertigo.samples.dao.services;

import io.vertigo.dynamo.store.StoreServices;
import io.vertigo.samples.dao.domain.Actor;

public interface ActorServices extends StoreServices {

	void saveActor(Actor actor);

	Actor getActorById(Long actId);

	void deleteMyActor(Long actId);

}
