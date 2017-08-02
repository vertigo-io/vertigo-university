package io.vertigo.demo.services.administration.utilisateur;

import java.util.Optional;

import javax.inject.Inject;

import io.vertigo.app.Home;
import io.vertigo.commons.transaction.Transactional;
import io.vertigo.core.locale.MessageText;
import io.vertigo.demo.dao.administration.utilisateur.LoginDAO;
import io.vertigo.demo.dao.administration.utilisateur.RoleDAO;
import io.vertigo.demo.dao.administration.utilisateur.UtilisateurDAO;
import io.vertigo.demo.domain.DtDefinitions;
import io.vertigo.demo.domain.administration.utilisateur.Login;
import io.vertigo.demo.domain.administration.utilisateur.Role;
import io.vertigo.demo.domain.administration.utilisateur.Utilisateur;
import io.vertigo.demo.domain.administration.utilisateur.UtilisateurCritere;
import io.vertigo.demo.domain.administration.utilisateur.UtilisateurLogin;
import io.vertigo.demo.services.DemoUserSession;
import io.vertigo.demo.services.util.PasswordHelper;
import io.vertigo.dynamo.criteria.Criterions;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.domain.model.DtListURI;
import io.vertigo.dynamo.domain.model.DtListURIForCriteria;
import io.vertigo.dynamo.domain.util.DtObjectUtil;
import io.vertigo.dynamo.store.StoreManager;
import io.vertigo.lang.Assertion;
import io.vertigo.lang.VUserException;
import io.vertigo.persona.security.VSecurityManager;
import io.vertigo.util.DateUtil;

/**
 * Impl�mentation des services associ�s � la gestion des utilisateurs.
 *
 * @author npiedeloup
 * @version $Id: UtilisateurServicesImpl.java,v 1.7 2014/08/05 09:14:42 npiedeloup Exp $
 */
@Transactional
public class UtilisateurServicesImpl implements UtilisateurServices {
	private static final String MSG_LOGIN_INVALID = "Les informations de connexion que vous avez fournies ne correspondent pas � cet utilisateur.";

	@Inject
	private LoginDAO loginDAO;
	@Inject
	private RoleDAO roleDAO;
	@Inject
	private UtilisateurDAO utilisateurDAO;
	@Inject
	private VSecurityManager securityManager;

	private final PasswordHelper passwordHelper = new PasswordHelper();

	/** {@inheritDoc} */
	@Override
	public Utilisateur connecterUtilisateur(final UtilisateurLogin utilisateurLogin) {
		// Authentification de l'utilisateur auprès de la base de données
		final Utilisateur utilisateur = loadUtilisateurByLogin(utilisateurLogin);
		// Charge la collection car la transaction est au niveau de la couche service
		utilisateur.getRoleList();
		return utilisateur;
	}

	/** {@inheritDoc} */
	@Override
	public Utilisateur loadUtilisateurWithRoles(final Long utiId) {
		final Utilisateur utilisateur = utilisateurDAO.get(utiId);
		//On charge les roles en mode transactionnel
		utilisateur.getRoleList();
		return utilisateur;
	}

	/** {@inheritDoc} */
	@Override
	public Role loadRole(final String rolCode) {
		return roleDAO.get(rolCode);
	}

	/** {@inheritDoc} */
	@Override
	public DtList<Role> getAllRoles() {
		final DtListURI collectionURI = new DtListURIForCriteria<>(DtObjectUtil.findDtDefinition(Role.class), null, null);
		return Home.getApp().getComponentSpace().resolve(StoreManager.class).getDataStore().findAll(collectionURI);
	}

	/** {@inheritDoc} */
	@Override
	public DtList<Utilisateur> getUtilisateurListByCritere(final UtilisateurCritere criteres) {
		return utilisateurDAO.listUtilisateurByCritere(criteres);
	}

