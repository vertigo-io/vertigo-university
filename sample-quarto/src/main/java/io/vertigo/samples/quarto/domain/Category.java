package io.vertigo.samples.quarto.domain;

import io.vertigo.core.lang.Cardinality;
import io.vertigo.datamodel.data.model.DataList;
import io.vertigo.datamodel.data.model.DataObject;
import io.vertigo.datamodel.data.stereotype.Field;

public final class Category implements DataObject {

	private static final long serialVersionUID = 1L;

	@Field(smartType = "STyText", label = "name")
	private String name;
	@Field(smartType = "STyText", label = "description")
	private String description;
	@Field(smartType = "STyDtCard", label = "cards", cardinality = Cardinality.MANY)
	private DataList<Card> cards;

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

	public DataList<Card> getCards() {
		return cards;
	}

	public void setCards(final DataList<Card> cards) {
		this.cards = cards;
	}

}
