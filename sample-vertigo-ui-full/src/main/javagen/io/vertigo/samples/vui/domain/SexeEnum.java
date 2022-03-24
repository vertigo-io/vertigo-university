package io.vertigo.samples.vui.domain;

import java.io.Serializable;

import io.vertigo.datamodel.structure.model.MasterDataEnum;
import io.vertigo.datamodel.structure.model.UID;

public enum SexeEnum implements MasterDataEnum<io.vertigo.samples.vui.domain.Sexe> {

	male("M"), //
	female("F")
	;

	private final Serializable entityId;

	private SexeEnum(final Serializable id) {
		entityId = id;
	}

	@Override
	public UID<io.vertigo.samples.vui.domain.Sexe> getEntityUID() {
		return UID.of(io.vertigo.samples.vui.domain.Sexe.class, entityId);
	}

}
