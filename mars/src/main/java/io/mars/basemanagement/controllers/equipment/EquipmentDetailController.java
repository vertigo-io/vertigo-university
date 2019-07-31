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
package io.mars.basemanagement.controllers.equipment;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import io.mars.basemanagement.domain.Business;
import io.mars.basemanagement.domain.Equipment;
import io.mars.basemanagement.services.equipment.EquipmentServices;
import io.mars.catalog.domain.EquipmentType;
import io.vertigo.ui.core.ViewContext;
import io.vertigo.ui.core.ViewContextKey;
import io.vertigo.ui.impl.springmvc.controller.AbstractVSpringMvcController;

@Controller
@RequestMapping("/basemanagement/equipment/")
public final class EquipmentDetailController extends AbstractVSpringMvcController {
	private static final ViewContextKey<Equipment> equipmentKey = ViewContextKey.of("equipment");
	private static final ViewContextKey<EquipmentType> equipmentTypeKey = ViewContextKey.of("equipmentType");
	private static final ViewContextKey<Business> businessKey = ViewContextKey.of("business");

	@Inject
	private EquipmentServices equipmentServices;

	public void initCommonContext(final ViewContext viewContext, final Long equipmentId) {
		final Equipment equipment = equipmentServices.get(equipmentId);
		viewContext.publishDto(equipmentKey, equipment);
		viewContext.publishDto(equipmentTypeKey, equipment.equipmentType().get());
		viewContext.publishDto(businessKey, equipment.business().get());
	}

	/**
	 * Here we can support some common actions with absolute redirect (common hav no page)
	 */

}
