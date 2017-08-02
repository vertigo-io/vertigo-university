package io.vertigo.samples.components.b_plugins;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.vertigo.core.component.Component;

public class Calculator6 implements Component {
	private final Map<String, OperationPlugin> operationDispatcher;

	@Inject
	public Calculator6(final List<OperationPlugin> operationPlugins) {
		operationDispatcher = new HashMap<>();
		for (final OperationPlugin operationPlugin : operationPlugins) {
			operationDispatcher.put(operationPlugin.getOperator(), operationPlugin);
		}
	}

	public int calc(final String operator, final int... values) {
		final OperationPlugin operationPlugin = operationDispatcher.get(operator);
		if (operationPlugin == null) {
			throw new IllegalArgumentException("no operation found for operator " + operator);
		}
		return operationPlugin.calc(values);
	}
}
