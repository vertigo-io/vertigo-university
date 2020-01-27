package io.vertigo.samples.quarto.domain;

import io.vertigo.datamodel.structure.model.DtList;
import io.vertigo.datamodel.structure.model.DtObject;
import io.vertigo.datamodel.structure.stereotype.Field;

public final class Card implements DtObject {

	private static final long serialVersionUID = 1L;

	@Field(domain = "DoText", label = "name")
	private String name;
	@Field(domain = "DoText", label = "description")
	private String description;
	@Field(domain = "DoDtStatusDtc", label = "status")
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
