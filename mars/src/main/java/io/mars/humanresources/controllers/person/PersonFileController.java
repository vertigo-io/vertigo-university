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
package io.mars.humanresources.controllers.person;

import java.io.Serializable;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.mars.domain.DtDefinitions;
import io.mars.humanresources.services.person.PersonServices;
import io.vertigo.dynamo.file.model.VFile;
import io.vertigo.lang.Assertion;
import io.vertigo.ui.core.AbstractUiListUnmodifiable;
import io.vertigo.ui.core.ViewContext;
import io.vertigo.ui.impl.springmvc.controller.AbstractVSpringMvcController;
import io.vertigo.ui.impl.springmvc.controller.AbstractVSpringMvcController.AcceptCtxQueryParam;
import io.vertigo.vega.engines.webservice.json.VegaUiObject;
import io.vertigo.vega.webservice.model.UiList;

/**
 * Service web de chargement des fichiers.
 * @author npiedeloup
 */
@Controller
@AcceptCtxQueryParam
@RequestMapping("/humanresources/person/file")
public final class PersonFileController extends AbstractVSpringMvcController {

	@Inject
	private PersonServices personServices;

	@GetMapping("/{ctxKey}/{propertyName}")
	@ResponseBody
	public VFile loadFile(
			final ViewContext viewContext,
			@PathVariable("ctxKey") final String ctxKey,
			@PathVariable("propertyName") final String propertyName) {
		//generic part
		final VegaUiObject uiObject = (VegaUiObject) viewContext.getUiObject(() -> ctxKey);
		final Serializable fileKey = (Serializable) uiObject.getTypedValue(propertyName, Serializable.class);

		//project specific part
		return personServices.getPersonPicture((Long) fileKey);
	}

	@GetMapping("/{ctxKey}/{elementId}/{propertyName}")
	@ResponseBody
	public VFile loadFile(
			final ViewContext viewContext,
			@PathVariable("ctxKey") final String ctxKey,
			@PathVariable("elementId") final String elementId,
			@PathVariable("propertyName") final String propertyName) {
		//generic part
		final UiList contextList = viewContext.getUiList(() -> ctxKey);
		Assertion.checkArgument(contextList instanceof AbstractUiListUnmodifiable, "This list {0} should be an Unmodifiable list, in order to get files", ctxKey);
		final VegaUiObject uiObject = (VegaUiObject) ((AbstractUiListUnmodifiable) contextList).getById(DtDefinitions.PersonFields.PERSON_ID.name(), elementId);
		Assertion.checkNotNull(uiObject, "No entity with id {0} in list {1}", elementId, ctxKey);
		final Serializable fileKey = (Serializable) uiObject.getTypedValue(propertyName, Serializable.class);

		//project specific part
		return personServices.getPersonPicture((Long) fileKey);
	}
}
