package io.vertigo.samples.vega.domain;

import java.util.List;

import io.vertigo.core.lang.BasicType;
import io.vertigo.core.lang.Cardinality;
import io.vertigo.core.lang.ListBuilder;
import io.vertigo.core.node.definition.Definition;
import io.vertigo.core.node.definition.DefinitionSpace;
import io.vertigo.core.node.definition.SimpleDefinitionProvider;
import io.vertigo.datamodel.data.definitions.DataDefinition;
import io.vertigo.datamodel.smarttype.definitions.SmartTypeDefinition;

public final class VegaDefinitionProvider implements SimpleDefinitionProvider {

	@Override
	public List<Definition> provideDefinitions(final DefinitionSpace definitionSpace) {
		final SmartTypeDefinition smartTypeId = SmartTypeDefinition.builder("STyIdentity", BasicType.String).build();
		final SmartTypeDefinition smartTypeText = SmartTypeDefinition.builder("STyText", BasicType.String).build();

		final DataDefinition movieDtDefinition = DataDefinition.builder("DtMovie")
				.addIdField("id", "id", smartTypeId)
				.addDataField("title", "title", smartTypeText, Cardinality.ONE, false/*persistent*/)
				.withSortField("title")
				.withDisplayField("title")
				.build();

		return new ListBuilder<Definition>()
				.add(smartTypeId)
				.add(smartTypeText)
				.add(movieDtDefinition)
				.build();
	}
}
