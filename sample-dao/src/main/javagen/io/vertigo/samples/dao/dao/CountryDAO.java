package io.vertigo.samples.dao.dao;

import javax.inject.Inject;
import io.vertigo.dynamo.impl.store.util.DAO;
import io.vertigo.dynamo.store.StoreManager;
import io.vertigo.dynamo.store.StoreServices;
import io.vertigo.dynamo.task.TaskManager;
import io.vertigo.samples.dao.domain.Country;

/**
 * DAO : Accès à un object (DTO, DTC). 
 * CountryDAO
 */
public final class CountryDAO extends DAO<Country, java.lang.Long> implements StoreServices {
	 
	/**
	 * Contructeur.
	 * @param storeManager Manager de persistance
	 * @param taskManager Manager de Task
	 */
	@Inject
	public CountryDAO(final StoreManager storeManager, final TaskManager taskManager) {
		super(Country.class, storeManager, taskManager);
	}
	

}
