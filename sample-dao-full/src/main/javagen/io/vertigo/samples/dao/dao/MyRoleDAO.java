package io.vertigo.samples.dao.dao;

import javax.inject.Inject;
import io.vertigo.dynamo.impl.store.util.DAO;
import io.vertigo.dynamo.store.StoreManager;
import io.vertigo.dynamo.store.StoreServices;
import io.vertigo.dynamo.task.TaskManager;
import io.vertigo.samples.dao.domain.MyRole;

/**
 * DAO : Accès à un object (DTO, DTC). 
 * MyRoleDAO
 */
public final class MyRoleDAO extends DAO<MyRole, java.lang.Long> implements StoreServices {

	/**
	 * Contructeur.
	 * @param storeManager Manager de persistance
	 * @param taskManager Manager de Task
	 */
	@Inject
	public MyRoleDAO(final StoreManager storeManager, final TaskManager taskManager) {
		super(MyRole.class, storeManager, taskManager);
	}

}
