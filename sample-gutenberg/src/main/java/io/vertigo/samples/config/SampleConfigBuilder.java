package io.vertigo.samples.config;

import io.vertigo.app.config.AppConfig;
import io.vertigo.app.config.AppConfigBuilder;
import io.vertigo.samples.components.a_basics.TextProcessorManager;
import io.vertigo.samples.components.a_basics.TextProcessorManagerImpl;

/**
 *
 * @author dt
 *
 */
public class SampleConfigBuilder {
	public AppConfig build() {
		return new AppConfigBuilder()
				.beginModule("gutenberg")
				.addComponent(TextProcessorManager.class, TextProcessorManagerImpl.class)
				.endModule()
				.build();
	}
}
