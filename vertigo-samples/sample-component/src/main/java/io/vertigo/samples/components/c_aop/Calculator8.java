package io.vertigo.samples.components.c_aop;

import java.util.stream.IntStream;

import io.vertigo.lang.Component;
import io.vertigo.samples.aspects.Spy;

@Spy
public class Calculator8 implements Component {

	public int sum(final int... values) {
		return IntStream.of(values)
				.sum();
	}

	public int mult(final int... values) {
		return IntStream.of(values)
				.reduce(1, (a, b) -> a * b);
	}
}
