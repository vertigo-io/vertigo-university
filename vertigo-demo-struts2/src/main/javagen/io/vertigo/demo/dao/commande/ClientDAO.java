package io.vertigo.demo.dao.commande;

import javax.inject.Inject;
import io.vertigo.dynamo.impl.store.util.DAO;
import io.vertigo.dynamo.store.StoreManager;
import io.vertigo.dynamo.store.StoreServices;
import io.vertigo.dynamo.task.TaskManager;
import io.vertigo.demo.domain.commande.Client;

/**
 * DAO : Accès à un object (DTO, DTC). 
 * ClientDAO
 */
public final class ClientDAO extends DAO<Client, java.lang.Long> implements StoreServices {
	 
	/**
	 * Contructeur.
	 * @param storeManager Manager de persistance
	 * @param taskManager Manager de Task
	 */
	@Inject
	public ClientDAO(final StoreManager storeManager, final TaskManager taskManager) {
		super(Client.class, storeManager, taskManager);
	}
	

}
