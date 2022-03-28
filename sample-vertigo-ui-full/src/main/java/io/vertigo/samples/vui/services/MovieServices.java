package io.vertigo.samples.vui.services;

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
import io.vertigo.samples.vui.VuiPAO;
import io.vertigo.samples.vui.dao.ActorDAO;
import io.vertigo.samples.vui.dao.MovieDAO;
import io.vertigo.samples.vui.domain.Actor;
import io.vertigo.samples.vui.domain.Country;
import io.vertigo.samples.vui.domain.Movie;
import io.vertigo.samples.vui.domain.MovieIndex;
import io.vertigo.samples.vui.domain.Role;
import io.vertigo.samples.vui.domain.SexeEnum;
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

	public Movie getById(final Long movId) {
		Assertion.check().isNotNull(movId);
		//---
		return movieDAO.get(movId);
	}

	public DtList<Movie> getMovies(final DtListState dtListState) {
		return movieDAO.findAll(Criterions.alwaysTrue(), dtListState);
	}

	public void save(final Movie movie) {
		movieDAO.save(movie);
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

	public void manipulateAccessors(final Long movId) {
		final Movie movie = getById(movId);
		//--- entity
		//----- read-------
		LogManager.getLogger(this.getClass()).info("country accessor isLoaded : " + movie.country().isLoaded());
		movie.country().load();
		LogManager.getLogger(this.getClass()).info("country accessor isLoaded : " + movie.country().isLoaded());
		final Country country = movie.country().get();
		//----- write-------
		movie.country().setId(null);
		LogManager.getLogger(this.getClass()).info("country accessor isLoaded : " + movie.country().isLoaded());
		movie.country().set(country);
		LogManager.getLogger(this.getClass()).info("country accessor isLoaded : " + movie.country().isLoaded());
		//--- list
		//--- read only
		movie.role().load();
		movie.role().get();

	}

	public Movie loadMovieWithRoles(final Long movId) {
		final Movie movie = getById(movId);
		movie.role().load();
		return movie;
	}

	public Movie loadMovieWithRolesAndReset(final Long movId) {
		final Movie movie = loadMovieWithRoles(movId);
		movie.role().reset();
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

	public long countMaleActorsInMovie(final Long movId) {
		Assertion.check().isNotNull(movId);
		//---
		final DtList<Actor> actors = actorDAO.getActorsByMovie(movId);

		return actors.stream()
				.filter(actor -> actor.sexe().getEnumValue() == SexeEnum.male)
				.count();
	}

}