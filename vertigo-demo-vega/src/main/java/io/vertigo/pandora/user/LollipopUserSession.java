package io.vertigo.pandora.user;

import java.util.Locale;

import io.vertigo.persona.security.UserSession;

public final class LollipopUserSession extends UserSession {
	private static final long serialVersionUID = 3522402730076977461L;

	/**
	 * @return Default Locale.
	 */
	@Override
	public Locale getLocale() {
		return Locale.FRANCE;
	}
}
