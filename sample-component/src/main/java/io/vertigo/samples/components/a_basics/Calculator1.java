package io.vertigo.samples.components.a_basics;

import java.util.stream.IntStream;

import io.vertigo.core.component.Component;

public class Calculator1 implements Component {

	public int sum(final int... values) {
		return IntStream.of(values)
				.sum();
	}
}
