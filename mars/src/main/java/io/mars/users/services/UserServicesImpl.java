package io.mars.users.services;

import io.mars.domain.users.ApplicationUser;
import io.mars.users.MarsUserSession;
import io.vertigo.commons.transaction.Transactional;
import io.vertigo.core.locale.MessageText;
import io.vertigo.lang.VUserException;
import io.vertigo.persona.security.VSecurityManager;

import javax.inject.Inject;

@Transactional
public class UserServicesImpl implements UserServices {
	private long usrIdSeq = 1;

	@Inject
	private VSecurityManager securityManager;

	@Override
	public ApplicationUser loginUser(final String login, final String password) {
		//mock login User
		if (login.isEmpty() || password.isEmpty() || login.startsWith(password) || password.startsWith(login)) {
			throw new VUserException(MessageText.of("Échec de la connexion : nom d'utilisateur inconnu ou mot de passe incorrect."));
		}

		final ApplicationUser applicationUser = new ApplicationUser();
		applicationUser.setFirstName(password);
		applicationUser.setLastName(login);
		applicationUser.setEmail(password + "." + login + "@kleegroup.com");
		applicationUser.setUsrId(usrIdSeq++);

		final MarsUserSession userSession = securityManager.<MarsUserSession> getCurrentUserSession().get();
		userSession.setApplicationUser(applicationUser);
		userSession.authenticate();

		return applicationUser;
	}

}
