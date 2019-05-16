package io.vertigo.pandora.webservices.movies;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import io.vertigo.dynamo.collections.model.FacetedQueryResult;
import io.vertigo.dynamo.collections.model.SelectedFacetValues;
import io.vertigo.dynamo.domain.model.DtListState;
import io.vertigo.dynamo.search.model.SearchQuery;
import io.vertigo.pandora.domain.movies.Movie;
import io.vertigo.pandora.domain.movies.MovieCaract;
import io.vertigo.pandora.domain.movies.MovieIndex;
import io.vertigo.pandora.domain.movies.MovieSynopsis;
import io.vertigo.pandora.domain.movies.MovieTrailer;
import io.vertigo.pandora.services.movies.MovieServices;
import io.vertigo.vega.engines.webservice.json.UiContext;
import io.vertigo.vega.webservice.WebServices;
import io.vertigo.vega.webservice.stereotype.AnonymousAccessAllowed;
import io.vertigo.vega.webservice.stereotype.GET;
import io.vertigo.vega.webservice.stereotype.IncludedFields;
import io.vertigo.vega.webservice.stereotype.InnerBodyParam;
import io.vertigo.vega.webservice.stereotype.POST;
import io.vertigo.vega.webservice.stereotype.PUT;
import io.vertigo.vega.webservice.stereotype.PathParam;
import io.vertigo.vega.webservice.stereotype.PathPrefix;
import io.vertigo.vega.webservice.stereotype.QueryParam;

@PathPrefix("/movies")
public class MovieWebServices implements WebServices {
	@Inject
	private MovieServices movieServices;

	@AnonymousAccessAllowed
	@GET("/search")
	public FacetedQueryResult<MovieIndex, SearchQuery> searchMovies(@QueryParam("q") final String criteria,
			@QueryParam("group") final Optional<String> clusteringFacetName, final DtListState dtListState) {
		return search(criteria, SelectedFacetValues.empty().build(), clusteringFacetName, dtListState);
	}

	@AnonymousAccessAllowed
	@POST("/search")
	public FacetedQueryResult search(@InnerBodyParam("criteria") final String criteria,
			@InnerBodyParam("facets") final SelectedFacetValues selectedFacetValues,
			@InnerBodyParam("group") final Optional<String> clusteringFacetName, final DtListState dtListState) {
		return movieServices.searchMovies(criteria, selectedFacetValues, dtListState,
				clusteringFacetName);
	}

	@AnonymousAccessAllowed
	@GET("/{id}")
	public Movie getMovie(final @PathParam("id") long id) {
		return movieServices.getMovie(id);
	}

	@AnonymousAccessAllowed
	@GET("/rankings/mark")
	public List<Movie> getMarkRanking() {
		return movieServices.getMarkRanking();
	}

	@AnonymousAccessAllowed
	@GET("/rankings/date")
	public List<Movie> getDateRanking() {
		return movieServices.getDateRanking();
	}

	@AnonymousAccessAllowed
	@PUT("/{id}")
	public Movie updateMovie(final @PathParam("id") long id, final Movie movie) {
		movie.setMovId(id);
		movieServices.save(movie);
		return getMovie(id);
	}

	@AnonymousAccessAllowed
	@POST("/")
	public void createMovie(final Movie movie) {
		throw new RuntimeException("Not supported yet !");
	}

	//-----------
	// Fragments
	//----------

	@AnonymousAccessAllowed
	@GET("/{id}/header")
	@IncludedFields({ "title", "movieType", "productionYear", "shortSynopsis" })
	public Movie getMovieHeader(final @PathParam("id") long id) {
		return movieServices.getMovie(id);
	}

	@AnonymousAccessAllowed
	@GET("/{id}/caract")
	public MovieCaract getMovieCaract(final @PathParam("id") long id) {
		return movieServices.getMovie(id, MovieCaract.class);
	}

	@AnonymousAccessAllowed
	@PUT("/{id}/caract")
	public MovieCaract updateMovieCaract(final @PathParam("id") long id, final MovieCaract movieCaract) {
		movieCaract.setMovId(id);
		movieServices.saveFragment(movieCaract);
		return movieServices.getMovie(id, MovieCaract.class);
	}

	@AnonymousAccessAllowed
	@GET("/{id}/synopsis")
	public MovieSynopsis getMovieSynopsis(final @PathParam("id") long id) {
		return movieServices.getMovie(id, MovieSynopsis.class);
	}

	@AnonymousAccessAllowed
	@PUT("/{id}/synopsis")
	public MovieSynopsis updateMovieSynopsis(final @PathParam("id") long id, final MovieSynopsis movieSynopsis) {
		movieSynopsis.setMovId(id);
		movieServices.saveFragment(movieSynopsis);
		return movieServices.getMovie(id, MovieSynopsis.class);
	}

	@AnonymousAccessAllowed
	@GET("/{id}/trailer")
	public MovieTrailer getMovieTrailer(final @PathParam("id") long id) {
		return movieServices.getMovie(id, MovieTrailer.class);
	}

	@AnonymousAccessAllowed
	@PUT("/{id}/trailer")
	public MovieTrailer updateMovieTrailer(final @PathParam("id") long id, final MovieTrailer movieTrailer) {
		movieTrailer.setMovId(id);
		movieServices.saveFragment(movieTrailer);
		return movieServices.getMovie(id, MovieTrailer.class);
	}

	@AnonymousAccessAllowed
	@GET("/{id}/casting")
	public UiContext getCasting(final @PathParam("id") long id) {
		final UiContext uiContext = new UiContext();
		uiContext.put("actors", movieServices.getActors(id));
		uiContext.put("writers", movieServices.getWriters(id));
		uiContext.put("camera", movieServices.getCamera(id));
		uiContext.put("producers", movieServices.getProducers(id));
		uiContext.put("directors", movieServices.getDirectors(id));
		return uiContext;
	}
}
