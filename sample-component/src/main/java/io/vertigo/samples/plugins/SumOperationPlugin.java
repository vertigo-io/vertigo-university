package io.vertigo.samples.plugins;

import io.vertigo.samples.components.b_plugins.OperationPlugin;

public final class SumOperationPlugin implements OperationPlugin {

	@Override
	public int calc(final int[] values) {
		int sum = 0;
		for (final int value : values) {
			sum += value;
		}
		return sum;
	}

	@Override
	public String getOperator() {
		return "sum";
	}
}
