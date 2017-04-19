package io.vertigo.demo.domain.referentiel;

import io.vertigo.dynamo.domain.model.DtMasterData;
import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.dynamo.domain.stereotype.Field;
import io.vertigo.dynamo.domain.util.DtObjectUtil;

/**
 * Attention cette classe est générée automatiquement !
 * Objet de données OuiNonChoice
 */
@io.vertigo.dynamo.domain.stereotype.DataSpace("ouiNon")
public final class OuiNonChoice implements DtMasterData {

	/** SerialVersionUID. */
	private static final long serialVersionUID = 1L;

	private Boolean key;
	private String libelle;

	/** {@inheritDoc} */
	@Override
	public URI<OuiNonChoice> getURI() {
		return DtObjectUtil.createURI(this);
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Valeur'.
	 * @return Boolean key
	 */
	@Field(domain = "DO_OUI_NON", type = "ID", required = true, label = "Valeur")
	public Boolean getKey() {
		return key;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Valeur'.
	 * @param key Boolean
	 */
	public void setKey(final Boolean key) {
		this.key = key;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Libell�'.
	 * @return String libelle <b>Obligatoire</b>
	 */
	@Field(domain = "DO_LIBELLE", required = true, label = "Libellé")
	public String getLibelle() {
		return libelle;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Libellé'.
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
