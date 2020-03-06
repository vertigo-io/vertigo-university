package io.vertigo.samples.hello.config;

import io.vertigo.core.node.config.ModuleConfig;
import io.vertigo.core.node.config.NodeConfig;
import io.vertigo.core.param.Param;
import io.vertigo.datamodel.DataModelFeatures;
import io.vertigo.samples.hello.webservices.HelloWebServices;
import io.vertigo.vega.VegaFeatures;

public final class HelloConfigurator {
	public static NodeConfig config(final int port) {
		return NodeConfig.builder()
				.addModule(new DataModelFeatures().build())
				.addModule(new VegaFeatures().withWebServices().withWebServicesEmbeddedServer(Param.of("port", Integer.toString(port))).build())
				//-----Declaration of a module named 'Hello' which contains a webservice component.
				.addModule(ModuleConfig.builder("Hello")
						.addComponent(HelloWebServices.class)
						.build())
				.build();
	}
}
