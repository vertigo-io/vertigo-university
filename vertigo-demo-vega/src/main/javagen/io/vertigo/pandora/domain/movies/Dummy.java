package io.vertigo.pandora.domain.movies;

import io.vertigo.dynamo.domain.model.DtObject;
import io.vertigo.dynamo.domain.stereotype.Field;
import io.vertigo.dynamo.domain.util.DtObjectUtil;

/**
 * Attention cette classe est générée automatiquement !
 * Objet de données Dummy
 */
public final class Dummy implements DtObject {

	/** SerialVersionUID. */
	private static final long serialVersionUID = 1L;

	private Long value;

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'value'.
	 * @return Long value <b>Obligatoire</b>
	 */
	@Field(domain = "DO_IDENTITY", required = true, label = "value")
	public Long getValue() {
		return value;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'value'.
	 * @param value Long <b>Obligatoire</b>
	 */
	public void setValue(final Long value) {
		this.value = value;
	}

	//Aucune Association déclarée

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return DtObjectUtil.toString(this);
	}
}
