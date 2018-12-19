package io.mars.hr.services.login;

import java.util.Optional;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import io.mars.commons.MarsUserSession;
import io.mars.hr.domain.Person;
import io.mars.hr.services.person.PersonServices;
import io.vertigo.account.account.Account;
import io.vertigo.account.authentication.AuthenticationManager;
import io.vertigo.account.authorization.VSecurityException;
import io.vertigo.account.impl.authentication.UsernamePasswordAuthenticationToken;
import io.vertigo.commons.transaction.Transactional;
import io.vertigo.core.component.Component;
import io.vertigo.core.locale.MessageText;
import io.vertigo.lang.VUserException;
import io.vertigo.persona.security.VSecurityManager;

@Transactional
public class LoginServices implements Component {

	@Inject
	private AuthenticationManager authenticationManager;
	@Inject
	private VSecurityManager securityManager;

	@Inject
	private PersonServices personServices;

	public void login(final String login, final String password) {
		final Optional<Account> loggedAccount = authenticationManager.login(new UsernamePasswordAuthenticationToken(login, password));
		if (!loggedAccount.isPresent()) {
			throw new VUserException("Login or Password invalid");
		}
		final Account account = loggedAccount.get();
		final Person person = personServices.getPerson(Long.valueOf(account.getId()));
		getUserSession().setLoggedPerson(person);
		getUserSession().setCurrentProfile("Administrator");
	}

	public boolean isAuthenticated() {
		final Optional<MarsUserSession> userSession = securityManager.<MarsUserSession> getCurrentUserSession();
		return userSession.isPresent() ? false : userSession.get().isAuthenticated();
	}

	public Person getLoggedPerson() {
		return getUserSession().getLoggedPerson();
	}

	public void logout(final HttpSession httpSession) {
		authenticationManager.logout();
		httpSession.invalidate();
	}

	public String changeProfile(final String profile) {
		//TODO
		getUserSession().setCurrentProfile(profile);
		return profile;
	}

	public String getActiveProfile() {
		return getUserSession().getCurrentProfile();
	}

	private MarsUserSession getUserSession() {
		return securityManager.<MarsUserSession> getCurrentUserSession().orElseThrow(() -> new VSecurityException(MessageText.of("No active session found")));
	}
}
