package io.vertigo.demo.domain.administration.utilisateur;

import io.vertigo.dynamo.domain.model.DtObject;
import io.vertigo.dynamo.domain.stereotype.Field;
import io.vertigo.dynamo.domain.util.DtObjectUtil;

/**
 * Attention cette classe est générée automatiquement !
 * Objet de données UtilisateurLogin
 */
public final class UtilisateurLogin implements DtObject {

	/** SerialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String login;
	private String password;
	private String newPassword;
	private String newPasswordCheck;

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Identifiant utilisateur'.
	 * @return String login <b>Obligatoire</b>
	 */
	@Field(domain = "DO_LOGIN", required = true, label = "Identifiant utilisateur")
	public String getLogin() {
		return login;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Identifiant utilisateur'.
	 * @param login String <b>Obligatoire</b>
	 */
	public void setLogin(final String login) {
		this.login = login;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Mot de passe'.
	 * @return String password <b>Obligatoire</b>
	 */
	@Field(domain = "DO_PASSWORD", required = true, label = "Mot de passe")
	public String getPassword() {
		return password;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Mot de passe'.
	 * @param password String <b>Obligatoire</b>
	 */
	public void setPassword(final String password) {
		this.password = password;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Nouveau mot de passe'.
	 * @return String newPassword <b>Obligatoire</b>
	 */
	@Field(domain = "DO_PASSWORD", required = true, label = "Nouveau mot de passe")
	public String getNewPassword() {
		return newPassword;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Nouveau mot de passe'.
	 * @param newPassword String <b>Obligatoire</b>
	 */
	public void setNewPassword(final String newPassword) {
		this.newPassword = newPassword;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Resaisir le mot de passe'.
	 * @return String newPasswordCheck <b>Obligatoire</b>
	 */
	@Field(domain = "DO_PASSWORD", required = true, label = "Resaisir le mot de passe")
	public String getNewPasswordCheck() {
		return newPasswordCheck;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Resaisir le mot de passe'.
	 * @param newPasswordCheck String <b>Obligatoire</b>
	 */
	public void setNewPasswordCheck(final String newPasswordCheck) {
		this.newPasswordCheck = newPasswordCheck;
	}

	//Aucune Association déclarée

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return DtObjectUtil.toString(this);
	}
}
