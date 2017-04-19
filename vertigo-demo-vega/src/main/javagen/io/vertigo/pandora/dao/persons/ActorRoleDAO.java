package io.vertigo.pandora.dao.persons;

import javax.inject.Inject;
import io.vertigo.dynamo.impl.store.util.DAO;
import io.vertigo.dynamo.store.StoreManager;
import io.vertigo.dynamo.store.StoreServices;
import io.vertigo.dynamo.task.TaskManager;
import io.vertigo.pandora.domain.persons.ActorRole;

/**
 * DAO : Accès à un object (DTO, DTC). 
 * ActorRoleDAO
 */
public final class ActorRoleDAO extends DAO<ActorRole, java.lang.Long> implements StoreServices {

	/**
	 * Contructeur.
	 * @param storeManager Manager de persistance
	 * @param taskManager Manager de Task
	 */
	@Inject
	public ActorRoleDAO(final StoreManager storeManager, final TaskManager taskManager) {
		super(ActorRole.class, storeManager, taskManager);
	}

}
