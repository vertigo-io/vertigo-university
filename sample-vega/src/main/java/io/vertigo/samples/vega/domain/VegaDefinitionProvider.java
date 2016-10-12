package io.vertigo.samples.vega.domain;

import java.util.Iterator;

import io.vertigo.app.config.DefinitionProvider;
import io.vertigo.core.spaces.definiton.Definition;
import io.vertigo.dynamo.domain.metamodel.DataType;
import io.vertigo.dynamo.domain.metamodel.Domain;
import io.vertigo.dynamo.domain.metamodel.DomainBuilder;
import io.vertigo.dynamo.domain.metamodel.DtDefinition;
import io.vertigo.dynamo.domain.metamodel.DtDefinitionBuilder;
import io.vertigo.util.ListBuilder;

public final class VegaDefinitionProvider implements DefinitionProvider {
	/** {@inheritDoc} */
	@Override
	public Iterator<Definition> iterator() {
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
				.build()
				.iterator();
	}
}
