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
package io.vertigo.samples.vui;

import io.vertigo.core.node.config.Feature;
import io.vertigo.core.node.config.Features;
import io.vertigo.samples.vui.dao.ActorDAO;
import io.vertigo.samples.vui.dao.MovieDAO;
import io.vertigo.samples.vui.dao.RoleDAO;
import io.vertigo.samples.vui.search.MovieIndexSearchClient;
import io.vertigo.samples.vui.services.MovieSearchLoader;
import io.vertigo.samples.vui.services.MovieServices;

public class VuiFeatures extends Features<VuiFeatures> {

	public VuiFeatures() {
		super("sample-vui");
	}

	@Feature("search")
	public VuiFeatures withSearch() {
		getModuleConfigBuilder().addComponent(MovieSearchLoader.class)
				.addComponent(MovieIndexSearchClient.class);
		return this;
	}

	@Override
	protected void buildFeatures() {
		getModuleConfigBuilder()
				.addComponent(MovieServices.class)
				.addComponent(MovieDAO.class)
				.addComponent(ActorDAO.class)
				.addComponent(RoleDAO.class)
				.addComponent(VuiPAO.class);

	}

}
