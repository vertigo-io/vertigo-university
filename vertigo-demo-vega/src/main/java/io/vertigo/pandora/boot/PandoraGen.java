package io.vertigo.pandora.boot;

import org.apache.log4j.Logger;

import io.vertigo.app.AutoCloseableApp;
import io.vertigo.app.Home;
import io.vertigo.studio.mda.MdaManager;

public final class PandoraGen {

	public static void main(final String[] args) {
		try (AutoCloseableApp app = new AutoCloseableApp(PandoraConfigurator.config(true))) {
			Home.getApp().getComponentSpace().resolve(MdaManager.class)
					.generate()
					/* Impression du Rapport d'exécution. */
					.displayResultMessage(System.out);
		} catch (final Exception e) {
			e.printStackTrace();
			Logger.getLogger(PandoraGen.class).warn("an error occured when starting", e);
		}
	}
}
