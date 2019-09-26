package io.mars.hr.services.login;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import io.mars.hr.domain.Person;
import io.mars.hr.services.person.PersonServices;
import io.mars.support.MarsUserSession;
import io.vertigo.account.account.Account;
import io.vertigo.account.authentication.AuthenticationManager;
import io.vertigo.account.authorization.VSecurityException;
import io.vertigo.account.impl.authentication.UsernamePasswordAuthenticationToken;
import io.vertigo.account.security.VSecurityManager;
import io.vertigo.commons.transaction.Transactional;
import io.vertigo.core.component.Component;
import io.vertigo.core.locale.MessageText;
import io.vertigo.dynamo.domain.model.DtListState;
import io.vertigo.dynamo.domain.model.UID;
import io.vertigo.lang.VUserException;
import io.vertigo.social.services.notification.Notification;
import io.vertigo.social.services.notification.NotificationServices;

@Transactional
public class LoginServices implements Component {

	@Inject
	private AuthenticationManager authenticationManager;
	@Inject
	private VSecurityManager securityManager;

	@Inject
	private NotificationServices notificationServices;

	@Inject
	private PersonServices personServices;

	public void login(final String login, final String password) {
		final Optional<Account> loggedAccount = authenticationManager.login(new UsernamePasswordAuthenticationToken(login, password));
		if (!loggedAccount.isPresent()) {
			sendNotificationToAll(Notification.builder()
					.withSender("System")
					.withTitle("Fail login attempt")
					.withContent("Try to login:'" + login + "")
					.withTTLInSeconds(600)
					.withType("MARS-LOGIN-ATTEMP") //should prefix by app, in case of multi-apps notifications
					.withTargetUrl("#")
					.build());
			throw new VUserException("Login or Password invalid");
		}
		final Account account = loggedAccount.get();
		final Person person = personServices.getPerson(Long.valueOf(account.getId()));
		getUserSession().setLoggedPerson(person);
		getUserSession().setCurrentProfile("Administrator");

		sendNotificationToAll(Notification.builder()
				.withSender(account.getDisplayName())
				.withTitle("New login")
				.withContent("User " + account.getDisplayName() + " just login")
				.withTTLInSeconds(600)
				.withType("MARS-LOGIN") //should prefix by app, in case of multi-apps notifications
				.withTargetUrl("/mars/hr/person/" + account.getId())
				.build());

	}

	private void sendNotificationToAll(final Notification notification) {
		final Set<UID<Account>> accountUIDs = personServices.getPersons(DtListState.of(null))
				.stream()
				.map((person) -> UID.of(Account.class, String.valueOf(person.getPersonId())))
				.collect(Collectors.toSet());
		notificationServices.send(notification, accountUIDs);
	}

	public boolean isAuthenticated() {
		final Optional<MarsUserSession> userSession = securityManager.<MarsUserSession> getCurrentUserSession();
		return !userSession.isPresent() ? false : userSession.get().isAuthenticated();
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
