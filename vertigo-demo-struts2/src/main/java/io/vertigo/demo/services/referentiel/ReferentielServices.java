package io.vertigo.demo.services.referentiel;

import java.util.List;

import io.vertigo.demo.domain.referentiel.Famille;
import io.vertigo.demo.domain.referentiel.OuiNonChoice;
import io.vertigo.lang.Component;

/**
 * Décrit les services associés à la gestion des utilisateurs.
 *
 * @author cgodard
 * @version $Id: ReferentielServices.java,v 1.1 2013/07/18 17:36:33 npiedeloup Exp $
 */
public interface ReferentielServices extends Component {

	/**
	 * Charge la liste des familles d'un produit.
	 */
	List<Famille> loadFamille();

	/**
	 * Retourne une famille
	 * @param famId identifiant d'une famille
	 *
	 * @return liste des familles
	 */
	Famille getFamille(final Long famId);

	/**
	 * @return Fournit la liste de choix pour Oui/Non
	 */
	List<OuiNonChoice> getOuiNonList();

}
