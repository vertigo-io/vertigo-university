package io.mars.basemanagement.domain;

import java.io.Serializable;

import io.vertigo.dynamo.domain.model.MasterDataEnum;
import io.vertigo.dynamo.domain.model.UID;

public enum JobStatusEnum implements MasterDataEnum<io.mars.basemanagement.domain.JobStatus> {

	done("DONE"), //
	inProgress("INPROGRESS"), //
	failed("FAILED"), //
	pending("PENDING")
	;

	private final Serializable entityId;

	private JobStatusEnum(final Serializable id) {
		entityId = id;
	}

	@Override
	public UID<io.mars.basemanagement.domain.JobStatus> getEntityUID() {
		return UID.of(io.mars.basemanagement.domain.JobStatus.class, entityId);
	}

}
