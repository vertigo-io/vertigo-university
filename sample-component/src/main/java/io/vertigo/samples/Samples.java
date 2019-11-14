package io.vertigo.samples;

import javax.inject.Inject;

import io.vertigo.core.node.AutoCloseableApp;
import io.vertigo.core.node.Home;
import io.vertigo.core.util.InjectorUtil;
import io.vertigo.samples.components.a_basics.Calculator1;
import io.vertigo.samples.components.a_basics.Calculator2;
import io.vertigo.samples.components.a_basics.Calculator3;
import io.vertigo.samples.components.a_basics.Calculator4;
import io.vertigo.samples.components.b_plugins.Calculator5;
import io.vertigo.samples.components.b_plugins.Calculator6;
import io.vertigo.samples.components.c_aop.Calculator7;
import io.vertigo.samples.components.c_aop.Calculator8;
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
public class Samples {

	@Inject
	private Calculator1 calculator1;
	@Inject
	private Calculator3 calculator3;
	@Inject
	private Calculator4 calculator4;
	@Inject
	private Calculator5 calculator5;
	@Inject
	private Calculator6 calculator6;
	@Inject
	private Calculator7 calculator7;
	@Inject
	private Calculator8 calculator8;

	public static void main(final String[] args) {
		try (AutoCloseableApp app = new AutoCloseableApp(new SampleConfigBuilder().build())) {
			final Samples sample = new Samples();
			InjectorUtil.injectMembers(sample);
			final Samples sample2 = InjectorUtil.newInstance(Samples.class);
			//-----
			sample.step1();
			sample2.step2();
			sample.step3();
			sample.step4();
			sample.step5();
			sample.step6();
			sample.step7();
			sample.step8();

		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	void step1() {
		System.out.println("|--- Samples using components");
		System.out.println("|");
		System.out.println("|--- a simple component");
		final int result1 = calculator1.sum(1, 2, 3, 4);
		System.out.println("|    | 1+2+3+4 = " + result1);

	}

	void step2() {
		final Calculator2 calculator2 = Home.getApp().getComponentSpace().resolve(Calculator2.class);
		System.out.println("|");
		System.out.println("|--- a component with a defined api");
		final int result2 = calculator2.sum(1, 2, 3, 4);
		System.out.println("|    | 1+2+3+4 = " + result2);
	}

	void step3() {
		System.out.println("|");
		System.out.println("|--- a component with params");
		final int result3 = calculator3.sum(1, 2, 3, 4);
		System.out.println("|    | offset+1+2+3+4 = " + result3);
	}

	void step4() {
		System.out.println("|");
		System.out.println("|--- a component with an activeable behavior");
		final int result4 = calculator4.sum(1, 2, 3, 4);
		System.out.println("|    | 1+2+3+4 = " + result4);
	}

	void step5() {

		System.out.println("|");
		System.out.println("|--- a component with plugin");
		final int result5 = calculator5.sum(1, 2, 3, 4);
		System.out.println("|    | 1+2+3+4 = " + result5);
	}

	void step6() {
		System.out.println("|");
		System.out.println("|--- a component with a list of plugins");
		System.out.println("|    | min (1, 2, 3, 4) = " + calculator6.calc("min", 1, 2, 3, 4));
		System.out.println("|    | sum (1, 2, 3, 4) = " + calculator6.calc("sum", 1, 2, 3, 4));
		System.out.println("|    | mult(1, 2, 3, 4) = " + calculator6.calc("mult", 1, 2, 3, 4));
		System.out.println("|    | max (1, 2, 3, 4) = " + calculator6.calc("max", 1, 2, 3, 4));
	}

	void step7() {
		System.out.println("|");
		System.out.println("|--- a component with an aspect");
		final int result7 = calculator7.sum(1, 2, 3, 4);
		System.out.println("|    | 1+2+3+4 = " + result7);
	}

	void step8() {
		System.out.println("|");
		System.out.println("|--- a component with an aspect");
		final int result8 = calculator8.sum(1, 2, 3, 4);
		System.out.println("|    | 1+2+3+4 = " + result8);

		final int result81 = calculator8.mult(1, 2, 3, 4);
		System.out.println("|    | 1*2*3*4 = " + result81);
	}
}
