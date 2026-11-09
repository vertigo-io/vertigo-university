package io.vertigo.samples.support;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;

import io.vertigo.core.util.InjectorUtil;
import io.vertigo.samples.BootSampleVui;

/**
 * JUnit 5 extension for the integration tests: starts the whole application once for all the tests
 * (same server and same configuration as BootSampleVui), then injects the components into each test class (@Inject).
 * Usage: annotate the test class with @ExtendWith(SampleVuiTestExtension.class).
 */
public final class SampleVuiTestExtension implements BeforeAllCallback, TestInstancePostProcessor {

	private static boolean started;

	@Override
	public void beforeAll(final ExtensionContext context) throws Exception {
		synchronized (SampleVuiTestExtension.class) {
			if (!started) {
				// noJoin: the server must not block the tests
				BootSampleVui.startServer(BootSampleVui.createJettyBootParamsBuilder().noJoin().build());
				started = true;
			}
		}
	}

	@Override
	public void postProcessTestInstance(final Object testInstance, final ExtensionContext context) {
		InjectorUtil.injectMembers(testInstance);
	}
}
