package io.vertigo.demo.dao.administration.utilisateur;

import javax.inject.Inject;
import io.vertigo.dynamo.impl.store.util.DAO;
import io.vertigo.dynamo.store.StoreManager;
import io.vertigo.dynamo.store.StoreServices;
import io.vertigo.dynamo.task.TaskManager;
import io.vertigo.demo.domain.administration.utilisateur.Login;

/**
 * DAO : Accès à un object (DTO, DTC). 
 * LoginDAO
 */
public final class LoginDAO extends DAO<Login, java.lang.Long> implements StoreServices {
	 
	/**
	 * Contructeur.
	 * @param storeManager Manager de persistance
	 * @param taskManager Manager de Task
	 */
	@Inject
	public LoginDAO(final StoreManager storeManager, final TaskManager taskManager) {
		super(Login.class, storeManager, taskManager);
	}
	

}
