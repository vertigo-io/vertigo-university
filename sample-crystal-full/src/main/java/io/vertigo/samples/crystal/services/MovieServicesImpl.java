package io.vertigo.samples.crystal.services;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.vertigo.commons.transaction.Transactional;
import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.VUserException;
import io.vertigo.core.lang.WrappedException;
import io.vertigo.datafactory.collections.model.FacetedQueryResult;
import io.vertigo.datafactory.collections.model.SelectedFacetValues;
import io.vertigo.datafactory.search.SearchManager;
import io.vertigo.datafactory.search.model.SearchQueryBuilder;
import io.vertigo.datamodel.structure.model.DtList;
import io.vertigo.datamodel.structure.model.DtListState;
import io.vertigo.samples.SamplesPAO;
import io.vertigo.samples.crystal.CrystalPAO;
import io.vertigo.samples.crystal.dao.ActorDAO;
import io.vertigo.samples.crystal.dao.MovieDAO;
import io.vertigo.samples.crystal.domain.Actor;
import io.vertigo.samples.crystal.domain.Country;
import io.vertigo.samples.crystal.domain.Movie;
import io.vertigo.samples.crystal.domain.MovieIndex;
import io.vertigo.samples.crystal.domain.Role;
import io.vertigo.samples.crystal.domain.SexeEnum;
import io.vertigo.samples.crystal.search.MovieIndexSearchClient;

@Transactional
public class MovieServicesImpl implements MovieServices {
	private static Logger logger = LogManager.getLogger(MovieServices.class);

	@Inject
	private MovieIndexSearchClient movieIndexSearchClient;
	@Inject
	private MovieDAO movieDAO;
	@Inject
	private ActorDAO actorDAO;
	@Inject
	private SamplesPAO samplesPAO;
	@Inject
	private CrystalPAO crystalPAO;

	@Inject
	private SearchManager searchManager;

	@Override
	public Movie getMovieById(final Long movId) {
		Assertion.check().isNotNull(movId);
		//---
		return movieDAO.get(movId);
	}

	@Override
	public List<Long> getActorsIdsByMovie(final Long movId) {
		Assertion.check().isNotNull(movId);
		//---
		return samplesPAO.getActorsIdsByMovie(movId);
	}

	@Override
	public DtList<MovieIndex> getMovieIndex(final List<Long> movieIds) {
		return crystalPAO.loadMovieIndex(movieIds);
	}

	@Override
	public long indexMovies() {
		try {
			return searchManager.reindexAll(searchManager.findFirstIndexDefinitionByKeyConcept(Movie.class)).get();
		} catch (InterruptedException | ExecutionException e) {
			throw WrappedException.wrap(e);
		}
	}

	@Override
	public FacetedQueryResult searchMovies(final String criteria, final SelectedFacetValues selectedFacetValues, final DtListState listState) {
		try {
			final SearchQueryBuilder searchQueryBuilder = movieIndexSearchClient.createSearchQueryBuilderMovieWithFacets(criteria, selectedFacetValues);
			return movieIndexSearchClient.loadList(searchQueryBuilder.build(), listState);
		} catch (final VUserException e) {
			if (e.getCause() != null) {
				logger.warn(e.getCause());
			}
			throw e;
		}
	}

	@Override
	public DtList<Movie> getMoviesInCountries(final List<Long> countryIds) {
		Assertion.check().isNotNull(countryIds);
		//---
		return movieDAO.getMoviesInCountries(countryIds);
	}

	@Override
	public void manipulateAccessors(final Long movId) {
		final Movie movie = getMovieById(movId);
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

	@Override
	public Movie loadMovieWithRoles(final Long movId) {
		final Movie movie = getMovieById(movId);
		movie.role().load();
		return movie;
	}

	@Override
	public Movie loadMovieWithRolesAndReset(final Long movId) {
		final Movie movie = loadMovieWithRoles(movId);
		movie.role().reset();
		return movie;
	}

	@Override
	public DtList<Role> getRolesByMovie(final Long movId) {
		Assertion.check().isNotNull(movId);
		// ---
		final Movie movie = movieDAO.get(movId);
		// two instructions with accessor , the fluent style is broken to avoid transparent loads within loops
		movie.role().load();
		return movie.role().get();
	}

	@Override
	public long countMaleActorsInMovie(final Long movId) {
		Assertion.check().isNotNull(movId);
		//---
		final DtList<Actor> actors = actorDAO.getActorsByMovie(movId);

		return actors.stream()
				.filter(actor -> actor.sexe().getEnumValue() == SexeEnum.male)
				.count();
	}

}
