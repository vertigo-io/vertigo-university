package io.vertigo.samples.components;

public final class Calculator2impl implements Calculator2 {

	@Override
	public int sum(final int... values) {
		int sum = 0;
		for (final int value : values) {
			sum += value;
		}
		return sum;
	}
}
