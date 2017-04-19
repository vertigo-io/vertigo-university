package io.vertigo.demo.dao.referentiel;

import javax.inject.Inject;
import io.vertigo.app.Home;
import io.vertigo.dynamo.task.metamodel.TaskDefinition;
import io.vertigo.dynamo.task.model.Task;
import io.vertigo.dynamo.task.model.TaskBuilder;
import io.vertigo.dynamo.impl.store.util.DAO;
import io.vertigo.dynamo.store.StoreManager;
import io.vertigo.dynamo.store.StoreServices;
import io.vertigo.dynamo.task.TaskManager;
import io.vertigo.demo.domain.referentiel.CodePostal;

/**
 * DAO : Accès à un object (DTO, DTC). 
 * CodePostalDAO
 */
public final class CodePostalDAO extends DAO<CodePostal, java.lang.Long> implements StoreServices {
	 
	/**
	 * Contructeur.
	 * @param storeManager Manager de persistance
	 * @param taskManager Manager de Task
	 */
	@Inject
	public CodePostalDAO(final StoreManager storeManager, final TaskManager taskManager) {
		super(CodePostal.class, storeManager, taskManager);
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
	 * Execute la tache TK_LOAD_CODE_POSTAL_BY_DEP_ID.
	 * @param depId Long 
	 * @return io.vertigo.dynamo.domain.model.DtList<io.vertigo.demo.domain.referentiel.CodePostal> dtcCodePostal
	*/
	public io.vertigo.dynamo.domain.model.DtList<io.vertigo.demo.domain.referentiel.CodePostal> loadCodePostalByDepId(final Long depId) {
		final Task task = createTaskBuilder("TK_LOAD_CODE_POSTAL_BY_DEP_ID")
				.addValue("DEP_ID", depId)
				.build();
		return getTaskManager()
				.execute(task)
				.getResult();
	}


}
