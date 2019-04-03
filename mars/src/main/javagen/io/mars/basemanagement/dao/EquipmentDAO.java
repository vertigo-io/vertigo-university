package io.mars.basemanagement.dao;

import javax.inject.Inject;

import io.vertigo.app.Home;
import io.vertigo.dynamo.task.metamodel.TaskDefinition;
import io.vertigo.dynamo.task.model.Task;
import io.vertigo.dynamo.task.model.TaskBuilder;
import io.vertigo.dynamo.domain.model.UID;
import io.vertigo.dynamo.impl.store.util.DAO;
import io.vertigo.dynamo.store.StoreManager;
import io.vertigo.dynamo.store.StoreServices;
import io.vertigo.dynamo.task.TaskManager;
import io.mars.basemanagement.domain.Equipment;
import io.vertigo.lang.Generated;

/**
 * This class is automatically generated.
 * DO NOT EDIT THIS FILE DIRECTLY.
 */
@Generated
public final class EquipmentDAO extends DAO<Equipment, java.lang.Long> implements StoreServices {

	/**
	 * Contructeur.
	 * @param storeManager Manager de persistance
	 * @param taskManager Manager de Task
	 */
	@Inject
	public EquipmentDAO(final StoreManager storeManager, final TaskManager taskManager) {
		super(Equipment.class, storeManager, taskManager);
	}

	/**
	 * Indique que le keyConcept associé à cette UID va être modifié.
	 * Techniquement cela interdit les opérations d'ecriture en concurrence
	 * et envoie un évenement de modification du keyConcept (à la fin de transaction eventuellement)
	 * @param UID UID du keyConcept modifié
	 * @return KeyConcept à modifier
	 */
	public Equipment readOneForUpdate(final UID<Equipment> uid) {
		return dataStore.readOneForUpdate(uid);
	}

	/**
	 * Indique que le keyConcept associé à cet id va être modifié.
	 * Techniquement cela interdit les opérations d'ecriture en concurrence
	 * et envoie un évenement de modification du keyConcept (à la fin de transaction eventuellement)
	 * @param id Clé du keyConcept modifié
	 * @return KeyConcept à modifier
	 */
	public Equipment readOneForUpdate(final java.lang.Long id) {
		return readOneForUpdate(createDtObjectUID(id));
	}

	/**
	 * Creates a taskBuilder.
	 * @param name  the name of the task
	 * @return the builder 
	 */
	private static TaskBuilder createTaskBuilder(final String name) {
		final TaskDefinition taskDefinition = Home.getApp().getDefinitionSpace().resolve(name, TaskDefinition.class);
		return Task.builder(taskDefinition);
	}

	/**
	 * Execute la tache TkGetEquipmentsByBaseCode.
	 * @param code String 
	 * @return io.vertigo.dynamo.domain.model.DtList<io.mars.basemanagement.domain.Equipment> equipments
	*/
	public io.vertigo.dynamo.domain.model.DtList<io.mars.basemanagement.domain.Equipment> getEquipmentsByBaseCode(final String code) {
		final Task task = createTaskBuilder("TkGetEquipmentsByBaseCode")
				.addValue("code", code)
				.build();
		return getTaskManager()
				.execute(task)
				.getResult();
	}

	/**
	 * Execute la tache TkGetLastPurchasedEquipmentsByBaseId.
	 * @param baseId Long 
	 * @return io.vertigo.dynamo.domain.model.DtList<io.mars.basemanagement.domain.Equipment> equipments
	*/
	public io.vertigo.dynamo.domain.model.DtList<io.mars.basemanagement.domain.Equipment> getLastPurchasedEquipmentsByBaseId(final Long baseId) {
		final Task task = createTaskBuilder("TkGetLastPurchasedEquipmentsByBaseId")
				.addValue("baseId", baseId)
				.build();
		return getTaskManager()
				.execute(task)
				.getResult();
	}

	/**
	 * Execute la tache TkInsertEquipmentsBatch.
	 * @param equipmentsList io.vertigo.dynamo.domain.model.DtList<io.mars.basemanagement.domain.Equipment> 
	*/
	public void insertEquipmentsBatch(final io.vertigo.dynamo.domain.model.DtList<io.mars.basemanagement.domain.Equipment> equipmentsList) {
		final Task task = createTaskBuilder("TkInsertEquipmentsBatch")
				.addValue("equipmentsList", equipmentsList)
				.build();
		getTaskManager().execute(task);
	}

	/**
	 * Execute la tache TkLoadEquipmentsByChunk.
	 * @param limit Long 
	 * @param offset Long 
	 * @param dateExist java.time.Instant 
	 * @return io.vertigo.dynamo.domain.model.DtList<io.mars.basemanagement.domain.Equipment> equipmentList
	*/
	public io.vertigo.dynamo.domain.model.DtList<io.mars.basemanagement.domain.Equipment> loadEquipmentsByChunk(final Long limit, final Long offset, final java.time.Instant dateExist) {
		final Task task = createTaskBuilder("TkLoadEquipmentsByChunk")
				.addValue("limit", limit)
				.addValue("offset", offset)
				.addValue("dateExist", dateExist)
				.build();
		return getTaskManager()
				.execute(task)
				.getResult();
	}

}
