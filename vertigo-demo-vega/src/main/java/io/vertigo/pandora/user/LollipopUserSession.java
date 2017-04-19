package io.vertigo.pandora.user;

import io.vertigo.persona.security.UserSession;

import java.util.Locale;

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
