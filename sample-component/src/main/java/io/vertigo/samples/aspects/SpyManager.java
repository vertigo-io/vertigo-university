package io.vertigo.samples.aspects;

import io.vertigo.core.node.component.Component;

public class SpyManager implements Component {

	public void log(final String info) {
		System.out.println("|    |   >log :" + info);
	}
}
