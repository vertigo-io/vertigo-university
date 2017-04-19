package io.vertigo.demo.ui.controller.accueil;

import javax.inject.Inject;

import io.vertigo.demo.domain.administration.utilisateur.Utilisateur;
import io.vertigo.demo.domain.administration.utilisateur.UtilisateurLogin;
import io.vertigo.demo.services.administration.utilisateur.UtilisateurServices;
import io.vertigo.demo.ui.controller.AbstractDemoActionSupport;
import io.vertigo.persona.security.VSecurityManager;
import io.vertigo.struts2.core.ContextForm;
import io.vertigo.struts2.core.UiRequestUtil;

/**
 * @author npiedeloup
 * @version $Id: LoginAction.java,v 1.6 2014/08/04 16:57:50 npiedeloup Exp $
 */
public final class LoginAction extends AbstractDemoActionSupport {
	private static final long serialVersionUID = 3517185648660870776L;

	@Inject
	private VSecurityManager securityManager;

	@Inject
	private UtilisateurServices utilisateurServices;

	private final ContextForm<UtilisateurLogin> utilisateurLoginRef = new ContextForm<>("utilisateur", this);

	/** {@inheritDoc} */
	@Override
	public void initContext() {
		final UtilisateurLogin utilisateurLogin = new UtilisateurLogin();
		utilisateurLogin.setLogin("test");
		utilisateurLoginRef.publish(utilisateurLogin);
		toModeEdit();
	}

	/**
	 * Connexion.
	 * @return outcome du login
	 */
	public String login() {
		final UtilisateurLogin utilisateurLogin = utilisateurLoginRef.readDto();
		final Utilisateur utilisateur = utilisateurServices.connecterUtilisateur(utilisateurLogin);
		return SUCCESS; //success va sur accueil
	}

	/**
	 * Déconnexion.
	 * @return outcome du logout
	 */
	public String logout() {
		UiRequestUtil.invalidateHttpSession();
		return "reload"; // reload la page
	}

	/** {@inheritDoc} */
	@Override
	public String getPageName() {
		return "Page de connexion";
	}
}
