package io.vertigo.samples.account.domain;

import io.vertigo.dynamo.domain.model.KeyConcept;
import io.vertigo.dynamo.domain.model.ListVAccessor;
import io.vertigo.dynamo.domain.model.UID;
import io.vertigo.dynamo.domain.model.VAccessor;
import io.vertigo.dynamo.domain.stereotype.Field;
import io.vertigo.dynamo.domain.util.DtObjectUtil;
import io.vertigo.lang.Generated;

/**
 * This class is automatically generated.
 * DO NOT EDIT THIS FILE DIRECTLY.
 */
@Generated
public final class Movie implements KeyConcept {
	private static final long serialVersionUID = 1L;

	private Long movId;
	private String name;
	private Integer year;
	private String imdbid;

	@io.vertigo.dynamo.domain.stereotype.Association(
			name = "A_MOV_COU",
			fkFieldName = "COU_ID",
			primaryDtDefinitionName = "DT_COUNTRY",
			primaryIsNavigable = true,
			primaryRole = "Country",
			primaryLabel = "Country",
			primaryMultiplicity = "0..1",
			foreignDtDefinitionName = "DT_MOVIE",
			foreignIsNavigable = false,
			foreignRole = "Movie",
			foreignLabel = "Movie",
			foreignMultiplicity = "0..*")
	private final VAccessor<io.vertigo.samples.account.domain.Country> couIdAccessor = new VAccessor<>(io.vertigo.samples.account.domain.Country.class, "Country");

	@io.vertigo.dynamo.domain.stereotype.Association(
			name = "A_ROL_MOV",
			fkFieldName = "MOV_ID",
			primaryDtDefinitionName = "DT_MOVIE",
			primaryIsNavigable = false,
			primaryRole = "Movie",
			primaryLabel = "Movie",
			primaryMultiplicity = "0..1",
			foreignDtDefinitionName = "DT_ROLE",
			foreignIsNavigable = true,
			foreignRole = "Role",
			foreignLabel = "Role",
			foreignMultiplicity = "0..*")
	private final ListVAccessor<io.vertigo.samples.account.domain.Role> roleAccessor = new ListVAccessor<>(this, "A_ROL_MOV", "Role");

	/** {@inheritDoc} */
	@Override
	public UID<Movie> getUID() {
		return UID.of(this);
	}

	/**
	 * Champ : ID.
	 * Récupère la valeur de la propriété 'Id'.
	 * @return Long movId <b>Obligatoire</b>
	 */
	@Field(domain = "DO_ID", type = "ID", required = true, label = "Id")
	public Long getMovId() {
		return movId;
	}

	/**
	 * Champ : ID.
	 * Définit la valeur de la propriété 'Id'.
	 * @param movId Long <b>Obligatoire</b>
	 */
	public void setMovId(final Long movId) {
		this.movId = movId;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Titre du film'.
	 * @return String name <b>Obligatoire</b>
	 */
	@Field(domain = "DO_LABEL_LONG", required = true, label = "Titre du film")
	public String getName() {
		return name;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Titre du film'.
	 * @param name String <b>Obligatoire</b>
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'AnnÃ©e'.
	 * @return Integer year
	 */
	@Field(domain = "DO_YEAR", label = "AnnÃ©e")
	public Integer getYear() {
		return year;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'AnnÃ©e'.
	 * @param year Integer
	 */
	public void setYear(final Integer year) {
		this.year = year;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Id Imdb'.
	 * @return String imdbid
	 */
	@Field(domain = "DO_LABEL", label = "Id Imdb")
	public String getImdbid() {
		return imdbid;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Id Imdb'.
	 * @param imdbid String
	 */
	public void setImdbid(final String imdbid) {
		this.imdbid = imdbid;
	}

	/**
	 * Champ : FOREIGN_KEY.
	 * Récupère la valeur de la propriété 'Country'.
	 * @return Long couId
	 */
	@Field(domain = "DO_ID", type = "FOREIGN_KEY", label = "Country")
	public Long getCouId() {
		return (Long) couIdAccessor.getId();
	}

	/**
	 * Champ : FOREIGN_KEY.
	 * Définit la valeur de la propriété 'Country'.
	 * @param couId Long
	 */
	public void setCouId(final Long couId) {
		couIdAccessor.setId(couId);
	}

	/**
	 * Association : Country.
	 * @return l'accesseur vers la propriété 'Country'
	 */
	public VAccessor<io.vertigo.samples.account.domain.Country> country() {
		return couIdAccessor;
	}

	@Deprecated
	public io.vertigo.samples.account.domain.Country getCountry() {
		// we keep the lazyness
		if (!couIdAccessor.isLoaded()) {
			couIdAccessor.load();
		}
		return couIdAccessor.get();
	}

	/**
	 * Retourne l'URI: Country.
	 * @return URI de l'association
	 */
	@Deprecated
	public io.vertigo.dynamo.domain.model.UID<io.vertigo.samples.account.domain.Country> getCountryURI() {
		return couIdAccessor.getURI();
	}

	/**
	 * Association : Role.
	 * @return l'accesseur vers la propriété 'Role'
	 */
	public ListVAccessor<io.vertigo.samples.account.domain.Role> role() {
		return roleAccessor;
	}

	/**
	 * Association : Role.
	 * @return DtList de io.vertigo.samples.account.domain.Role
	 */
	@Deprecated
	public io.vertigo.dynamo.domain.model.DtList<io.vertigo.samples.account.domain.Role> getRoleList() {
		// we keep the lazyness
		if (!roleAccessor.isLoaded()) {
			roleAccessor.load();
		}
		return roleAccessor.get();
	}

	/**
	 * Association URI: Role.
	 * @return URI de l'association
	 */
	@Deprecated
	public io.vertigo.dynamo.domain.metamodel.association.DtListURIForSimpleAssociation getRoleDtListURI() {
		return (io.vertigo.dynamo.domain.metamodel.association.DtListURIForSimpleAssociation) roleAccessor.getDtListURI();
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return DtObjectUtil.toString(this);
	}
}
