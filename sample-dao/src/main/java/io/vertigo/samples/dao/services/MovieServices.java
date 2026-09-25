package io.vertigo.samples.dao.services;

import io.vertigo.datamodel.data.model.DtList;
import io.vertigo.datamodel.data.model.DtListState;
import io.vertigo.datastore.impl.dao.StoreServices;
import io.vertigo.samples.dao.domain.Movie;

public interface MovieServices extends StoreServices {

	Movie createMovie(String name, Integer year);

	Movie getMovieById(Long movId);

	DtList<Movie> listMovies(String nameStart, DtListState listState);

	int countMovies(String nameStart);

}
