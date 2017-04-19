package io.vertigo.samples.store.config;

import javax.inject.Inject;

import io.vertigo.app.AutoCloseableApp;
import io.vertigo.core.component.di.injector.DIInjector;
import io.vertigo.dynamo.store.StoreManager;

public class StoreSample {
	@Inject
	private StoreManager storeManager;

	public static void main(final String[] args) {
		try (final AutoCloseableApp app = new AutoCloseableApp(new SampleConfigBuilder().build())) {
			final StoreSample sample = new StoreSample();
			DIInjector.injectMembers(sample, app.getComponentSpace());
			//-----
			sample.step1();
		}
	}

	void step1() {
		storeManager.getDataStore().count(null);
	}
}
