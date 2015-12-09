package io.vertigo.samples.components.a_basics;

import io.vertigo.lang.Component;

import javax.inject.Inject;
import javax.inject.Named;

public final class Calculator3 implements Component {
	private final int offset;

	@Inject
	public Calculator3(@Named("offset") final int offset) {
		this.offset = offset;
	}

	public int sum(final int... values) {
		int sum = offset;
		for (final int value : values) {
			sum += value;
		}
		return sum;
	}
}
