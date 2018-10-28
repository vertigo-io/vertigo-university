package io.mars.catalog.services.equipment;

import javax.inject.Inject;

import io.mars.catalog.dao.EquipmentCategoryDAO;
import io.mars.catalog.dao.EquipmentTypeDAO;
import io.mars.catalog.domain.EquipmentCategory;
import io.mars.catalog.domain.EquipmentType;
import io.vertigo.commons.transaction.Transactional;
import io.vertigo.core.component.Component;
import io.vertigo.dynamo.criteria.Criterions;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.domain.model.DtListState;

@Transactional
public class EquipmentServices implements Component {

	@Inject
	private EquipmentCategoryDAO equipmentCategoryDAO;

	@Inject
	private EquipmentTypeDAO equipmentTypeDAO;

	
	
	public EquipmentCategory getEquipmentCategoryFromId(final Long equipmentCategoryId) {
		return equipmentCategoryDAO.get(equipmentCategoryId);
	}

	public void saveEquipmentCategory(final EquipmentCategory equipmentCategory) {
		equipmentCategoryDAO.save(equipmentCategory);
	}

	public DtList<EquipmentCategory> getEquipmentCategories(final DtListState dtListState) {
		return equipmentCategoryDAO.findAll(Criterions.alwaysTrue(), dtListState.getMaxRows().orElse(50));
	}
	
	
	public EquipmentType getEquipmentTypeFromId(final Long equipmentTypeId) {
		return equipmentTypeDAO.get(equipmentTypeId);
	}

	public void saveEquipmentType(final EquipmentType equipmentType) {
		equipmentTypeDAO.save(equipmentType);
	}

	public DtList<EquipmentType> getEquipmentTypes(final DtListState dtListState) {
		return equipmentTypeDAO.findAll(Criterions.alwaysTrue(), dtListState.getMaxRows().orElse(50));
	}
	
}
