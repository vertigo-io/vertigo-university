package io.vertigo.samples.components;

import io.vertigo.lang.Plugin;

public interface OperationPlugin extends Plugin {
	int calc(int[] values);
}
