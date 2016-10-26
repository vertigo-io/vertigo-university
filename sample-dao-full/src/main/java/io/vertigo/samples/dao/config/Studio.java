package io.vertigo.samples.dao.config;

import javax.inject.Inject;

import io.vertigo.app.AutoCloseableApp;
import io.vertigo.core.component.di.injector.Injector;
import io.vertigo.studio.mda.MdaManager;

public class Studio {
	@Inject
	private MdaManager mdaManager;

	public static void main(final String[] args) {
		try (final AutoCloseableApp app = new AutoCloseableApp(new SampleStudioConfigBuilder().build())) {
			final Studio sample = new Studio();
			Injector.injectMembers(sample, app.getComponentSpace());
			//-----
			sample.cleanGenerate();
		}
	}

	void cleanGenerate() {
		mdaManager.clean();
		mdaManager.generate().displayResultMessage(System.out);
	}
}
