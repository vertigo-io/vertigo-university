package io.vertigo.demo.domain.tutorial;

import io.vertigo.dynamo.domain.model.Entity;
import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.dynamo.domain.stereotype.Field;
import io.vertigo.dynamo.domain.util.DtObjectUtil;

/**
 * Attention cette classe est générée automatiquement !
 * Objet de données Commune
 */
@io.vertigo.dynamo.domain.stereotype.DataSpace("inseeCsv")
public final class Commune implements Entity {

	/** SerialVersionUID. */
	private static final long serialVersionUID = 1L;

	private Long idInsee;
	private String codePostal;
	private String commune;
	private String departement;

	/** {@inheritDoc} */
	@Override
	public URI<Commune> getURI() {
		return DtObjectUtil.createURI(this);
	}

	/**
	 * Champ : ID.
	 * Récupère la valeur de la propriété 'ID INSEE'.
	 * @return Long idInsee <b>Obligatoire</b>
	 */
	@Field(domain = "DO_IDENTIFIANT", type = "ID", required = true, label = "ID INSEE")
	public Long getIdInsee() {
		return idInsee;
	}

	/**
	 * Champ : ID.
	 * Définit la valeur de la propriété 'ID INSEE'.
	 * @param idInsee Long <b>Obligatoire</b>
	 */
	public void setIdInsee(final Long idInsee) {
		this.idInsee = idInsee;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Code postal'.
	 * @return String codePostal <b>Obligatoire</b>
	 */
	@Field(domain = "DO_CODE_POSTAL", required = true, label = "Code postal")
	public String getCodePostal() {
		return codePostal;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Code postal'.
	 * @param codePostal String <b>Obligatoire</b>
	 */
	public void setCodePostal(final String codePostal) {
		this.codePostal = codePostal;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Commune'.
	 * @return String commune <b>Obligatoire</b>
	 */
	@Field(domain = "DO_LIBELLE", required = true, label = "Commune")
	public String getCommune() {
		return commune;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Commune'.
	 * @param commune String <b>Obligatoire</b>
	 */
	public void setCommune(final String commune) {
		this.commune = commune;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'D�partement'.
	 * @return String departement <b>Obligatoire</b>
	 */
	@Field(domain = "DO_LIBELLE", required = true, label = "D�partement")
	public String getDepartement() {
		return departement;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'D�partement'.
	 * @param departement String <b>Obligatoire</b>
	 */
	public void setDepartement(final String departement) {
		this.departement = departement;
	}

	//Aucune Association déclarée

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return DtObjectUtil.toString(this);
	}
}
