package io.vertigo.samples.vui.services;

import java.util.List;

import io.vertigo.datafactory.collections.model.FacetedQueryResult;
import io.vertigo.datafactory.collections.model.SelectedFacetValues;
import io.vertigo.datamodel.structure.model.DtList;
import io.vertigo.datamodel.structure.model.DtListState;
import io.vertigo.datastore.impl.dao.StoreServices;
import io.vertigo.samples.vui.domain.Movie;
import io.vertigo.samples.vui.domain.MovieIndex;
import io.vertigo.samples.vui.domain.Role;

public interface MovieServices extends StoreServices {

	Movie getById(Long movId);

	DtList<Movie> getMovies(DtListState dtListState);

	void save(Movie movie);

	List<Long> getActorsIdsByMovie(Long movId);

	DtList<MovieIndex> getMovieIndex(List<Long> movieIds);

	long indexMovies();

	FacetedQueryResult searchMovies(String criteria, SelectedFacetValues selectedFacetValues, DtListState dtListState);

	DtList<Movie> getMoviesInCountries(final List<Long> countryIds);

	void manipulateAccessors(Long movId);

	Movie loadMovieWithRoles(Long movId);

	Movie loadMovieWithRolesAndReset(Long movId);

	DtList<Role> getRolesByMovie(Long movId);

	long countMaleActorsInMovie(Long movId);

}
