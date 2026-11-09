package io.vertigo.samples.vui.services;

import javax.inject.Inject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.vertigo.core.lang.VUserException;
import io.vertigo.datamodel.data.model.DtList;
import io.vertigo.samples.support.SampleVuiTestExtension;
import io.vertigo.samples.vui.domain.MovieDisplay;

/**
 * Integration tests of the movies business functions: the application is started with its demo data,
 * and the tests call the service as the application does.
 */
@ExtendWith(SampleVuiTestExtension.class)
public class MovieServicesTest {

	private static final long GERMANY_ID = 250L;
	private static final long JAPAN_ID = 392L;

	@Inject
	private MovieServices movieServices;

	@Test
	public void testMoviesOfCountry() {
		final DtList<MovieDisplay> movies = movieServices.getMoviesDisplayByCountry(GERMANY_ID);
		Assertions.assertEquals(1, movies.size());
		Assertions.assertEquals("The Grand Budapest Hotel", movies.get(0).getName());
	}

	@Test
	public void testCountryWithoutMovie() {
		final VUserException exception = Assertions.assertThrows(VUserException.class,
				() -> movieServices.getMoviesDisplayByCountry(JAPAN_ID));
		Assertions.assertEquals("Aucun film pour ce pays", exception.getMessage());
	}
}
