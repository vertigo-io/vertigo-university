package io.vertigo.samples.crystal.services;

import java.util.Optional;

import javax.inject.Inject;

import io.vertigo.account.account.Account;
import io.vertigo.account.authentication.AuthenticationManager;
import io.vertigo.account.authentication.AuthenticationToken;
import io.vertigo.account.authorization.AuthorizationManager;
import io.vertigo.account.impl.authentication.UsernamePasswordAuthenticationToken;
import io.vertigo.commons.transaction.Transactional;
import io.vertigo.samples.crystal.authorization.GlobalAuthorizations;
import io.vertigo.samples.crystal.authorization.SecuredEntities.MovieAuthorizations;

@Transactional
public class LoginServicesImpl implements LoginServices {

	@Inject
	private AuthenticationManager authenticationManager;
	@Inject
	private AuthorizationManager authorizationManager;

	@Override
	public Account login(final String login, final String password) {
		final AuthenticationToken token = new UsernamePasswordAuthenticationToken(login, password);
		final Optional<Account> account = authenticationManager.login(token);
		addSecurity();
		return account.get();
	}

	private void addSecurity() {
		authorizationManager.obtainUserAuthorizations().addAuthorization(GlobalAuthorizations.ATZ_SPECIAL.getAuthorization());
		authorizationManager.obtainUserAuthorizations().addAuthorization(MovieAuthorizations.ATZ_MOVIE$READ.getAuthorization());
		authorizationManager.obtainUserAuthorizations().withSecurityKeys("couId", 1178);
	}

}
