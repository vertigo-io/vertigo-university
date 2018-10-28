package io.mars.basemanagement.services.equipment;

import javax.inject.Inject;

import io.mars.basemanagement.dao.EquipmentDAO;
import io.mars.basemanagement.domain.Equipment;
import io.vertigo.commons.transaction.Transactional;
import io.vertigo.core.component.Component;
import io.vertigo.dynamo.criteria.Criterions;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.domain.model.DtListState;

@Transactional
public class EquipmentServicesImpl implements Component {

	@Inject
	private EquipmentDAO equipmentDAO;

	public Equipment get(final Long equipmentId) {
		return equipmentDAO.get(equipmentId);
	}

	public void save(final Equipment equipment) {
		equipmentDAO.save(equipment);
	}

	public DtList<Equipment> getEquipments(final DtListState dtListState) {
		return equipmentDAO.findAll(Criterions.alwaysTrue(), dtListState.getMaxRows().orElse(50));
	}
}
