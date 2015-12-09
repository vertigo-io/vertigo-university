package io.vertigo.samples;

import io.vertigo.app.App;
import io.vertigo.samples.components.Calculator1;
import io.vertigo.samples.components.Calculator2;
import io.vertigo.samples.components.Calculator3;
import io.vertigo.samples.components.Calculator4;
import io.vertigo.samples.components.Calculator5;
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

			System.out.println("|--- Sample using a simple component");
			final Calculator1 calculator1 = app.getComponentSpace().resolve(Calculator1.class);
			final int result1 = calculator1.sum(1, 2, 3);
			System.out.println("|    | 1+2+3 = " + result1);

			System.out.println("|");
			System.out.println("|--- Sample using a component with a defined api");
			final Calculator2 calculator2 = app.getComponentSpace().resolve(Calculator2.class);
			final int result2 = calculator2.sum(1, 2, 3);
			System.out.println("|    | 1+2+3 = " + result2);

			System.out.println("|");
			System.out.println("|--- Sample using a component with params");
			final Calculator3 calculator3 = app.getComponentSpace().resolve(Calculator3.class);
			final int result3 = calculator3.sum(1, 2, 3);
			System.out.println("|    | offset+1+2+3 = " + result3);

			System.out.println("|");
			System.out.println("|--- Sample using a component with params");
			final Calculator4 calculator4 = app.getComponentSpace().resolve(Calculator4.class);
			final int result4 = calculator4.sum(1, 2, 3);
			System.out.println("|    | 1+2+3 = " + result4);

			System.out.println("|");
			System.out.println("|--- Sample using a component with a plugin");
			final Calculator5 calculator5 = app.getComponentSpace().resolve(Calculator5.class);
			final int result5 = calculator5.sum(1, 2, 3);
			System.out.println("|    | 1+2+3 = " + result5);

		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}
