package io.vertigo.samples.dao.services;

import java.util.Optional;

import io.vertigo.datastore.impl.dao.StoreServices;

public interface RepriseServices extends StoreServices {

	void fillCountries();

	Long countActors();

	Optional<Long> fillActors(long limit, long offset);

	Long countMovies();

	Optional<Long> fillMovies(long limit, long offset);

	Long countRoles();

	Optional<Long> fillRoles(long limit, long offset);

}
