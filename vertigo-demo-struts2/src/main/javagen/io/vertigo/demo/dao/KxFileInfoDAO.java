package io.vertigo.demo.dao;

import javax.inject.Inject;
import io.vertigo.dynamo.impl.store.util.DAO;
import io.vertigo.dynamo.store.StoreManager;
import io.vertigo.dynamo.store.StoreServices;
import io.vertigo.dynamo.task.TaskManager;
import io.vertigo.demo.domain.KxFileInfo;

/**
 * DAO : Accès à un object (DTO, DTC). 
 * KxFileInfoDAO
 */
public final class KxFileInfoDAO extends DAO<KxFileInfo, java.lang.Long> implements StoreServices {
	 
	/**
	 * Contructeur.
	 * @param storeManager Manager de persistance
	 * @param taskManager Manager de Task
	 */
	@Inject
	public KxFileInfoDAO(final StoreManager storeManager, final TaskManager taskManager) {
		super(KxFileInfo.class, storeManager, taskManager);
	}
	

}
