package io.vertigo.pandora.boot;

import org.apache.log4j.Logger;

import io.vertigo.app.AutoCloseableApp;

public final class Pandora {

	public static void main(final String[] args) {
		try (AutoCloseableApp app = new AutoCloseableApp(PandoraConfigurator.config(false))) {
			//	AppShell.startShell(5222);
			Thread.sleep(Long.MAX_VALUE);
		} catch (final Exception e) {
			e.printStackTrace();
			Logger.getLogger(Pandora.class).warn("an error occured when starting", e);
		}
	}
}
