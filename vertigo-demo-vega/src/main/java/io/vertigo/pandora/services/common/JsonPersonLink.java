package io.vertigo.pandora.services.common;

import io.vertigo.dynamo.domain.model.DtObject;

public class JsonPersonLink implements DtObject {
	private static final long serialVersionUID = 223396411883456722L;
	private long code;
	private String name;
	private String role;
	private String photoURL;
	private Boolean leadActor;

	public long getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public String getRole() {
		return role;
	}

	public String getPhotoURL() {
		return photoURL;
	}

	public boolean isLeadActor() {
		return leadActor != null ? leadActor : false;
	}
}
