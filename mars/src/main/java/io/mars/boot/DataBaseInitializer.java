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
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.inject.Inject;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import io.mars.basemanagement.dao.BaseDAO;
import io.mars.basemanagement.domain.Base;
import io.mars.basemanagement.domain.BaseTypeEnum;
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
 * Init masterdata list.
 * @author jmforhan
 */
public class DataBaseInitializer implements ComponentInitializer {

	private static final int EQUIPMENT_TYPE_CSV_FILE_COLUMN_NUMBER = 3;
	private static final int PERSON_CSV_FILE_COLUMN_NUMBER = 3;

	@Inject
	private ResourceManager resourceManager;
	@Inject
	private VTransactionManager transactionManager;
	@Inject
	private SqlDataBaseManager sqlDataBaseManager;

	@Inject
	private BaseDAO baseDAO;

	@Inject
	private PersonDAO personDAO;

	@Inject
	private EquipmentCategoryDAO equipmentCategoryDAO;

	@Inject
	private EquipmentTypeDAO equipmentTypeDAO;

	/** {@inheritDoc} */
	@Override
	public void init() {
		createDataBase();
		createInitialBases(baseDAO, transactionManager);
		// createInitialPersons(personDao, transactionManager);
		createInitialPersonsFromCSV("initdata/persons.csv");
		createInitialEquipmentCategories();
		// createInitialEquipmentTypes(equipmentTypeDAO,equipmentCategoryDAO, transactionManager);
		createInitialEquipmentTypesFromCSV("initdata/equipmentTypes.csv");

	}

