package io.mars.maintenance.domain;

import java.io.Serializable;

import io.vertigo.dynamo.domain.model.MasterDataEnum;
import io.vertigo.dynamo.domain.model.URI;

public enum WorkOrderStatusEnum implements MasterDataEnum<io.mars.maintenance.domain.WorkOrderStatus> {

	;

	private final Serializable entityId;

	private WorkOrderStatusEnum(final Serializable id) {
		entityId = id;
	}

	@Override
	public URI<io.mars.maintenance.domain.WorkOrderStatus> getEntityUri() {
		return URI.of(io.mars.maintenance.domain.WorkOrderStatus.class, entityId);
	}

}