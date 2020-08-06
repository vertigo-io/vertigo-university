package io.vertigo.samples.quarto.domain;

import io.vertigo.datamodel.structure.model.DtObject;
import io.vertigo.datamodel.structure.stereotype.Field;

public final class Status implements DtObject {

	private static final long serialVersionUID = 1L;

	@Field(smartType = "STyText", label = "name")
	private String name;
	@Field(smartType = "STyText", label = "color")
	private String color;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getColor() {
		return color;
	}

	public void setColor(final String color) {
		this.color = color;
	}

}
