package io.vertigo.samples.vega.domain;

import java.util.List;

import io.vertigo.core.node.definition.Definition;
import io.vertigo.core.node.definition.DefinitionSpace;
import io.vertigo.core.node.definition.SimpleDefinitionProvider;
import io.vertigo.core.util.ListBuilder;
import io.vertigo.dynamo.domain.metamodel.DataType;
import io.vertigo.dynamo.domain.metamodel.Domain;
import io.vertigo.dynamo.domain.metamodel.DtDefinition;

public final class VegaDefinitionProvider implements SimpleDefinitionProvider {

	@Override
	public List<Definition> provideDefinitions(final DefinitionSpace definitionSpace) {
		final Domain domainId = Domain.builder("DoIdentity", DataType.String).build();
		final Domain domainText = Domain.builder("DoText", DataType.String).build();

		final DtDefinition movieDtDefinition = DtDefinition.builder("DtMovie")
				.addIdField("id", "id", domainId)
				.addDataField("title", "title", domainText, true, false/*persistent*/)
				.withSortField("title")
				.withDisplayField("title")
				.build();

		return new ListBuilder<Definition>()
				.add(domainId)
				.add(domainText)
				.add(movieDtDefinition)
				.build();
	}
}
