package io.mars.basemanagement.datageneration;

import java.util.List;

import javax.inject.Inject;

import io.mars.basemanagement.BasemanagementPAO;
import io.mars.basemanagement.dao.BusinessDAO;
import io.mars.basemanagement.dao.EquipmentDAO;
import io.mars.basemanagement.domain.Base;
import io.mars.basemanagement.domain.Equipment;
import io.mars.catalog.dao.EquipmentCategoryDAO;
import io.mars.catalog.dao.EquipmentTypeDAO;
import io.mars.catalog.domain.EquipmentCategory;
import io.mars.catalog.domain.EquipmentType;
import io.mars.domain.DtDefinitions.EquipmentCategoryFields;
import io.mars.support.util.CSVReaderUtil;
import io.vertigo.commons.transaction.Transactional;
import io.vertigo.core.component.Component;
import io.vertigo.core.resource.ResourceManager;
import io.vertigo.dynamo.criteria.Criterions;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.lang.Assertion;

@Transactional
public class EquipmentGenerator implements Component {

	private static final int EQUIPMENT_TYPE_CSV_FILE_COLUMN_NUMBER = 3;

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

	public void createInitialEquipments(final int equipmentUnitsToGenerate, final List<Base> bases) {
		final DtList<Equipment> equipments = new FakeEquipmentListBuilder()
				.withBases(bases)
				.withGeosectorIdList(basemanagementPAO.selectGeosectorId())
				.withBusinessList(businessDAO.selectBusiness())
				.withEquipmentTypeList(equipmentTypeDAO.selectEquipmentType())
				.withMaxValues(equipmentUnitsToGenerate)
				.build();

		equipmentDAO.insertEquipmentsBatch(equipments);
	}

	public void createInitialEquipmentCategories() {
		equipmentCategoryDAO.create(createEquipmentCategory(true, "Bot"));
		equipmentCategoryDAO.create(createEquipmentCategory(true, "Building"));
		equipmentCategoryDAO.create(createEquipmentCategory(true, "Vehicle"));
	}

	private void consume(final String csvFilePath, final String[] record) {
		Assertion.checkArgument(record.length == EQUIPMENT_TYPE_CSV_FILE_COLUMN_NUMBER,
				"CSV File {0} Format not suitable for Equipment Types", csvFilePath);
		//---
		final Boolean enabled = Boolean.valueOf(record[0]);
		final String nextCategoryLabel = record[1];
		final String equipmentTypeName = record[2];
		final EquipmentCategory equipmentCategory = equipmentCategoryDAO.find(Criterions.isEqualTo(EquipmentCategoryFields.label, nextCategoryLabel));

		equipmentTypeDAO.create(createEquipmentType(enabled, equipmentTypeName, equipmentCategory));
	}

	public void createInitialEquipmentTypesFromCSV(final String csvFilePath) {
		CSVReaderUtil.parseCSV(resourceManager, csvFilePath, this::consume);
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
