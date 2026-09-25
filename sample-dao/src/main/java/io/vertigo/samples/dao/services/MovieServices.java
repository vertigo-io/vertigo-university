package io.vertigo.samples.dao.services;

import io.vertigo.datastore.impl.dao.StoreServices;
import io.vertigo.samples.dao.domain.Movie;

public interface MovieServices extends StoreServices {

	Movie createMovie(String name, Integer year);

	Movie getMovieById(Long movId);

}
