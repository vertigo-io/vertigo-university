package io.vertigo.samples.dao.domain;

import io.vertigo.dynamo.domain.model.DtObject;
import io.vertigo.dynamo.domain.stereotype.Field;
import io.vertigo.dynamo.domain.util.DtObjectUtil;

/**
 * Attention cette classe est générée automatiquement !
 * Objet de données MovieByYear
 */
public final class MovieByYear implements DtObject {

	/** SerialVersionUID. */
	private static final long serialVersionUID = 1L;

	private Integer year;
	private Long moviesCount;

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
	 * Récupère la valeur de la propriété 'Nombre de film'.
	 * @return Long moviesCount
	 */
	@Field(domain = "DO_ID", label = "Nombre de film")
	public Long getMoviesCount() {
		return moviesCount;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Nombre de film'.
	 * @param moviesCount Long
	 */
	public void setMoviesCount(final Long moviesCount) {
		this.moviesCount = moviesCount;
	}

	//Aucune Association déclarée

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return DtObjectUtil.toString(this);
	}
}
