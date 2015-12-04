package io.vertigo.samples;

import io.vertigo.app.App;
import io.vertigo.samples.components.Calculator;
import io.vertigo.samples.config.SampleConfigBuilder;

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
public class Sample {
	public static void main(final String[] args) {

		try (App app = new App(new SampleConfigBuilder().build())) {
			final Calculator calculator = app.getComponentSpace().resolve(Calculator.class);
			final int result = calculator.sum(1, 2, 3);
			System.out.print("1+2+3 = " + result);

		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}
