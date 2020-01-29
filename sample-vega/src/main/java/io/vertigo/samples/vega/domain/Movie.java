package io.vertigo.samples.vega.domain;

import io.vertigo.core.lang.Cardinality;
import io.vertigo.datamodel.structure.model.KeyConcept;
import io.vertigo.datamodel.structure.model.UID;
import io.vertigo.datamodel.structure.stereotype.Field;

public final class Movie implements KeyConcept {
	private static final long serialVersionUID = -5975848806293357234L;
	@Field(smartType = "STyIdentity", type = "ID", cardinality = Cardinality.ONE, label = "id")
	private int id;
	@Field(smartType = "STyText", label = "name")
	private String title;

	public Movie setId(final int id) {
		this.id = id;
		return this;
	}

	public int getId() {
		return id;
	}

	public Movie setTitle(final String title) {
		this.title = title;
		return this;
	}

	public String getTitle() {
		return title;
	}

	@Override
	public UID<Movie> getUID() {
		return UID.of(this);
	}
}
