package io.vertigo.samples.vui.services;

import javax.inject.Inject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.vertigo.datamodel.data.model.DtList;
import io.vertigo.samples.support.AbstractSampleVuiRollbackTestCase;
import io.vertigo.samples.vui.domain.Movie;
import io.vertigo.samples.vui.domain.MovieDisplay;

/**
 * Integration test that modifies data: it runs in a transaction rolled back at the end,
 * so Japan has still no movie for the other tests.
 */
public class MovieServicesRollbackTest extends AbstractSampleVuiRollbackTestCase {

	private static final long JAPAN_ID = 392L;

	@Inject
	private MovieServices movieServices;

	@Test
	public void testSaveMovieOfCountry() {
		final Movie movie = new Movie();
		movie.setName("Le Voyage de Chihiro");
		movie.setYear(2001);
		movie.setCouId(JAPAN_ID);
		movieServices.save(movie);

		final DtList<MovieDisplay> movies = movieServices.getMoviesDisplayByCountry(JAPAN_ID);
		Assertions.assertEquals(1, movies.size());
		Assertions.assertEquals("Le Voyage de Chihiro", movies.get(0).getName());
	}
}
