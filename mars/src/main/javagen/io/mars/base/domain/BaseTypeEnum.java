package io.mars.base.domain;

import java.io.Serializable;

import io.vertigo.dynamo.domain.model.MasterDataEnum;
import io.vertigo.dynamo.domain.model.URI;

public enum BaseTypeEnum implements MasterDataEnum<io.mars.base.domain.BaseType> {

	hydro("HYDRO"), //
	mine("MINE")
	;

	private final Serializable entityId;

	private BaseTypeEnum(final Serializable id) {
		entityId = id;
	}

	@Override
	public URI<io.mars.base.domain.BaseType> getEntityUri() {
		return URI.of(io.mars.base.domain.BaseType.class, entityId);
	}

}
