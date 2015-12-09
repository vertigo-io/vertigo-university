package io.vertigo.samples;

import io.vertigo.app.App;
import io.vertigo.samples.components.a_basics.Calculator1;
import io.vertigo.samples.components.a_basics.Calculator2;
import io.vertigo.samples.components.a_basics.Calculator3;
import io.vertigo.samples.components.a_basics.Calculator4;
import io.vertigo.samples.components.b_plugins.Calculator5;
import io.vertigo.samples.components.b_plugins.Calculator6;
import io.vertigo.samples.components.c_aop.Calculator7;
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

			System.out.println("|--- Samples using components");
			System.out.println("|");
			System.out.println("|--- a simple component");
			final Calculator1 calculator1 = app.getComponentSpace().resolve(Calculator1.class);
			final int result1 = calculator1.sum(1, 2, 3, 4);
			System.out.println("|    | 1+2+3+4 = " + result1);

			System.out.println("|");
			System.out.println("|--- a component with a defined api");
			final Calculator2 calculator2 = app.getComponentSpace().resolve(Calculator2.class);
			final int result2 = calculator2.sum(1, 2, 3, 4);
			System.out.println("|    | 1+2+3+4 = " + result2);

			System.out.println("|");
			System.out.println("|--- a component with params");
			final Calculator3 calculator3 = app.getComponentSpace().resolve(Calculator3.class);
			final int result3 = calculator3.sum(1, 2, 3, 4);
			System.out.println("|    | offset+1+2+3+4 = " + result3);

			System.out.println("|");
			System.out.println("|--- a component with an activeable behavior");
			final Calculator4 calculator4 = app.getComponentSpace().resolve(Calculator4.class);
			final int result4 = calculator4.sum(1, 2, 3, 4);
			System.out.println("|    | 1+2+3+4 = " + result4);

			System.out.println("|");
			System.out.println("|--- a component with plugin");
			final Calculator5 calculator5 = app.getComponentSpace().resolve(Calculator5.class);
			final int result5 = calculator4.sum(1, 2, 3, 4);
			System.out.println("|    | 1+2+3+4 = " + result5);

			System.out.println("|");
			System.out.println("|--- a component with a list of plugins");
			final Calculator6 calculator6 = app.getComponentSpace().resolve(Calculator6.class);
			System.out.println("|    | min(1, 2, 3, 4) = " + calculator6.calc("min", 1, 2, 3, 4));
			System.out.println("|    | sum(1, 2, 3, 4) = " + calculator6.calc("sum", 1, 2, 3, 4));
			System.out.println("|    | mult(1, 2, 3, 4) = " + calculator6.calc("mult", 1, 2, 3, 4));

			System.out.println("|");
			System.out.println("|--- a component with an aspect");
			final Calculator7 calculator7 = app.getComponentSpace().resolve(Calculator7.class);
			final int result7 = calculator7.sum(1, 2, 3, 4);
			System.out.println("|    | 1+2+3+4 = " + result7);

		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}
