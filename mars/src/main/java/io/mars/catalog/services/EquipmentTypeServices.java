package io.mars.catalog.services;

import io.mars.catalog.domain.EquipmentType;
import io.vertigo.core.component.Component;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.domain.model.DtListState;

public interface EquipmentTypeServices extends Component {

	DtList<EquipmentType> getEquipmentTypes(DtListState dtListState);

	void save(EquipmentType equipmentType);

	EquipmentType get(Long equipmentTypeId);
}
