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

import io.vertigo.core.lang.MapBuilder;
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
					.map(line -> new Integer(line.split("[\\s|,|\\.]|--").length))
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
					.map(word -> word.length())
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
					.collect(() -> new HashMap<>(), (map, key) -> map.compute(key, (k, v) -> (v == null) ? 1 : v + 1L), (x, y) -> x.putAll(y));
		} catch (final IOException e) {
			throw WrappedException.wrap(e);
		}

		return histo;
	}

	private static Map<Character, Integer> buildMapScore() {
		final MapBuilder<Character, Integer> mb = new MapBuilder<>();
		mb.put('A', 9)
				.put('B', 3)
				.put('C', 3)
				.put('D', 2)
				.put('E', 1)
				.put('F', 4)
				.put('G', 2)
				.put('H', 4)
				.put('I', 1)
				.put('J', 8)
				.put('K', 10)
				.put('L', 1)
				.put('M', 2)
				.put('N', 1)
				.put('O', 1)
				.put('P', 3)
				.put('Q', 8)
				.put('R', 1)
				.put('S', 1)
				.put('T', 1)
				.put('U', 1)
				.put('V', 4)
				.put('W', 10)
				.put('X', 10)
				.put('Y', 10)
				.put('Z', 10);
		return mb.unmodifiable().build();
	}

	private static Map<Character, Integer> buildMapOccurence() {
		final MapBuilder<Character, Integer> mb = new MapBuilder<>();
		mb.put('A', 9)
				.put('B', 2)
				.put('C', 2)
				.put('D', 3)
				.put('E', 15)
				.put('F', 2)
				.put('G', 2)
				.put('H', 2)
				.put('I', 8)
				.put('J', 1)
				.put('K', 1)
				.put('L', 5)
				.put('M', 3)
				.put('N', 6)
				.put('O', 6)
				.put('P', 2)
				.put('Q', 1)
				.put('R', 6)
				.put('S', 6)
				.put('T', 6)
				.put('U', 6)
				.put('V', 2)
				.put('W', 1)
				.put('X', 1)
				.put('Y', 1)
				.put('Z', 1);
		return mb.unmodifiable().build();
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
					.flatMap(line -> Arrays.<String> stream(line.split("[\\s|,|\\.]|--")))
					.mapToInt(word -> scoreScrabble(word))
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
					.flatMap(line -> Arrays.<String> stream(line.split("[\\s|,|\\.]|--")))
					.distinct()
					.collect(Collectors.groupingBy(x -> scoreScrabble(x)));
		} catch (final IOException e) {
			throw WrappedException.wrap(e);
		}
		return score;
	}

}
