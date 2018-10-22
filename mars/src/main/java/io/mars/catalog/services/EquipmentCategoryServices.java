package io.mars.catalog.services;

import io.mars.catalog.domain.EquipmentCategory;
import io.vertigo.core.component.Component;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.domain.model.DtListState;

public interface EquipmentCategoryServices extends Component {

	DtList<EquipmentCategory> getEquipmentCategorys(DtListState dtListState);

	void save(EquipmentCategory equipmentCategory);

	EquipmentCategory get(Long equipmentCategoryId);
}
