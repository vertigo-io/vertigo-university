package io.vertigo.samples.config;

import io.vertigo.app.config.AppConfig;
import io.vertigo.app.config.AppConfigBuilder;
import io.vertigo.samples.components.Calculator1;
import io.vertigo.samples.components.Calculator2;
import io.vertigo.samples.components.Calculator2impl;
import io.vertigo.samples.components.Calculator3;
import io.vertigo.samples.components.Calculator4;
import io.vertigo.samples.components.Calculator5;
import io.vertigo.samples.plugins.SumOperatorPlugin;

public class SampleConfigBuilder {
	public AppConfig build() {
		return new AppConfigBuilder()
				.beginModule("sample")
				.withNoAPI()
				//a simple component must inherit from Component, that's all
				.addComponent(Calculator1.class)

				//Or you can add a component with an api and an impl
				.addComponent(Calculator2.class, Calculator2impl.class)

				//You can parameterize a component 
				.beginComponent(Calculator3.class)
				.addParam("offset", "1000")
				.endComponent()

				//Or you can use a plugin to externalize the complexity and be able to change 
				.addComponent(Calculator4.class)
				.addPlugin(SumOperatorPlugin.class)

				//and you can add behaviors on your component
				//activeable has two methods : start() and stop()
				.addComponent(Calculator5.class)

				.endModule()
				.build();
	}
}
