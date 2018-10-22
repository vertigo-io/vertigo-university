/**
 * vertigo - simple java starter
 *
 * Copyright (C) 2013-2018, KleeGroup, direction.technique@kleegroup.com (http://www.kleegroup.com)
 * KleeGroup, Centre d'affaire la Boursidiere - BP 159 - 92357 Le Plessis Robinson Cedex - France
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 *
 */
package io.mars.boot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.inject.Inject;

import io.mars.base.dao.BaseDAO;
import io.mars.base.domain.Base;
import io.mars.base.domain.BaseType;
import io.mars.base.domain.BaseTypeEnum;
import io.vertigo.commons.transaction.VTransactionManager;
import io.vertigo.commons.transaction.VTransactionWritable;
import io.vertigo.core.component.ComponentInitializer;
import io.vertigo.core.resource.ResourceManager;
import io.vertigo.database.sql.SqlDataBaseManager;
import io.vertigo.database.sql.connection.SqlConnection;
import io.vertigo.database.sql.statement.SqlStatement;
import io.vertigo.lang.WrappedException;
/*import io.vertigo.mars.dao.movies.MovieDAO;
import io.vertigo.mars.domain.movies.Movie;*/

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

	@Inject
	private BaseDAO baseDao;

	
	/** {@inheritDoc} */
	@Override
	public void init() {
		createDataBase();
		createInitialBases(baseDao, transactionManager);
		

	}

	private void createDataBase() {
		final SqlConnection connection = sqlDataBaseManager.getConnectionProvider(SqlDataBaseManager.MAIN_CONNECTION_PROVIDER_NAME).obtainConnection();
		execSqlScript(connection, "sqlgen/crebas.sql");
		execSqlScript(connection, "sqlgen/init_masterdata_base_type.sql");		
		
	}

	private void execSqlScript(final SqlConnection connection, final String scriptPath) {
		try (final BufferedReader in = new BufferedReader(new InputStreamReader(resourceManager.resolve(scriptPath).openStream()))) {
			final StringBuilder crebaseSql = new StringBuilder();
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
		} catch (final IOException e) {
			throw WrappedException.wrap(e, "Can't exec script {0}", scriptPath);
		}
	}

	private static void execCallableStatement(final SqlConnection connection, final SqlDataBaseManager sqlDataBaseManager, final String sql) {
		try {
			sqlDataBaseManager.executeUpdate(SqlStatement.builder(sql).build(), connection);
		} catch (final SQLException e) {
			throw WrappedException.wrap(e, "Can't exec command {0}", sql);
		}
		try {
			connection.commit();
		} catch (SQLException e) {
			throw WrappedException.wrap(e);
		}
	}

	private static void createInitialBases(final BaseDAO baseDao, final VTransactionManager transactionManager) {
		try (VTransactionWritable tx = transactionManager.createCurrentTransaction()) {
			baseDao.create(createBase(BaseTypeEnum.hydro,
									"Alpha Base", 
									"ALPHA1", 
									85, 
									LocalDate.of(2023, 12, 5), 
									"This facility is the first to have been established in Valles Marineris and one of the first martian base ever established. It was named after the first lunar base Alpha, before the Moon disappeared from the solar system in 1999.", 
									"43,566666N 51,47795W",
									BigDecimal.valueOf(12500.33), 
									BigDecimal.valueOf(255.40)));
			tx.commit();
		}
	}
	
	
	
	private static Base createBase(final BaseTypeEnum baseTypeEnumValue, 
									final String baseName, 
									final String baseCode, 
									final int healthLevel, 
									final LocalDate creationDate,
									final String description,
									final String geoLocation,
									final BigDecimal assetsValue,
									final BigDecimal rentingFee) {
		final Base base = new Base();
		base.setCode(baseCode);
		base.setName(baseName);
		base.baseType().setEnumValue(baseTypeEnumValue);
		base.setHealthLevel(healthLevel);
		base.setCreationDate(creationDate);
		base.setDescription(description);
		base.setGeoLocation(geoLocation);
		base.setAssetsValue(assetsValue);
		base.setRentingFee(rentingFee);
		return base;
	}
}