	/** {@inheritDoc} */
	@Override
	public void saveUtilisateur(final Utilisateur utilisateur, final DtList<Role> dtcRole) {
		Assertion.checkNotNull(utilisateur);
		// ---------------------------------------------------------------------
		if (utilisateur.getUtiId() == null) {
			utilisateur.setDateCreation(DateUtil.newDateTime());
		} else {
			utilisateur.setDateDerniereModif(DateUtil.newDateTime());
		}

		utilisateur.setAuteurDerniereModif(getCurrentUtilisateur().getPrenomNom());
		utilisateurDAO.save(utilisateur);
		if (dtcRole != null) {
			utilisateurDAO.updateNN(utilisateur.getRoleDtListURI(), dtcRole);
		}
	}

	private Utilisateur getCurrentUtilisateur() {
		return securityManager.<DemoUserSession> getCurrentUserSession().get().getUtilisateur();
	}

	/** {@inheritDoc} */
	@Override
	public void saveUtilisateur(final UtilisateurLogin utilisateurLogin, final Utilisateur utilisateur, final DtList<Role> dtcRole) {
		Assertion.checkNotNull(utilisateurLogin);
		// ---------------------------------------------------------------------
		final boolean isCreation = utilisateur.getUtiId() == null;
		saveUtilisateur(utilisateur, dtcRole);
		if (!isCreation) {
			final Utilisateur loggedUser = loadUtilisateurByLogin(utilisateurLogin);
			if (!loggedUser.getUtiId().equals(utilisateur.getUtiId())) {
				throw new VUserException(new MessageText(MSG_LOGIN_INVALID, null));
			}
			loginDAO.delete(utilisateur.getLoginList().get(0).getLogId());
		}
		final Login login = new Login();
		login.setUtiId(utilisateur.getUtiId());
		login.setLogin(utilisateurLogin.getLogin());
		final String encodedPassword = passwordHelper.encodePassword(passwordHelper.generateNewSalt(), utilisateurLogin.getPassword());
		login.setPassword(encodedPassword);
		loginDAO.create(login);
	}

	/** {@inheritDoc} */
	@Override
	public void deleteUtilisateur(final Long utiId) {
		throw new UnsupportedOperationException("operation not supported yet !!!");
		//utilisateurDAO.delete(utiId);
	}

	/**
	 * Authentification de l'utilisateur en base de données.<br>
	 * Récupération de l'utilisateur dans la base de données de l'application si l'utilisateur est authentifié.
	 *
	 * @param utilisateurLogin login et mot de passe de l'utilisateur cherchant à se connecter
	 * @return l'utilisateur correspondant aux login et mot de passe saisis.
	 */
	private Utilisateur loadUtilisateurByLogin(final UtilisateurLogin utilisateurLogin) {
		Assertion.checkNotNull(utilisateurLogin);
		//-----
		final Optional<Login> logins = loginDAO.findOptional(Criterions.isEqualTo(DtDefinitions.LoginFields.LOGIN, utilisateurLogin.getLogin()));
		//On effectue le même traitement si le login est incorrect pour éviter l'analyse par le temps
		final String password = !logins.isPresent() ? "UnknownPassword" : logins.get().getPassword();
		final String salt = passwordHelper.extractSalt(password);
		final String encodePassword = passwordHelper.encodePassword(salt, utilisateurLogin.getPassword());

		if (!logins.isPresent() || !password.equals(encodePassword)) {
			throw new VUserException(new MessageText("Login ou mot de passe incorrect", null));
		}

		final DemoUserSession session = securityManager.<DemoUserSession> getCurrentUserSession().get();
		final Utilisateur utilisateur = utilisateurDAO.get(logins.get().getUtiId());
		session.setUtilisateur(utilisateur);
		session.authenticate();

		for (final Role role : utilisateur.getRoleList()) {
			session.addRole(Home.getApp().getDefinitionSpace().resolve(role.getRolCode(), io.vertigo.persona.security.metamodel.Role.class));
		}

		return utilisateur;
	}
}
