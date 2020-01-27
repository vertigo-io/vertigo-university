package io.vertigo.samples.quarto.domain;

import io.vertigo.datamodel.structure.model.DtList;
import io.vertigo.datamodel.structure.model.DtObject;
import io.vertigo.datamodel.structure.stereotype.Field;

public final class Category implements DtObject {

	private static final long serialVersionUID = 1L;

	@Field(domain = "DoText", label = "name")
	private String name;
	@Field(domain = "DoText", label = "description")
	private String description;
	@Field(domain = "DoDtCardDtc", label = "cards")
	private DtList<Card> cards;

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

	public DtList<Card> getCards() {
		return cards;
	}

	public void setCards(final DtList<Card> cards) {
		this.cards = cards;
	}

}
