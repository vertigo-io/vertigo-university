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
import io.vertigo.samples.dao.domain.MyMovie;

/**
 * DAO : Accès à un object (DTO, DTC). 
 * MyMovieDAO
 */
public final class MyMovieDAO extends DAO<MyMovie, java.lang.Long> implements StoreServices {
	 
	/**
	 * Contructeur.
	 * @param storeManager Manager de persistance
	 * @param taskManager Manager de Task
	 */
	@Inject
	public MyMovieDAO(final StoreManager storeManager, final TaskManager taskManager) {
		super(MyMovie.class, storeManager, taskManager);
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
	 * Execute la tache TK_GET_MOVIES_BY_CRITERIA.
	 * @param title String 
	 * @param year Integer 
	 * @return io.vertigo.dynamo.domain.model.DtList<io.vertigo.samples.dao.domain.MyMovie> movies
	*/
	public io.vertigo.dynamo.domain.model.DtList<io.vertigo.samples.dao.domain.MyMovie> getMoviesByCriteria(final String title, final Integer year) {
		final Task task = createTaskBuilder("TK_GET_MOVIES_BY_CRITERIA")
				.addValue("TITLE", title)
				.addValue("YEAR", year)
				.build();
		return getTaskManager()
				.execute(task)
				.getResult();
	}


}
