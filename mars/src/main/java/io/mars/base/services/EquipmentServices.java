package io.mars.base.services;

import io.mars.base.domain.Equipment;
import io.vertigo.core.component.Component;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.domain.model.DtListState;

public interface EquipmentServices extends Component {

	DtList<Equipment> getEquipments(DtListState dtListState);

	void save(Equipment equipment);

	Equipment get(Long equipmentId);
}
