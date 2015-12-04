package io.vertigo.samples.hello;

import io.vertigo.app.App;
import io.vertigo.samples.hello.config.HelloConfigurator;

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
public class HelloWorld {
	public static void main(final String[] args) {
		final int PORT = 8080;
		try (App app = new App(HelloConfigurator.config(PORT))) {
			System.in.read();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}
