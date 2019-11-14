package io.vertigo.samples.crystal.domain;

import io.vertigo.core.lang.Generated;
import io.vertigo.dynamo.domain.model.Entity;
import io.vertigo.dynamo.domain.model.UID;
import io.vertigo.dynamo.domain.stereotype.Field;
import io.vertigo.dynamo.domain.util.DtObjectUtil;

/**
 * This class is automatically generated.
 * DO NOT EDIT THIS FILE DIRECTLY.
 */
@Generated
public final class Sexe implements Entity {
	private static final long serialVersionUID = 1L;

	private String sexCd;
	private String label;

	/** {@inheritDoc} */
	@Override
	public UID<Sexe> getUID() {
		return UID.of(this);
	}
	
	/**
	 * Champ : ID.
	 * Récupère la valeur de la propriété 'Id'.
	 * @return String sexCd <b>Obligatoire</b>
	 */
	@Field(domain = "DoCode", type = "ID", required = true, label = "Id")
	public String getSexCd() {
		return sexCd;
	}

	/**
	 * Champ : ID.
	 * Définit la valeur de la propriété 'Id'.
	 * @param sexCd String <b>Obligatoire</b>
	 */
	public void setSexCd(final String sexCd) {
		this.sexCd = sexCd;
	}
	
	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Label'.
	 * @return String label <b>Obligatoire</b>
	 */
	@Field(domain = "DoLabelLong", required = true, label = "Label")
	public String getLabel() {
		return label;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Label'.
	 * @param label String <b>Obligatoire</b>
	 */
	public void setLabel(final String label) {
		this.label = label;
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return DtObjectUtil.toString(this);
	}
}
