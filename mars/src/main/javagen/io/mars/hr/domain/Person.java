package io.mars.hr.domain;

import io.vertigo.dynamo.domain.model.Entity;
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
public final class Person implements Entity {
	private static final long serialVersionUID = 1L;

	private Long personId;
	private String firstName;
	private String lastName;
	private String email;
	private Long picturefileId;
	private String picturefileIdTmp;
	private String tags;
	private java.time.LocalDate dateHired;

	@io.vertigo.dynamo.domain.stereotype.Association(
			name = "APersonGroups",
			fkFieldName = "groupId",
			primaryDtDefinitionName = "DtGroups",
			primaryIsNavigable = true,
			primaryRole = "Group",
			primaryLabel = "Group",
			primaryMultiplicity = "0..1",
			foreignDtDefinitionName = "DtPerson",
			foreignIsNavigable = false,
			foreignRole = "Person",
			foreignLabel = "Person",
			foreignMultiplicity = "0..*")
	private final VAccessor<io.mars.hr.domain.Groups> groupIdAccessor = new VAccessor<>(io.mars.hr.domain.Groups.class, "Group");

	/** {@inheritDoc} */
	@Override
	public UID<Person> getUID() {
		return UID.of(this);
	}
	
	/**
	 * Champ : ID.
	 * Récupère la valeur de la propriété 'Id'.
	 * @return Long personId <b>Obligatoire</b>
	 */
	@Field(domain = "DoId", type = "ID", required = true, label = "Id")
	public Long getPersonId() {
		return personId;
	}

	/**
	 * Champ : ID.
	 * Définit la valeur de la propriété 'Id'.
	 * @param personId Long <b>Obligatoire</b>
	 */
	public void setPersonId(final Long personId) {
		this.personId = personId;
	}
	
	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'First Name'.
	 * @return String firstName
	 */
	@Field(domain = "DoLabel", label = "First Name")
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'First Name'.
	 * @param firstName String
	 */
	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Last Name'.
	 * @return String lastName
	 */
	@Field(domain = "DoLabel", label = "Last Name")
	public String getLastName() {
		return lastName;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Last Name'.
	 * @param lastName String
	 */
	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'E-mail'.
	 * @return String email
	 */
	@Field(domain = "DoEmail", label = "E-mail")
	public String getEmail() {
		return email;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'E-mail'.
	 * @param email String
	 */
	public void setEmail(final String email) {
		this.email = email;
	}
	
	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Picture'.
	 * @return Long picturefileId
	 */
	@Field(domain = "DoId", label = "Picture")
	public Long getPicturefileId() {
		return picturefileId;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Picture'.
	 * @param picturefileId Long
	 */
	public void setPicturefileId(final Long picturefileId) {
		this.picturefileId = picturefileId;
	}
	
	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Picture'.
	 * @return String picturefileIdTmp
	 */
	@Field(domain = "DoLabel", persistent = false, label = "Picture")
	public String getPicturefileIdTmp() {
		return picturefileIdTmp;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Picture'.
	 * @param picturefileIdTmp String
	 */
	public void setPicturefileIdTmp(final String picturefileIdTmp) {
		this.picturefileIdTmp = picturefileIdTmp;
	}
	
	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Tags'.
	 * @return String tags
	 */
	@Field(domain = "DoMultipleIds", label = "Tags")
	public String getTags() {
		return tags;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Tags'.
	 * @param tags String
	 */
	public void setTags(final String tags) {
		this.tags = tags;
	}
	
	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Date hired'.
	 * @return LocalDate dateHired
	 */
	@Field(domain = "DoLocaldate", label = "Date hired")
	public java.time.LocalDate getDateHired() {
		return dateHired;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Date hired'.
	 * @param dateHired LocalDate
	 */
	public void setDateHired(final java.time.LocalDate dateHired) {
		this.dateHired = dateHired;
	}
	
	/**
	 * Champ : FOREIGN_KEY.
	 * Récupère la valeur de la propriété 'Group'.
	 * @return Long groupId
	 */
	@Field(domain = "DoId", type = "FOREIGN_KEY", label = "Group")
	public Long getGroupId() {
		return (Long) groupIdAccessor.getId();
	}

	/**
	 * Champ : FOREIGN_KEY.
	 * Définit la valeur de la propriété 'Group'.
	 * @param groupId Long
	 */
	public void setGroupId(final Long groupId) {
		groupIdAccessor.setId(groupId);
	}
	
	/**
	 * Champ : COMPUTED.
	 * Récupère la valeur de la propriété calculée 'Full name'.
	 * @return String fullName
	 */
	@Field(domain = "DoLabel", type = "COMPUTED", persistent = false, label = "Full name")
	public String getFullName() {
		return getFirstName() + " " + getLastName();
	}

 	/**
	 * Association : Group.
	 * @return l'accesseur vers la propriété 'Group'
	 */
	public VAccessor<io.mars.hr.domain.Groups> group() {
		return groupIdAccessor;
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return DtObjectUtil.toString(this);
	}
}
