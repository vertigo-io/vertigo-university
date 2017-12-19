package io.vertigo.samples.crystal.services;

import java.util.List;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;

import io.vertigo.commons.transaction.Transactional;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.lang.Assertion;
import io.vertigo.samples.SamplesPAO;
import io.vertigo.samples.crystal.dao.ActorDAO;
import io.vertigo.samples.crystal.dao.MovieDAO;
import io.vertigo.samples.crystal.dao.MovieProxyDAO;
import io.vertigo.samples.crystal.domain.Actor;
import io.vertigo.samples.crystal.domain.Country;
import io.vertigo.samples.crystal.domain.Movie;
import io.vertigo.samples.crystal.domain.Role;
import io.vertigo.samples.crystal.domain.SexeEnum;

@Transactional
public class MovieServicesImpl implements MovieServices {

	@Inject
	private MovieDAO movieDAO;
	@Inject
	private ActorDAO actorDAO;
	@Inject
	private SamplesPAO samplesPAO;
	@Inject
	private MovieProxyDAO movieProxyDAO;

	@Override
	public Movie getMovieById(final Long movId) {
		Assertion.checkNotNull(movId);
		//---
		return movieDAO.get(movId);
	}

	@Override
	public List<Long> getActorsIdsByMovie(final Long movId) {
		Assertion.checkNotNull(movId);
		//---
		return samplesPAO.getActorsIdsByMovie(movId);
	}

	@Override
	public DtList<Movie> getMoviesInCountries(final List<Long> countryIds) {
		Assertion.checkNotNull(countryIds);
		//---
		return movieDAO.getMoviesInCountries(countryIds);
	}

	@Override
	public void manipulateAccessors(final Long movId) {
		final Movie movie = getMovieById(movId);
		//--- entity
		//----- read-------
		LogManager.getLogger(this.getClass()).info("country accessor isLoaded : " + movie.country().isLoaded());
		movie.country().load();
		LogManager.getLogger(this.getClass()).info("country accessor isLoaded : " + movie.country().isLoaded());
		final Country country = movie.country().get();
		//----- write-------
		movie.country().setId(null);
		LogManager.getLogger(this.getClass()).info("country accessor isLoaded : " + movie.country().isLoaded());
		movie.country().set(country);
		LogManager.getLogger(this.getClass()).info("country accessor isLoaded : " + movie.country().isLoaded());
		//--- list
		//--- read only
		movie.role().load();
		movie.role().get();

	}

	@Override
	public Movie loadMovieWithRoles(final Long movId) {
		final Movie movie = getMovieById(movId);
		movie.role().load();
		return movie;
	}

	@Override
	public Movie loadMovieWithRolesAndReset(final Long movId) {
		final Movie movie = loadMovieWithRoles(movId);
		movie.role().reset();
		return movie;
	}

	@Override
	public DtList<Role> getRolesByMovie(final Long movId) {
		Assertion.checkNotNull(movId);
		// ---
		final Movie movie = movieDAO.get(movId);
		// two instructions with accessor , the fluent style is broken to avoid transparent loads within loops
		movie.role().load();
		return movie.role().get();
	}

	@Override
	public long countMaleActorsInMovie(final Long movId) {
		Assertion.checkNotNull(movId);
		//---
		final DtList<Actor> actors = actorDAO.getActorsByMovie(movId);

		return actors.stream()
				.filter(actor -> actor.sexe().getEnumValue() == SexeEnum.male)
				.count();
	}

	@Override
	public long countMoviesInCountry(final Long couId) {
		Assertion.checkNotNull(couId);
		//---
		return movieProxyDAO.count(couId);
	}

}