	private void createDataBase() {
		final SqlConnection connection = sqlDataBaseManager.getConnectionProvider(SqlDataBaseManager.MAIN_CONNECTION_PROVIDER_NAME).obtainConnection();
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

	/*
	private static void createInitialPersons(final PersonDAO personDao, final VTransactionManager transactionManager) {
		try (VTransactionWritable tx = transactionManager.createCurrentTransaction()) {
			personDao.create(createPerson(
					"James T.", "Kirk", "jtkirk@mmc.co.mars"));
			personDao.create(createPerson(
					"John", "König", "jkonig@mmc.co.mars"));
			personDao.create(createPerson(
					"Neil", "Armstrong", "narmstrong@mmc.co.mars"));
			personDao.create(createPerson(
					"Jean-Luc", "Picard", "jlpicard@mmc.co.mars"));
			personDao.create(createPerson(
					"Darth", "Vader", "dvader@mmc.co.mars"));
			personDao.create(createPerson(
					"Han", "Solo", "hsolo@mmc.co.mars"));
			personDao.create(createPerson(
					"Jim", "Lovell", "jlovell@mmc.co.mars"));
			personDao.create(createPerson(
					"William", "Adama", "wadama@mmc.co.mars"));
			personDao.create(createPerson(
					"Worker", "One", "wone@mmc.co.mars"));
			personDao.create(createPerson(
					"Worker", "Two", "wtwo@mmc.co.mars"));
			personDao.create(createPerson(
					"Worker", "Three", "wthree@mmc.co.mars"));
			tx.commit();
		}
	}
	 */

	private void createInitialEquipmentCategories() {
		try (VTransactionWritable tx = transactionManager.createCurrentTransaction()) {

			equipmentCategoryDAO.create(createEquipmentCategory(true, "Bot"));
			equipmentCategoryDAO.create(createEquipmentCategory(true, "Building"));
			equipmentCategoryDAO.create(createEquipmentCategory(true, "Vehicle"));
			tx.commit();
		}
	}

	private void createInitialEquipmentTypesFromCSV(
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

	/*
	private static void createInitialEquipmentTypes(final EquipmentTypeDAO equipmentTypeDAO, final EquipmentCategoryDAO equipmentCategoryDAO, final VTransactionManager transactionManager) {
		try (VTransactionWritable tx = transactionManager.createCurrentTransaction()) {
	
			// Buildings
			DtList<EquipmentCategory> categoryList = equipmentCategoryDAO.getListByDtFieldName(EquipmentCategoryFields.LABEL, "Building", 1);
			EquipmentCategory equipmentCategory = categoryList.get(0);
	
			equipmentTypeDAO.create(createEquipmentType(true, "Wind Power Plant", equipmentCategory));
			equipmentTypeDAO.create(createEquipmentType(true, "Nuclear Power Plant", equipmentCategory));
			equipmentTypeDAO.create(createEquipmentType(true, "Solar Power Plant", equipmentCategory));
			equipmentTypeDAO.create(createEquipmentType(true, "Mobile Power Generator", equipmentCategory));
			equipmentTypeDAO.create(createEquipmentType(true, "Power Suply Depot", equipmentCategory));
	
			equipmentTypeDAO.create(createEquipmentType(true, "Hydroponic Farm", equipmentCategory));
			equipmentTypeDAO.create(createEquipmentType(true, "Martian Soil Farm", equipmentCategory));
			equipmentTypeDAO.create(createEquipmentType(true, "Ranch", equipmentCategory));
			equipmentTypeDAO.create(createEquipmentType(true, "Food Factory", equipmentCategory));
			equipmentTypeDAO.create(createEquipmentType(true, "Farming Area", equipmentCategory));
	
			equipmentTypeDAO.create(createEquipmentType(true, "Hospital", equipmentCategory));
	
			equipmentTypeDAO.create(createEquipmentType(true, "Radio Station", equipmentCategory));
	
			equipmentTypeDAO.create(createEquipmentType(true, "Water Station", equipmentCategory));
			equipmentTypeDAO.create(createEquipmentType(true, "Terraformation Plant", equipmentCategory));
			equipmentTypeDAO.create(createEquipmentType(true, "Air Recycling Station", equipmentCategory));
	
			equipmentTypeDAO.create(createEquipmentType(true, "Mining Area", equipmentCategory));
			equipmentTypeDAO.create(createEquipmentType(true, "Mine", equipmentCategory));
	
			equipmentTypeDAO.create(createEquipmentType(true, "Police Station", equipmentCategory));
			equipmentTypeDAO.create(createEquipmentType(true, "Fire Station", equipmentCategory));
	
			equipmentTypeDAO.create(createEquipmentType(true, "Robotics Facility", equipmentCategory));
	
			// Vehicles
			categoryList = equipmentCategoryDAO.getListByDtFieldName(EquipmentCategoryFields.LABEL, "Vehicle", 1);
			equipmentCategory = categoryList.get(0);
	
			equipmentTypeDAO.create(createEquipmentType(true, "Mobile Power Generator", equipmentCategory));
	
			equipmentTypeDAO.create(createEquipmentType(true, "Tractor", equipmentCategory));
	
			equipmentTypeDAO.create(createEquipmentType(true, "Field Hospital", equipmentCategory));
			equipmentTypeDAO.create(createEquipmentType(true, "Medic Vehicle", equipmentCategory));
			equipmentTypeDAO.create(createEquipmentType(true, "Bacta Tank", equipmentCategory));
	
			equipmentTypeDAO.create(createEquipmentType(true, "Satellite", equipmentCategory));
	
			equipmentTypeDAO.create(createEquipmentType(true, "Space Construction Vehicle", equipmentCategory));
			equipmentTypeDAO.create(createEquipmentType(true, "Martian Mining Vehicle", equipmentCategory));
	
			equipmentTypeDAO.create(createEquipmentType(true, "Emergency Vehicle", equipmentCategory));
	
			// Bots
			categoryList = equipmentCategoryDAO.getListByDtFieldName(EquipmentCategoryFields.LABEL, "Bot", 1);
			equipmentCategory = categoryList.get(0);
	
			equipmentTypeDAO.create(createEquipmentType(true, "Satellite", equipmentCategory));
			equipmentTypeDAO.create(createEquipmentType(true, "Droid", equipmentCategory));
			equipmentTypeDAO.create(createEquipmentType(true, "Drone", equipmentCategory));
	
			tx.commit();
		}
	}
	*/

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
