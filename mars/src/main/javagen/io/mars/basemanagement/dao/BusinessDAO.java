package io.mars.basemanagement.dao;

import javax.inject.Inject;

import io.vertigo.app.Home;
import io.vertigo.dynamo.task.metamodel.TaskDefinition;
import io.vertigo.dynamo.task.model.Task;
import io.vertigo.dynamo.task.model.TaskBuilder;
import io.vertigo.dynamo.impl.store.util.DAO;
import io.vertigo.dynamo.store.StoreManager;
import io.vertigo.dynamo.store.StoreServices;
import io.vertigo.dynamo.task.TaskManager;
import io.mars.basemanagement.domain.Business;
import io.vertigo.lang.Generated;

/**
 * This class is automatically generated.
 * DO NOT EDIT THIS FILE DIRECTLY.
 */
@Generated
public final class BusinessDAO extends DAO<Business, java.lang.Long> implements StoreServices {

	/**
	 * Contructeur.
	 * @param storeManager Manager de persistance
	 * @param taskManager Manager de Task
	 */
	@Inject
	public BusinessDAO(final StoreManager storeManager, final TaskManager taskManager) {
		super(Business.class, storeManager, taskManager);
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
	 * Execute la tache TkSelectBusiness.
	 * @return io.vertigo.dynamo.domain.model.DtList<io.mars.basemanagement.domain.Business> dtcBusiness
	*/
	public io.vertigo.dynamo.domain.model.DtList<io.mars.basemanagement.domain.Business> selectBusiness() {
		final Task task = createTaskBuilder("TkSelectBusiness")
				.build();
		return getTaskManager()
				.execute(task)
				.getResult();
	}

}
