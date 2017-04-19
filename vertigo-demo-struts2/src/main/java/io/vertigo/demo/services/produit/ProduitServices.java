package io.vertigo.demo.services.produit;

import io.vertigo.demo.domain.produit.Produit;
import io.vertigo.demo.domain.produit.ProduitCritere;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.lang.Component;

/**
 * Décrit les services associés à la gestion des produits.
 *
 * @author cgodard
 * @version $Id: ProduitServices.java,v 1.6 2014/02/07 16:48:27 npiedeloup Exp $
 */
public interface ProduitServices extends Component {

	/**
	 * Charge un produit par son id technique.
	 *
	 * @param prdId identifiant
	 * @return produit
	 */
	Produit loadProduit(Long prdId);

	/**
	 * Retourne la liste des produits répondant aux critères.
	 *
	 * @param criteres critères de recherche des produits
	 * @return liste d'produits répondant aux critères donnés en entrée
	 */
	DtList<Produit> getProduitListByCritere(ProduitCritere criteres);

	/**
	 * Sauvegarde en base un produit.
	 *
	 * @param produit produit à persister en base
	 */
	void saveProduit(Produit produit);

	/**
	 * Supprime un produit en base de données.
	 *
	 * @param prdId Id produit à supprimer en base
	 */
	void deleteProduit(Long prdId);

}
