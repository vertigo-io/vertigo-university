package io.vertigo.samples.crystal.domain;

import java.io.Serializable;

import io.vertigo.dynamo.domain.model.MasterDataEnum;
import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.dynamo.domain.util.DtObjectUtil;

public enum SexeEnum implements MasterDataEnum<io.vertigo.samples.crystal.domain.Sexe> {

	male("M"), //
	female("F")
	;

	private final URI<io.vertigo.samples.crystal.domain.Sexe> entityUri;

	private SexeEnum(final Serializable id) {
		entityUri = DtObjectUtil.createURI(io.vertigo.samples.crystal.domain.Sexe.class, id);
	}

	@Override
	public URI<io.vertigo.samples.crystal.domain.Sexe> getEntityUri() {
		return entityUri;
	}

}
