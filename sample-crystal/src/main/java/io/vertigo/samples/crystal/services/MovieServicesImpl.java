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
import io.vertigo.samples.crystal.CrystalPAO;
import io.vertigo.samples.crystal.dao.MovieDAO;
import io.vertigo.samples.crystal.domain.Movie;
import io.vertigo.samples.crystal.domain.MovieIndex;
import io.vertigo.samples.crystal.domain.Role;
import io.vertigo.samples.crystal.search.MovieSearchClient;

@Transactional
public class MovieServicesImpl implements MovieServices {

	private static Logger logger = LogManager.getLogger(MovieServices.class);

	@Inject
	private MovieDAO movieDAO;
	@Inject
	private MovieSearchClient movieSearchClient;

	@Inject
	private CrystalPAO crystalPAO;

	@Inject
	private SearchManager searchManager;

	@Override
	public Movie getMovieById(final Long movId) {
		Assertion.check().isNotNull(movId);
		// ---
		return movieDAO.get(movId);
	}

	@Override
	public DtList<Role> getRolesByMovie(final Long movId) {
		Assertion.check().isNotNull(movId);
		// ---
		final Movie movie = movieDAO.get(movId);
		movie.role().load();
		return movie.role().get();
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
			final SearchQueryBuilder searchQueryBuilder = movieSearchClient.createSearchQueryBuilderMovie(criteria, selectedFacetValues);
			return movieSearchClient.loadList(searchQueryBuilder.build(), listState);
		} catch (final VUserException e) {
			if (e.getCause() != null) {
				logger.warn(e.getCause());
			}
			throw e;
		}
	}

}
