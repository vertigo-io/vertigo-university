package io.vertigo.samples.vega.domain;

import java.util.List;

import io.vertigo.core.definition.Definition;
import io.vertigo.core.definition.DefinitionSpace;
import io.vertigo.core.definition.SimpleDefinitionProvider;
import io.vertigo.dynamo.domain.metamodel.DataType;
import io.vertigo.dynamo.domain.metamodel.Domain;
import io.vertigo.dynamo.domain.metamodel.DomainBuilder;
import io.vertigo.dynamo.domain.metamodel.DtDefinition;
import io.vertigo.dynamo.domain.metamodel.DtDefinitionBuilder;
import io.vertigo.util.ListBuilder;

public final class VegaDefinitionProvider extends SimpleDefinitionProvider {

	@Override
	public List<Definition> provideDefinitions(final DefinitionSpace definitionSpace) {
		final Domain domainId = new DomainBuilder("DO_IDENTITY", DataType.String).build();
		final Domain domainText = new DomainBuilder("DO_TEXT", DataType.String).build();

		final DtDefinition movieDtDefinition = new DtDefinitionBuilder("DT_MOVIE")
				.addIdField("ID", "id", domainId, false, false)
				.addDataField("TITLE", "title", domainText, true, false/*persistent*/, true/*sort*/ , true/*display*/)
				.build();

		return new ListBuilder<Definition>()
				.add(domainId)
				.add(domainText)
				.add(movieDtDefinition)
				.build();
	}
}
