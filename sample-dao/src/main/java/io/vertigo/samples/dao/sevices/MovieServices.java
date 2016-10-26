package io.vertigo.samples.dao.sevices;

import io.vertigo.dynamo.store.StoreServices;
import io.vertigo.samples.dao.domain.Movie;

public interface MovieServices extends StoreServices {

	void saveMovie(Movie movie);
}
