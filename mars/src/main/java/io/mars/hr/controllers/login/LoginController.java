package io.mars.hr.controllers.login;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.mars.hr.services.login.LoginServices;
import io.vertigo.account.impl.authentication.PasswordHelper;
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
	public String initContext(final ViewContext viewContext) {
		if (!loginServices.isAuthenticated()) {
			viewContext.publishRef(loginKey, "");
			viewContext.publishRef(passwordKey, "");
			return null;
		} else {
			return "redirect:/home/";
		}
	}

	@PostMapping("/_login")
	public String doLogin(@RequestParam("login") final String login, @RequestParam("password") final String password) {
		final String pass = new PasswordHelper().createPassword(password);
		System.out.println("createInputPass:" + pass);
		loginServices.login(login, password);
		return "redirect:/home/";
	}

}
