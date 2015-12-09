package io.vertigo.samples.components;

import io.vertigo.lang.Plugin;

public interface OperationPlugin extends Plugin {
	public final class MultOperatorPlugin implements OperationPlugin {

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

	int calc(int[] values);

	String getOperator();
}
