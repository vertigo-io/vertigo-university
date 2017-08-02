package io.vertigo.samples.components.a_basics;

import java.util.stream.IntStream;

import io.vertigo.core.component.Activeable;
import io.vertigo.core.component.Component;

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
		return IntStream.of(values)
				.sum();
	}
}
