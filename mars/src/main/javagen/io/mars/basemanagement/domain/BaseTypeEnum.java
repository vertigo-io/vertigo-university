package io.mars.basemanagement.domain;

import java.io.Serializable;

import io.vertigo.dynamo.domain.model.MasterDataEnum;
import io.vertigo.dynamo.domain.model.UID;

public enum BaseTypeEnum implements MasterDataEnum<io.mars.basemanagement.domain.BaseType> {

	hydro("HYDRO"), //
	mine("MINE"), //
	dwelling("DWELLING")
	;

	private final Serializable entityId;

	private BaseTypeEnum(final Serializable id) {
		entityId = id;
	}

	@Override
	public UID<io.mars.basemanagement.domain.BaseType> getEntityUID() {
		return UID.of(io.mars.basemanagement.domain.BaseType.class, entityId);
	}

}
