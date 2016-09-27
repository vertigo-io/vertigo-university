package io.vertigo.samples.components.c_aop;

import java.util.stream.IntStream;

import io.vertigo.lang.Component;
import io.vertigo.samples.aspects.Spy;

public class Calculator8 implements Component {

	@Spy
	public int sum(final int... values) {
		return IntStream.of(values)
				.sum();
	}
}
