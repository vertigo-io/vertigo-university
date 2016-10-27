package io.vertigo.samples;

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
 * SamplesPAO
 */
public final class SamplesPAO implements StoreServices {
	private final TaskManager taskManager;

	/**
	 * Constructeur.
	 * @param taskManager Manager des Task
	 */
	@Inject
	public SamplesPAO(final TaskManager taskManager) {
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
	 * Execute la tache TK_GET_MOVIE_DISPLAY.
	 * @return io.vertigo.dynamo.domain.model.DtList<io.vertigo.samples.dao.domain.MovieDisplay> movies
	*/
	public io.vertigo.dynamo.domain.model.DtList<io.vertigo.samples.dao.domain.MovieDisplay> getMovieDisplay() {
		final Task task = createTaskBuilder("TK_GET_MOVIE_DISPLAY")
				.build();
		return getTaskManager()
				.execute(task)
				.getResult();
	}

	/**
	 * Execute la tache TK_GET_MOVIE_BY_YEAR.
	 * @return io.vertigo.dynamo.domain.model.DtList<io.vertigo.samples.dao.domain.MovieByYear> movies
	*/
	public io.vertigo.dynamo.domain.model.DtList<io.vertigo.samples.dao.domain.MovieByYear> getMovieByYear() {
		final Task task = createTaskBuilder("TK_GET_MOVIE_BY_YEAR")
				.build();
		return getTaskManager()
				.execute(task)
				.getResult();
	}

    
    private TaskManager getTaskManager(){
    	return taskManager;
    } 
}
