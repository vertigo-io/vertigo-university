package io.vertigo.samples.plugins;

import io.vertigo.samples.components.OperationPlugin;

public final class MinOperationPlugin implements OperationPlugin {

	@Override
	public int calc(final int[] values) {
		Integer min = null;
		for (final int value : values) {
			if (min == null || value < min) {
				min = value;
			}
		}
		return min;
	}

	@Override
	public String getOperator() {
		return "min";
	}
}
