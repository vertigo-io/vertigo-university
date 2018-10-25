package io.mars.base.domain;

import java.io.Serializable;

import io.vertigo.dynamo.domain.model.MasterDataEnum;
import io.vertigo.dynamo.domain.model.URI;

public enum JobStatusEnum implements MasterDataEnum<io.mars.base.domain.JobStatus> {

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
	public URI<io.mars.base.domain.JobStatus> getEntityUri() {
		return URI.of(io.mars.base.domain.JobStatus.class, entityId);
	}

}
