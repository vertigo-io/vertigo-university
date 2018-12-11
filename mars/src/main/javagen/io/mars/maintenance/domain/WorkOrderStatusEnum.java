package io.mars.maintenance.domain;

import java.io.Serializable;

import io.vertigo.dynamo.domain.model.MasterDataEnum;
import io.vertigo.dynamo.domain.model.UID;

public enum WorkOrderStatusEnum implements MasterDataEnum<io.mars.maintenance.domain.WorkOrderStatus> {

	done("DONE"), //
	inProgress("INPROGRESS"), //
	failed("FAILED"), //
	pending("PENDING")
	;

	private final Serializable entityId;

	private WorkOrderStatusEnum(final Serializable id) {
		entityId = id;
	}

	@Override
	public UID<io.mars.maintenance.domain.WorkOrderStatus> getEntityUID() {
		return UID.of(io.mars.maintenance.domain.WorkOrderStatus.class, entityId);
	}

}
