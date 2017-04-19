package io.vertigo.pandora.services.persons;

import java.util.List;
import java.util.Optional;

import io.vertigo.dynamo.collections.ListFilter;
import io.vertigo.dynamo.collections.model.FacetedQueryResult;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.domain.model.DtListState;
import io.vertigo.dynamo.search.model.SearchQuery;
import io.vertigo.lang.Component;
import io.vertigo.pandora.domain.persons.Person;
import io.vertigo.pandora.domain.persons.PersonIndex;

public interface PersonServices extends Component {

	Person getPerson(Long id);

	FacetedQueryResult<PersonIndex, SearchQuery> searchPersons(String criteria, List<ListFilter> listFilters, DtListState dtListState, Optional<String> group);

	void save(final Person person);

	//-------------
	//  Private
	//-------------

	DtList<PersonIndex> getPersonIndex(List<Long> personIds);

}
