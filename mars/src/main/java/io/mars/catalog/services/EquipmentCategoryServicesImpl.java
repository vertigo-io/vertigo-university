package io.mars.catalog.services;

import javax.inject.Inject;

import io.mars.catalog.dao.EquipmentTypeDAO;
import io.mars.catalog.domain.EquipmentType;
import io.vertigo.commons.transaction.Transactional;
import io.vertigo.dynamo.criteria.Criterions;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.domain.model.DtListState;

@Transactional
public class EquipmentCategoryServicesImpl implements EquipmentTypeServices {

	@Inject
	private EquipmentTypeDAO equipmentTypeDAO;

	@Override
	public EquipmentType get(final Long equipmentTypeId) {
		return equipmentTypeDAO.get(equipmentTypeId);
	}

	@Override
	public void save(final EquipmentType equipmentType) {
		equipmentTypeDAO.save(equipmentType);
	}

	@Override
	public DtList<EquipmentType> getEquipmentTypes(final DtListState dtListState) {
		return equipmentTypeDAO.findAll(Criterions.alwaysTrue(), dtListState.getMaxRows().orElse(50));
	}
}
