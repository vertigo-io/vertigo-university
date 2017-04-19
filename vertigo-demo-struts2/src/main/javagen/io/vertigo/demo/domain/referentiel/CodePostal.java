package io.vertigo.demo.domain.referentiel;

import io.vertigo.dynamo.domain.model.Entity;
import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.dynamo.domain.stereotype.Field;
import io.vertigo.dynamo.domain.util.DtObjectUtil;

/**
 * Attention cette classe est générée automatiquement !
 * Objet de données CodePostal
 */
public final class CodePostal implements Entity {

	/** SerialVersionUID. */
	private static final long serialVersionUID = 1L;

	private Long cpoId;
	private String codePostal;
	private Long vilId;
	private io.vertigo.demo.domain.referentiel.Ville ville;

	/** {@inheritDoc} */
	@Override
	public URI<CodePostal> getURI() {
		return DtObjectUtil.createURI(this);
	}

	/**
	 * Champ : ID.
	 * Récupère la valeur de la propriété 'CPO_ID'.
	 * @return Long cpoId <b>Obligatoire</b>
	 */
	@Field(domain = "DO_IDENTIFIANT", type = "ID", required = true, label = "CPO_ID")
	public Long getCpoId() {
		return cpoId;
	}

	/**
	 * Champ : ID.
	 * Définit la valeur de la propriété 'CPO_ID'.
	 * @param cpoId Long <b>Obligatoire</b>
	 */
	public void setCpoId(final Long cpoId) {
		this.cpoId = cpoId;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'CODE POSTAL'.
	 * @return String codePostal <b>Obligatoire</b>
	 */
	@Field(domain = "DO_CODE_POSTAL", required = true, label = "CODE POSTAL")
	public String getCodePostal() {
		return codePostal;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'CODE POSTAL'.
	 * @param codePostal String <b>Obligatoire</b>
	 */
	public void setCodePostal(final String codePostal) {
		this.codePostal = codePostal;
	}

	/**
	 * Champ : FOREIGN_KEY.
	 * Récupère la valeur de la propriété 'Ville'.
	 * @return Long vilId <b>Obligatoire</b>
	 */
	@Field(domain = "DO_IDENTIFIANT", type = "FOREIGN_KEY", required = true, label = "Ville")
	public Long getVilId() {
		return vilId;
	}

	/**
	 * Champ : FOREIGN_KEY.
	 * Définit la valeur de la propriété 'Ville'.
	 * @param vilId Long <b>Obligatoire</b>
	 */
	public void setVilId(final Long vilId) {
		this.vilId = vilId;
	}


	// Association : Client non navigable
	/**
	 * Association : Ville.
	 * @return io.vertigo.demo.domain.referentiel.Ville
	 */
	public io.vertigo.demo.domain.referentiel.Ville getVille() {
		final io.vertigo.dynamo.domain.model.URI<io.vertigo.demo.domain.referentiel.Ville> fkURI = getVilleURI();
		if (fkURI == null) {
			return null;
		}
		//On est toujours dans un mode lazy. On s'assure cependant que l'objet associé n'a pas changé
		if (ville == null || !fkURI.equals(ville.getURI())) {
			ville = io.vertigo.app.Home.getApp().getComponentSpace().resolve(io.vertigo.dynamo.store.StoreManager.class).getDataStore().readOne(fkURI);
		}
		return ville;
	}

	/**
	 * Retourne l'URI: Ville.
	 * @return URI de l'association
	 */
	@io.vertigo.dynamo.domain.stereotype.Association (
			name = "A_CPO_VIL",
			fkFieldName = "VIL_ID",
			primaryDtDefinitionName = "DT_VILLE",
			primaryIsNavigable = true,
			primaryRole = "Ville",
			primaryLabel = "Ville",
			primaryMultiplicity = "1..1",
			foreignDtDefinitionName = "DT_CODE_POSTAL",
			foreignIsNavigable = false,
			foreignRole = "CodePostal",
			foreignLabel = "CodePostal",
			foreignMultiplicity = "0..*")
	public io.vertigo.dynamo.domain.model.URI<io.vertigo.demo.domain.referentiel.Ville> getVilleURI() {
		return io.vertigo.dynamo.domain.util.DtObjectUtil.createURI(this, "A_CPO_VIL", io.vertigo.demo.domain.referentiel.Ville.class);
	}

	// Association : Code postal non navigable

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return DtObjectUtil.toString(this);
	}
}
