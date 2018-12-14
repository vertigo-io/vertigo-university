package io.mars.commons.controllers;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.mars.commons.services.LoginServices;
import io.vertigo.account.account.Account;
import io.vertigo.ui.core.ViewContext;
import io.vertigo.ui.core.ViewContextKey;
import io.vertigo.ui.impl.springmvc.controller.AbstractVSpringMvcController;

@Controller
@RequestMapping("/login")
public class LoginController extends AbstractVSpringMvcController {

	private static final ViewContextKey<String> loginKey = ViewContextKey.of("login");
	private static final ViewContextKey<String> passwordKey = ViewContextKey.of("password");

	@Inject
	private LoginServices loginServices;

	@GetMapping("/")
	public void initContext(final ViewContext viewContext) {
		viewContext.publishRef(loginKey, "");
		viewContext.publishRef(passwordKey, "");
	}

	@PostMapping("/_login")
	public String doLogin(@RequestParam("login") final String login, @RequestParam("password") final String password) {
		//final String pass = new PasswordHelper().createPassword(password);
		final Account loggedAccount = loginServices.login(login, password);
		System.out.println("login : " + loggedAccount);
		return "redirect:/home/";
	}

}
