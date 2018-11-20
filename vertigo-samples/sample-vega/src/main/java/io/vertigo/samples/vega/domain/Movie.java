package io.vertigo.samples.vega.domain;

import io.vertigo.dynamo.domain.model.KeyConcept;
import io.vertigo.dynamo.domain.model.UID;
import io.vertigo.dynamo.domain.stereotype.Field;

public final class Movie implements KeyConcept {
	private static final long serialVersionUID = -5975848806293357234L;
	@Field(domain = "DO_IDENTITY", type = "ID", required = true, label = "id")
	private int id;
	@Field(domain = "DO_TEXT", label = "name")
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
