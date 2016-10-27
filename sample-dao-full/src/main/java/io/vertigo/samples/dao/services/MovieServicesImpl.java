package io.vertigo.samples.dao.services;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.store.criteria.FilterCriteriaBuilder;
import io.vertigo.dynamo.transaction.Transactional;
import io.vertigo.lang.Assertion;
import io.vertigo.samples.dao.dao.ActorDAO;
import io.vertigo.samples.dao.dao.MovieDAO;
import io.vertigo.samples.dao.dao.RoleDAO;
import io.vertigo.samples.dao.domain.Actor;
import io.vertigo.samples.dao.domain.Country;
import io.vertigo.samples.dao.domain.Movie;
import io.vertigo.samples.dao.domain.Role;

@Transactional
public class MovieServicesImpl implements MovieServices {

	@Inject
	private MovieDAO movieDAO;
	@Inject
	private ActorDAO actorDAO;
	@Inject
	private RoleDAO roleDAO;

	@Override
	public Movie getMovieById(final Long movId) {
		Assertion.checkNotNull(movId);
		// ---
		return movieDAO.get(movId);
	}

	@Override
	public DtList<Movie> findMoviesByCriteria(final String title, final Integer year) {
		final FilterCriteriaBuilder<Movie> movieFilterCriteriaBuilder = new FilterCriteriaBuilder<>();
		movieFilterCriteriaBuilder.withPrefix("NAME", title);
		movieFilterCriteriaBuilder.addFilter("YEAR", year);

		return movieDAO.findAll(movieFilterCriteriaBuilder.build(), 500);
	}

	@Override
	public DtList<Movie> findMoviesByKsp(final String title, final Integer year) {
		return movieDAO.getMoviesByCriteria(title, year);
	}

	@Override
	public DtList<Actor> getActorsByMovie1(final Long movId) {
		final DtList<Actor> result = new DtList<>(Actor.class);
		result.addAll(
				movieDAO.get(movId).getRoleList()
						.stream()
						.map((role) -> role.getActor())
						.collect(Collectors.toList()));
		return result;
	}

	@Override
	public DtList<Actor> getActorsByMovie2(final Long movId) {
		return actorDAO.getActorsInMovie(movId);
	}

	@Override
	public void addActorToMovie(final Long actId, final Long movId, final String role) {
		Assertion.checkNotNull(actId);
		Assertion.checkNotNull(movId);
		Assertion.checkArgNotEmpty(role);
		// ---
		final Role newRole = new Role();
		newRole.setMovId(movId);
		newRole.setActId(actId);
		newRole.setAsCharacter(role);
		roleDAO.save(newRole);

	}

	@Override
	public DtList<Movie> findMoviesByKspWhereIn(final String title, final Integer year, final DtList<Country> countries) {
		Assertion.checkNotNull(countries);
		// ---
		return movieDAO.getMoviesByCriteriaWithCountry(title, Optional.ofNullable(year), countries);
	}

}
