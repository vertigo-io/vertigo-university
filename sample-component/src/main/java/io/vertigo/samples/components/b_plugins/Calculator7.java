package io.vertigo.samples.components.b_plugins;

import io.vertigo.lang.Component;
import io.vertigo.lang.Option;
import io.vertigo.samples.aspects.SpyManager;

import javax.inject.Inject;
import javax.inject.Named;

public class Calculator7 implements Component {
	@Inject
	@Named("spyManager")
	private Option<SpyManager> spyManagerOption;

	public int sum(final int... values) {
		if (spyManagerOption.isDefined()) {
			spyManagerOption.get().log("call sum() ");
		}
		if (spyManagerOption.isEmpty()) {
			//here is anempty option
		}

		int sum = 0;
		for (final int value : values) {
			sum += value;
		}
		return sum;
	}
}
