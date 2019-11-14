package io.vertigo.samples.vega;

import java.io.IOException;

import io.vertigo.core.node.AutoCloseableApp;
import io.vertigo.samples.vega.config.SampleVegaConfigurator;

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
public class SampleVega {
	private static final int PORT = 8080;

	public static void main(final String[] args) throws IOException {
		try (AutoCloseableApp app = new AutoCloseableApp(SampleVegaConfigurator.config(PORT))) {
			System.in.read();
		}
	}
}
