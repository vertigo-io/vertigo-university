package io.mars.opendata.datageneration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.inject.Inject;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import io.mars.opendata.dao.OpendataSetDAO;
import io.mars.opendata.domain.OpendataSet;
import io.mars.opendata.domain.OpendataSetStatusEnum;
import io.vertigo.commons.transaction.Transactional;
import io.vertigo.core.component.Component;
import io.vertigo.core.resource.ResourceManager;
import io.vertigo.lang.Assertion;
import io.vertigo.lang.WrappedException;

@Transactional
public class OpendataSetGenerator implements Component {

	private static final int OPENDATA_SERVICES_CSV_FILE_COLUMN_NUMBER = 6;

	@Inject
	private ResourceManager resourceManager;
	@Inject
	private OpendataSetDAO opendataSetDAO;

	public void createInitialOpendataSetsFromCSV(final String csvFilePath) {
		try (Reader reader = new BufferedReader(new InputStreamReader(resourceManager.resolve(csvFilePath).openStream()));
				CSVReader csvReader = new CSVReaderBuilder(reader)
						.withCSVParser(new CSVParserBuilder()
								.withSeparator(';')
								.build())
						.withSkipLines(1)
						.build();) {
			String[] nextRecord;

			while ((nextRecord = csvReader.readNext()) != null) {
				Assertion.checkArgument(nextRecord.length == OPENDATA_SERVICES_CSV_FILE_COLUMN_NUMBER, "CSV File {0} Format not suitable for Equipment Types", csvFilePath);
				//
				final OpendataSetStatusEnum status;
				switch (nextRecord[0]) {
					case "Enabled":
					case "enabled":
						status = OpendataSetStatusEnum.enabled;
						break;
					case "Disabled":
					case "disabled":
						status = OpendataSetStatusEnum.disabled;
						break;
					default:
						throw new IOException("Can't load csv file " + csvFilePath + " unknown status value : " + nextRecord[0]);
				}

				final String code = nextRecord[1];
				final String title = nextRecord[2];
				final String description = nextRecord[3];
				final String endpointURL = nextRecord[4];
				final String tags = nextRecord[5];

				opendataSetDAO.create(createOpendataSet(status, code, title, description, endpointURL, tags));
			}

		} catch (final IOException e) {
			throw WrappedException.wrap(e, "Can't load csv file {0}", csvFilePath);
		}

	}

	private static OpendataSet createOpendataSet(
			final OpendataSetStatusEnum opendataSetStatusEnumValue,
			final String code,
			final String title,
			final String description,
			final String endpointURL,
			final String tags) {
		final OpendataSet opendataSet = new OpendataSet();
		opendataSet.opendataSetStatus().setEnumValue(opendataSetStatusEnumValue);
		opendataSet.setTitle(title);
		opendataSet.setDescription(description);
		opendataSet.setCode(code);
		opendataSet.setEndPointUrl(endpointURL);
		opendataSet.setTags(tags);
		return opendataSet;
	}

}
