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

		final long count = scrabbleService.countWords(path);
		System.out.println("Count = " + count);

		final String concat = scrabbleService.concatMot(path, ",");
		System.out.println("Concat = " + concat);

		/*final Map<String, Long> histo = scrabbleService.histogramFrequenceMotAvecCollectors(path);
		System.out.println("HistogramAvec = " + histo);
		
		final Map<String, Long> histo2 = scrabbleService.histogramFrequenceMotSansCollectors(path);
		System.out.println("HistogramSans = " + histo2);*/

		final int scoreMot = scrabbleService.scoreScrabble("abcd");
		System.out.println("Score = " + scoreMot);

		final int meilleurScoreScrabble = scrabbleService.meilleurScoreScrabble(path);
		System.out.println("Meilleur score scrabble = " + meilleurScoreScrabble);

		final Map<Integer, List<String>> histoScore = scrabbleService.histogramScore(path);
		System.out.println("Histo score scrabble = " + histoScore);
	}

}
