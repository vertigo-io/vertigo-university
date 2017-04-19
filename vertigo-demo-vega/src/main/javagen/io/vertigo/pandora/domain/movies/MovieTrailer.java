package io.vertigo.pandora.domain.movies;

import io.vertigo.dynamo.domain.model.Fragment;
import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.dynamo.domain.stereotype.Field;
import io.vertigo.dynamo.domain.util.DtObjectUtil;

/**
 * Attention cette classe est générée automatiquement !
 * Objet de données MovieTrailer
 */
@io.vertigo.dynamo.domain.stereotype.Fragment(fragmentOf = "DT_MOVIE")
public final class MovieTrailer implements Fragment<Movie> {

	/** SerialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String trailerName;
	private String trailerHref;
	private Long movId;

	/** {@inheritDoc} */
	@Override
	public URI<Movie> getEntityURI() {
		return DtObjectUtil.createEntityURI(this); 
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'TRAILER NAME'.
	 * @return String trailerName
	 */
	@Field(domain = "DO_LABEL", label = "TRAILER NAME")
	public String getTrailerName() {
		return trailerName;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'TRAILER NAME'.
	 * @param trailerName String
	 */
	public void setTrailerName(final String trailerName) {
		this.trailerName = trailerName;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'TRAILER HREF'.
	 * @return String trailerHref
	 */
	@Field(domain = "DO_HREF", label = "TRAILER HREF")
	public String getTrailerHref() {
		return trailerHref;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'TRAILER HREF'.
	 * @param trailerHref String
	 */
	public void setTrailerHref(final String trailerHref) {
		this.trailerHref = trailerHref;
	}

	/**
	 * Champ : FOREIGN_KEY.
	 * Récupère la valeur de la propriété 'MOV ID'.
	 * @return Long movId <b>Obligatoire</b>
	 */
	@Field(domain = "DO_IDENTITY", type = "FOREIGN_KEY", required = true, label = "MOV ID")
	public Long getMovId() {
		return movId;
	}

	/**
	 * Champ : FOREIGN_KEY.
	 * Définit la valeur de la propriété 'MOV ID'.
	 * @param movId Long <b>Obligatoire</b>
	 */
	public void setMovId(final Long movId) {
		this.movId = movId;
	}

	//Aucune Association déclarée

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return DtObjectUtil.toString(this);
	}
}
