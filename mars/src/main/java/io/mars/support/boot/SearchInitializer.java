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
/**
 *
 */
package io.mars.support.boot;

import javax.inject.Inject;

import io.vertigo.app.Home;
import io.vertigo.core.component.ComponentInitializer;
import io.vertigo.dynamo.search.SearchManager;
import io.vertigo.dynamo.search.metamodel.SearchIndexDefinition;

/**
 * Init sample data for the app.
 * @author dszniten
 */
public class SearchInitializer implements ComponentInitializer {

	@Inject
	private SearchManager searchManager;

	/** {@inheritDoc} */
	@Override
	public void init() {
		Home.getApp().registerPreActivateFunction(() -> {
			Home.getApp().getDefinitionSpace()
					.getAll(SearchIndexDefinition.class)
					.stream()
					.filter(indexDefinition -> !"IdxSupplier".equals(indexDefinition.getName()))
					.forEach((indexDefinition) -> searchManager.reindexAll(indexDefinition));
		});
	}

}
