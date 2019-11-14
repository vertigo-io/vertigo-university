package io.vertigo.samples.components.c_aop;

import java.util.stream.IntStream;

import io.vertigo.core.node.component.Component;
import io.vertigo.samples.aspects.Spy;

public class Calculator7 implements Component {

	@Spy
	public int sum(final int... values) {
		return IntStream.of(values)
				.sum();
	}
}
