package io.vertigo.samples.dao.domain;

import io.vertigo.dynamo.domain.stereotype.Field;
import io.vertigo.dynamo.domain.model.Entity;
import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.dynamo.domain.util.DtObjectUtil;
/**
 * Attention cette classe est générée automatiquement !
 * Objet de données MyActor
 */
public final class MyActor implements Entity {

	/** SerialVersionUID. */
	private static final long serialVersionUID = 1L;

	private Long actId;
	private String name;
	private String sexe;

	/** {@inheritDoc} */
	@Override
	public URI<MyActor> getURI() {
		return DtObjectUtil.createURI(this);
	}
	
	/**
	 * Champ : ID.
	 * Récupère la valeur de la propriété 'Id'. 
	 * @return Long actId <b>Obligatoire</b>
	 */
	@Field(domain = "DO_ID", type = "ID", required = true, label = "Id")
	public Long getActId() {
		return actId;
	}

	/**
	 * Champ : ID.
	 * Définit la valeur de la propriété 'Id'.
	 * @param actId Long <b>Obligatoire</b>
	 */
	public void setActId(final Long actId) {
		this.actId = actId;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Nom'. 
	 * @return String name 
	 */
	@Field(domain = "DO_LABEL_LONG", label = "Nom")
	public String getName() {
		return name;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Nom'.
	 * @param name String 
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Sexe'. 
	 * @return String sexe 
	 */
	@Field(domain = "DO_CODE", label = "Sexe")
	public String getSexe() {
		return sexe;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Sexe'.
	 * @param sexe String 
	 */
	public void setSexe(final String sexe) {
		this.sexe = sexe;
	}


	// Association : Role non navigable

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return DtObjectUtil.toString(this);
	}
}
