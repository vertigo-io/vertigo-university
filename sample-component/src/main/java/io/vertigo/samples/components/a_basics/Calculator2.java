package io.vertigo.samples.components.a_basics;

import io.vertigo.core.component.Component;

public interface Calculator2 extends Component {

	int sum(final int... values);
}
