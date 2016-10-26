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
import io.vertigo.samples.dao.domain.Country;

/**
 * DAO : Accès à un object (DTO, DTC). 
 * CountryDAO
 */
public final class CountryDAO extends DAO<Country, java.lang.Long> implements StoreServices {
	 
	/**
	 * Contructeur.
	 * @param storeManager Manager de persistance
	 * @param taskManager Manager de Task
	 */
	@Inject
	public CountryDAO(final StoreManager storeManager, final TaskManager taskManager) {
		super(Country.class, storeManager, taskManager);
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
	 * Execute la tache TK_LOAD_COUNTRIES.
	 * @return io.vertigo.dynamo.domain.model.DtList<io.vertigo.samples.dao.domain.Country> countryList
	*/
	public io.vertigo.dynamo.domain.model.DtList<io.vertigo.samples.dao.domain.Country> loadCountries() {
		final Task task = createTaskBuilder("TK_LOAD_COUNTRIES")
				.build();
		return getTaskManager()
				.execute(task)
				.getResult();
	}


}
