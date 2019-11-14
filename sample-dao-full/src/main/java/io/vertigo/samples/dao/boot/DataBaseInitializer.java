/**
 *
 */
package io.vertigo.samples.dao.boot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

import javax.inject.Inject;

import io.vertigo.core.lang.WrappedException;
import io.vertigo.core.node.component.ComponentInitializer;
import io.vertigo.core.resource.ResourceManager;
import io.vertigo.database.sql.SqlDataBaseManager;
import io.vertigo.database.sql.connection.SqlConnection;
import io.vertigo.database.sql.statement.SqlStatement;

/**
 * Init masterdata list.
 * @author jmforhan
 */
public class DataBaseInitializer implements ComponentInitializer {

	@Inject
	private ResourceManager resourceManager;
	@Inject
	private SqlDataBaseManager sqlDataBaseManager;

	/** {@inheritDoc} */
	@Override
	public void init() {
		createDataBase();
	}

	private void createDataBase() {
		SqlConnection connection;
		connection = sqlDataBaseManager.getConnectionProvider("mine").obtainConnection();
		execSqlScript(connection, "sqlgen/crebas.sql");
		execSqlScript(connection, "sqlgen/crebas_mine.sql");
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
					execPreparedStatement(connection, sqlDataBaseManager, crebaseSql.toString());
					crebaseSql.setLength(0);
				}
			}
			in.close();
		} catch (final IOException e) {
			throw WrappedException.wrap(e, "Can't exec script {0}", scriptPath);
		}
	}

	private static void execPreparedStatement(final SqlConnection connection, final SqlDataBaseManager sqlDataBaseManager, final String sql) {
		try {
			sqlDataBaseManager
					.executeUpdate(SqlStatement.builder(sql).build(), connection);
		} catch (final SQLException e) {
			throw WrappedException.wrap(e, "Can't exec command {0}", sql);
		}
	}

}
