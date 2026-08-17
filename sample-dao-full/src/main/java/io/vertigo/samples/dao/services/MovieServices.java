package io.vertigo.samples.dao.services;

import io.vertigo.datamodel.data.model.DataList;
import io.vertigo.datastore.impl.dao.StoreServices;
import io.vertigo.samples.dao.domain.Actor;
import io.vertigo.samples.dao.domain.Country;
import io.vertigo.samples.dao.domain.Movie;
import io.vertigo.samples.dao.domain.MovieByYear;
import io.vertigo.samples.dao.domain.MovieDisplay;

public interface MovieServices extends StoreServices {

	Movie getMovieById(Long movId);

	DataList<Movie> findMoviesByCriteria(String title, Integer year);

	DataList<Movie> findMoviesByKsp(String title, Integer year);

	DataList<Actor> getActorsByMovie1(Long movId);

	DataList<Actor> getActorsByMovie2(Long movId);

	void addActorToMovie(Long actId, Long movId, String role);

	DataList<Movie> findMoviesByKspWhereIn(String title, Integer year, DataList<Country> countries);

	DataList<Movie> getMoviesWith100Actors();

	DataList<MovieDisplay> getMovieDisplay();

	DataList<MovieByYear> getMoviesByDate();

}
