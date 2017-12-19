package io.vertigo.samples.crystal.services;

import javax.inject.Inject;

import io.vertigo.commons.transaction.Transactional;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.lang.Assertion;
import io.vertigo.samples.crystal.dao.MovieDAO;
import io.vertigo.samples.crystal.domain.Movie;
import io.vertigo.samples.crystal.domain.Role;

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

	@Override
	public DtList<Role> getRolesByMovie(final Long movId) {
		Assertion.checkNotNull(movId);
		// ---
		return movieDAO.get(movId).getRoleList();
	}

}
