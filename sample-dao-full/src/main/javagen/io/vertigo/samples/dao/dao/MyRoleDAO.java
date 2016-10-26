package io.vertigo.samples.dao.dao;

import javax.inject.Inject;
import io.vertigo.app.Home;
import io.vertigo.dynamo.task.metamodel.TaskDefinition;
import io.vertigo.dynamo.task.model.Task;
import io.vertigo.dynamo.task.model.TaskBuilder;
import io.vertigo.dynamo.impl.store.util.DAO;
import io.vertigo.dynamo.store.StoreManager;
import io.vertigo.dynamo.store.StoreServices;
import io.vertigo.dynamo.task.TaskManager;
import io.vertigo.samples.dao.domain.MyRole;

/**
 * DAO : Accès à un object (DTO, DTC). 
 * MyRoleDAO
 */
public final class MyRoleDAO extends DAO<MyRole, java.lang.Long> implements StoreServices {
	 
	/**
	 * Contructeur.
	 * @param storeManager Manager de persistance
	 * @param taskManager Manager de Task
	 */
	@Inject
	public MyRoleDAO(final StoreManager storeManager, final TaskManager taskManager) {
		super(MyRole.class, storeManager, taskManager);
	}
	

	/**
	 * Creates a taskBuilder.
	 * @param name  the name of the task
	 * @return the builder 
	 */
	private static TaskBuilder createTaskBuilder(final String name) {
		final TaskDefinition taskDefinition = Home.getApp().getDefinitionSpace().resolve(name, TaskDefinition.class);
		return new TaskBuilder(taskDefinition);
	}

	/**
	 * Execute la tache TK_GET_ROLES_IN_MOVIES.
	 * @param movies io.vertigo.dynamo.domain.model.DtList<io.vertigo.samples.dao.domain.MyMovie> 
	 * @return io.vertigo.dynamo.domain.model.DtList<io.vertigo.samples.dao.domain.MyRole> roles
	*/
	public io.vertigo.dynamo.domain.model.DtList<io.vertigo.samples.dao.domain.MyRole> getRolesInMovies(final io.vertigo.dynamo.domain.model.DtList<io.vertigo.samples.dao.domain.MyMovie> movies) {
		final Task task = createTaskBuilder("TK_GET_ROLES_IN_MOVIES")
				.addValue("MOVIES", movies)
				.build();
		return getTaskManager()
				.execute(task)
				.getResult();
	}


}
