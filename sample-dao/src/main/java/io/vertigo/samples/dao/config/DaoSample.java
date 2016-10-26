package io.vertigo.samples.dao.config;

import io.vertigo.app.AutoCloseableApp;
import io.vertigo.app.config.AppConfigBuilder;
import io.vertigo.core.component.di.injector.Injector;

public class DaoSample {

	public static void main(final String[] args) {
		final AppConfigBuilder appConfigBuilder = SampleConfigBuilder.createAppConfigBuilder();
		try (final AutoCloseableApp app = new AutoCloseableApp(appConfigBuilder.build())) {
			final DaoSample sample = new DaoSample();
			Injector.injectMembers(sample, app.getComponentSpace());
			//-----
			sample.step1();
		}
	}

	void step1() {
		// nothing
	}
}
