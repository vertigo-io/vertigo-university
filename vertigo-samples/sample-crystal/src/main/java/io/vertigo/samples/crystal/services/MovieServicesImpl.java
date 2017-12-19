package io.vertigo.samples.crystal.services;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import io.vertigo.commons.transaction.Transactional;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.search.SearchManager;
import io.vertigo.lang.Assertion;
import io.vertigo.lang.WrappedException;
import io.vertigo.samples.crystal.CrystalPAO;
import io.vertigo.samples.crystal.dao.MovieDAO;
import io.vertigo.samples.crystal.domain.Movie;
import io.vertigo.samples.crystal.domain.MovieIndex;
import io.vertigo.samples.crystal.domain.Role;

@Transactional
public class MovieServicesImpl implements MovieServices {

	@Inject
	private MovieDAO movieDAO;

	@Inject
	private CrystalPAO crystalPAO;

	@Inject
	private SearchManager searchManager;

	@Override
	public Movie getMovieById(final Long movId) {
		Assertion.checkNotNull(movId);
		// ---
		return movieDAO.get(movId);
	}

	@Override
	public DtList<Role> getRolesByMovie(final Long movId) {
		Assertion.checkNotNull(movId);
		// ---
		return movieDAO.get(movId).getRoleList();
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

}
