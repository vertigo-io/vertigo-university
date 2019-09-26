/**
 *
 */
package io.mars.support.boot;

import io.mars.basemanagement.domain.Base;
import io.mars.basemanagement.domain.BaseType;
import io.mars.basemanagement.domain.Business;
import io.mars.basemanagement.domain.Geosector;
import io.mars.basemanagement.domain.Tag;
import io.mars.catalog.domain.EquipmentType;
import io.mars.maintenance.domain.TicketStatus;
import io.mars.maintenance.domain.WorkOrderStatus;
import io.vertigo.dynamo.impl.store.datastore.AbstractMasterDataDefinitionProvider;

/**
 * Init masterdata list.
 * @author jmforhan
 */
public class MarsMasterDataDefinitionProvider extends AbstractMasterDataDefinitionProvider {

	@Override
	public void declareMasterDataLists() {
		registerDtMasterDatas(BaseType.class);
		registerDtMasterDatas(EquipmentType.class);
		registerDtMasterDatas(WorkOrderStatus.class);
		registerDtMasterDatas(TicketStatus.class);
		registerDtMasterDatas(Tag.class);
		registerDtMasterDatas(Geosector.class);
		registerDtMasterDatas(Business.class);
		registerDtMasterDatas(Base.class);

	}

}
