package io.vertigo.samples.components.c_aop;

import io.vertigo.lang.Component;
import io.vertigo.samples.aspects.Spy;

public class Calculator8 implements Component {

	@Spy
	public int sum(final int... values) {
		int sum = 0;
		for (final int value : values) {
			sum += value;
		}
		return sum;
	}
}
