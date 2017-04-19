package io.vertigo.pandora.domain.movies;

import io.vertigo.dynamo.domain.model.Fragment;
import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.dynamo.domain.stereotype.Field;
import io.vertigo.dynamo.domain.util.DtObjectUtil;

/**
 * Attention cette classe est générée automatiquement !
 * Objet de données MovieSynopsis
 */
@io.vertigo.dynamo.domain.stereotype.Fragment(fragmentOf = "DT_MOVIE")
public final class MovieSynopsis implements Fragment<Movie> {

	/** SerialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String synopsis;
	private String shortSynopsis;
	private Long movId;

	/** {@inheritDoc} */
	@Override
	public URI<Movie> getEntityURI() {
		return DtObjectUtil.createEntityURI(this); 
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'SYNOPSIS'.
	 * @return String synopsis
	 */
	@Field(domain = "DO_TEXT", label = "SYNOPSIS")
	public String getSynopsis() {
		return synopsis;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'SYNOPSIS'.
	 * @param synopsis String
	 */
	public void setSynopsis(final String synopsis) {
		this.synopsis = synopsis;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'SHORT SYNOPSIS'.
	 * @return String shortSynopsis
	 */
	@Field(domain = "DO_TEXT", label = "SHORT SYNOPSIS")
	public String getShortSynopsis() {
		return shortSynopsis;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'SHORT SYNOPSIS'.
	 * @param shortSynopsis String
	 */
	public void setShortSynopsis(final String shortSynopsis) {
		this.shortSynopsis = shortSynopsis;
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
