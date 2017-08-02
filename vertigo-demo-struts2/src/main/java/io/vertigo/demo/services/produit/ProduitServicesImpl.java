package io.vertigo.demo.services.produit;

import javax.inject.Inject;

import io.vertigo.commons.transaction.Transactional;
import io.vertigo.demo.dao.produit.ProduitDAO;
import io.vertigo.demo.domain.produit.Produit;
import io.vertigo.demo.domain.produit.ProduitCritere;
import io.vertigo.demo.ui.util.SecurityUtil;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.domain.model.DtListURIForCriteria;

/**
 * Implémentation des services associés à la gestion des produits.
 *
 * @author cgodard
 * @version $Id: ProduitServicesImpl.java,v 1.5 2014/02/07 16:48:27 npiedeloup Exp $
 */
@Transactional
public class ProduitServicesImpl implements ProduitServices {

	@Inject
	private ProduitDAO produitDAO;

	/** {@inheritDoc} */
	@Override
	public Produit loadProduit(final Long prdId) {
		final Produit produit = produitDAO.get(prdId);
		SecurityUtil.hasAccess(produit, "OP_READ");
		return produit;
	}

	/** {@inheritDoc} */
	@Override
	public DtList<Produit> getProduitListByCritere(final ProduitCritere criteres) {
		return produitDAO.findAll(DtListURIForCriteria.createCriteria(criteres), 100);
	}

	/** {@inheritDoc} */
	@Override
	public void saveProduit(final Produit produit) {
		SecurityUtil.hasAccess(produit, "OP_WRITE");
		produitDAO.save(produit);
	}

	/** {@inheritDoc} */
	@Override
	public void deleteProduit(final Long prdId) {
		final Produit produit = produitDAO.get(prdId);
		SecurityUtil.hasAccess(produit, "OP_WRITE");
		produitDAO.delete(prdId);
	}

}
