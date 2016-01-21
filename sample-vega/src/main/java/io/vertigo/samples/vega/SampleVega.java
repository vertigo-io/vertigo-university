package io.vertigo.samples.vega;

import io.vertigo.app.App;
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

	public static void main(final String[] args) {
		try (App app = new App(SampleVegaConfigurator.config(PORT))) {
			System.in.read();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}
