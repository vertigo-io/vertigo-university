package io.vertigo.samples.vega.webservices;

import java.util.ArrayList;
import java.util.List;

import io.vertigo.samples.vega.domain.Movie;
import io.vertigo.vega.webservice.WebServices;
import io.vertigo.vega.webservice.stereotype.AnonymousAccessAllowed;
import io.vertigo.vega.webservice.stereotype.GET;
import io.vertigo.vega.webservice.stereotype.PathParam;
import io.vertigo.vega.webservice.stereotype.PathPrefix;

@PathPrefix("/movies")
public class MovieWebServices implements WebServices {
	
	@AnonymousAccessAllowed
	@GET("/{id}")
	public Movie getMovie(@PathParam("id") int id) {
		return new Movie().setId(id).setTitle(" sample movie number '" + id + "'");
	}

	@AnonymousAccessAllowed
	@GET("/")
	public List<Movie> getAllMovies() {
		List<Movie> movies = new ArrayList<>();
		for (int id = 0; id < 10; id++) {
			Movie movie = new Movie().setId(id).setTitle(" sample movie number '" + id + "'");
			movies.add(movie);
		}
		return movies;
	}
}
