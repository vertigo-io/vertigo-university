package io.vertigo.samples.dao.dao;

import javax.inject.Inject;
import io.vertigo.dynamo.impl.store.util.DAO;
import io.vertigo.dynamo.store.StoreManager;
import io.vertigo.dynamo.store.StoreServices;
import io.vertigo.dynamo.task.TaskManager;
import io.vertigo.samples.dao.domain.Movie;

/**
 * DAO : Accès à un object (DTO, DTC). 
 * MovieDAO
 */
public final class MovieDAO extends DAO<Movie, java.lang.Long> implements StoreServices {
	 
	/**
	 * Contructeur.
	 * @param storeManager Manager de persistance
	 * @param taskManager Manager de Task
	 */
	@Inject
	public MovieDAO(final StoreManager storeManager, final TaskManager taskManager) {
		super(Movie.class, storeManager, taskManager);
	}
	

}
