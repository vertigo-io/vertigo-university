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
package io.mars.hr.controllers.login;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.mars.hr.domain.Person;
import io.mars.hr.services.login.LoginServices;
import io.vertigo.ui.core.ViewContext;
import io.vertigo.ui.core.ViewContextKey;
import io.vertigo.ui.impl.springmvc.controller.AbstractVSpringMvcController;

@Controller
@RequestMapping("/userProfil")
@ControllerAdvice(assignableTypes = { AbstractVSpringMvcController.class })
public final class UserProfilController extends AbstractVSpringMvcController {
	private static final ViewContextKey<Person> connectedUserKey = ViewContextKey.of("connectedUser");
	private static final ViewContextKey<String> activeProfileKey = ViewContextKey.of("activeProfile");

	@Inject
	private LoginServices loginServices;

	@ModelAttribute
	public void initContext(final ViewContext viewContext) {
		if (isNewContext() && loginServices.isAuthenticated()) { //must support all cases
			viewContext.publishDto(connectedUserKey, loginServices.getLoggedPerson());
			viewContext.publishRef(activeProfileKey, loginServices.getActiveProfile());
		}
	}

	@GetMapping("/_changeProfile")
	public String doCreate(@RequestParam("profile") final String profile) {
		loginServices.changeProfile(profile);
		return "redirect:/home/";
	}
	/*
		@Inject
		private VSecurityManager securityManager;
		@Inject
		private AccountManager identityManager;

		@GetMapping("/userInfo")
		public Person getUserInfo() {
			final Optional<MarsUserSession> userSession = securityManager.getCurrentUserSession();
			if (userSession.isPresent()) {
				return userSession.get().getLoggedPerson();
			}
			return null;
		}

		@GetMapping("/userPhoto")
		public VFile getUserPhoto() {
			final Optional<MarsUserSession> userSession = securityManager.getCurrentUserSession();
			if (userSession.isPresent()) {
				final String userId = userSession.get().getLoggedAccount().getId();
				return identityManager.getPhoto(UID.of(Account.class, userId))
						.orElse(identityManager.getDefaultPhoto());
			}
			return null;
		}*/
}
