package io.mars.basemanagement.datageneration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.function.Consumer;

import javax.inject.Inject;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import io.mars.basemanagement.dao.BusinessDAO;
import io.mars.basemanagement.dao.GeosectorDAO;
import io.mars.basemanagement.domain.Business;
import io.mars.basemanagement.domain.Geosector;
import io.vertigo.commons.transaction.Transactional;
import io.vertigo.core.component.Component;
import io.vertigo.core.resource.ResourceManager;
import io.vertigo.lang.Assertion;
import io.vertigo.lang.WrappedException;

@Transactional
public class ReferenceDataGenerator implements Component {

	private static final int BUSINESS_CSV_FILE_COLUMN_NUMBER = 1;
	private static final int GEOSECTOR_CSV_FILE_COLUMN_NUMBER = 1;

	@Inject
	private GeosectorDAO geosectorDAO;
	@Inject
	private BusinessDAO businessDAO;
	@Inject
	private ResourceManager resourceManager;

	public void generateReferenceData() {
		createInitialDataFromCSV("initdata/geosectors.csv", this::createInitialGeosectorFromCSV);
		createInitialDataFromCSV("initdata/businesses.csv", this::createInitialBusinessFromCSV);

	}

	private void createInitialBusinessFromCSV(final String[] businessRecord) {
		Assertion.checkArgument(businessRecord.length == BUSINESS_CSV_FILE_COLUMN_NUMBER, "CSV File Format not suitable for Equipment Types");
		// ---
		final String businessName = businessRecord[0];
		businessDAO.create(createBusiness(businessName));
	}

	private void createInitialGeosectorFromCSV(final String[] geoSectorRecord) {
		Assertion.checkArgument(geoSectorRecord.length == GEOSECTOR_CSV_FILE_COLUMN_NUMBER, "CSV File Format not suitable for Equipment Types");
		// ---
		final String geosectorName = geoSectorRecord[0];
		geosectorDAO.create(createGeosector(geosectorName));
	}

	private static Business createBusiness(final String businessName) {
		final Business business = new Business();
		business.setName(businessName);
		return business;
	}

	private static Geosector createGeosector(final String geosectorName) {
		final Geosector geosector = new Geosector();
		geosector.setSectorLabel(geosectorName);
		return geosector;
	}

	private void createInitialDataFromCSV(
			final String csvFilePath, final Consumer<String[]> lineHandler) {
		try (Reader reader = new BufferedReader(new InputStreamReader(resourceManager.resolve(csvFilePath).openStream()));
				CSVReader csvReader = new CSVReaderBuilder(reader)
						.withCSVParser(new CSVParserBuilder()
								.withSeparator(';')
								.build())
						.withSkipLines(1)
						.build();) {
			String[] nextRecord;

			while ((nextRecord = csvReader.readNext()) != null) {
				lineHandler.accept(nextRecord);
			}
		} catch (final IOException e) {
			throw WrappedException.wrap(e, "Can't load csv file {0}", csvFilePath);
		}

	}
}
