/**
 *
 */
package io.vertigo.samples.dao.boot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

import javax.inject.Inject;

import io.vertigo.core.resource.ResourceManager;
import io.vertigo.core.spaces.component.ComponentInitializer;
import io.vertigo.dynamo.database.SqlDataBaseManager;
import io.vertigo.dynamo.database.connection.SqlConnection;
import io.vertigo.dynamo.database.statement.SqlCallableStatement;
import io.vertigo.dynamo.transaction.VTransactionManager;
import io.vertigo.lang.WrappedException;

/**
 * Init masterdata list.
 * @author jmforhan
 */
public class DataBaseInitializer implements ComponentInitializer {

	@Inject
	private ResourceManager resourceManager;
	@Inject
	private VTransactionManager transactionManager;
	@Inject
	private SqlDataBaseManager sqlDataBaseManager;

	/** {@inheritDoc} */
	@Override
	public void init() {
		createDataBase();
	}

	private void createDataBase() {
		SqlConnection connection;
		try {
			connection = sqlDataBaseManager.getConnectionProvider(SqlDataBaseManager.MAIN_CONNECTION_PROVIDER_NAME).obtainConnection();
		} catch (final SQLException e) {
			throw WrappedException.wrapIfNeeded(e, "Can't open connection");
		}
		execSqlScript(connection, "sqlgen/crebas.sql");
	}

	private void execSqlScript(final SqlConnection connection, final String scriptPath) {
		try {
			final StringBuilder crebaseSql = new StringBuilder();
			final BufferedReader in = new BufferedReader(new InputStreamReader(resourceManager.resolve(scriptPath).openStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				final String adaptedInputLine = inputLine.replaceAll("-- .*", "");//removed comments
				if (!"".equals(adaptedInputLine)) {
					crebaseSql.append(adaptedInputLine).append('\n');
				}
				if (inputLine.trim().endsWith(";")) {
					execCallableStatement(connection, sqlDataBaseManager, crebaseSql.toString());
					crebaseSql.setLength(0);
				}
			}
			in.close();
		} catch (final IOException e) {
			throw WrappedException.wrapIfNeeded(e, "Can't exec script {0}", scriptPath);
		}
	}

	private static void execCallableStatement(final SqlConnection connection, final SqlDataBaseManager sqlDataBaseManager, final String sql) {
		try (final SqlCallableStatement callableStatement = sqlDataBaseManager.createCallableStatement(connection, sql)) {
			callableStatement.init();
			callableStatement.executeUpdate();
		} catch (final SQLException e) {
			throw WrappedException.wrapIfNeeded(e, "Can't exec command {0}", sql);
		}
	}

}
