package io.vertigo.samples.main;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import io.vertigo.samples.ScrabbleService;

/***
 * @author dt
 */
public class MainScrabble {

	public static void main(final String[] args) throws URISyntaxException, IOException {

		final Path path = Paths.get(ClassLoader.getSystemResource("maupassant.txt").toURI());

		final ScrabbleService scrabbleService = new ScrabbleService();

		final Map<String, Long> histo = scrabbleService.histogram(path);
		//System.out.println("Histogram = " + histo);

		final Map<String, Long> histo2 = scrabbleService.histogram2(path);
		//System.out.println("Histogram2 = " + histo2);

		//final int scoreMotLePlusLong = scrabbleService.scoreScrabble(motLePlusLong);
		//System.out.println("Score mot le plus long = " + scoreMotLePlusLong);

		final int meilleurScoreScrabble = scrabbleService.meilleurScoreScrabble(path);
		System.out.println("Meilleur score scrabble = " + meilleurScoreScrabble);

		final Map<Integer, List<String>> histoScore = scrabbleService.histogramScore(path);
		System.out.println("Histo score scrabble = " + histoScore);
	}

}
