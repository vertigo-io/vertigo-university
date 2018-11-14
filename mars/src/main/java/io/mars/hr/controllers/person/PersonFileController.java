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
package io.mars.hr.controllers.person;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import io.mars.hr.services.person.PersonServices;
import io.vertigo.dynamo.file.model.VFile;
import io.vertigo.ui.core.ProtectedValueUtil;
import io.vertigo.ui.impl.springmvc.controller.AbstractVSpringMvcController;
import io.vertigo.ui.impl.springmvc.controller.AbstractVSpringMvcController.AcceptCtxQueryParam;

/**
 * Service web de chargement des fichiers.
 * @author npiedeloup
 */
@Controller
@AcceptCtxQueryParam
@RequestMapping("/hr/person/file")
public final class PersonFileController extends AbstractVSpringMvcController {

	@Inject
	private PersonServices personServices;

	@GetMapping("/{protectedUrl}")
	public VFile loadFile(@PathVariable("protectedUrl") final String protectedUrl) {
		final Long fileKey = ProtectedValueUtil.readProtectedValue(protectedUrl);
		//project specific part
		return personServices.getPersonPicture(fileKey);
	}
}
