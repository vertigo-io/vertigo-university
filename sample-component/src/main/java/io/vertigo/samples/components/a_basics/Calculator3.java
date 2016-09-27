package io.vertigo.samples.components.a_basics;

import java.util.stream.IntStream;

import javax.inject.Inject;
import javax.inject.Named;

import io.vertigo.lang.Component;

public final class Calculator3 implements Component {
	/**
	 * Params can be injected on fields.
	 * Params must be named using the annotation.
	 */
	@Inject
	@Named("log")
	private boolean log;

	private final int offset;

	/**
	 * Params can be injected directely on the constructor.
	 * This way is great because
	 * it encourages immutability
	 * and
	 * it is similar to how to do when the components were created manually
	 */
	@Inject
	public Calculator3(@Named("offset") final int offset) {
		this.offset = offset;
	}

	public int sum(final int... values) {
		if (log) {
			System.out.println("log has been activated on " + this.getClass().getSimpleName());
		}
		return IntStream.of(values)
				.reduce(offset, Integer::sum);
	}
}
