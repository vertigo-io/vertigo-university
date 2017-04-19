package io.vertigo.demo.domain.commande;

import io.vertigo.dynamo.domain.model.Entity;
import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.dynamo.domain.stereotype.Field;
import io.vertigo.dynamo.domain.util.DtObjectUtil;

/**
 * Attention cette classe est générée automatiquement !
 * Objet de données Commande
 */
public final class Commande implements Entity {

	/** SerialVersionUID. */
	private static final long serialVersionUID = 1L;

	private Long comId;
	private String code;
	private java.util.Date dateCreation;
	private java.util.Date dateValidation;
	private java.math.BigDecimal montantTotal;
	private Long cliId;
	private io.vertigo.dynamo.domain.model.DtList<io.vertigo.demo.domain.commande.LigneCommande> ligneCommande;

	/** {@inheritDoc} */
	@Override
	public URI<Commande> getURI() {
		return DtObjectUtil.createURI(this);
	}

	/**
	 * Champ : ID.
	 * Récupère la valeur de la propriété 'COM ID'.
	 * @return Long comId <b>Obligatoire</b>
	 */
	@Field(domain = "DO_IDENTIFIANT", type = "ID", required = true, label = "COM ID")
	public Long getComId() {
		return comId;
	}

	/**
	 * Champ : ID.
	 * Définit la valeur de la propriété 'COM ID'.
	 * @param comId Long <b>Obligatoire</b>
	 */
	public void setComId(final Long comId) {
		this.comId = comId;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'CODE'.
	 * @return String code <b>Obligatoire</b>
	 */
	@Field(domain = "DO_CODE", required = true, label = "CODE")
	public String getCode() {
		return code;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'CODE'.
	 * @param code String <b>Obligatoire</b>
	 */
	public void setCode(final String code) {
		this.code = code;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'DATE CREATION'.
	 * @return java.util.Date dateCreation <b>Obligatoire</b>
	 */
	@Field(domain = "DO_DATE", required = true, label = "DATE CREATION")
	public java.util.Date getDateCreation() {
		return dateCreation;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'DATE CREATION'.
	 * @param dateCreation java.util.Date <b>Obligatoire</b>
	 */
	public void setDateCreation(final java.util.Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'DATE VALIDATION'.
	 * @return java.util.Date dateValidation
	 */
	@Field(domain = "DO_DATE", label = "DATE VALIDATION")
	public java.util.Date getDateValidation() {
		return dateValidation;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'DATE VALIDATION'.
	 * @param dateValidation java.util.Date
	 */
	public void setDateValidation(final java.util.Date dateValidation) {
		this.dateValidation = dateValidation;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'MONTANT TOTAL'.
	 * @return java.math.BigDecimal montantTotal
	 */
	@Field(domain = "DO_MONTANT", label = "MONTANT TOTAL")
	public java.math.BigDecimal getMontantTotal() {
		return montantTotal;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'MONTANT TOTAL'.
	 * @param montantTotal java.math.BigDecimal
	 */
	public void setMontantTotal(final java.math.BigDecimal montantTotal) {
		this.montantTotal = montantTotal;
	}

	/**
	 * Champ : FOREIGN_KEY.
	 * Récupère la valeur de la propriété 'Client'.
	 * @return Long cliId <b>Obligatoire</b>
	 */
	@Field(domain = "DO_IDENTIFIANT", type = "FOREIGN_KEY", required = true, label = "Client")
	public Long getCliId() {
		return cliId;
	}

	/**
	 * Champ : FOREIGN_KEY.
	 * Définit la valeur de la propriété 'Client'.
	 * @param cliId Long <b>Obligatoire</b>
	 */
	public void setCliId(final Long cliId) {
		this.cliId = cliId;
	}

	/**
	 * Association : LigneCommande.
	 * @return io.vertigo.dynamo.domain.model.DtList<io.vertigo.demo.domain.commande.LigneCommande>
	 */
	public io.vertigo.dynamo.domain.model.DtList<io.vertigo.demo.domain.commande.LigneCommande> getLigneCommandeList() {
//		return this.<io.vertigo.demo.domain.commande.LigneCommande> getList(getLigneCommandeListURI());
		// On doit avoir une clé primaire renseignée. Si ce n'est pas le cas, on renvoie une liste vide
		if (io.vertigo.dynamo.domain.util.DtObjectUtil.getId(this) == null) {
			return new io.vertigo.dynamo.domain.model.DtList<>(io.vertigo.demo.domain.commande.LigneCommande.class);
		}
		final io.vertigo.dynamo.domain.model.DtListURI fkDtListURI = getLigneCommandeDtListURI();
		io.vertigo.lang.Assertion.checkNotNull(fkDtListURI);
		//---------------------------------------------------------------------
		//On est toujours dans un mode lazy.
		if (ligneCommande == null) {
			ligneCommande = io.vertigo.app.Home.getApp().getComponentSpace().resolve(io.vertigo.dynamo.store.StoreManager.class).getDataStore().findAll(fkDtListURI);
		}
		return ligneCommande;
	}

	/**
	 * Association URI: LigneCommande.
	 * @return URI de l'association
	 */
	@io.vertigo.dynamo.domain.stereotype.Association (
			name = "A_LCO_CMD",
			fkFieldName = "COM_ID",
			primaryDtDefinitionName = "DT_COMMANDE",
			primaryIsNavigable = true,
			primaryRole = "Commande",
			primaryLabel = "Commande",
			primaryMultiplicity = "1..1",
			foreignDtDefinitionName = "DT_LIGNE_COMMANDE",
			foreignIsNavigable = true,
			foreignRole = "LigneCommande",
			foreignLabel = "LigneCommande",
			foreignMultiplicity = "0..*")
	public io.vertigo.dynamo.domain.metamodel.association.DtListURIForSimpleAssociation getLigneCommandeDtListURI() {
		return io.vertigo.dynamo.domain.util.DtObjectUtil.createDtListURIForSimpleAssociation(this, "A_LCO_CMD", "LigneCommande");
	}

	// Association : Client non navigable

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return DtObjectUtil.toString(this);
	}
}
