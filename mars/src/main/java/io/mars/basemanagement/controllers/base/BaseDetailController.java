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
package io.mars.basemanagement.controllers.base;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import io.mars.basemanagement.domain.Base;
import io.mars.basemanagement.domain.BaseType;
import io.mars.basemanagement.domain.Picture;
import io.mars.basemanagement.services.base.BaseServices;
import io.vertigo.dynamo.file.model.VFile;
import io.vertigo.ui.core.ProtectedValueUtil;
import io.vertigo.ui.core.ViewContext;
import io.vertigo.ui.core.ViewContextKey;
import io.vertigo.ui.impl.springmvc.controller.AbstractVSpringMvcController;

@Controller
@RequestMapping("/basemanagement/base/")
public final class BaseDetailController extends AbstractVSpringMvcController {
	private static final ViewContextKey<Base> baseKey = ViewContextKey.of("base");
	private static final ViewContextKey<BaseType> baseTypesKey = ViewContextKey.of("baseTypes");
	private static final ViewContextKey<Picture> basePictures = ViewContextKey.of("basePictures");

	@Inject
	private BaseServices baseServices;

	public void initCommonContext(final ViewContext viewContext, final Long baseId) {
		viewContext.publishMdl(baseTypesKey, BaseType.class, null); //all
		viewContext.publishDto(baseKey, baseServices.get(baseId));
		viewContext.publishDtListModifiable(basePictures, baseServices.getPictures(baseId));
	}

	@GetMapping("{baseId}/mainPicture")
	public VFile loadFile(@PathVariable("baseId") final Long baseId) {
		return baseServices.getBaseMainPicture(baseId);
	}

	@GetMapping("/picture/{protectedUrl}")
	public VFile loadFile(@PathVariable("protectedUrl") final String protectedUrl) {
		final Long fileKey = ProtectedValueUtil.readProtectedValue(protectedUrl, Long.class);
		return baseServices.getBasePicture(fileKey);
	}
	/**
	 * Here we can support some common actions with absolute redirect (common hav no page)
	 */

}
