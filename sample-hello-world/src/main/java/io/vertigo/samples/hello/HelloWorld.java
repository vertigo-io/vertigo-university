package io.vertigo.samples.hello;

import java.io.IOException;

import io.vertigo.core.node.AutoCloseableApp;
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
	private static final int PORT = 8080;

	public static void main(final String[] args) throws IOException {
		try (AutoCloseableApp app = new AutoCloseableApp(HelloConfigurator.config(PORT))) {
			System.in.read();
		}
	}
}
