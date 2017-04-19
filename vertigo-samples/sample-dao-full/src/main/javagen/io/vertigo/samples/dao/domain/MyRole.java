package io.vertigo.samples.dao.domain;

import io.vertigo.dynamo.domain.model.Entity;
import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.dynamo.domain.stereotype.Field;
import io.vertigo.dynamo.domain.util.DtObjectUtil;

/**
 * Attention cette classe est générée automatiquement !
 * Objet de données MyRole
 */
@io.vertigo.dynamo.domain.stereotype.DataSpace("mine")
public final class MyRole implements Entity {

	/** SerialVersionUID. */
	private static final long serialVersionUID = 1L;

	private Long rolId;
	private String asCharacter;
	private Long movId;
	private Long actId;
	private io.vertigo.samples.dao.domain.MyActor actor;

	/** {@inheritDoc} */
	@Override
	public URI<MyRole> getURI() {
		return DtObjectUtil.createURI(this);
	}

	/**
	 * Champ : ID.
	 * Récupère la valeur de la propriété 'Id'.
	 * @return Long rolId <b>Obligatoire</b>
	 */
	@Field(domain = "DO_ID", type = "ID", required = true, label = "Id")
	public Long getRolId() {
		return rolId;
	}

	/**
	 * Champ : ID.
	 * Définit la valeur de la propriété 'Id'.
	 * @param rolId Long <b>Obligatoire</b>
	 */
	public void setRolId(final Long rolId) {
		this.rolId = rolId;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Dans le role de'.
	 * @return String asCharacter
	 */
	@Field(domain = "DO_LABEL_VERY_LONG", label = "Dans le role de")
	public String getAsCharacter() {
		return asCharacter;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Dans le role de'.
	 * @param asCharacter String
	 */
	public void setAsCharacter(final String asCharacter) {
		this.asCharacter = asCharacter;
	}

	/**
	 * Champ : FOREIGN_KEY.
	 * Récupère la valeur de la propriété 'Movie'.
	 * @return Long movId
	 */
	@Field(domain = "DO_ID", type = "FOREIGN_KEY", label = "Movie")
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

	/**
	 * Champ : FOREIGN_KEY.
	 * Récupère la valeur de la propriété 'Actor'.
	 * @return Long actId
	 */
	@Field(domain = "DO_ID", type = "FOREIGN_KEY", label = "Actor")
	public Long getActId() {
		return actId;
	}

	/**
	 * Champ : FOREIGN_KEY.
	 * Définit la valeur de la propriété 'Actor'.
	 * @param actId Long
	 */
	public void setActId(final Long actId) {
		this.actId = actId;
	}

	/**
	 * Association : Actor.
	 * @return io.vertigo.samples.dao.domain.MyActor
	 */
	public io.vertigo.samples.dao.domain.MyActor getActor() {
		final io.vertigo.dynamo.domain.model.URI<io.vertigo.samples.dao.domain.MyActor> fkURI = getActorURI();
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
			name = "A_MROL_MACT",
			fkFieldName = "ACT_ID",
			primaryDtDefinitionName = "DT_MY_ACTOR",
			primaryIsNavigable = true,
			primaryRole = "Actor",
			primaryLabel = "Actor",
			primaryMultiplicity = "0..1",
			foreignDtDefinitionName = "DT_MY_ROLE",
			foreignIsNavigable = false,
			foreignRole = "Role",
			foreignLabel = "Role",
			foreignMultiplicity = "0..*")
	public io.vertigo.dynamo.domain.model.URI<io.vertigo.samples.dao.domain.MyActor> getActorURI() {
		return io.vertigo.dynamo.domain.util.DtObjectUtil.createURI(this, "A_MROL_MACT", io.vertigo.samples.dao.domain.MyActor.class);
	}

	// Association : Movie non navigable

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return DtObjectUtil.toString(this);
	}
}
