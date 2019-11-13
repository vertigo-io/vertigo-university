package io.vertigo.samples.components.a_basics;

import java.util.Optional;
import java.util.stream.IntStream;

import javax.inject.Inject;

import io.vertigo.core.component.Component;
import io.vertigo.core.param.ParamValue;

public final class Calculator3 implements Component {
	/**
	 * Params can be injected on fields.
	 * Params must be named using the annotation.
	 */
	@Inject
	@ParamValue("log")
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
	public Calculator3(@ParamValue("offset") final Optional<Integer> offset) {
		this.offset = offset.orElse(0);
	}

	public int sum(final int... values) {
		if (log) {
			System.out.println("|    |   >log has been activated on " + this.getClass().getSimpleName());
		}
		return offset + IntStream.of(values)
				.reduce(0, Integer::sum);
	}
}
