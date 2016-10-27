package io.vertigo.samples.dao.sevices;

import javax.inject.Inject;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.transaction.Transactional;
import io.vertigo.samples.dao.dao.ActorDAO;
import io.vertigo.samples.dao.dao.CountryDAO;
import io.vertigo.samples.dao.dao.MovieDAO;
import io.vertigo.samples.dao.dao.RoleDAO;
import io.vertigo.samples.dao.domain.Actor;
import io.vertigo.samples.dao.domain.Country;
import io.vertigo.samples.dao.domain.Movie;
import io.vertigo.samples.dao.domain.Role;
import io.vertigo.samples.reprise.ReprisePAO;

@Transactional
public class RepriseServicesImpl implements RepriseServices {

	@Inject
	private CountryDAO countryDAO;
	@Inject
	private MovieDAO movieDAO;
	@Inject
	private ActorDAO actorDAO;
	@Inject
	private RoleDAO roleDAO;
	@Inject
	private ReprisePAO reprisePAO;

	@Override
	public void fillCountries() {
		final DtList<Country> existingCountries = countryDAO.loadCountries();
		reprisePAO.insertCountriesBatch(existingCountries);

	}

	@Override
	public Long countActors() {
		return reprisePAO.countActors();
	}

	@Override
	public void fillActors(final long limit, final long offset) {
		final DtList<Actor> existingActors = actorDAO.loadActorsByChunk(limit, offset);
		reprisePAO.insertActorsBatch(existingActors);

	}

	@Override
	public Long countMovies() {
		return reprisePAO.countMovies();
	}

	@Override
	public void fillMovies(final long limit, final long offset, final long minMovie, final long maxMovie) {
		final DtList<Movie> existingMovies = movieDAO.loadMoviesByChunk(limit, offset, minMovie, maxMovie);
		reprisePAO.insertMoviesBatch(existingMovies);

	}

	@Override
	public Long countRoles() {
		return reprisePAO.countRoles();
	}

	@Override
	public void fillRoles(final long limit, final long offset, final long minMovie, final long maxMovie) {
		final DtList<Role> existingMovies = roleDAO.loadRolesByChunk(limit, offset, minMovie, maxMovie);
		reprisePAO.insertRolesBatch(existingMovies);

	}

	@Override
	public Long minMovie() {
		return reprisePAO.minMovie();
	}

	@Override
	public Long maxMovie() {
		return reprisePAO.maxMovie();
	}

}
