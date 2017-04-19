package io.vertigo.samples.plugins;

import java.util.stream.IntStream;

import io.vertigo.samples.components.b_plugins.OperationPlugin;

public final class MinOperationPlugin implements OperationPlugin {

	@Override
	public int calc(final int[] values) {
		return IntStream.of(values)
				.min().orElseThrow(() -> new IllegalArgumentException("no min"));
	}

	@Override
	public String getOperator() {
		return "min";
	}
}
