package io.vertigo.pandora.services.persons;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import io.vertigo.commons.transaction.Transactional;
import io.vertigo.dynamo.collections.ListFilter;
import io.vertigo.dynamo.collections.model.FacetedQueryResult;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.domain.model.DtListState;
import io.vertigo.dynamo.search.model.SearchQuery;
import io.vertigo.dynamo.search.model.SearchQueryBuilder;
import io.vertigo.pandora.dao.persons.PersonDAO;
import io.vertigo.pandora.dao.persons.PersonsPAO;
import io.vertigo.pandora.domain.movies.Dummy;
import io.vertigo.pandora.domain.persons.Person;
import io.vertigo.pandora.domain.persons.PersonIndex;

@Transactional
public class PersonServicesImpl implements PersonServices {

	@Inject
	private PersonDAO personDAO;
	@Inject
	private PersonsPAO personsPAO;

	@Override
	public Person getPerson(final Long id) {
		return personDAO.get(id);
	}
	//
	//	/**
	//	 * Complete person's movies informations
	//	 * @param movie
	//	 */
	//	public void completePersonMoviesInfo(final Person person) {
	//		//complete movies info
	//		setMovieExistsInDB(person.getMovieLinks());
	//
	//	}
	//
	//	public void setMovieExistsInDB(final DtList<MovieLink> movies) {
	//		if (null != movies) {
	//			for (final MovieLink link : movies) {
	//				final Optional<AlloCineData> data = kvStoreManager.find(BDD_MOVIES, String.valueOf(link.getCode()), AlloCineData.class);
	//				link.setExistsInBdd(data.isPresent());
	//			}
	//		}
	//	}

	@Override
	public void save(final Person person) {
		personDAO.save(person);
	}

	@Override
	public DtList<PersonIndex> getPersonIndex(final List<Long> personIds) {
		final DtList<Dummy> ids = new DtList<>(Dummy.class);
		for (final Long personId : personIds) {
			final Dummy id = new Dummy();
			id.setValue(personId);
			ids.add(id);
		}
		return personsPAO.loadPersonIndex(ids);
	}

	@Override
	public FacetedQueryResult<PersonIndex, SearchQuery> searchPersons(final String criteria, final List<ListFilter> listFilters, final DtListState dtListState, final Optional<String> group) {
		final SearchQueryBuilder searchQueryBuilder = personDAO.createSearchQueryBuilderPerson(criteria, listFilters);
		if (group.isPresent()) {
			searchQueryBuilder.withFacetClustering(group.get());
		}
		return personDAO.loadList(searchQueryBuilder.build(), dtListState);
	}
}
