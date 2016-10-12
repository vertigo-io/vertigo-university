package io.vertigo.samples.config;

import io.vertigo.app.config.AppConfig;
import io.vertigo.app.config.AppConfigBuilder;
import io.vertigo.samples.aspects.SpyAspect;
import io.vertigo.samples.aspects.SpyManager;
import io.vertigo.samples.components.a_basics.Calculator1;
import io.vertigo.samples.components.a_basics.Calculator2;
import io.vertigo.samples.components.a_basics.Calculator2impl;
import io.vertigo.samples.components.a_basics.Calculator3;
import io.vertigo.samples.components.a_basics.Calculator4;
import io.vertigo.samples.components.b_plugins.Calculator5;
import io.vertigo.samples.components.b_plugins.Calculator6;
import io.vertigo.samples.components.c_aop.Calculator7;
import io.vertigo.samples.components.c_aop.Calculator8;
import io.vertigo.samples.plugins.MaxOperationPlugin;
import io.vertigo.samples.plugins.MinOperationPlugin;
import io.vertigo.samples.plugins.MultOperationlugin;
import io.vertigo.samples.plugins.SumOperationPlugin;

public class SampleConfigBuilder {
	public AppConfig build() {
		return new AppConfigBuilder()
				.beginModule("aspects")
				.withNoAPI()
				.addComponent(SpyManager.class)
				.addAspect(SpyAspect.class)
				.endModule()

				.beginModule("sample")
				.withNoAPI()

				//a simple component must inherit from Component, that's all
				.addComponent(Calculator1.class)

				//Or you can add a component with an api and an impl
				.addComponent(Calculator2.class, Calculator2impl.class)

				//You can parameterize a component
				.beginComponent(Calculator3.class)
				.addParam("offset", "1000")
				.addParam("log", "true")
				.endComponent()

				//and you can add behaviors on your component
				//activeable has two methods : start() and stop()
				.addComponent(Calculator4.class)

				//Or you can use a plugin to externalize the complexity and be able to change
				.addComponent(Calculator5.class)
				.addPlugin(SumOperationPlugin.class)

				.addComponent(Calculator6.class)
				.addPlugin(SumOperationPlugin.class)
				.addPlugin(MinOperationPlugin.class)
				.addPlugin(MaxOperationPlugin.class)
				.addPlugin(MultOperationlugin.class)

				// Aop on one method
				.addComponent(Calculator7.class)
				// Aop on class
				.addComponent(Calculator8.class)

				.endModule()
				.build();
	}
}
