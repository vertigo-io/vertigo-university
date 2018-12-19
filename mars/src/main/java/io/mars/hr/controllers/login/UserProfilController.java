package io.mars.hr.controllers.login;

import java.util.Optional;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.mars.commons.MarsUserSession;
import io.mars.hr.domain.Person;
import io.vertigo.account.account.Account;
import io.vertigo.account.account.AccountManager;
import io.vertigo.dynamo.domain.model.UID;
import io.vertigo.dynamo.file.model.VFile;
import io.vertigo.persona.security.VSecurityManager;

@Controller
@RequestMapping("/userProfil")
public class UserProfilController {

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
	}

}
