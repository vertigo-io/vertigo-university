package io.vertigo.samples.hello.config;

import io.vertigo.connectors.javalin.JavalinFeatures;
import io.vertigo.core.node.config.ModuleConfig;
import io.vertigo.core.node.config.NodeConfig;
import io.vertigo.core.param.Param;
import io.vertigo.datamodel.DataModelFeatures;
import io.vertigo.samples.hello.webservices.HelloWebServices;
import io.vertigo.vega.VegaFeatures;

public final class HelloConfigurator {
	public static NodeConfig config(final int port) {
		return NodeConfig.builder()
				.addModule(new JavalinFeatures().withEmbeddedServer(Param.of("port", port)).build())
				.addModule(new DataModelFeatures().build())
				.addModule(new VegaFeatures()
						.withWebServices()
						.withJavalinWebServerPlugin()
						.build())
				//-----Declaration of a module named 'Hello' which contains a webservice component.
				.addModule(ModuleConfig.builder("Hello")
						.addComponent(HelloWebServices.class)
						.build())
				.build();
	}
}
