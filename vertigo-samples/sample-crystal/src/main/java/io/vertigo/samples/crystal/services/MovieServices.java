package io.vertigo.samples.crystal.services;

import java.util.List;

import io.vertigo.dynamo.collections.model.FacetedQueryResult;
import io.vertigo.dynamo.collections.model.SelectedFacetValues;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.domain.model.DtListState;
import io.vertigo.dynamo.store.StoreServices;
import io.vertigo.samples.crystal.domain.Movie;
import io.vertigo.samples.crystal.domain.MovieIndex;
import io.vertigo.samples.crystal.domain.Role;

public interface MovieServices extends StoreServices {

	Movie getMovieById(Long movId);

	DtList<Role> getRolesByMovie(Long movId);

	DtList<MovieIndex> getMovieIndex(List<Long> movieIds);

	long indexMovies();

	FacetedQueryResult searchMovies(String criteria, SelectedFacetValues selectedFacetValues, DtListState dtListState);

}
