package io.mars.opendata.domain;

import java.io.Serializable;

import io.vertigo.dynamo.domain.model.MasterDataEnum;
import io.vertigo.dynamo.domain.model.UID;

public enum OpendataSetStatusEnum implements MasterDataEnum<io.mars.opendata.domain.OpendataSetStatus> {

	enabled("ENABLED"), //
	disabled("DISABLED")
	;

	private final Serializable entityId;

	private OpendataSetStatusEnum(final Serializable id) {
		entityId = id;
	}

	@Override
	public UID<io.mars.opendata.domain.OpendataSetStatus> getEntityUID() {
		return UID.of(io.mars.opendata.domain.OpendataSetStatus.class, entityId);
	}

}
