package io.vertigo.samples.dao.dao;

import javax.inject.Inject;

import io.vertigo.core.lang.Generated;
import io.vertigo.core.node.Home;
import io.vertigo.dynamo.task.metamodel.TaskDefinition;
import io.vertigo.dynamo.task.model.Task;
import io.vertigo.dynamo.task.model.TaskBuilder;
import io.vertigo.dynamo.impl.store.util.DAO;
import io.vertigo.dynamo.store.StoreManager;
import io.vertigo.dynamo.store.StoreServices;
import io.vertigo.dynamo.task.TaskManager;
import io.vertigo.samples.dao.domain.Actor;

/**
 * This class is automatically generated.
 * DO NOT EDIT THIS FILE DIRECTLY.
 */
@Generated
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
		return Task.builder(taskDefinition);
	}

	/**
	 * Execute la tache TkGetActorsInMovie.
	 * @param movId Long 
	 * @return DtList de Actor actors
	*/
	public io.vertigo.dynamo.domain.model.DtList<io.vertigo.samples.dao.domain.Actor> getActorsInMovie(final Long movId) {
		final Task task = createTaskBuilder("TkGetActorsInMovie")
				.addValue("movId", movId)
				.build();
		return getTaskManager()
				.execute(task)
				.getResult();
	}

	/**
	 * Execute la tache TkInsertActorsBatch.
	 * @param actorsList DtList de Actor 
	*/
	public void insertActorsBatch(final io.vertigo.dynamo.domain.model.DtList<io.vertigo.samples.dao.domain.Actor> actorsList) {
		final Task task = createTaskBuilder("TkInsertActorsBatch")
				.addValue("actorsList", actorsList)
				.build();
		getTaskManager().execute(task);
	}

	/**
	 * Execute la tache TkLoadActorsByChunk.
	 * @param limit Long 
	 * @param offset Long 
	 * @return DtList de Actor actorsList
	*/
	public io.vertigo.dynamo.domain.model.DtList<io.vertigo.samples.dao.domain.Actor> loadActorsByChunk(final Long limit, final Long offset) {
		final Task task = createTaskBuilder("TkLoadActorsByChunk")
				.addValue("limit", limit)
				.addValue("offset", offset)
				.build();
		return getTaskManager()
				.execute(task)
				.getResult();
	}

}
