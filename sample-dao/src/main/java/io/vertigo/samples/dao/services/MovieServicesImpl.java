package io.vertigo.samples.dao.services;

import javax.inject.Inject;

import io.vertigo.commons.transaction.Transactional;
import io.vertigo.core.lang.Assertion;
import io.vertigo.datamodel.criteria.Criteria;
import io.vertigo.datamodel.criteria.Criterions;
import io.vertigo.datamodel.data.model.DtList;
import io.vertigo.datamodel.data.model.DtListState;
import io.vertigo.samples.dao.aspect.Supervision;
import io.vertigo.samples.dao.dao.MovieDAO;
import io.vertigo.samples.dao.domain.DtDefinitions;
import io.vertigo.samples.dao.domain.Movie;

@Transactional
@Supervision
public class MovieServicesImpl implements MovieServices {

	@Inject
	private MovieDAO movieDAO;

	@Override
	public Movie createMovie(final String name, final Integer year) {
		Assertion.check().isNotNull(name);
		final Movie movie = new Movie();
		movie.setName(name);
		movie.setYear(year);
		movieDAO.save(movie);
		return movie;
	}

	@Override
	public Movie getMovieById(final Long movId) {
		Assertion.check().isNotNull(movId);
		// ---
		return movieDAO.get(movId);
	}

	@Override
	public DtList<Movie> listMovies(final String nameStart, final DtListState listState) {
		return movieDAO.findAll(criteriaByPrefix(nameStart), listState.withDefault(250, null, null));
	}

	@Override
	public int countMovies(final String nameStart) {
		return movieDAO.count(criteriaByPrefix(nameStart));
	}

	private static Criteria<Movie> criteriaByPrefix(final String nameStart) {
		return (nameStart != null && !nameStart.isEmpty())
				? Criterions.startsWith(DtDefinitions.MovieFields.name, nameStart)
				: Criterions.alwaysTrue();
	}

}
