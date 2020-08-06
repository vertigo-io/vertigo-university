package io.vertigo.samples.quarto.domain;

import io.vertigo.core.lang.Cardinality;
import io.vertigo.datamodel.structure.model.DtList;
import io.vertigo.datamodel.structure.model.DtObject;
import io.vertigo.datamodel.structure.stereotype.Field;

public final class Card implements DtObject {

	private static final long serialVersionUID = 1L;

	@Field(smartType = "STyText", label = "name")
	private String name;
	@Field(smartType = "STyText", label = "description")
	private String description;
	@Field(smartType = "STyDtStatus", label = "status", cardinality = Cardinality.MANY)
	private DtList<Status> status;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public DtList<Status> getStatus() {
		return status;
	}

	public void setStatus(final DtList<Status> status) {
		this.status = status;
	}

}
