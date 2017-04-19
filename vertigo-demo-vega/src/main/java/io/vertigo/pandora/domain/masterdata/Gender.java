package io.vertigo.pandora.domain.masterdata;

import io.vertigo.dynamo.domain.model.DtObject;
import io.vertigo.dynamo.domain.stereotype.Field;

public class Gender implements DtObject {

	private static final long serialVersionUID = 7787861867305619213L;

	@Field(domain = "DO_CODE", label = "Code")
	private String code;
	@Field(domain = "DO_LABEL", label = "Label")
	private String label;
	@Field(domain = "DO_ACTIVE", label = "Active")
	private boolean isActive;

	public String getCode() {
		return code;
	}

	public void setCode(final String code) {
		this.code = code;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(final String label) {
		this.label = label;
	}

	public boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(final boolean isActive) {
		this.isActive = isActive;
	}

}
