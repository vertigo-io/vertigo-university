package io.vertigo.samples.main;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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

		starbucksService.printStarbucks(starbucks);

	}

}
