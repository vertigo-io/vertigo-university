package io.vertigo.demo.services.administration.utilisateur;

import io.vertigo.core.component.Component;
import io.vertigo.demo.domain.administration.utilisateur.Role;
import io.vertigo.demo.domain.administration.utilisateur.Utilisateur;
import io.vertigo.demo.domain.administration.utilisateur.UtilisateurCritere;
import io.vertigo.demo.domain.administration.utilisateur.UtilisateurLogin;
import io.vertigo.dynamo.domain.model.DtList;

/**
 * Décrit les services associés à la gestion des utilisateurs.
 *
 * @author cgodard
 * @version $Id: UtilisateurServices.java,v 1.4 2014/02/07 16:48:27 npiedeloup Exp $
 */
public interface UtilisateurServices extends Component {

	/**
	 * Connecte un utilisateur authentifi� avec un identifiant et un mot de passe.
	 *
	 * @param utilisateurLogin informations de login
	 * @return utilisateur connect�
	 */
	Utilisateur connecterUtilisateur(UtilisateurLogin utilisateurLogin);

	/**
	 * Charge un utilisateur par son id technique.
	 *
	 * @return utilisateur connect�
	 */
	Utilisateur loadUtilisateurWithRoles(Long utiId);

	/**
	 * Charge un role par son code.
	 *
	 * @param rolCode code du role
	 * @return Role
	 */
	Role loadRole(String rolCode);

	/**
	 * Charge la liste des roles
	 *
	 * @return liste de roles
	 */
	public DtList<Role> getAllRoles();

	/**
	 * Retourne la liste des utilisateurs r�pondant aux crit�res.
	 *
	 * @param criteres crit�res de recherche des utilisateurs
	 * @return liste d'utilisateurs r�pondant aux crit�res donn�s en entr�e
	 */
	DtList<Utilisateur> getUtilisateurListByCritere(UtilisateurCritere criteres);

	/**
	 * Sauvegarde en base un utilisateur.
	 *
	 * @param utilisateur utilisateur � persister en base
	 */
	void saveUtilisateur(Utilisateur utilisateur, DtList<Role> dtcRole);

	/**
	 * Sauvegarde en base un utilisateur, et ses informations de login.
	 *
	 * @param utilisateur utilisateur � persister en base
	 * @param dtcRole Liste des droits
	 */
	void saveUtilisateur(final UtilisateurLogin login, final Utilisateur utilisateur, DtList<Role> dtcRole);

	/**
	 * Supprime un utilisateur en base de donn�es.
	 *
	 * @param utiId Id utilisateur � supprimer en base
	 */
	void deleteUtilisateur(Long utiId);

}
