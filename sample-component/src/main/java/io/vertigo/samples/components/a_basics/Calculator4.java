package io.vertigo.samples.components.a_basics;

import io.vertigo.lang.Activeable;
import io.vertigo.lang.Component;

public class Calculator4 implements Component, Activeable {

	@Override
	public void start() {
		System.out.println("component " + this.getClass().getSimpleName() + " is started");
	}

	@Override
	public void stop() {
		System.out.println("component " + this.getClass().getSimpleName() + " is stopped");
	}

	public int sum(final int... values) {
		int sum = 0;
		for (final int value : values) {
			sum += value;
		}
		return sum;
	}
}
