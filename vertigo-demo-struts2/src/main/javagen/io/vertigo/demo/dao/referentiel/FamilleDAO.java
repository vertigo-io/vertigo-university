package io.vertigo.demo.dao.referentiel;

import javax.inject.Inject;
import io.vertigo.dynamo.impl.store.util.DAO;
import io.vertigo.dynamo.store.StoreManager;
import io.vertigo.dynamo.store.StoreServices;
import io.vertigo.dynamo.task.TaskManager;
import io.vertigo.demo.domain.referentiel.Famille;

/**
 * DAO : Accès à un object (DTO, DTC). 
 * FamilleDAO
 */
public final class FamilleDAO extends DAO<Famille, java.lang.Long> implements StoreServices {
	 
	/**
	 * Contructeur.
	 * @param storeManager Manager de persistance
	 * @param taskManager Manager de Task
	 */
	@Inject
	public FamilleDAO(final StoreManager storeManager, final TaskManager taskManager) {
		super(Famille.class, storeManager, taskManager);
	}
	

}
