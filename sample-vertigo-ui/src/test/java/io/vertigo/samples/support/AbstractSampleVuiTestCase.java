package io.vertigo.samples.support;

import org.junit.jupiter.api.extension.ExtendWith;

import io.vertigo.core.node.Node;
import io.vertigo.datastore.cache.CacheManager;

/**
 * Parent class of the integration tests: the application is started once for all the tests (SampleVuiTestExtension),
 * and the components are injected into the test class (@Inject).
 * Tests that modify data extend AbstractSampleVuiRollbackTestCase instead.
 */
@ExtendWith(SampleVuiTestExtension.class)
public abstract class AbstractSampleVuiTestCase {

	/**
	 * Clears the caches of the application (entities read before a rollback must not be served again).
	 */
	protected static void clearCache() {
		Node.getNode().getComponentSpace().resolve(CacheManager.class).clearAll();
	}
}
