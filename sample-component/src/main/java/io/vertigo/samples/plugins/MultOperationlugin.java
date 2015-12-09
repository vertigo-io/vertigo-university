package io.vertigo.samples.plugins;

import io.vertigo.samples.components.b_plugins.OperationPlugin;

public final class MultOperationlugin implements OperationPlugin {

	@Override
	public int calc(final int[] values) {
		int result = 1;
		for (final int value : values) {
			result *= value;
		}
		return result;
	}

	@Override
	public String getOperator() {
		return "mult";
	}
}
