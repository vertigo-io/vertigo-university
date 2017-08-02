package io.vertigo.demo.security;

import io.vertigo.app.Home;
import io.vertigo.persona.security.metamodel.Role;


/**
 * Warning. This class is generated automatically !
 *
 * Enum of the roles known by the vertigo application.
 */
public enum Roles {

	/**
	 * Consultation de pays.
	 */
	R_REF_PAY_CONS,
	/**
	 * Création de pays.
	 */
	R_REF_PAY_CREA,
	/**
	 * Modification de pays.
	 */
	R_REF_PAY_MODIF,
	/**
	 * Consultation de profil.
	 */
	R_REF_PRF_CONS,
	/**
	 * Création de profil.
	 */
	R_REF_PRF_CREA,
	/**
	 * Modification de profil.
	 */
	R_REF_PRF_MODIF,
	/**
	 * Consultation d'utilisateur.
	 */
	R_REF_UTI_CONS,
	/**
	 * Création d'utilisateur.
	 */
	R_REF_UTI_CREA,
	/**
	 * Modification d'utilisateur.
	 */
	R_REF_UTI_MODIF;


	/**
	 * Get the associated role for Vertigo.
	 *
	 * @param code
	 *            role code
	 * @return role
	 */
	public static Role getSecurityRole(final String code) {
		return Home.getApp().getDefinitionSpace().resolve(code, Role.class);
	}

	/**
	 * Get the associated role for Vertigo.
	 *
	 * @return role
	 */
	public Role getSecurityRole() {
		return getSecurityRole(name());
	}

}
