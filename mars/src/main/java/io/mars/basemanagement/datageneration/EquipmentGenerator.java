package io.mars.basemanagement.datageneration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.inject.Inject;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import io.mars.basemanagement.BasemanagementPAO;
import io.mars.basemanagement.dao.BusinessDAO;
import io.mars.basemanagement.dao.EquipmentDAO;
import io.mars.basemanagement.domain.Equipment;
import io.mars.catalog.dao.EquipmentCategoryDAO;
import io.mars.catalog.dao.EquipmentTypeDAO;
import io.mars.catalog.domain.EquipmentCategory;
import io.mars.catalog.domain.EquipmentType;
import io.mars.datageneration.GenerationConfig;
import io.mars.domain.DtDefinitions.EquipmentCategoryFields;
import io.vertigo.commons.transaction.Transactional;
import io.vertigo.core.component.Component;
import io.vertigo.core.resource.ResourceManager;
import io.vertigo.dynamo.criteria.Criterions;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.lang.Assertion;
import io.vertigo.lang.WrappedException;

@Transactional
public class EquipmentGenerator implements Component {

	@Inject
	private EquipmentDAO equipmentDAO;
	@Inject
	private EquipmentCategoryDAO equipmentCategoryDAO;
	@Inject
	private EquipmentTypeDAO equipmentTypeDAO;
	@Inject
	private BasemanagementPAO basemanagementPAO;
	@Inject
	private BusinessDAO businessDAO;
	@Inject
	private ResourceManager resourceManager;

	public void createInitialEquipments() {
		final DtList<Equipment> equipmentList = new FakeEquipmentListBuilder()
				.withBaseIdList(basemanagementPAO.selectBaseId())
				.withGeosectorIdList(basemanagementPAO.selectGeosectorId())
				.withBusinessList(businessDAO.selectBusiness())
				.withEquipmentTypeList(equipmentTypeDAO.selectEquipmentType())
				.withMaxValues(GenerationConfig.EQUIPMENT_UNITS_TO_GENERATE)
				.build();

		equipmentDAO.insertEquipmentsBatch(equipmentList);
	}

	public void createInitialEquipmentCategories() {
		equipmentCategoryDAO.create(createEquipmentCategory(true, "Bot"));
		equipmentCategoryDAO.create(createEquipmentCategory(true, "Building"));
		equipmentCategoryDAO.create(createEquipmentCategory(true, "Vehicle"));
	}

	public void createInitialEquipmentTypesFromCSV(final String csvFilePath) {
		try (Reader reader = new BufferedReader(new InputStreamReader(resourceManager.resolve(csvFilePath).openStream()));
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
				Assertion.checkArgument(nextRecord.length == GenerationConfig.EQUIPMENT_TYPE_CSV_FILE_COLUMN_NUMBER, "CSV File {0} Format not suitable for Equipment Types", csvFilePath);

				final Boolean enabled = Boolean.valueOf(nextRecord[0]);
				final String nextCategoryLabel = nextRecord[1];
				final String equipmentTypeName = nextRecord[2];

				if (nextCategoryLabel != currentCategoryLabel) {
					currentCategoryLabel = nextCategoryLabel;
					equipmentCategory = equipmentCategoryDAO.find(Criterions.isEqualTo(EquipmentCategoryFields.LABEL, nextCategoryLabel));
				}
				equipmentTypeDAO.create(createEquipmentType(enabled, equipmentTypeName, equipmentCategory));
			}

		} catch (final IOException e) {
			throw WrappedException.wrap(e, "Can't load csv file {0}", csvFilePath);
		}

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
