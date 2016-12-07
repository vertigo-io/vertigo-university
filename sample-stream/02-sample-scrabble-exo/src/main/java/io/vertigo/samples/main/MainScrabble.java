package io.vertigo.samples.main;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import io.vertigo.samples.ScrabbleService;

/***
 * @author dt
 */
public class MainScrabble {

	public static void main(final String[] args) throws URISyntaxException, IOException {

		final Path path = Paths.get(ClassLoader.getSystemResource("maupassant.txt").toURI());

		final ScrabbleService scrabbleService = new ScrabbleService();

		final long count = scrabbleService.countWords(path);
		System.out.println("Count = " + count);

	}

}
