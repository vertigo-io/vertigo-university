package io.vertigo.samples.quarto.config;

import java.util.List;

import io.vertigo.core.node.definition.Definition;
import io.vertigo.core.node.definition.DefinitionSpace;
import io.vertigo.core.node.definition.SimpleDefinitionProvider;
import io.vertigo.core.util.ListBuilder;
import io.vertigo.datamodel.impl.smarttype.formatter.FormatterDefault;
import io.vertigo.datamodel.structure.metamodel.DataType;
import io.vertigo.datamodel.structure.metamodel.Domain;
import io.vertigo.datamodel.structure.metamodel.FormatterDefinition;

public final class QuartoDefinitionProvider implements SimpleDefinitionProvider {

	@Override
	public List<Definition> provideDefinitions(final DefinitionSpace definitionSpace) {
		final FormatterDefinition formatterDefinition = new FormatterDefinition("FmtDefault", FormatterDefault.class.getName(), null);

		final Domain domainText = Domain.builder("DoText", DataType.String)
				.withFormatter(formatterDefinition).build();

		return new ListBuilder<Definition>()
				.add(formatterDefinition)
				.add(domainText)
				.build();
	}
}
