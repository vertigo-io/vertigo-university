package io.vertigo.demo.dao.commande;

import javax.inject.Inject;
import io.vertigo.dynamo.impl.store.util.DAO;
import io.vertigo.dynamo.store.StoreManager;
import io.vertigo.dynamo.store.StoreServices;
import io.vertigo.dynamo.task.TaskManager;
import io.vertigo.demo.domain.commande.LigneCommande;

/**
 * DAO : Accès à un object (DTO, DTC). 
 * LigneCommandeDAO
 */
public final class LigneCommandeDAO extends DAO<LigneCommande, java.lang.Long> implements StoreServices {
	 
	/**
	 * Contructeur.
	 * @param storeManager Manager de persistance
	 * @param taskManager Manager de Task
	 */
	@Inject
	public LigneCommandeDAO(final StoreManager storeManager, final TaskManager taskManager) {
		super(LigneCommande.class, storeManager, taskManager);
	}
	

}
