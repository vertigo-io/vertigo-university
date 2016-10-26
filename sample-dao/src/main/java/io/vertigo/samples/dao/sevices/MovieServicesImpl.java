package io.vertigo.samples.dao.sevices;

import javax.inject.Inject;

import io.vertigo.dynamo.transaction.Transactional;
import io.vertigo.lang.Assertion;
import io.vertigo.samples.dao.aspect.Supervision;
import io.vertigo.samples.dao.dao.MyMovieDAO;
import io.vertigo.samples.dao.domain.MyMovie;

@Transactional
public class MovieServicesImpl implements MovieServices {

	@Inject
	private MyMovieDAO myMovieDAO;

	@Override
	@Supervision
	public void saveMyMovie(final MyMovie myMovie) {
		Assertion.checkNotNull(myMovie);
		// ---
		myMovieDAO.save(myMovie);
	}

}
