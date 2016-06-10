package io.vertigo.samples.vega.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MovieDAO {

	// Mock-up persistence
	private Map<Integer, Movie> cinematheque = null;
	private int sequence = 0;

	// Initialize
	public MovieDAO() {
		cinematheque = new HashMap<>();
		for (int id = 0; id < 10; id++) {
			final Movie movie = new Movie().setId(id).setTitle(" sample movie number '" + sequence + "'");
			cinematheque.put(sequence, movie);
			sequence++;
		}
	}

	public Movie getMovie(final int id) {
		return cinematheque.get(id);
	}

	public Collection<Movie> listMovie() {
		return cinematheque.values();
	}

	public int putMovie(final String title) {
		// Does not look up for duplicates
		final Movie newMovie = new Movie().setId(sequence).setTitle(title + "(" + sequence + ")");
		cinematheque.put(sequence, newMovie);
		sequence++;
		return newMovie.getId();
	}

	public void deleteMovie(final int id) {
		cinematheque.remove(id);
	}

}
