/**
 *
 */
package io.vertigo.samples.dao.boot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Collections;

import javax.inject.Inject;

import io.vertigo.core.lang.WrappedException;
import io.vertigo.core.node.component.ComponentInitializer;
import io.vertigo.core.resource.ResourceManager;
import io.vertigo.database.sql.SqlManager;
import io.vertigo.database.sql.connection.SqlConnection;
import io.vertigo.database.sql.statement.SqlStatement;

/**
 * Creates the two in-memory databases at startup :
 * the main one (schema and demo data), and the "mine" one (the schema targeted by the Reprise).
 * @author jmforhan
 */
public class DataBaseInitializer implements ComponentInitializer {

	@Inject
	private ResourceManager resourceManager;
	@Inject
	private SqlManager sqlManager;

	/** {@inheritDoc} */
	@Override
	public void init() {
		initDataBase(SqlManager.MAIN_CONNECTION_PROVIDER_NAME, "sqlgen/crebas.sql", "sql/initdata.sql");
		initDataBase("mine", "sqlgen/crebas_mine.sql");
	}

	private void initDataBase(final String connectionName, final String... scriptPaths) {
		final SqlConnection connection = sqlManager.getConnectionProvider(connectionName).obtainConnection();
		try {
			for (final String scriptPath : scriptPaths) {
				execSqlScript(connection, scriptPath);
			}
			connection.commit();
		} catch (final SQLException e) {
			throw WrappedException.wrap(e, "Can't init database {0}", connectionName);
		} finally {
			try {
				connection.close();
			} catch (final SQLException e) {
				throw WrappedException.wrap(e, "Can't close connection");
			}
		}
	}

	private void execSqlScript(final SqlConnection connection, final String scriptPath) {
		try (final BufferedReader in = new BufferedReader(new InputStreamReader(resourceManager.resolve(scriptPath).openStream(), StandardCharsets.UTF_8))) {
			final StringBuilder sqlStatement = new StringBuilder();
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				final String adaptedInputLine = inputLine.replaceAll("-- .*", "");//removed comments
				if (!"".equals(adaptedInputLine)) {
					sqlStatement.append(adaptedInputLine).append('\n');
				}
				if (inputLine.trim().endsWith(";")) {
					execPreparedStatement(connection, sqlStatement.toString());
					sqlStatement.setLength(0);
				}
			}
		} catch (final IOException e) {
			throw WrappedException.wrap(e, "Can't exec script {0}", scriptPath);
		}
	}

	private void execPreparedStatement(final SqlConnection connection, final String sql) {
		try {
			sqlManager.executeUpdate(SqlStatement.builder(sql).build(), Collections.emptyMap(), connection);
		} catch (final SQLException e) {
			throw WrappedException.wrap(e, "Can't exec command {0}", sql);
		}
	}

}
