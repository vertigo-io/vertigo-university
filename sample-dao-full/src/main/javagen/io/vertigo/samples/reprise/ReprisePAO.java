package io.vertigo.samples.reprise;

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
 * ReprisePAO
 */
public final class ReprisePAO implements StoreServices {
	private final TaskManager taskManager;

	/**
	 * Constructeur.
	 * @param taskManager Manager des Task
	 */
	@Inject
	public ReprisePAO(final TaskManager taskManager) {
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
	 * Execute la tache TK_INSERT_COUNTRIES_BATCH.
	 * @param countryList io.vertigo.dynamo.domain.model.DtList<io.vertigo.samples.dao.domain.Country> 
	*/
	public void insertCountriesBatch(final io.vertigo.dynamo.domain.model.DtList<io.vertigo.samples.dao.domain.Country> countryList) {
		final Task task = createTaskBuilder("TK_INSERT_COUNTRIES_BATCH")
				.addValue("COUNTRY_LIST", countryList)
				.build();
		getTaskManager().execute(task);
	}

	/**
	 * Execute la tache TK_COUNT_ACTORS.
	 * @return Long count
	*/
	public Long countActors() {
		final Task task = createTaskBuilder("TK_COUNT_ACTORS")
				.build();
		return getTaskManager()
				.execute(task)
				.getResult();
	}

	/**
	 * Execute la tache TK_INSERT_ACTORS_BATCH.
	 * @param actorsList io.vertigo.dynamo.domain.model.DtList<io.vertigo.samples.dao.domain.Actor> 
	*/
	public void insertActorsBatch(final io.vertigo.dynamo.domain.model.DtList<io.vertigo.samples.dao.domain.Actor> actorsList) {
		final Task task = createTaskBuilder("TK_INSERT_ACTORS_BATCH")
				.addValue("ACTORS_LIST", actorsList)
				.build();
		getTaskManager().execute(task);
	}

	/**
	 * Execute la tache TK_COUNT_MOVIES.
	 * @return Long count
	*/
	public Long countMovies() {
		final Task task = createTaskBuilder("TK_COUNT_MOVIES")
				.build();
		return getTaskManager()
				.execute(task)
				.getResult();
	}

	/**
	 * Execute la tache TK_MIN_MOVIE.
	 * @return Long count
	*/
	public Long minMovie() {
		final Task task = createTaskBuilder("TK_MIN_MOVIE")
				.build();
		return getTaskManager()
				.execute(task)
				.getResult();
	}

	/**
	 * Execute la tache TK_MAX_MOVIE.
	 * @return Long count
	*/
	public Long maxMovie() {
		final Task task = createTaskBuilder("TK_MAX_MOVIE")
				.build();
		return getTaskManager()
				.execute(task)
				.getResult();
	}

	/**
	 * Execute la tache TK_INSERT_MOVIES_BATCH.
	 * @param moviesList io.vertigo.dynamo.domain.model.DtList<io.vertigo.samples.dao.domain.Movie> 
	*/
	public void insertMoviesBatch(final io.vertigo.dynamo.domain.model.DtList<io.vertigo.samples.dao.domain.Movie> moviesList) {
		final Task task = createTaskBuilder("TK_INSERT_MOVIES_BATCH")
				.addValue("MOVIES_LIST", moviesList)
				.build();
		getTaskManager().execute(task);
	}

	/**
	 * Execute la tache TK_COUNT_ROLES.
	 * @return Long count
	*/
	public Long countRoles() {
		final Task task = createTaskBuilder("TK_COUNT_ROLES")
				.build();
		return getTaskManager()
				.execute(task)
				.getResult();
	}

	/**
	 * Execute la tache TK_INSERT_ROLES_BATCH.
	 * @param rolesList io.vertigo.dynamo.domain.model.DtList<io.vertigo.samples.dao.domain.Role> 
	*/
	public void insertRolesBatch(final io.vertigo.dynamo.domain.model.DtList<io.vertigo.samples.dao.domain.Role> rolesList) {
		final Task task = createTaskBuilder("TK_INSERT_ROLES_BATCH")
				.addValue("ROLES_LIST", rolesList)
				.build();
		getTaskManager().execute(task);
	}

    
    private TaskManager getTaskManager(){
    	return taskManager;
    } 
}
