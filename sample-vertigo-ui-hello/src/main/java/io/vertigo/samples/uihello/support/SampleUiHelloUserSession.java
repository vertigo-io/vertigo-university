package io.vertigo.samples.uihello.support;

import java.util.Locale;

import io.vertigo.account.security.UserSession;

public class SampleUiHelloUserSession extends UserSession {

	private static final long serialVersionUID = 1L;

	/** {@inheritDoc} */
	@Override
	public Locale getLocale() {
		return Locale.FRANCE;
	}
}
