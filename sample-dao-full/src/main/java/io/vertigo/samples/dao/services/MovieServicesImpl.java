package io.vertigo.samples.dao.services;

import javax.inject.Inject;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.store.criteria.FilterCriteriaBuilder;
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

	@Override
	public DtList<MyMovie> findMoviesByCriteria1(final String title, final Integer year) {
		final FilterCriteriaBuilder<MyMovie> movieFilterCriteriaBuilder = new FilterCriteriaBuilder<>();
		movieFilterCriteriaBuilder.withPrefix("NAME", title);
		movieFilterCriteriaBuilder.addFilter("YEAR", year);

		return myMovieDAO.findAll(movieFilterCriteriaBuilder.build(), 500);
	}

	@Override
	public DtList<MyMovie> findMoviesByCriteria2(final String title, final Integer year) {
		return myMovieDAO.getMoviesByCriteria(title, year);
	}

}
