package io.vertigo.pandora.services.persons;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.vertigo.commons.transaction.VTransactionManager;
import io.vertigo.core.component.Activeable;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.dynamo.search.SearchManager;
import io.vertigo.dynamo.search.metamodel.SearchChunk;
import io.vertigo.dynamo.search.metamodel.SearchIndexDefinition;
import io.vertigo.dynamo.search.model.SearchIndex;
import io.vertigo.dynamo.task.TaskManager;
import io.vertigo.dynamox.search.AbstractSqlSearchLoader;
import io.vertigo.pandora.domain.persons.Person;
import io.vertigo.pandora.domain.persons.PersonIndex;

public final class PersonSearchLoader extends AbstractSqlSearchLoader<Long, Person, PersonIndex> implements Activeable {

	private final PersonServices personServices;
	private final SearchManager searchManager;
	private SearchIndexDefinition indexDefinition;

	@Inject
	public PersonSearchLoader(final TaskManager taskManager, final SearchManager searchManager, final VTransactionManager transactionManager, final PersonServices personServices) {
		super(taskManager, transactionManager);
		this.searchManager = searchManager;
		this.personServices = personServices;

	}

	@Override
	public void start() {
		indexDefinition = searchManager.findIndexDefinitionByKeyConcept(Person.class);

	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<SearchIndex<Person, PersonIndex>> loadData(final SearchChunk<Person> searchChunk) {
		final List<Long> personIds = new ArrayList<>();

		for (final URI<Person> uri : searchChunk.getAllURIs()) {
			personIds.add((Long) uri.getId());
		}
		final DtList<PersonIndex> personIndexes = personServices.getPersonIndex(personIds);
		final List<SearchIndex<Person, PersonIndex>> personSearchIndexes = new ArrayList<>(personIds.size());
		for (final PersonIndex personIndex : personIndexes) {
			personSearchIndexes.add(SearchIndex.createIndex(indexDefinition,
					URI.of(indexDefinition.getKeyConceptDtDefinition(), personIndex.getPerId()), personIndex));
		}
		return personSearchIndexes;
	}

}
