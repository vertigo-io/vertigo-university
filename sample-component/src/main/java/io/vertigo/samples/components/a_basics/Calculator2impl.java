package io.vertigo.samples.components.a_basics;

import java.util.stream.IntStream;

public final class Calculator2impl implements Calculator2 {

	@Override
	public int sum(final int... values) {
		return IntStream.of(values)
				.sum();

	}
}
