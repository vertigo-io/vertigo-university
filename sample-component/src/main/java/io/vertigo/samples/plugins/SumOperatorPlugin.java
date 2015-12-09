package io.vertigo.samples.plugins;

import io.vertigo.samples.components.OperationPlugin;

public final class SumOperatorPlugin implements OperationPlugin {

	@Override
	public int calc(final int[] values) {
		int sum = 0;
		for (final int value : values) {
			sum += value;
		}
		return sum;
	}
}
