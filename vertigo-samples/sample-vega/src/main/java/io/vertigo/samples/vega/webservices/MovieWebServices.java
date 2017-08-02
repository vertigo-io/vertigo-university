package io.vertigo.samples.vega.webservices;

import java.util.Collection;

import io.vertigo.samples.vega.domain.Movie;
import io.vertigo.samples.vega.domain.MovieDAO;
import io.vertigo.vega.webservice.WebServices;
import io.vertigo.vega.webservice.stereotype.AnonymousAccessAllowed;
import io.vertigo.vega.webservice.stereotype.DELETE;
import io.vertigo.vega.webservice.stereotype.GET;
import io.vertigo.vega.webservice.stereotype.PUT;
import io.vertigo.vega.webservice.stereotype.PathParam;
import io.vertigo.vega.webservice.stereotype.PathPrefix;

@PathPrefix("/movies")
public class MovieWebServices implements WebServices {
	
	// Mock-up persistence
	private final MovieDAO cinematheque = new MovieDAO();

	@AnonymousAccessAllowed
	@PUT("/{title}")
	public int putMovie(@PathParam("title") String title) {
		return cinematheque.putMovie(title);
	}
	
	@AnonymousAccessAllowed
	@DELETE("/{id}")
	public void deleteMovie(@PathParam("id") int id) {
		cinematheque.deleteMovie(id);
	}

	@AnonymousAccessAllowed
	@GET("/{id}")
	public Movie getMovie(@PathParam("id") int id) {
		return cinematheque.getMovie(id);
	}

	@AnonymousAccessAllowed
	@GET("/")
	public Collection<Movie> getAllMovies() {
		return cinematheque.listMovie();
	}
}
