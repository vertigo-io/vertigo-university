package io.mars.base.services;

import javax.inject.Inject;

import io.mars.base.dao.EquipmentDAO;
import io.mars.base.domain.Equipment;
import io.vertigo.commons.transaction.Transactional;
import io.vertigo.dynamo.criteria.Criterions;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.domain.model.DtListState;

@Transactional
public class EquipmentServicesImpl implements EquipmentServices {

	@Inject
	private EquipmentDAO equipmentDAO;

	@Override
	public Equipment get(final Long equipmentId) {
		return equipmentDAO.get(equipmentId);
	}

	@Override
	public void save(final Equipment equipment) {
		equipmentDAO.save(equipment);
	}

	@Override
	public DtList<Equipment> getEquipments(final DtListState dtListState) {
		return equipmentDAO.findAll(Criterions.alwaysTrue(), dtListState.getMaxRows().orElse(50));
	}
}
