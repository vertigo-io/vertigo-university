package io.vertigo.samples.components.scrabble;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.function.Function;
import java.util.stream.Collectors;

import io.vertigo.core.lang.WrappedException;

/**
 *
 * @author dt
 *
 */
public class ScrabbleManagerImpl implements ScrabbleManager {

	private static final Map<Character, Integer> SCRABBLE_POINT = buildMapScore();

	private static final Map<Character, Integer> SCRABBLE_OCCURENCE = buildMapOccurence();

	/**
	 *
	 * @param text
	 * @return
	 */
	@Override
	public long nombreDeMots(final Path text) {

		try {
			return Files.lines(text)
					.map(line -> line.split("[\\s|,|\\.]|--").length)
					.reduce(0, (x, y) -> x + y + 1);
		} catch (final IOException e) {
			throw WrappedException.wrap(e);
		}
	}

	/**
	 *
	 */
	@Override
	public int nombreDeCaracteres(final Path text) {
		try {
			return Files.lines(text)
					.flatMap(line -> Arrays.stream(line.split("[\\s|,|\\.]|--")))
					.map(String::length)
					.reduce(0, (x, y) -> x + y);
		} catch (final IOException e) {
			throw WrappedException.wrap(e);
		}
	}

	/**
	 *
	 */
	@Override
	public String motLePlusLong(final Path text) {
		try {

			return Files.lines(text)
					.flatMap(line -> Arrays.stream(line.split("[\\s|,|\\.]|--")))
					.reduce("", (x, y) -> x.length() > y.length() ? x : y);

		} catch (final IOException e) {
			throw WrappedException.wrap(e);
		}
	}

	/**
	 *
	 */
	@Override
	public Map<String, Long> histogram(final Path text) {
		Map<String, Long> histo;
		try {
			histo = Files.lines(text)
					.flatMap(line -> Arrays.stream(line.split("[\\s|,|\\.]|--")))
					.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		} catch (final IOException e) {
			throw WrappedException.wrap(e);
		}

		return histo;
	}

	/**
	 *
	 */
	@Override
	public Map<String, Long> histogram2(final Path text) {
		final Map<String, Long> histo;
		try {
			histo = Files.lines(text)
					.flatMap(line -> Arrays.stream(line.split("[\\s|,|\\.]|--")))
					.collect(HashMap::new, (map, key) -> map.compute(key, (k, v) -> v == null ? 1 : v + 1L), HashMap::putAll);
		} catch (final IOException e) {
			throw WrappedException.wrap(e);
		}

		return histo;
	}

	private static Map<Character, Integer> buildMapScore() {
		final Map<Character, Integer> mb = Map.ofEntries(
				Map.entry('A', 9),
				Map.entry('B', 3),
				Map.entry('C', 3),
				Map.entry('D', 2),
				Map.entry('E', 1),
				Map.entry('F', 4),
				Map.entry('G', 2),
				Map.entry('H', 4),
				Map.entry('I', 1),
				Map.entry('J', 8),
				Map.entry('K', 10),
				Map.entry('L', 1),
				Map.entry('M', 2),
				Map.entry('N', 1),
				Map.entry('O', 1),
				Map.entry('P', 3),
				Map.entry('Q', 8),
				Map.entry('R', 1),
				Map.entry('S', 1),
				Map.entry('T', 1),
				Map.entry('U', 1),
				Map.entry('V', 4),
				Map.entry('W', 10),
				Map.entry('X', 10),
				Map.entry('Y', 10),
				Map.entry('Z', 10));
		return mb;
	}

	private static Map<Character, Integer> buildMapOccurence() {
		final Map<Character, Integer> mb = Map.ofEntries(
				Map.entry('A', 9), Map.entry('B', 2), Map.entry('C', 2), Map.entry('D', 3), Map.entry('E', 15), Map.entry('F', 2), Map.entry('G', 2), Map.entry('H', 2), Map.entry('I', 8), Map.entry('J', 1), Map.entry('K', 1), Map.entry('L', 5), Map.entry('M', 3), Map.entry('N', 6), Map.entry('O', 6), Map.entry('P', 2), Map.entry('Q', 1), Map.entry('R', 6), Map.entry('S', 6), Map.entry('T', 6),
				Map.entry('U', 6), Map.entry('V', 2), Map.entry('W', 1), Map.entry('X', 1), Map.entry('Y', 1), Map.entry('Z', 1));
		return mb;
	}

	/**
	 *
	 */
	@Override
	public int scoreScrabble(final String word) {
		final int score = word.chars()
				.map(c -> SCRABBLE_POINT.getOrDefault(Character.toUpperCase((char) c), 0))
				.sum();
		return score;
	}

	/**
	 *
	 */
	@Override
	public int meilleurScoreScrabble(final Path text) {
		OptionalInt score;
		try {
			score = Files.lines(text)
					.flatMap(line -> Arrays.<String>stream(line.split("[\\s|,|\\.]|--")))
					.mapToInt(this::scoreScrabble)
					.max();

		} catch (final IOException e) {
			throw WrappedException.wrap(e);
		}
		return score.orElse(0);
	}

	/**
	 *
	 */
	@Override
	public Map<Integer, List<String>> histogramScore(final Path text) {
		final Map<Integer, List<String>> score;
		try {
			score = Files.lines(text)
					.flatMap(line -> Arrays.<String>stream(line.split("[\\s|,|\\.]|--")))
					.distinct()
					.collect(Collectors.groupingBy(this::scoreScrabble));
		} catch (final IOException e) {
			throw WrappedException.wrap(e);
		}
		return score;
	}

}
