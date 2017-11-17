package io.vertigo.demo.ui.controller.administration.utilisateur;

import javax.inject.Inject;

import io.vertigo.demo.domain.administration.utilisateur.Utilisateur;
import io.vertigo.demo.domain.administration.utilisateur.UtilisateurCritere;
import io.vertigo.demo.services.administration.utilisateur.UtilisateurServices;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.domain.util.DtObjectUtil;
import io.vertigo.struts2.core.AbstractActionSupport;
import io.vertigo.struts2.core.ContextForm;
import io.vertigo.struts2.core.ContextList;
import io.vertigo.struts2.core.ContextRef;

/**
 * @author npiedeloup
 * @version $Id: UtilisateurListAction.java,v 1.3 2014/06/27 12:21:39 pchretien Exp $
 */
public final class UtilisateurListAction extends AbstractActionSupport {
	private static final long serialVersionUID = -77969871290649147L;

	@Inject
	private UtilisateurServices utilisateurServices;

	private final ContextForm<UtilisateurCritere> utilisateurCritereForm = new ContextForm<>("utilisateurCritere", this);
	private final ContextRef<Boolean> allUserRef = new ContextRef<>("allUser", Boolean.class, this);
	private final ContextList<Utilisateur> utilisateurs = new ContextList<>("utilisateurs", this);

	/** {@inheritDoc} */
	@Override
	protected void initContext() {
		utilisateurCritereForm.publish(new UtilisateurCritere());
		allUserRef.set(Boolean.TRUE);
		utilisateurs.publish(new DtList<Utilisateur>(DtObjectUtil.findDtDefinition(Utilisateur.class)));
		toModeEdit();
	}

	protected void reloadList() {
		final UtilisateurCritere utilisateurCritere = utilisateurCritereForm.readDto();
		if (allUserRef.get()) {
			utilisateurCritere.setIsActif(null); //pr�f�rer un autre boolean dans le crit�re : ici c'est pour test
		}
		utilisateurs.publish(utilisateurServices.getUtilisateurListByCritere(utilisateurCritere));
	}

	public String rechercher() {
		reloadList();
		return NONE;
	}

	public String rechercherActif() {
		utilisateurCritereForm.getUiObject().setTypedValue("isActif", true);
		allUserRef.set(Boolean.FALSE);
		reloadList();
		return NONE;
	}

	public String rechercherInactif() {
		utilisateurCritereForm.getUiObject().setTypedValue("isActif", false);
		allUserRef.set(Boolean.FALSE);
		reloadList();
		return NONE;
	}

	public String rechercherTous() {
		utilisateurCritereForm.getUiObject().setTypedValue("isActif", false);
		allUserRef.set(Boolean.TRUE);
		reloadList();
		return NONE;
	}
}
