package io.vertigo.samples.quarto.domain;

import io.vertigo.core.lang.Cardinality;
import io.vertigo.datamodel.structure.model.DtList;
import io.vertigo.datamodel.structure.model.DtObject;
import io.vertigo.datamodel.structure.stereotype.Field;

public final class Theme implements DtObject {

	private static final long serialVersionUID = 1L;

	@Field(smartType = "STyText", label = "name")
	private String name;
	@Field(smartType = "STyDtCategory", label = "categories", cardinality = Cardinality.MANY)
	private DtList<Category> categories;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public DtList<Category> getCategories() {
		return categories;
	}

	public void setCategories(final DtList<Category> categories) {
		this.categories = categories;
	}

}
