package io.vertigo.samples.vega.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Cinematheque {

	// Mock-up persistence
	private Map<Integer, Movie> cinematheque = null;
	private int sequence = 0;

	// Initialize
	public Cinematheque() {
		cinematheque = new HashMap<Integer, Movie>();
		for (int id = 0; id < 10; id++) {
			Movie movie = new Movie().setId(id).setTitle(" sample movie number '" + sequence + "'");
			cinematheque.put(sequence, movie);
			sequence++;
		}
	}

	public Movie getMovie(int id) {
		return cinematheque.get(id);
	}
	
	public Collection<Movie> listMovie() {
		return cinematheque.values();
	}

	public int putMovie(String title) {
		// Does not look up for duplicates
		final Movie newMovie = new Movie().setId(sequence).setTitle(title + "("+ sequence +")");
		cinematheque.put(sequence, newMovie);
		sequence++;
		return newMovie.getId();
	}
	
	public void deleteMovie(int id) {
		cinematheque.remove(id);
	}
	
}
