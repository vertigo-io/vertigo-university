package io.vertigo.demo.ui.controller.administration.utilisateur;

import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;

import io.vertigo.demo.domain.administration.utilisateur.Role;
import io.vertigo.demo.domain.administration.utilisateur.Utilisateur;
import io.vertigo.demo.domain.administration.utilisateur.UtilisateurLogin;
import io.vertigo.demo.services.administration.utilisateur.UtilisateurServices;
import io.vertigo.demo.ui.controller.AbstractDemoActionSupport;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.struts2.core.ContextForm;
import io.vertigo.struts2.core.ContextList;

/**
 * @author npiedeloup
 * @version $Id: UtilisateurDetailAction.java,v 1.3 2014/06/27 12:21:39 pchretien Exp $
 */
public final class UtilisateurDetailAction extends AbstractDemoActionSupport {

	private static final long serialVersionUID = 5176368775307768464L;

	@Inject
	private UtilisateurServices utilisateurServices;

	private final ContextForm<UtilisateurLogin> utilisateurLogin = new ContextForm<>("utilisateurLogin", this);
	private final ContextForm<Utilisateur> utilisateur = new ContextForm<>("utilisateur", this);
	private final ContextList<Role> roles = new ContextList<>("roles", this);

	/**
	 * @param utiId Id de l'élément a afficher.
	 */
	public void initContext(@Named("utiId") final Optional<Long> utiId) {
		if (utiId.isPresent()) {
			final Utilisateur dtoUtilisateur = utilisateurServices.loadUtilisateurWithRoles(utiId.get());
			utilisateur.publish(dtoUtilisateur);
			roles.publish(dtoUtilisateur.getRoleList());
		} else {
			utilisateur.publish(new Utilisateur());
			roles.publish(new DtList<>(Role.class));
			toModeCreate();
		}
		utilisateurLogin.publish(new UtilisateurLogin());
	}

	/**
	 * @return Outcome d'enregistrement
	 */
	public String doSave() {
		utilisateur.checkErrors();
		if (isModeCreate()) {
			utilisateurServices.saveUtilisateur(utilisateurLogin.readDto(), utilisateur.readDto(), roles.readDtList());
		} else {
			utilisateurServices.saveUtilisateur(utilisateur.readDto(), roles.readDtList());
		}
		return SUCCESS;
	}

	/**
	 * @param utiId Identifiant de l'utilisateur
	 * @return Outcome de suppression
	 */
	public String doDelete(@Named("utiId") final Long utiId) {
		utilisateurServices.deleteUtilisateur(utiId);
		return "success_delete";
	}

	/**
	 * @return Outcome du passage en edition
	 */
	public String doEdit() {
		toModeEdit();
		return NONE;
	}

	/** {@inheritDoc} */
	@Override
	public String getPageName() {
		if (isModeCreate()) {
			return "Creation d'un utilisateur";
		} else if (isModeEdit()) {
			return "Modification d'un utilisateur";
		}
		return "Detail d'un utilisateur";
	}
}
