package io.vertigo.samples.dao.services;

import java.util.Optional;

import javax.inject.Inject;

import io.vertigo.commons.transaction.Transactional;
import io.vertigo.dynamo.domain.model.DtList;
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
	public Optional<Long> fillActors(final long limit, final long offset) {
		final DtList<Actor> existingActors = actorDAO.loadActorsByChunk(limit, offset);
		if (!existingActors.isEmpty()) {
			reprisePAO.insertActorsBatch(existingActors);
			return Optional.of(existingActors.get(existingActors.size() - 1).getActId());
		}
		return Optional.empty();

	}

	@Override
	public Long countMovies() {
		return reprisePAO.countMovies();
	}

	@Override
	public Optional<Long> fillMovies(final long limit, final long offset) {
		final DtList<Movie> existingMovies = movieDAO.loadMoviesByChunk(limit, offset);
		if (!existingMovies.isEmpty()) {
			reprisePAO.insertMoviesBatch(existingMovies);
			return Optional.of(existingMovies.get(existingMovies.size() - 1).getMovId());
		}
		return Optional.empty();

	}

	@Override
	public Long countRoles() {
		return reprisePAO.countRoles();
	}

	@Override
	public Optional<Long> fillRoles(final long limit, final long offset) {
		final DtList<Role> existingRoles = roleDAO.loadRolesByChunk(limit, offset);
		if (!existingRoles.isEmpty()) {
			reprisePAO.insertRolesBatch(existingRoles);
			return Optional.of(existingRoles.get(existingRoles.size() - 1).getRolId());
		}
		return Optional.empty();

	}

}
