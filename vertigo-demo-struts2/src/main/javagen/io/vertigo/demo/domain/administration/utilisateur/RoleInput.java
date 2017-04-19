package io.vertigo.demo.domain.administration.utilisateur;

import io.vertigo.dynamo.domain.model.DtObject;
import io.vertigo.dynamo.domain.stereotype.Field;
import io.vertigo.dynamo.domain.util.DtObjectUtil;

/**
 * Attention cette classe est générée automatiquement !
 * Objet de données RoleInput
 */
public final class RoleInput implements DtObject {

	/** SerialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String rolCode;

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Role'.
	 * @return String rolCode <b>Obligatoire</b>
	 */
	@Field(domain = "DO_CODE", required = true, label = "Role")
	public String getRolCode() {
		return rolCode;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Role'.
	 * @param rolCode String <b>Obligatoire</b>
	 */
	public void setRolCode(final String rolCode) {
		this.rolCode = rolCode;
	}

	//Aucune Association déclarée

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return DtObjectUtil.toString(this);
	}
}
