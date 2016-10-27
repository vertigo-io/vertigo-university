package io.vertigo.samples.dao.services;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.store.StoreServices;
import io.vertigo.samples.dao.domain.Actor;
import io.vertigo.samples.dao.domain.Country;
import io.vertigo.samples.dao.domain.Movie;
import io.vertigo.samples.dao.domain.MovieByYear;
import io.vertigo.samples.dao.domain.MovieDisplay;

public interface MovieServices extends StoreServices {

	Movie getMovieById(Long movId);

	DtList<Movie> findMoviesByCriteria(String title, Integer year);

	DtList<Movie> findMoviesByKsp(String title, Integer year);

	DtList<Actor> getActorsByMovie1(Long movId);

	DtList<Actor> getActorsByMovie2(Long movId);

	void addActorToMovie(Long actId, Long movId, String role);

	DtList<Movie> findMoviesByKspWhereIn(String title, Integer year, DtList<Country> countries);

	DtList<Movie> getMoviesWith100Actors();

	DtList<MovieDisplay> getMovieDisplay();

	DtList<MovieByYear> getMoviesByDate();

}
