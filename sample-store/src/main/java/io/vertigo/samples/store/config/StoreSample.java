package io.vertigo.samples.store.config;

import io.vertigo.app.App;
import io.vertigo.core.component.di.injector.Injector;
import io.vertigo.dynamo.store.StoreManager;

import javax.inject.Inject;

public class StoreSample {
	@Inject
	private StoreManager storeManager;

	public static void main(final String[] args) {
		try (final App app = new App(new SampleConfigBuilder().build())) {
			final StoreSample sample = new StoreSample();
			Injector.injectMembers(sample, app.getComponentSpace());
			//-----
			sample.step1();
		}
	}

	void step1() {
		storeManager.getDataStore().count(null);
	}
}
