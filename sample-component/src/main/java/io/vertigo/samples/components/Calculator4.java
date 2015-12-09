package io.vertigo.samples.components;

import io.vertigo.lang.Component;

import javax.inject.Inject;

public final class Calculator4 implements Component {
	@Inject
	private OperationPlugin operationPlugin;

	/**
	* when an operation is too complicated or can be executed in different ways, it's preferable to use a plugin.
	* a plugin is a kind of param.
	 * 
	 * a param defines the args to execute a task.
	 * a plugin defines 'how' a task can be executed.
	 * 
	 * let's imagine a car.
	 * This car has a motor.
	 * A motor is a plugin, at the factory you can change the motor (to improve power or to change to an electric one) 
	 * 
	 * A plugin is encapsulated.
	 * A plugin can not be used outside the component to which it belongs.
	 * 
	 */
	public int sum(final int... values) {
		return operationPlugin.calc(values);
	}
}
