package io.mars.hr.controllers.login;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.mars.hr.services.login.LoginServices;
import io.vertigo.lang.VUserException;
import io.vertigo.ui.core.ViewContext;
import io.vertigo.ui.core.ViewContextKey;
import io.vertigo.ui.impl.springmvc.controller.AbstractVSpringMvcController;
import io.vertigo.util.StringUtil;
import io.vertigo.vega.webservice.validation.UiMessageStack;

@Controller
@RequestMapping("/login")
public class LoginController extends AbstractVSpringMvcController {

	private static final ViewContextKey<String> loginKey = ViewContextKey.of("login");
	private static final ViewContextKey<String> passwordKey = ViewContextKey.of("password");

	@Inject
	private LoginServices loginServices;

	@GetMapping("/")
	public String initContext(final ViewContext viewContext, final UiMessageStack uiMessageStack, @RequestParam(name = "code", required = false) final Integer code) {
		if (!loginServices.isAuthenticated()) {
			if (code != null && code.equals(401)) {
				uiMessageStack.warning("You have been disconnected");
				return "redirect:/login/";
			} else if (code != null && code.equals(400)) {
				uiMessageStack.warning("You have been inactive for too long, your login has expired");
				return "redirect:/login/";
			}
			viewContext.publishRef(loginKey, "");
			viewContext.publishRef(passwordKey, "");
			return null;
		} else {
			return "redirect:/home/";
		}
	}

	@PostMapping("/_login")
	public String doLogin(@RequestParam("login") final String login, @RequestParam("password") final String password) {
		if (StringUtil.isEmpty(login) || StringUtil.isEmpty(password)) {
			throw new VUserException("Login and Password are mandatory");
		}
		loginServices.login(login, password);
		return "redirect:/home/";
	}

	@GetMapping("/_logout")
	public String logout(final HttpSession httpSession) {
		loginServices.logout(httpSession);
		return "redirect:/login/";
	}

}
