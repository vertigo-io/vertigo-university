package io.vertigo.samples.crystal.services;

import io.vertigo.dynamo.store.StoreServices;
import io.vertigo.samples.crystal.domain.Movie;

public interface MovieServices extends StoreServices {

	Movie getMovieById(Long movId);

}
