package io.vertigo.samples.account.dao;

import javax.inject.Inject;

import io.vertigo.core.lang.Generated;
import io.vertigo.datamodel.structure.model.UID;
import io.vertigo.datastore.entitystore.EntityStoreManager;
import io.vertigo.datastore.impl.dao.DAO;
import io.vertigo.datastore.impl.dao.StoreServices;
import io.vertigo.datamodel.smarttype.SmartTypeManager;
import io.vertigo.datamodel.task.TaskManager;
import io.vertigo.samples.account.domain.Movie;

/**
 * This class is automatically generated.
 * DO NOT EDIT THIS FILE DIRECTLY.
 */
@Generated
public final class MovieDAO extends DAO<Movie, java.lang.Long> implements StoreServices {

	/**
	 * Contructeur.
	 * @param entityStoreManager Manager de persistance
	 * @param taskManager Manager de Task
	 * @param smartTypeManager SmartTypeManager
	 */
	@Inject
	public MovieDAO(final EntityStoreManager entityStoreManager, final TaskManager taskManager, final SmartTypeManager smartTypeManager) {
		super(Movie.class, entityStoreManager, taskManager, smartTypeManager);
	}

	/**
	 * Indique que le keyConcept associé à cette UID va être modifié.
	 * Techniquement cela interdit les opérations d'ecriture en concurrence
	 * et envoie un évenement de modification du keyConcept (à la fin de transaction eventuellement)
	 * @param UID UID du keyConcept modifié
	 * @return KeyConcept à modifier
	 */
	public Movie readOneForUpdate(final UID<Movie> uid) {
		return entityStoreManager.readOneForUpdate(uid);
	}

	/**
	 * Indique que le keyConcept associé à cet id va être modifié.
	 * Techniquement cela interdit les opérations d'ecriture en concurrence
	 * et envoie un évenement de modification du keyConcept (à la fin de transaction eventuellement)
	 * @param id Clé du keyConcept modifié
	 * @return KeyConcept à modifier
	 */
	public Movie readOneForUpdate(final java.lang.Long id) {
		return readOneForUpdate(createDtObjectUID(id));
	}
}
