package io.vertigo.samples.plugins;

import java.util.stream.IntStream;

import io.vertigo.samples.components.b_plugins.OperationPlugin;

public final class MaxOperationPlugin implements OperationPlugin {
	@Override
	public int calc(final int[] values) {
		return IntStream.of(values)
				.max().orElseThrow(() -> new IllegalArgumentException("no max"));
	}

	@Override
	public String getOperator() {
		return "max";
	}
}
