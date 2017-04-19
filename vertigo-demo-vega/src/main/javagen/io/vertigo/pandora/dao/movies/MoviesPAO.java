package io.vertigo.pandora.dao.movies;

import javax.inject.Inject;

import io.vertigo.app.Home;
import io.vertigo.lang.Assertion;
import io.vertigo.dynamo.task.TaskManager;
import io.vertigo.dynamo.task.metamodel.TaskDefinition;
import io.vertigo.dynamo.task.model.Task;
import io.vertigo.dynamo.task.model.TaskBuilder;
import io.vertigo.dynamo.store.StoreServices;

/**
 * PAO : Accès aux objects du package. 
 * MoviesPAO
 */
public final class MoviesPAO implements StoreServices {
	private final TaskManager taskManager;

	/**
	 * Constructeur.
	 * @param taskManager Manager des Task
	 */
	@Inject
	public MoviesPAO(final TaskManager taskManager) {
		Assertion.checkNotNull(taskManager);
		//-----
		this.taskManager = taskManager;
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
	 * Execute la tache TK_IMPORT_ACTOR_ROLES.
	 * @param dtc io.vertigo.dynamo.domain.model.DtList<io.vertigo.pandora.domain.persons.ActorRole> 
	*/
	public void importActorRoles(final io.vertigo.dynamo.domain.model.DtList<io.vertigo.pandora.domain.persons.ActorRole> dtc) {
		final Task task = createTaskBuilder("TK_IMPORT_ACTOR_ROLES")
				.addValue("DTC", dtc)
				.build();
		getTaskManager().execute(task);
	}

	/**
	 * Execute la tache TK_IMPORT_MOVIES.
	 * @param dtc io.vertigo.dynamo.domain.model.DtList<io.vertigo.pandora.domain.movies.Movie> 
	*/
	public void importMovies(final io.vertigo.dynamo.domain.model.DtList<io.vertigo.pandora.domain.movies.Movie> dtc) {
		final Task task = createTaskBuilder("TK_IMPORT_MOVIES")
				.addValue("DTC", dtc)
				.build();
		getTaskManager().execute(task);
	}

	/**
	 * Execute la tache TK_LOAD_MOVIE_INDEX.
	 * @param dtc io.vertigo.dynamo.domain.model.DtList<io.vertigo.pandora.domain.movies.Dummy> 
	 * @return io.vertigo.dynamo.domain.model.DtList<io.vertigo.pandora.domain.movies.MovieIndex> dtcIndex
	*/
	public io.vertigo.dynamo.domain.model.DtList<io.vertigo.pandora.domain.movies.MovieIndex> loadMovieIndex(final io.vertigo.dynamo.domain.model.DtList<io.vertigo.pandora.domain.movies.Dummy> dtc) {
		final Task task = createTaskBuilder("TK_LOAD_MOVIE_INDEX")
				.addValue("DTC", dtc)
				.build();
		return getTaskManager()
				.execute(task)
				.getResult();
	}

	/**
	 * Execute la tache TK_REMOVE_ALL_ACTOR_ROLES.
	*/
	public void removeAllActorRoles() {
		final Task task = createTaskBuilder("TK_REMOVE_ALL_ACTOR_ROLES")
				.build();
		getTaskManager().execute(task);
	}

	/**
	 * Execute la tache TK_REMOVE_ALL_MOVIES.
	*/
	public void removeAllMovies() {
		final Task task = createTaskBuilder("TK_REMOVE_ALL_MOVIES")
				.build();
		getTaskManager().execute(task);
	}

	private TaskManager getTaskManager() {
		return taskManager;
	}
}
