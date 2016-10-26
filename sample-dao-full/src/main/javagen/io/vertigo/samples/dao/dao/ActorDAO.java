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
import io.vertigo.samples.dao.domain.Actor;

/**
 * DAO : Accès à un object (DTO, DTC). 
 * ActorDAO
 */
public final class ActorDAO extends DAO<Actor, java.lang.Long> implements StoreServices {
	 
	/**
	 * Contructeur.
	 * @param storeManager Manager de persistance
	 * @param taskManager Manager de Task
	 */
	@Inject
	public ActorDAO(final StoreManager storeManager, final TaskManager taskManager) {
		super(Actor.class, storeManager, taskManager);
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
	 * Execute la tache TK_LOAD_ACTORS_BY_CHUNK.
	 * @param limit Long 
	 * @param offset Long 
	 * @return io.vertigo.dynamo.domain.model.DtList<io.vertigo.samples.dao.domain.Actor> actorsList
	*/
	public io.vertigo.dynamo.domain.model.DtList<io.vertigo.samples.dao.domain.Actor> loadActorsByChunk(final Long limit, final Long offset) {
		final Task task = createTaskBuilder("TK_LOAD_ACTORS_BY_CHUNK")
				.addValue("LIMIT", limit)
				.addValue("OFFSET", offset)
				.build();
		return getTaskManager()
				.execute(task)
				.getResult();
	}


}
