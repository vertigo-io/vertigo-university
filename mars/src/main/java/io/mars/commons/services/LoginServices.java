package io.mars.commons.services;

import java.util.Optional;

import javax.inject.Inject;

import io.vertigo.account.account.Account;
import io.vertigo.account.authentication.AuthenticationManager;
import io.vertigo.account.impl.authentication.UsernamePasswordAuthenticationToken;
import io.vertigo.commons.transaction.Transactional;
import io.vertigo.core.component.Component;
import io.vertigo.lang.VUserException;

@Transactional
public class LoginServices implements Component {

	@Inject
	private AuthenticationManager authenticationManager;

	public Account login(final String login, final String password) {
		final Optional<Account> loggedAccount = authenticationManager.login(new UsernamePasswordAuthenticationToken(login, password));
		if (!loggedAccount.isPresent()) {
			throw new VUserException("Login or Password invalid");
		}
		return loggedAccount.get();
	}

}
