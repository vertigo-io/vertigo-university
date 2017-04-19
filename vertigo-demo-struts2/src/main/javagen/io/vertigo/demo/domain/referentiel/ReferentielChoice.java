package io.vertigo.demo.domain.referentiel;

import io.vertigo.dynamo.domain.model.Entity;
import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.dynamo.domain.stereotype.Field;
import io.vertigo.dynamo.domain.util.DtObjectUtil;

/**
 * Attention cette classe est générée automatiquement !
 * Objet de données ReferentielChoice
 */
public final class ReferentielChoice implements Entity {

	/** SerialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String referentielName;
	private String libelle;

	/** {@inheritDoc} */
	@Override
	public URI<ReferentielChoice> getURI() {
		return DtObjectUtil.createURI(this);
	}

	/**
	 * Champ : ID.
	 * Récupère la valeur de la propriété 'R�f�rentiel'.
	 * @return String referentielName <b>Obligatoire</b>
	 */
	@Field(domain = "DO_LIBELLE", type = "ID", required = true, label = "R�f�rentiel")
	public String getReferentielName() {
		return referentielName;
	}

	/**
	 * Champ : ID.
	 * Définit la valeur de la propriété 'R�f�rentiel'.
	 * @param referentielName String <b>Obligatoire</b>
	 */
	public void setReferentielName(final String referentielName) {
		this.referentielName = referentielName;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'R�f�rentiel'.
	 * @return String libelle <b>Obligatoire</b>
	 */
	@Field(domain = "DO_LIBELLE", required = true, label = "R�f�rentiel")
	public String getLibelle() {
		return libelle;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'R�f�rentiel'.
	 * @param libelle String <b>Obligatoire</b>
	 */
	public void setLibelle(final String libelle) {
		this.libelle = libelle;
	}

	//Aucune Association déclarée

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return DtObjectUtil.toString(this);
	}
}
