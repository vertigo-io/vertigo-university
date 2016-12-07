package io.vertigo.samples;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
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

	private static final String PATTERN_MOT = "[\\s|,|\\.|'|«|»|\\?|\\(|\\)|:]|--";

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

	/**
	 * @throws IOException
	 *
	 */
	public long countWords(final Path text) throws IOException {
		try (Stream<String> lines = Files.lines(text)) {
			return lines.flatMap(line -> Arrays.stream(line.split(PATTERN_MOT)))
					.filter(w -> !w.isEmpty())
					.count();
		}
	}

}
