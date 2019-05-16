package io.vertigo.samples.vega.domain;

import java.util.Locale;

import io.vertigo.account.security.UserSession;

public class SampleSession extends UserSession {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1337L;

	@Override
	public Locale getLocale() {
		// TODO Auto-generated method stub
		return Locale.FRANCE;
	}

}
