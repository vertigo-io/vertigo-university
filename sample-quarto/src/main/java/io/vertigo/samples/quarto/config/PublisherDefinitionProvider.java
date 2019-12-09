/**
 * vertigo - simple java starter
 *
 * Copyright (C) 2013-2018, KleeGroup, direction.technique@kleegroup.com (http://www.kleegroup.com)
 * KleeGroup, Centre d'affaire la Boursidiere - BP 159 - 92357 Le Plessis Robinson Cedex - France
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.vertigo.samples.quarto.config;

import java.util.List;

import io.vertigo.core.node.definition.Definition;
import io.vertigo.core.node.definition.DefinitionSpace;
import io.vertigo.core.node.definition.SimpleDefinitionProvider;
import io.vertigo.core.util.ListBuilder;
import io.vertigo.quarto.services.publisher.metamodel.PublisherDataDefinition;
import io.vertigo.quarto.services.publisher.metamodel.PublisherNodeDefinition;
import io.vertigo.quarto.services.publisher.metamodel.PublisherNodeDefinitionBuilder;

public final class PublisherDefinitionProvider implements SimpleDefinitionProvider {

	private static PublisherDataDefinition createTrelloTheme() {
		final PublisherNodeDefinition status = new PublisherNodeDefinitionBuilder()
				.addStringField("name")
				.addStringField("color")
				.build();

		final PublisherNodeDefinition card = new PublisherNodeDefinitionBuilder()
				.addStringField("name")
				.addStringField("description")
				.addListField("status", status)
				.build();

		final PublisherNodeDefinition category = new PublisherNodeDefinitionBuilder()
				.addStringField("name")
				.addStringField("description")
				.addListField("cards", card)
				.build();

		final PublisherNodeDefinition theme = new PublisherNodeDefinitionBuilder()
				.addStringField("name")
				.addListField("categories", category)
				.build();

		return new PublisherDataDefinition("PuTheme", theme);
	}

	@Override
	public List<Definition> provideDefinitions(final DefinitionSpace definitionSpace) {

		return new ListBuilder<Definition>()
				.add(createTrelloTheme())
				.build();
	}

}
