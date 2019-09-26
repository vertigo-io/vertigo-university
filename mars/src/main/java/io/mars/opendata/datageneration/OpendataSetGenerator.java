package io.mars.opendata.datageneration;

import javax.inject.Inject;

import io.mars.opendata.dao.OpendataSetDAO;
import io.mars.opendata.domain.OpendataSet;
import io.mars.opendata.domain.OpendataSetStatusEnum;
import io.mars.support.util.CSVReaderUtil;
import io.vertigo.commons.transaction.Transactional;
import io.vertigo.core.component.Component;
import io.vertigo.core.resource.ResourceManager;
import io.vertigo.lang.Assertion;

@Transactional
public class OpendataSetGenerator implements Component {

	private static final int OPENDATA_SERVICES_CSV_FILE_COLUMN_NUMBER = 6;

	@Inject
	private ResourceManager resourceManager;
	@Inject
	private OpendataSetDAO opendataSetDAO;

	public void createInitialOpendataSetsFromCSV(final String csvFilePath) {
		CSVReaderUtil.parseCSV(resourceManager, csvFilePath, this::consume);
	}

	private void consume(String csvFilePath, String[] record) {
		Assertion.checkArgument(record.length == OPENDATA_SERVICES_CSV_FILE_COLUMN_NUMBER, "CSV File {0} Format not suitable for Equipment Types", csvFilePath);
		//
		final OpendataSetStatusEnum status;
		switch (record[0]) {
			case "Enabled":
			case "enabled":
				status = OpendataSetStatusEnum.enabled;
				break;
			case "Disabled":
			case "disabled":
				status = OpendataSetStatusEnum.disabled;
				break;
			default:
				throw new RuntimeException("Can't load csv file " + csvFilePath + " unknown status value : " + record[0]);
		}

		final String code = record[1];
		final String title = record[2];
		final String description = record[3];
		final String endpointURL = record[4];
		final String tags = record[5];

		opendataSetDAO.create(createOpendataSet(status, code, title, description, endpointURL, tags));
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
