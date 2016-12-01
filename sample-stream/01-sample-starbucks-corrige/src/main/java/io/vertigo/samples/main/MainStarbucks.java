package io.vertigo.samples.main;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import io.vertigo.samples.StarbucksService;
import io.vertigo.samples.dto.StarbucksDto;

/***
 * @author dt
 */
public class MainStarbucks {

	/**
	 *
	 */
	private MainStarbucks() {
		//private!
	}

	/**
	 *
	 * @param args
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public static void main(final String[] args) throws URISyntaxException, IOException {

		final Path path = Paths.get(ClassLoader.getSystemResource("starbucks.csv").toURI());

		final StarbucksService starbucksService = new StarbucksService();

		final List<StarbucksDto> starbucks = starbucksService.getStarbucks(path);

		final long nbStarbucks = starbucksService.countCity(starbucks);
		System.out.println("Nombre de starbucks = " + nbStarbucks);

		final Map<String, Long> villeHisto = starbucksService.villeHistogram(starbucks);
		System.out.println("Ville histo = " + villeHisto);

		final Optional<String> villeMaxStarbucks = starbucksService.villeAvecLePlusDeStarbucks(starbucks);
		System.out.println("Ville avec le plus de starbucks = " + villeMaxStarbucks.orElse("Aucune"));

		final Optional<String> villeMaxStarbucksFr = starbucksService.villeAvecLePlusDeStarbucksFilter(starbucks, s -> "FR".equals(s.getCountry()));
		System.out.println("Ville avec le plus de starbucks en France = " + villeMaxStarbucksFr.orElse("Aucune"));
	}

}
