package io.vertigo.samples;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import io.vertigo.app.AutoCloseableApp;
import io.vertigo.samples.components.scrabble.ScrabbleManager;
import io.vertigo.samples.components.text.TextProcessorManager;
import io.vertigo.samples.config.SampleConfigBuilder;

/***
 * @author dt
 */
public class Gutenberg {

	public static void main(final String[] args) throws URISyntaxException {
		try (final AutoCloseableApp app = new AutoCloseableApp(new SampleConfigBuilder().build())) {

			final TextProcessorManager textProcessor = app.getComponentSpace().resolve(TextProcessorManager.class);
			final ScrabbleManager scrabbleManager = app.getComponentSpace().resolve(ScrabbleManager.class);

			final Path path = Paths.get(ClassLoader.getSystemResource("maupassant.txt").toURI());

			final long resultNbLignes = textProcessor.process(path);
			System.out.println("Nombre de lignes = " + resultNbLignes);

			final long resultNbMots = scrabbleManager.nombreDeMots(path);
			System.out.println("Nombre de mots = " + resultNbMots);

			final String motLePlusLong = scrabbleManager.motLePlusLong(path);
			System.out.println("Mot le plus long = " + motLePlusLong);

			final Map<String, Long> histo = scrabbleManager.histogram(path);
			//System.out.println("Histogram = " + histo);

			final Map<String, Long> histo2 = scrabbleManager.histogram2(path);
			//System.out.println("Histogram2 = " + histo2);

			final int scoreMotLePlusLong = scrabbleManager.scoreScrabble(motLePlusLong);
			System.out.println("Score mot le plus long = " + scoreMotLePlusLong);

			final int meilleurScoreScrabble = scrabbleManager.meilleurScoreScrabble(path);
			System.out.println("Meilleur score scrabble = " + meilleurScoreScrabble);

			final Map<Integer, List<String>> histoScore = scrabbleManager.histogramScore(path);
			System.out.println("Histo score scrabble = " + histoScore);

		}
	}

}
