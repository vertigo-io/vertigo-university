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
package io.vertigo.samples.vui.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.vertigo.account.authorization.VSecurityException;
import io.vertigo.account.security.VSecurityManager;
import io.vertigo.core.locale.MessageText;
import io.vertigo.core.node.Node;
import io.vertigo.samples.support.SampleUserSession;
import io.vertigo.samples.vui.domain.Country;
import io.vertigo.samples.vui.domain.User;
import io.vertigo.ui.core.ViewContext;
import io.vertigo.ui.core.ViewContextKey;
import io.vertigo.ui.impl.springmvc.argumentresolvers.ViewAttribute;
import io.vertigo.ui.impl.springmvc.controller.AbstractVSpringMvcController;

@Controller
@RequestMapping("/country")
@ControllerAdvice(assignableTypes = { AbstractVSpringMvcController.class })
public final class CountryChoiceController extends AbstractVSpringMvcController {
	private static final ViewContextKey<User> userKey = ViewContextKey.of("user");
	private static final ViewContextKey<Country> countriesKey = ViewContextKey.of("countriesMdl");

	@ModelAttribute
	public void initContext(final ViewContext viewContext) {
		if (isNewContext()) { //InitContext only once
			viewContext.publishDto(userKey, obtainUser());
			viewContext.publishMdl(countriesKey, Country.class, null);
		}
	}

	@PostMapping("/_changeCountry")
	public ViewContext doChange(final ViewContext viewContext, @ViewAttribute("user") final User user) {
		final User sessionUser = obtainUser();
		sessionUser.setCouId(user.getCouId());
		viewContext.publishDto(userKey, sessionUser);
		return viewContext;
	}

	//	private User obtainUser() {
	//		final SampleUserSession session = Node.getNode().getComponentSpace().resolve(VSecurityManager.class)
	//				.<SampleUserSession> getCurrentUserSession().orElseThrow(() -> new VSecurityException(MessageText.of("No active session found")));
	//		final User user = session.getAttribute("sessionUser");
	//		Assertion.check().isNotNull(user);
	//		return user;
	//	}
	private User obtainUser() {
		final SampleUserSession session = Node.getNode().getComponentSpace().resolve(VSecurityManager.class)
				.<SampleUserSession> getCurrentUserSession().orElseThrow(() -> new VSecurityException(MessageText.of("No active session found")));
		User user = session.getAttribute("sessionUser");
		if (user != null) {
			return user;
		}
		user = new User();
		user.setCouId(1128L);
		session.putAttribute("sessionUser", user);
		return user;
	}

}
