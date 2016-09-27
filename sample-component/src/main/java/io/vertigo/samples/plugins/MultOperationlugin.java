package io.vertigo.samples.plugins;

import java.util.stream.IntStream;

import io.vertigo.samples.components.b_plugins.OperationPlugin;

public final class MultOperationlugin implements OperationPlugin {

	@Override
	public int calc(final int[] values) {
		return IntStream.of(values)
				.reduce(1, (x, y) -> x * y);
	}

	@Override
	public String getOperator() {
		return "mult";
	}
}
