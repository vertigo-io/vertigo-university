package io.vertigo.samples.config;

import io.vertigo.app.AutoCloseableApp;
import io.vertigo.app.config.AppConfig;
import io.vertigo.app.config.ModuleConfig;
import io.vertigo.commons.CommonsFeatures;
import io.vertigo.core.param.Param;
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
		final AppConfig appConfig = AppConfig.builder()
				.addModule(new CommonsFeatures().build())
				.addModule(new VegaFeatures().withWebServices().withWebServicesEmbeddedServer(Param.of("port", "8080")).build())
				//-----Declaration of a module named 'Hello' which contains a webservice component.
				.addModule(ModuleConfig.builder("Hello")
						.addComponent(HelloWebServices.class)
						.build())
				.build();

		try (AutoCloseableApp app = new AutoCloseableApp(appConfig)) {
			//do whatever you want
		}
	}
}
