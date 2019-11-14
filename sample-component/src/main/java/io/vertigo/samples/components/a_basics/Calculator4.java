package io.vertigo.samples.components.a_basics;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import io.vertigo.core.node.component.Activeable;
import io.vertigo.core.node.component.Component;
import io.vertigo.core.node.definition.Definition;
import io.vertigo.core.node.definition.DefinitionSpace;
import io.vertigo.core.node.definition.SimpleDefinitionProvider;

public class Calculator4 implements Component, Activeable, SimpleDefinitionProvider {

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

	@Override
	public List<Definition> provideDefinitions(final DefinitionSpace definitionSpace) {
		return Collections.emptyList();
	}
}
