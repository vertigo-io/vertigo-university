package io.vertigo.samples.support;

import javax.inject.Inject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import io.vertigo.commons.transaction.VTransactionManager;
import io.vertigo.commons.transaction.VTransactionWritable;

/**
 * Parent class of the integration tests that modify data: each test runs in a transaction that is rolled back
 * at the end, so the database keeps its demo data for the other tests.
 * The services called by the test reuse this transaction (@Transactional), instead of committing their own.
 */
public abstract class AbstractSampleVuiRollbackTestCase extends AbstractSampleVuiTestCase {

	@Inject
	private VTransactionManager transactionManager;

	private VTransactionWritable currentTransaction;

	@BeforeEach
	public final void createTransaction() {
		currentTransaction = transactionManager.createCurrentTransaction();
	}

	@AfterEach
	public final void rollbackTransaction() {
		if (transactionManager.hasCurrentTransaction()) {
			currentTransaction.rollback();
		} else {
			Assertions.fail("Every test must run in a transaction that is rolled back");
		}
		clearCache();
	}
}
