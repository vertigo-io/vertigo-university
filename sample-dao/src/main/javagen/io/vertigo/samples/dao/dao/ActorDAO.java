package io.vertigo.samples.dao.dao;

import javax.inject.Inject;
import io.vertigo.dynamo.impl.store.util.DAO;
import io.vertigo.dynamo.store.StoreManager;
import io.vertigo.dynamo.store.StoreServices;
import io.vertigo.dynamo.task.TaskManager;
import io.vertigo.samples.dao.domain.Actor;

/**
 * DAO : Accès à un object (DTO, DTC). 
 * ActorDAO
 */
public final class ActorDAO extends DAO<Actor, java.lang.Long> implements StoreServices {

	/**
	 * Contructeur.
	 * @param storeManager Manager de persistance
	 * @param taskManager Manager de Task
	 */
	@Inject
	public ActorDAO(final StoreManager storeManager, final TaskManager taskManager) {
		super(Actor.class, storeManager, taskManager);
	}

}
