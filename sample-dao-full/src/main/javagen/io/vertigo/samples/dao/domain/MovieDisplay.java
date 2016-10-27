package io.vertigo.samples.dao.domain;

import io.vertigo.dynamo.domain.stereotype.Field;
import io.vertigo.dynamo.domain.model.DtObject;
import io.vertigo.dynamo.domain.util.DtObjectUtil;
/**
 * Attention cette classe est générée automatiquement !
 * Objet de données MovieDisplay
 */
public final class MovieDisplay implements DtObject {

	/** SerialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String name;
	private Integer year;
	private String country;
	private Long actorsCount;

	
	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Titre'. 
	 * @return String name 
	 */
	@Field(domain = "DO_LABEL_LONG", label = "Titre")
	public String getName() {
		return name;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Titre'.
	 * @param name String 
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Année'. 
	 * @return Integer year 
	 */
	@Field(domain = "DO_YEAR", label = "Année")
	public Integer getYear() {
		return year;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Année'.
	 * @param year Integer 
	 */
	public void setYear(final Integer year) {
		this.year = year;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Pays'. 
	 * @return String country 
	 */
	@Field(domain = "DO_LABEL", label = "Pays")
	public String getCountry() {
		return country;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Pays'.
	 * @param country String 
	 */
	public void setCountry(final String country) {
		this.country = country;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Nombre d'acteur'. 
	 * @return Long actorsCount 
	 */
	@Field(domain = "DO_ID", label = "Nombre d'acteur")
	public Long getActorsCount() {
		return actorsCount;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Nombre d'acteur'.
	 * @param actorsCount Long 
	 */
	public void setActorsCount(final Long actorsCount) {
		this.actorsCount = actorsCount;
	}

	//Aucune Association déclarée

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return DtObjectUtil.toString(this);
	}
}
