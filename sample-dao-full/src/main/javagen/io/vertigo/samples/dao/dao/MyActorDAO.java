package io.vertigo.samples.dao.dao;

import javax.inject.Inject;
import io.vertigo.dynamo.impl.store.util.DAO;
import io.vertigo.dynamo.store.StoreManager;
import io.vertigo.dynamo.store.StoreServices;
import io.vertigo.dynamo.task.TaskManager;
import io.vertigo.samples.dao.domain.MyActor;

/**
 * DAO : Accès à un object (DTO, DTC). 
 * MyActorDAO
 */
public final class MyActorDAO extends DAO<MyActor, java.lang.Long> implements StoreServices {
	 
	/**
	 * Contructeur.
	 * @param storeManager Manager de persistance
	 * @param taskManager Manager de Task
	 */
	@Inject
	public MyActorDAO(final StoreManager storeManager, final TaskManager taskManager) {
		super(MyActor.class, storeManager, taskManager);
	}
	

}
