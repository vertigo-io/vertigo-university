package io.vertigo.pandora.domain.persons;

import io.vertigo.dynamo.domain.model.Entity;
import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.dynamo.domain.stereotype.Field;
import io.vertigo.dynamo.domain.util.DtObjectUtil;

/**
 * Attention cette classe est générée automatiquement !
 * Objet de données ActorRole
 */
public final class ActorRole implements Entity {

	/** SerialVersionUID. */
	private static final long serialVersionUID = 1L;

	private Long aroId;
	private String role;
	private Long perId;
	private Long movId;
	private io.vertigo.pandora.domain.persons.Person actor;

	/** {@inheritDoc} */
	@Override
	public URI<ActorRole> getURI() {
		return DtObjectUtil.createURI(this);
	}

	/**
	 * Champ : ID.
	 * Récupère la valeur de la propriété 'ARO ID'.
	 * @return Long aroId <b>Obligatoire</b>
	 */
	@Field(domain = "DO_IDENTITY", type = "ID", required = true, label = "ARO ID")
	public Long getAroId() {
		return aroId;
	}

	/**
	 * Champ : ID.
	 * Définit la valeur de la propriété 'ARO ID'.
	 * @param aroId Long <b>Obligatoire</b>
	 */
	public void setAroId(final Long aroId) {
		this.aroId = aroId;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'ROLE'.
	 * @return String role
	 */
	@Field(domain = "DO_LABEL", label = "ROLE")
	public String getRole() {
		return role;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'ROLE'.
	 * @param role String
	 */
	public void setRole(final String role) {
		this.role = role;
	}

	/**
	 * Champ : FOREIGN_KEY.
	 * Récupère la valeur de la propriété 'Actor'.
	 * @return Long perId
	 */
	@Field(domain = "DO_IDENTITY", type = "FOREIGN_KEY", label = "Actor")
	public Long getPerId() {
		return perId;
	}

	/**
	 * Champ : FOREIGN_KEY.
	 * Définit la valeur de la propriété 'Actor'.
	 * @param perId Long
	 */
	public void setPerId(final Long perId) {
		this.perId = perId;
	}

	/**
	 * Champ : FOREIGN_KEY.
	 * Récupère la valeur de la propriété 'Movie'.
	 * @return Long movId
	 */
	@Field(domain = "DO_IDENTITY", type = "FOREIGN_KEY", label = "Movie")
	public Long getMovId() {
		return movId;
	}

	/**
	 * Champ : FOREIGN_KEY.
	 * Définit la valeur de la propriété 'Movie'.
	 * @param movId Long
	 */
	public void setMovId(final Long movId) {
		this.movId = movId;
	}

	// Association : Movie non navigable
	/**
	 * Association : Actor.
	 * @return io.vertigo.pandora.domain.persons.Person
	 */
	public io.vertigo.pandora.domain.persons.Person getActor() {
		final io.vertigo.dynamo.domain.model.URI<io.vertigo.pandora.domain.persons.Person> fkURI = getActorURI();
		if (fkURI == null) {
			return null;
		}
		//On est toujours dans un mode lazy. On s'assure cependant que l'objet associé n'a pas changé
		if (actor == null || !fkURI.equals(actor.getURI())) {
			actor = io.vertigo.app.Home.getApp().getComponentSpace().resolve(io.vertigo.dynamo.store.StoreManager.class).getDataStore().readOne(fkURI);
		}
		return actor;
	}

	/**
	 * Retourne l'URI: Actor.
	 * @return URI de l'association
	 */
	@io.vertigo.dynamo.domain.stereotype.Association(
			name = "A_MRO_PER",
			fkFieldName = "PER_ID",
			primaryDtDefinitionName = "DT_PERSON",
			primaryIsNavigable = true,
			primaryRole = "Actor",
			primaryLabel = "Actor",
			primaryMultiplicity = "0..1",
			foreignDtDefinitionName = "DT_ACTOR_ROLE",
			foreignIsNavigable = true,
			foreignRole = "Roles",
			foreignLabel = "Roles",
			foreignMultiplicity = "0..*")
	public io.vertigo.dynamo.domain.model.URI<io.vertigo.pandora.domain.persons.Person> getActorURI() {
		return io.vertigo.dynamo.domain.util.DtObjectUtil.createURI(this, "A_MRO_PER", io.vertigo.pandora.domain.persons.Person.class);
	}


	/** {@inheritDoc} */
	@Override
	public String toString() {
		return DtObjectUtil.toString(this);
	}
}
