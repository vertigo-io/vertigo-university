package io.vertigo.samples.components;

import io.vertigo.lang.Component;

public interface Calculator2 extends Component {

	int sum(final int... values);
}
