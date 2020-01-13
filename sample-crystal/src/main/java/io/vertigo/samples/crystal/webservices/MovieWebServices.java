package io.vertigo.samples.crystal.webservices;

import javax.inject.Inject;

import io.vertigo.datafactory.collections.model.FacetedQueryResult;
import io.vertigo.datafactory.collections.model.SelectedFacetValues;
import io.vertigo.datafactory.search.model.SearchQuery;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.domain.model.DtListState;
import io.vertigo.samples.crystal.domain.Movie;
import io.vertigo.samples.crystal.domain.MovieIndex;
import io.vertigo.samples.crystal.domain.Role;
import io.vertigo.samples.crystal.services.MovieServices;
import io.vertigo.vega.webservice.WebServices;
import io.vertigo.vega.webservice.stereotype.AnonymousAccessAllowed;
import io.vertigo.vega.webservice.stereotype.GET;
import io.vertigo.vega.webservice.stereotype.InnerBodyParam;
import io.vertigo.vega.webservice.stereotype.POST;
import io.vertigo.vega.webservice.stereotype.PathParam;
import io.vertigo.vega.webservice.stereotype.PathPrefix;
import io.vertigo.vega.webservice.stereotype.QueryParam;

@PathPrefix("/movies")
public class MovieWebServices implements WebServices {
	@Inject
	private MovieServices movieServices;

	@AnonymousAccessAllowed
	@GET("/{id}")
	public Movie getMovie(final @PathParam("id") long movId) {
		return movieServices.getMovieById(movId);
	}

	@AnonymousAccessAllowed
	@GET("/{id}/roles")
	public DtList<Role> getRolesByMovie(final @PathParam("id") long movId) {
		return movieServices.getRolesByMovie(movId);
	}

	@AnonymousAccessAllowed
	@GET("/_search")
	public FacetedQueryResult<MovieIndex, SearchQuery> searchMovies(@QueryParam("q") final String criteria) {
		return movieServices.searchMovies(criteria, SelectedFacetValues.empty().build(), DtListState.of(5));
	}

	@AnonymousAccessAllowed
	@POST("/_search")
	public FacetedQueryResult<MovieIndex, SearchQuery> search(@InnerBodyParam("criteria") final String criteria,
			@InnerBodyParam("facets") final SelectedFacetValues selectedFacetValues,
			@InnerBodyParam("state") final DtListState dtListState) {
		return movieServices.searchMovies(criteria, selectedFacetValues, dtListState);
	}
}
