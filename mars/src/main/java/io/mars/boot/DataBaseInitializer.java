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
import java.io.Reader;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import io.mars.basemanagement.BasemanagementPAO;
import io.mars.basemanagement.dao.BaseDAO;
import io.mars.basemanagement.dao.BusinessDAO;
import io.mars.basemanagement.dao.EquipmentDAO;
import io.mars.basemanagement.dao.GeosectorDAO;
import io.mars.basemanagement.domain.Base;
import io.mars.basemanagement.domain.Business;
import io.mars.basemanagement.domain.Equipment;
import io.mars.basemanagement.domain.Geosector;
import io.mars.catalog.dao.EquipmentCategoryDAO;
import io.mars.catalog.dao.EquipmentTypeDAO;
import io.mars.catalog.domain.EquipmentCategory;
import io.mars.catalog.domain.EquipmentType;
import io.mars.domain.DtDefinitions.EquipmentCategoryFields;
import io.mars.humanresources.dao.PersonDAO;
import io.mars.humanresources.domain.Person;
import io.vertigo.commons.transaction.VTransactionManager;
import io.vertigo.commons.transaction.VTransactionWritable;
import io.vertigo.core.component.ComponentInitializer;
import io.vertigo.core.resource.ResourceManager;
import io.vertigo.database.sql.SqlDataBaseManager;
import io.vertigo.database.sql.connection.SqlConnection;
import io.vertigo.database.sql.statement.SqlStatement;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.lang.Assertion;
import io.vertigo.lang.WrappedException;

/**
 * Init sample data for the app.
 * @author dszniten
 */
public class DataBaseInitializer implements ComponentInitializer {

	private static final int EQUIPMENT_TYPE_CSV_FILE_COLUMN_NUMBER = 3;
	private static final int PERSON_CSV_FILE_COLUMN_NUMBER = 3;
	private static final int BUSINESS_CSV_FILE_COLUMN_NUMBER = 1;
	private static final int GEOSECTOR_CSV_FILE_COLUMN_NUMBER = 1;

	@Inject
	private BusinessDAO businessDAO;

	@Inject
	private GeosectorDAO geosectorDAO;

	@Inject
	private ResourceManager resourceManager;
	@Inject
	private VTransactionManager transactionManager;
	@Inject
	private SqlDataBaseManager sqlDataBaseManager;

	@Inject
	private BaseDAO baseDAO;

	@Inject
	private EquipmentDAO equipmentDAO;

	@Inject
	private PersonDAO personDAO;

	@Inject
	private EquipmentCategoryDAO equipmentCategoryDAO;

	@Inject
	private EquipmentTypeDAO equipmentTypeDAO;

	@Inject
	private BasemanagementPAO basemanagementPAO;

	/** {@inheritDoc} */
	@Override
	public void init() {
		createDataBase();
		createInitialGeosectorsFromCSV("initdata/geosectors.csv");
		createInitialBases();
		createInitialPersonsFromCSV("initdata/persons.csv");
		createInitialEquipmentCategories();
		createInitialEquipmentTypesFromCSV("initdata/equipmentTypes.csv");
		createInitialBusinessesFromCSV("initdata/businesses.csv");

		createInitialEquipments();

	}

