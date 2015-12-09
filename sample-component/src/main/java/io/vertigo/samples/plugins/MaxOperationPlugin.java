package io.vertigo.samples.plugins;

import io.vertigo.samples.components.b_plugins.OperationPlugin;

public final class MaxOperationPlugin implements OperationPlugin {
	@Override
	public int calc(final int[] values) {
		Integer max = null;
		for (final int value : values) {
			if (max == null || value > max) {
				max = value;
			}
		}
		return max;
	}

	@Override
	public String getOperator() {
		return "max";
	}
}
