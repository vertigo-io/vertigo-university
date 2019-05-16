package io.mars.maintenance.domain;

import java.io.Serializable;

import io.vertigo.dynamo.domain.model.MasterDataEnum;
import io.vertigo.dynamo.domain.model.UID;

public enum TicketStatusEnum implements MasterDataEnum<io.mars.maintenance.domain.TicketStatus> {

	open("OPEN"), //
	assigned("ASSIGNED"), //
	closed("CLOSED")
	;

	private final Serializable entityId;

	private TicketStatusEnum(final Serializable id) {
		entityId = id;
	}

	@Override
	public UID<io.mars.maintenance.domain.TicketStatus> getEntityUID() {
		return UID.of(io.mars.maintenance.domain.TicketStatus.class, entityId);
	}

}
