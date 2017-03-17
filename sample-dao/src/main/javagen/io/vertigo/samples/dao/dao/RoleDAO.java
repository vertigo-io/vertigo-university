package io.vertigo.samples.dao.dao;

import javax.inject.Inject;
import io.vertigo.dynamo.impl.store.util.DAO;
import io.vertigo.dynamo.store.StoreManager;
import io.vertigo.dynamo.store.StoreServices;
import io.vertigo.dynamo.task.TaskManager;
import io.vertigo.samples.dao.domain.Role;

/**
 * DAO : Accès à un object (DTO, DTC). 
 * RoleDAO
 */
public final class RoleDAO extends DAO<Role, java.lang.Long> implements StoreServices {

	/**
	 * Contructeur.
	 * @param storeManager Manager de persistance
	 * @param taskManager Manager de Task
	 */
	@Inject
	public RoleDAO(final StoreManager storeManager, final TaskManager taskManager) {
		super(Role.class, storeManager, taskManager);
	}

}
