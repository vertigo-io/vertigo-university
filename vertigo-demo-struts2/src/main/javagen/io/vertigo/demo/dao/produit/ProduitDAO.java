package io.vertigo.demo.dao.produit;

import javax.inject.Inject;
import io.vertigo.dynamo.impl.store.util.DAO;
import io.vertigo.dynamo.store.StoreManager;
import io.vertigo.dynamo.store.StoreServices;
import io.vertigo.dynamo.task.TaskManager;
import io.vertigo.demo.domain.produit.Produit;

/**
 * DAO : Accès à un object (DTO, DTC). 
 * ProduitDAO
 */
public final class ProduitDAO extends DAO<Produit, java.lang.Long> implements StoreServices {
	 
	/**
	 * Contructeur.
	 * @param storeManager Manager de persistance
	 * @param taskManager Manager de Task
	 */
	@Inject
	public ProduitDAO(final StoreManager storeManager, final TaskManager taskManager) {
		super(Produit.class, storeManager, taskManager);
	}
	

}
