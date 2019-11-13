package io.vertigo.samples.plugins;

import java.util.stream.IntStream;

import io.vertigo.samples.components.b_plugins.OperationPlugin;

public final class SumOperationPlugin implements OperationPlugin {

	@Override
	public int calc(final int[] values) {
		return IntStream.of(values)
				.sum();
	}

	@Override
	public String getOperator() {
		return "sum";
	}
}
