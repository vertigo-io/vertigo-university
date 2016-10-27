package io.vertigo.samples.dao.services;

import io.vertigo.dynamo.store.StoreServices;
import io.vertigo.samples.dao.domain.MyActor;

public interface ActorServices extends StoreServices {

	void saveMyActor(MyActor myActor);

	MyActor getMyActorById(Long actId);

	void deleteMyActor(MyActor myActor);

}
