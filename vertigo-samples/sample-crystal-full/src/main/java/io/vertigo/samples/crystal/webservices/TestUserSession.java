package io.vertigo.samples.crystal.webservices;

import java.util.Locale;

import io.vertigo.account.security.UserSession;

public final class TestUserSession extends UserSession {
	private static final long serialVersionUID = 1L;

	@Override
	public Locale getLocale() {
		return Locale.FRANCE;
	}
}
