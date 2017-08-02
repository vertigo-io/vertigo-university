package io.vertigo.samples.components.scrabble;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import io.vertigo.core.component.Manager;

/**
 *
 * @author dt
 *
 */
public interface ScrabbleManager extends Manager {

	/**
	 *
	 * @param text
	 * @return
	 */
	public long nombreDeMots(Path text);

	/**
	 *
	 * @param text
	 * @return
	 */
	public int nombreDeCaracteres(final Path text);

	/**
	 *
	 * @param text
	 * @return
	 */
	public String motLePlusLong(final Path text);

	/**
	 *
	 * @param text
	 * @return
	 */
	public Map<String, Long> histogram(final Path text);

	/**
	 *
	 * @param text
	 * @return
	 */
	public Map<String, Long> histogram2(final Path text);

	/**
	 *
	 * @param word
	 * @return
	 */
	public int scoreScrabble(final String word);

	/**
	 *
	 * @param text
	 * @return
	 */
	public int meilleurScoreScrabble(final Path text);

	public Map<Integer, List<String>> histogramScore(final Path text);
}
