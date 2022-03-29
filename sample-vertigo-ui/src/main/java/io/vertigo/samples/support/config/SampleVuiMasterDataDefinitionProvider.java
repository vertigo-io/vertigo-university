/**
 * vertigo - application development platform
 *
 * Copyright (C) 2013-2022, Vertigo.io, team@vertigo.io
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
package io.vertigo.samples.support.config;

import io.vertigo.datastore.impl.entitystore.AbstractMasterDataDefinitionProvider;
import io.vertigo.samples.vui.domain.Country;
import io.vertigo.samples.vui.domain.Role;
import io.vertigo.samples.vui.domain.Sexe;

/**
 * Init masterdata list.
 * @author jmforhan
 */
public class SampleVuiMasterDataDefinitionProvider extends AbstractMasterDataDefinitionProvider {

	@Override
	public void declareMasterDataLists() {
		registerDtMasterDatas(Role.class);
		registerDtMasterDatas(Sexe.class);
		registerDtMasterDatas(Country.class);
	}

}
