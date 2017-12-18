package io.vertigo.samples.crystal.services;

import javax.inject.Inject;

import io.vertigo.commons.transaction.Transactional;
import io.vertigo.lang.Assertion;
import io.vertigo.samples.crystal.dao.MovieDAO;
import io.vertigo.samples.crystal.domain.Movie;

@Transactional
public class MovieServicesImpl implements MovieServices {

	@Inject
	private MovieDAO movieDAO;

	@Override
	public Movie getMovieById(final Long movId) {
		Assertion.checkNotNull(movId);
		// ---
		return movieDAO.get(movId);
	}

}
