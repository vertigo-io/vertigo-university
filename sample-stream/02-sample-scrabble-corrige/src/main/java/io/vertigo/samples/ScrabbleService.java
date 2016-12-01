package io.vertigo.samples;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author dt
 *
 */
public class ScrabbleService {

	private static final Map<Character, Integer> SCRABBLE_POINT = buildMapScore();

	private static final Map<Character, Integer> SCRABBLE_OCCURENCE = buildMapOccurence();

	public static <K, V> Map.Entry<K, V> entry(final K key, final V value) {
		return new AbstractMap.SimpleEntry<>(key, value);
	}

	public static <K, U> Collector<Map.Entry<K, U>, ?, Map<K, U>> entriesToMap() {
		return Collectors.toMap((e) -> e.getKey(), (e) -> e.getValue());
	}

	private static Map<Character, Integer> buildMapScore() {
		return Collections.unmodifiableMap(Stream.of(
				entry('A', 9),
				entry('B', 3),
				entry('C', 3),
				entry('D', 2),
				entry('E', 1),
				entry('F', 4),
				entry('G', 2),
				entry('H', 4),
				entry('I', 1),
				entry('J', 8),
				entry('K', 10),
				entry('L', 1),
				entry('M', 2),
				entry('N', 1),
				entry('O', 1),
				entry('P', 3),
				entry('Q', 8),
				entry('R', 1),
				entry('S', 1),
				entry('T', 1),
				entry('U', 1),
				entry('V', 4),
				entry('W', 10),
				entry('X', 10),
				entry('Y', 10),
				entry('Z', 10))
				.collect(entriesToMap()));
	}

	private static Map<Character, Integer> buildMapOccurence() {
		return Collections.unmodifiableMap(Stream.of(
				entry('A', 9),
				entry('B', 2),
				entry('C', 2),
				entry('D', 3),
				entry('E', 15),
				entry('F', 2),
				entry('G', 2),
				entry('H', 2),
				entry('I', 8),
				entry('J', 1),
				entry('K', 1),
				entry('L', 5),
				entry('M', 3),
				entry('N', 6),
				entry('O', 6),
				entry('P', 2),
				entry('Q', 1),
				entry('R', 6),
				entry('S', 6),
				entry('T', 6),
				entry('U', 6),
				entry('V', 2),
				entry('W', 1),
				entry('X', 1),
				entry('Y', 1),
				entry('Z', 1))
				.collect(entriesToMap()));
	}

	/**
	 * @throws IOException
	 *
	 */
	public Map<String, Long> histogram(final Path text) throws IOException {
		Map<String, Long> histo;
		try (Stream<String> lines = Files.lines(text)) {
			histo = lines
					.flatMap(line -> Arrays.stream(line.split("[\\s|,|\\.]|--")))
					.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		}
		return histo;
	}

	/**
	 * @throws IOException
	 *
	 */
	public Map<String, Long> histogram2(final Path text) throws IOException {
		final Map<String, Long> histo;
		try (Stream<String> lines = Files.lines(text)) {
			histo = lines
					.flatMap(line -> Arrays.stream(line.split("[\\s|,|\\.]|--")))
					.collect(() -> new HashMap<>(), (map, key) -> map.compute(key, (k, v) -> (v == null) ? 1 : v + 1L), (x, y) -> x.putAll(y));
		}

		return histo;
	}

	/**
	 *
	 */
	public int scoreScrabble(final String word) {
		final int score = word.chars()
				.map(c -> SCRABBLE_POINT.getOrDefault(Character.toUpperCase((char) c), 0))
				.sum();
		return score;
	}

	/**
	 * @throws IOException
	 *
	 */
	public int meilleurScoreScrabble(final Path text) throws IOException {
		OptionalInt score;
		try (Stream<String> lines = Files.lines(text)) {
			score = lines
					.flatMap(line -> Arrays.<String> stream(line.split("[\\s|,|\\.]|--")))
					.mapToInt(word -> scoreScrabble(word))
					.max();

		}

		return score.orElse(0);
	}

	/**
	 * @throws IOException
	 *
	 */
	public Map<Integer, List<String>> histogramScore(final Path text) throws IOException {
		final Map<Integer, List<String>> score;
		try (Stream<String> lines = Files.lines(text)) {
			score = lines
					.flatMap(line -> Arrays.<String> stream(line.split("[\\s|,|\\.]|--")))
					.distinct()
					.collect(Collectors.groupingBy(x -> scoreScrabble(x)));
		}
		return score;
	}

}
