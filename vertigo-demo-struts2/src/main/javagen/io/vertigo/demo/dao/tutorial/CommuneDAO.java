package io.vertigo.demo.dao.tutorial;

import javax.inject.Inject;
import io.vertigo.dynamo.impl.store.util.DAO;
import io.vertigo.dynamo.store.StoreManager;
import io.vertigo.dynamo.store.StoreServices;
import io.vertigo.dynamo.task.TaskManager;
import io.vertigo.demo.domain.tutorial.Commune;

/**
 * DAO : Accès à un object (DTO, DTC). 
 * CommuneDAO
 */
public final class CommuneDAO extends DAO<Commune, java.lang.Long> implements StoreServices {
	 
	/**
	 * Contructeur.
	 * @param storeManager Manager de persistance
	 * @param taskManager Manager de Task
	 */
	@Inject
	public CommuneDAO(final StoreManager storeManager, final TaskManager taskManager) {
		super(Commune.class, storeManager, taskManager);
	}
	

}
