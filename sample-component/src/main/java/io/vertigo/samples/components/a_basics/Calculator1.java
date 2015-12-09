package io.vertigo.samples.components.a_basics;

import io.vertigo.lang.Component;

public class Calculator1 implements Component {

	public int sum(final int... values) {
		int sum = 0;
		for (final int value : values) {
			sum += value;
		}
		return sum;
	}
}
