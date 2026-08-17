package io.vertigo.samples.quarto.domain;

import io.vertigo.core.lang.Cardinality;
import io.vertigo.datamodel.data.model.DataList;
import io.vertigo.datamodel.data.model.DataObject;
import io.vertigo.datamodel.data.stereotype.Field;

public final class Theme implements DataObject {

	private static final long serialVersionUID = 1L;

	@Field(smartType = "STyText", label = "name")
	private String name;
	@Field(smartType = "STyDtCategory", label = "categories", cardinality = Cardinality.MANY)
	private DataList<Category> categories;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public DataList<Category> getCategories() {
		return categories;
	}

	public void setCategories(final DataList<Category> categories) {
		this.categories = categories;
	}

}
