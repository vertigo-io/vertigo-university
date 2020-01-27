package io.vertigo.samples.quarto.domain;

import io.vertigo.datamodel.structure.model.DtList;
import io.vertigo.datamodel.structure.model.DtObject;
import io.vertigo.datamodel.structure.stereotype.Field;

public final class Theme implements DtObject {

	private static final long serialVersionUID = 1L;

	@Field(domain = "DoText", label = "name")
	private String name;
	@Field(domain = "DoDtCategoryDtc", label = "categories")
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
