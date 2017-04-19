package io.vertigo.samples.dao.dao;

import javax.inject.Inject;
import io.vertigo.dynamo.impl.store.util.DAO;
import io.vertigo.dynamo.store.StoreManager;
import io.vertigo.dynamo.store.StoreServices;
import io.vertigo.dynamo.task.TaskManager;
import io.vertigo.samples.dao.domain.MyCountry;

/**
 * DAO : Accès à un object (DTO, DTC). 
 * MyCountryDAO
 */
public final class MyCountryDAO extends DAO<MyCountry, java.lang.Long> implements StoreServices {

	/**
	 * Contructeur.
	 * @param storeManager Manager de persistance
	 * @param taskManager Manager de Task
	 */
	@Inject
	public MyCountryDAO(final StoreManager storeManager, final TaskManager taskManager) {
		super(MyCountry.class, storeManager, taskManager);
	}

}
