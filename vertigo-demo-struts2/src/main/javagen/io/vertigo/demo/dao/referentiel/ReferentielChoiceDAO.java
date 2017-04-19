package io.vertigo.demo.dao.referentiel;

import javax.inject.Inject;
import io.vertigo.dynamo.impl.store.util.DAO;
import io.vertigo.dynamo.store.StoreManager;
import io.vertigo.dynamo.store.StoreServices;
import io.vertigo.dynamo.task.TaskManager;
import io.vertigo.demo.domain.referentiel.ReferentielChoice;

/**
 * DAO : Accès à un object (DTO, DTC). 
 * ReferentielChoiceDAO
 */
public final class ReferentielChoiceDAO extends DAO<ReferentielChoice, java.lang.String> implements StoreServices {
	 
	/**
	 * Contructeur.
	 * @param storeManager Manager de persistance
	 * @param taskManager Manager de Task
	 */
	@Inject
	public ReferentielChoiceDAO(final StoreManager storeManager, final TaskManager taskManager) {
		super(ReferentielChoice.class, storeManager, taskManager);
	}
	

}
