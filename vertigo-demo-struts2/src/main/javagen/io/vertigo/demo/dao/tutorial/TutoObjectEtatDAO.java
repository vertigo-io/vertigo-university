package io.vertigo.demo.dao.tutorial;

import javax.inject.Inject;
import io.vertigo.dynamo.impl.store.util.DAO;
import io.vertigo.dynamo.store.StoreManager;
import io.vertigo.dynamo.store.StoreServices;
import io.vertigo.dynamo.task.TaskManager;
import io.vertigo.demo.domain.tutorial.TutoObjectEtat;

/**
 * DAO : Accès à un object (DTO, DTC). 
 * TutoObjectEtatDAO
 */
public final class TutoObjectEtatDAO extends DAO<TutoObjectEtat, java.lang.Long> implements StoreServices {
	 
	/**
	 * Contructeur.
	 * @param storeManager Manager de persistance
	 * @param taskManager Manager de Task
	 */
	@Inject
	public TutoObjectEtatDAO(final StoreManager storeManager, final TaskManager taskManager) {
		super(TutoObjectEtat.class, storeManager, taskManager);
	}
	

}
