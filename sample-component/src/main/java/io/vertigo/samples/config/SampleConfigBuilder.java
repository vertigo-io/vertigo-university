package io.vertigo.samples.config;

import io.vertigo.app.config.AppConfig;
import io.vertigo.app.config.AppConfigBuilder;
import io.vertigo.samples.components.Calculator;

public class SampleConfigBuilder {
	public AppConfig build() {
		return new AppConfigBuilder()
				.beginModule("sample")
				.withNoAPI()
				.addComponent(Calculator.class)
				.endModule()
				.build();
	}
}