	private void createDataBase() {
		final SqlConnection connection = sqlDataBaseManager.getConnectionProvider(SqlDataBaseManager.MAIN_CONNECTION_PROVIDER_NAME).obtainConnection();
		execCallableStatement(connection, sqlDataBaseManager, "drop all objects;");
		execSqlScript(connection, "sqlgen/crebas.sql");
		execSqlScript(connection, "sqlgen/init_masterdata_base_type.sql");
		execSqlScript(connection, "sqlgen/init_masterdata_job_status.sql");
		execSqlScript(connection, "sqlgen/init_masterdata_work_order_status.sql");

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
		} catch (final SQLException e) {
			throw WrappedException.wrap(e);
		}
	}

	private void createInitialBases() {
		try (VTransactionWritable tx = transactionManager.createCurrentTransaction()) {

			List<String> nameFirstPartDictionnary1 = Arrays.asList("Alpha", "Beta", "Gamma", "Delta", "Epsilon", "Zeta", "Eta", "Theta");
			List<String> nameSecondPartDictionnary2 = Arrays.asList("Centauri", "Aldebaran", "Pisces", "Cygnus", "Pegasus", "Dragon", "Andromeda");

			List<Base> baseList = new FakeBaseListBuilder()
					.withMaxValues(50)
					.withBasemanagementPAO(basemanagementPAO)
					.withNameDictionnaries(nameFirstPartDictionnary1, nameSecondPartDictionnary2)
					.build();

			for (Base base : baseList) {
				baseDAO.create(base);
			}

			tx.commit();
		}
	}

	private void createInitialEquipments() {
		try (VTransactionWritable tx = transactionManager.createCurrentTransaction()) {
			List<Equipment> equipmentList = new FakeEquipmentListBuilder()
					.withBasemanagementPAO(basemanagementPAO)
					.withBusinessDAO(businessDAO)
					.withEquipmentTypeDAO(equipmentTypeDAO)
					.withMaxValues(100)
					.build();

			for (Equipment equipment : equipmentList) {
				equipmentDAO.create(equipment);
			}

			tx.commit();

		}
	}

	private void createInitialEquipmentCategories() {
		try (VTransactionWritable tx = transactionManager.createCurrentTransaction()) {

			equipmentCategoryDAO.create(createEquipmentCategory(true, "Bot"));
			equipmentCategoryDAO.create(createEquipmentCategory(true, "Building"));
			equipmentCategoryDAO.create(createEquipmentCategory(true, "Vehicle"));
			tx.commit();
		}
	}

	private void createInitialBusinessesFromCSV(final String csvFilePath) {
		try (
				VTransactionWritable tx = transactionManager.createCurrentTransaction();
				Reader reader = new BufferedReader(new InputStreamReader(resourceManager.resolve(csvFilePath).openStream()));
				CSVReader csvReader = new CSVReaderBuilder(reader)
						.withCSVParser(new CSVParserBuilder()
								.withSeparator(';')
								.build())
						.withSkipLines(1)
						.build();) {
			String[] nextRecord;

			while ((nextRecord = csvReader.readNext()) != null) {
				Assertion.checkArgument(nextRecord.length == BUSINESS_CSV_FILE_COLUMN_NUMBER, "CSV File {0} Format not suitable for Equipment Types", csvFilePath);

				final String businessName = nextRecord[0];
				businessDAO.create(createBusiness(businessName));
			}
			tx.commit();

		} catch (final IOException e) {
			throw WrappedException.wrap(e, "Can't load csv file {0}", csvFilePath);
		}

	}

	private void createInitialGeosectorsFromCSV(final String csvFilePath) {
		try (
				VTransactionWritable tx = transactionManager.createCurrentTransaction();
				Reader reader = new BufferedReader(new InputStreamReader(resourceManager.resolve(csvFilePath).openStream()));
				CSVReader csvReader = new CSVReaderBuilder(reader)
						.withCSVParser(new CSVParserBuilder()
								.withSeparator(';')
								.build())
						.withSkipLines(1)
						.build();) {
			String[] nextRecord;

			while ((nextRecord = csvReader.readNext()) != null) {
				Assertion.checkArgument(nextRecord.length == GEOSECTOR_CSV_FILE_COLUMN_NUMBER, "CSV File {0} Format not suitable for Equipment Types", csvFilePath);

				final String geosectorName = nextRecord[0];
				geosectorDAO.create(createGeosector(geosectorName));
			}
			tx.commit();

		} catch (final IOException e) {
			throw WrappedException.wrap(e, "Can't load csv file {0}", csvFilePath);
		}

	}

	private void createInitialEquipmentTypesFromCSV(final String csvFilePath) {

		try (
				VTransactionWritable tx = transactionManager.createCurrentTransaction();
				Reader reader = new BufferedReader(new InputStreamReader(resourceManager.resolve(csvFilePath).openStream()));
				CSVReader csvReader = new CSVReaderBuilder(reader)
						.withCSVParser(new CSVParserBuilder()
								.withSeparator(';')
								.build())
						.withSkipLines(1)
						.build();) {
			String[] nextRecord;
			String currentCategoryLabel = "";
			EquipmentCategory equipmentCategory = null;

			while ((nextRecord = csvReader.readNext()) != null) {
				Assertion.checkArgument(nextRecord.length == EQUIPMENT_TYPE_CSV_FILE_COLUMN_NUMBER, "CSV File {0} Format not suitable for Equipment Types", csvFilePath);

				final Boolean enabled = Boolean.valueOf(nextRecord[0]);
				final String nextCategoryLabel = nextRecord[1];
				final String equipmentTypeName = nextRecord[2];

				if (nextCategoryLabel != currentCategoryLabel) {
					currentCategoryLabel = nextCategoryLabel;
					final DtList<EquipmentCategory> categoryList = equipmentCategoryDAO.getListByDtFieldName(EquipmentCategoryFields.LABEL, nextCategoryLabel, 1);
					Assertion.checkArgument(categoryList.size() == 1, "Could not fully determine Equipment Category Entity for category : {0} ", nextCategoryLabel);
					equipmentCategory = categoryList.get(0);
				}
				equipmentTypeDAO.create(createEquipmentType(enabled, equipmentTypeName, equipmentCategory));
			}

			tx.commit();

		} catch (final IOException e) {
			throw WrappedException.wrap(e, "Can't load csv file {0}", csvFilePath);
		}

	}

	private void createInitialPersonsFromCSV(
			final String csvFilePath) {

		try (
				VTransactionWritable tx = transactionManager.createCurrentTransaction();
				Reader reader = new BufferedReader(new InputStreamReader(resourceManager.resolve(csvFilePath).openStream()));
				CSVReader csvReader = new CSVReaderBuilder(reader)
						.withCSVParser(new CSVParserBuilder()
								.withSeparator(';')
								.build())
						.withSkipLines(1)
						.build();) {
			String[] nextRecord;

			while ((nextRecord = csvReader.readNext()) != null) {
				Assertion.checkArgument(nextRecord.length == PERSON_CSV_FILE_COLUMN_NUMBER, "CSV File {0} Format not suitable for Persons", csvFilePath);

				final String firstName = nextRecord[0];
				final String lastName = nextRecord[1];
				final String email = nextRecord[2];

				personDAO.create(createPerson(firstName, lastName, email));
			}

			tx.commit();

		} catch (final IOException e) {
			throw WrappedException.wrap(e, "Can't load csv file {0}", csvFilePath);
		}

	}

	private Business createBusiness(String businessName) {
		Business business = new Business();
		business.setName(businessName);
		return business;
	}

	private Geosector createGeosector(String geosectorName) {
		Geosector geosector = new Geosector();
		geosector.setSectorLabel(geosectorName);
		return geosector;
	}

	private static Person createPerson(final String firstName,
			final String lastName,
			final String eMail) {
		final Person person = new Person();
		person.setFirstName(firstName);
		person.setLastName(lastName);
		person.setEmail(eMail);
		return person;
	}

	private static EquipmentCategory createEquipmentCategory(final boolean active, final String label) {
		final EquipmentCategory equipmentCategory = new EquipmentCategory();
		equipmentCategory.setActive(active);
		equipmentCategory.setLabel(label);
		return equipmentCategory;
	}

	private static EquipmentType createEquipmentType(final boolean active, final String label, final EquipmentCategory equipmentCategory) {
		final EquipmentType equipmentType = new EquipmentType();
		equipmentType.setActive(active);
		equipmentType.setLabel(label);
		equipmentType.equipmentCategory().set(equipmentCategory);
		return equipmentType;
	}

}
