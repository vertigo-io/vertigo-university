package io.vertigo.samples.dao.sevices;

import io.vertigo.dynamo.store.StoreServices;

public interface RepriseServices extends StoreServices {

	void fillCountries();

	Long countActors();

	void fillActors(long limit, long offset);

	Long countMovies();

	void fillMovies(long limit, long offset, long minMovie, long maxMovie);

	Long countRoles();

	void fillRoles(long limit, long offset, long minMovie, long maxMovie);

	Long minMovie();

	Long maxMovie();

}
