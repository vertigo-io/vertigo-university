package io.vertigo.samples.crystal.domain;

import java.io.Serializable;

import io.vertigo.dynamo.domain.model.MasterDataEnum;
import io.vertigo.dynamo.domain.model.UID;

public enum SexeEnum implements MasterDataEnum<io.vertigo.samples.crystal.domain.Sexe> {

	male("M"), //
	female("F")
	;

	private final Serializable entityId;

	private SexeEnum(final Serializable id) {
		entityId = id;
	}

	@Override
	public UID<io.vertigo.samples.crystal.domain.Sexe> getEntityUri() {
		return UID.of(io.vertigo.samples.crystal.domain.Sexe.class, entityId);
	}

}
