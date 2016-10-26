package io.vertigo.samples.dao.sevices;

import javax.inject.Inject;

import io.vertigo.dynamo.transaction.Transactional;
import io.vertigo.lang.Assertion;
import io.vertigo.samples.dao.dao.MovieDAO;
import io.vertigo.samples.dao.domain.Movie;

@Transactional
public class MovieServicesImpl implements MovieServices {

	@Inject
	private MovieDAO movieDAO;

	@Override
	public void saveMovie(final Movie movie) {
		Assertion.checkNotNull(movie);
		// ---
		movieDAO.save(movie);
	}

}
