package io.vertigo.demo.services.client;

import io.vertigo.demo.domain.client.ClientCritere;
import io.vertigo.demo.domain.commande.Client;
import io.vertigo.dynamo.domain.model.DtList;

/**
 * D�crit les services associ�s � la gestion des produits.
 * 
 * @author cgodard
 * @version $Id: ClientServices.java,v 1.2 2014/02/07 16:48:27 npiedeloup Exp $
 */
public interface ClientServices {

	/**
	 * Charge un produit par son id technique.
	 * 
	 * @param prdId identifiant
	 * @return produit 
	 */
	Client loadClient(Long prdId);

	/**
	 * Retourne la liste des produits r�pondant aux crit�res.
	 * 
	 * @param criteres crit�res de recherche des produits
	 * @return liste d'produits r�pondant aux crit�res donn�s en entr�e
	 */
	DtList<Client> getClientListByCritere(ClientCritere criteres);

	/**
	 * Sauvegarde en base un produit.
	 * 
	 * @param produit produit � persister en base
	 */
	void saveClient(Client produit);

	/**
	 * Supprime un produit en base de donn�es.
	 * 
	 * @param cliId Id produit � supprimer en base
	 */
	void deleteClient(Long cliId);

}
