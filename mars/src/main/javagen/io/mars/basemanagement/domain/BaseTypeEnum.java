package io.mars.basemanagement.domain;

import java.io.Serializable;

import io.vertigo.dynamo.domain.model.MasterDataEnum;
import io.vertigo.dynamo.domain.model.URI;

public enum BaseTypeEnum implements MasterDataEnum<io.mars.basemanagement.domain.BaseType> {

	hydro("HYDRO"), //
	mine("MINE")
	;

	private final Serializable entityId;

	private BaseTypeEnum(final Serializable id) {
		entityId = id;
	}

	@Override
	public URI<io.mars.basemanagement.domain.BaseType> getEntityUri() {
		return URI.of(io.mars.basemanagement.domain.BaseType.class, entityId);
	}

}
