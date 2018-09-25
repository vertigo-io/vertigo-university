package io.vertigo.mars.user;

import io.vertigo.persona.security.UserSession;

import java.util.Locale;

import io.vertigo.mars.domain.users.ApplicationUser;

public final class MarsUserSession extends UserSession {
	private static final long serialVersionUID = 3522402730076977461L;

	private ApplicationUser applicationUser;

	/**
	 * @return Default Locale.
	 */
	@Override
	public Locale getLocale() {
		return Locale.FRANCE;
	}

	public void setApplicationUser(final ApplicationUser applicationUser) {
		this.applicationUser = applicationUser;
	}

	public ApplicationUser getApplicationUser() {
		return applicationUser;
	}
}
