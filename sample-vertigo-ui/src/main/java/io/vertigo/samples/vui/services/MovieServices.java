package io.vertigo.samples.vui.services;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.vertigo.commons.transaction.Transactional;
import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.VUserException;
import io.vertigo.core.lang.WrappedException;
import io.vertigo.core.node.component.Component;
import io.vertigo.datafactory.collections.model.FacetedQueryResult;
import io.vertigo.datafactory.collections.model.SelectedFacetValues;
import io.vertigo.datafactory.search.SearchManager;
import io.vertigo.datafactory.search.model.SearchQueryBuilder;
import io.vertigo.datamodel.criteria.Criterions;
import io.vertigo.datamodel.structure.model.DtList;
import io.vertigo.datamodel.structure.model.DtListState;
import io.vertigo.datamodel.structure.model.DtObject;
import io.vertigo.datamodel.structure.util.VCollectors;
import io.vertigo.datastore.entitystore.EntityStoreManager;
import io.vertigo.samples.vui.VuiPAO;
import io.vertigo.samples.vui.dao.ActorDAO;
import io.vertigo.samples.vui.dao.MovieDAO;
import io.vertigo.samples.vui.domain.Actor;
import io.vertigo.samples.vui.domain.Movie;
import io.vertigo.samples.vui.domain.MovieIndex;
import io.vertigo.samples.vui.domain.Role;
import io.vertigo.samples.vui.search.MovieIndexSearchClient;

@Transactional
public class MovieServices implements Component {
	private static Logger logger = LogManager.getLogger(MovieServices.class);

	@Inject
	private Optional<MovieIndexSearchClient> movieIndexSearchClient;
	@Inject
	private MovieDAO movieDAO;
	@Inject
	private ActorDAO actorDAO;
	@Inject
	private VuiPAO vuiPAO;

	@Inject
	private Optional<SearchManager> searchManager;

	@Inject
	private EntityStoreManager entityStoreManager;

	public Movie getById(final Long movId) {
		Assertion.check().isNotNull(movId);
		//---
		return movieDAO.get(movId);
	}

	public DtList<Movie> getMovies(final DtListState dtListState) {
		return movieDAO.findAll(Criterions.alwaysTrue(), dtListState.withDefault(250, null, null));
	}

	public void save(final Movie movie) {
		movieDAO.save(movie);
	}

	public void saveList(final DtList<Movie> movies) {
		movieDAO.updateMoviesBatch(movies);
	}

	public List<Long> getActorsIdsByMovie(final Long movId) {
		Assertion.check().isNotNull(movId);
		//---
		return vuiPAO.getActorsIdsByMovie(movId);
	}

	public DtList<MovieIndex> getMovieIndex(final List<Long> movieIds) {
		return vuiPAO.loadMovieIndex(movieIds);
	}

	public long indexMovies() {
		try {
			return searchManager.get().reindexAll(searchManager.get().findFirstIndexDefinitionByKeyConcept(Movie.class)).get();
		} catch (InterruptedException | ExecutionException e) {
			throw WrappedException.wrap(e);
		}
	}

	public FacetedQueryResult searchMovies(final String criteria, final SelectedFacetValues selectedFacetValues, final DtListState listState) {
		try {
			final SearchQueryBuilder searchQueryBuilder = movieIndexSearchClient.get().createSearchQueryBuilderMovieWithFacets(criteria, selectedFacetValues);
			return movieIndexSearchClient.get().loadList(searchQueryBuilder.build(), listState);
		} catch (final VUserException e) {
			if (e.getCause() != null) {
				logger.warn(e.getCause());
			}
			throw e;
		}
	}

	public DtList<Movie> getMoviesInCountries(final List<Long> countryIds) {
		Assertion.check().isNotNull(countryIds);
		//---
		return movieDAO.getMoviesInCountries(countryIds);
	}

	public Movie loadMovieWithRoles(final Long movId) {
		final Movie movie = getById(movId);
		movie.role().load();
		return movie;
	}

	public DtList<Role> getRolesByMovie(final Long movId) {
		Assertion.check().isNotNull(movId);
		// ---
		final Movie movie = movieDAO.get(movId);
		// two instructions with accessor , the fluent style is broken to avoid transparent loads within loops
		movie.role().load();
		return movie.role().get();
	}

	public DtList<Actor> getActorsByMovie(final Long movId) {
		Assertion.check().isNotNull(movId);
		//---
		return actorDAO.getActorsByMovie(movId);
	}

	public DtList<Role> sortRoles(final DtList<Role> roles, final DtListState dtListState) {
		DtList<Role> sortedList;
		if (dtListState.getSortFieldName().isPresent() && dtListState.getSortFieldName().get().equals("asCharacter")) {
			sortedList = roles;
			final Comparator<Role> comparator = new Comparator<>() {
				@Override
				public int compare(final Role o1, final Role o2) {
					final int sortDesc = dtListState.isSortDesc().orElse(false) ? 1 : -1;
					final int order1 = extractOrder(o1.getAsCharacter());
					final int order2 = extractOrder(o2.getAsCharacter());
					if (order1 == order2) {
						return o1.getAsCharacter().compareToIgnoreCase(o2.getAsCharacter()) * sortDesc;
					} else if (order1 == -1) {
						return sortDesc;
					} else if (order2 == -1) {
						return -sortDesc;
					}
					return (order1 - order2) * sortDesc;
				}

				private int extractOrder(final String asCharacter) {
					final int begin = asCharacter.indexOf('<');
					final int end = asCharacter.indexOf('>', begin);
					if (begin > 0 && end > 0) {
						return Integer.parseInt(asCharacter.substring(begin + 1, end));
					}
					return -1;
				}
			};
			sortedList = roles.stream()
					.sorted(comparator)
					.collect(VCollectors.toDtList(roles.getDefinition()));
		} else {
			sortedList = applySort(roles, dtListState);
		}
		return applyPagination(sortedList, dtListState.withDefault(250, null, null));
	}

	/*private <D extends DtObject> DtList<D> applySortAndPagination(final DtList<D> unFilteredList, final DtListState dtListState) {
		final DtList<D> sortedList = applySort(unFilteredList, dtListState);
		return applyPagination(sortedList, dtListState);
	}*/

	private <D extends DtObject> DtList<D> applySort(final DtList<D> unFilteredList, final DtListState dtListState) {
		final DtList<D> sortedList;
		if (dtListState.getSortFieldName().isPresent()) {
			sortedList = entityStoreManager.sort(unFilteredList, dtListState.getSortFieldName().get(), dtListState.isSortDesc().get());
		} else {
			sortedList = unFilteredList;
		}
		return sortedList;
	}

	private <D extends DtObject> DtList<D> applyPagination(final DtList<D> unFilteredList, final DtListState dtListState) {
		if (dtListState.getSkipRows() >= unFilteredList.size()) {
			return new DtList<>(unFilteredList.getDefinition());
		} else if (dtListState.getMaxRows().isPresent()) {
			return unFilteredList
					.stream()
					.skip(dtListState.getSkipRows())
					.limit(dtListState.getMaxRows().get())
					.collect(VCollectors.toDtList(unFilteredList.getDefinition()));
		}
		return unFilteredList;
	}

}
