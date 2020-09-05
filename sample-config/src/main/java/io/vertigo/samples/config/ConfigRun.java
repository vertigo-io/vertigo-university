package io.vertigo.samples.config;

import io.vertigo.commons.CommonsFeatures;
import io.vertigo.connectors.javalin.JavalinFeatures;
import io.vertigo.core.node.AutoCloseableNode;
import io.vertigo.core.node.config.ModuleConfig;
import io.vertigo.core.node.config.NodeConfig;
import io.vertigo.core.param.Param;
import io.vertigo.datamodel.DataModelFeatures;
import io.vertigo.vega.VegaFeatures;

/***
 * Start the main method.
 *
 * Call "http://localhost:8080/hello" with your web browser.
 * You may receive an "hello world" back.
 *
 *
 *
 * @author pchretien
 */
public class ConfigRun {

	public static void main(final String[] args) {
		final NodeConfig nodeConfig = NodeConfig.builder()
				.addModule(new JavalinFeatures().withEmbeddedServer(Param.of("port", "8080")).build())
				.addModule(new CommonsFeatures().build())
				.addModule(new DataModelFeatures().build())
				.addModule(new VegaFeatures().withWebServices().withJavalinWebServerPlugin().build())
				//-----Declaration of a module named 'Hello' which contains a webservice component.
				.addModule(ModuleConfig.builder("Hello")
						.addComponent(HelloWebServices.class)
						.build())
				.build();

		try (AutoCloseableNode node = new AutoCloseableNode(nodeConfig)) {
			//do whatever you want
		}
	}
}
