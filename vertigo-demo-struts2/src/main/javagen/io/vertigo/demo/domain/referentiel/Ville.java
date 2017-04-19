package io.vertigo.demo.domain.referentiel;

import io.vertigo.dynamo.domain.model.Entity;
import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.dynamo.domain.stereotype.Field;
import io.vertigo.dynamo.domain.util.DtObjectUtil;

/**
 * Attention cette classe est générée automatiquement !
 * Objet de données Ville
 */
public final class Ville implements Entity {

	/** SerialVersionUID. */
	private static final long serialVersionUID = 1L;

	private Long vilId;
	private String libelle;
	private Long depId;
	private io.vertigo.demo.domain.referentiel.Departement departement;

	/** {@inheritDoc} */
	@Override
	public URI<Ville> getURI() {
		return DtObjectUtil.createURI(this);
	}

	/**
	 * Champ : ID.
	 * Récupère la valeur de la propriété 'VIL_ID'.
	 * @return Long vilId <b>Obligatoire</b>
	 */
	@Field(domain = "DO_IDENTIFIANT", type = "ID", required = true, label = "VIL_ID")
	public Long getVilId() {
		return vilId;
	}

	/**
	 * Champ : ID.
	 * Définit la valeur de la propriété 'VIL_ID'.
	 * @param vilId Long <b>Obligatoire</b>
	 */
	public void setVilId(final Long vilId) {
		this.vilId = vilId;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'LIBELLE'.
	 * @return String libelle <b>Obligatoire</b>
	 */
	@Field(domain = "DO_LIBELLE", required = true, label = "LIBELLE")
	public String getLibelle() {
		return libelle;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'LIBELLE'.
	 * @param libelle String <b>Obligatoire</b>
	 */
	public void setLibelle(final String libelle) {
		this.libelle = libelle;
	}

	/**
	 * Champ : FOREIGN_KEY.
	 * Récupère la valeur de la propriété 'Departement'.
	 * @return Long depId <b>Obligatoire</b>
	 */
	@Field(domain = "DO_IDENTIFIANT", type = "FOREIGN_KEY", required = true, label = "Departement")
	public Long getDepId() {
		return depId;
	}

	/**
	 * Champ : FOREIGN_KEY.
	 * Définit la valeur de la propriété 'Departement'.
	 * @param depId Long <b>Obligatoire</b>
	 */
	public void setDepId(final Long depId) {
		this.depId = depId;
	}

	/**
	 * Association : Departement.
	 * @return io.vertigo.demo.domain.referentiel.Departement
	 */
	public io.vertigo.demo.domain.referentiel.Departement getDepartement() {
		final io.vertigo.dynamo.domain.model.URI<io.vertigo.demo.domain.referentiel.Departement> fkURI = getDepartementURI();
		if (fkURI == null) {
			return null;
		}
		//On est toujours dans un mode lazy. On s'assure cependant que l'objet associé n'a pas changé
		if (departement == null || !fkURI.equals(departement.getURI())) {
			departement = io.vertigo.app.Home.getApp().getComponentSpace().resolve(io.vertigo.dynamo.store.StoreManager.class).getDataStore().readOne(fkURI);
		}
		return departement;
	}

	/**
	 * Retourne l'URI: Departement.
	 * @return URI de l'association
	 */
	@io.vertigo.dynamo.domain.stereotype.Association (
			name = "A_DEP_VIL",
			fkFieldName = "DEP_ID",
			primaryDtDefinitionName = "DT_DEPARTEMENT",
			primaryIsNavigable = true,
			primaryRole = "Departement",
			primaryLabel = "Departement",
			primaryMultiplicity = "1..1",
			foreignDtDefinitionName = "DT_VILLE",
			foreignIsNavigable = true,
			foreignRole = "Ville",
			foreignLabel = "Ville",
			foreignMultiplicity = "0..*")
	public io.vertigo.dynamo.domain.model.URI<io.vertigo.demo.domain.referentiel.Departement> getDepartementURI() {
		return io.vertigo.dynamo.domain.util.DtObjectUtil.createURI(this, "A_DEP_VIL", io.vertigo.demo.domain.referentiel.Departement.class);
	}

	// Association : CodePostal non navigable

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return DtObjectUtil.toString(this);
	}
}
