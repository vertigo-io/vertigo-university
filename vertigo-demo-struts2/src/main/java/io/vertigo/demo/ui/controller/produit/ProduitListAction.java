package io.vertigo.demo.ui.controller.produit;

import javax.inject.Inject;

import io.vertigo.demo.domain.produit.Produit;
import io.vertigo.demo.domain.produit.ProduitCritere;
import io.vertigo.demo.services.produit.ProduitServices;
import io.vertigo.demo.ui.controller.AbstractDemoActionSupport;
import io.vertigo.struts2.core.ContextList;

/**
 * @author prahmoune
 * @version $Id: ProduitListAction.java,v 1.4 2014/06/27 12:21:39 pchretien Exp $
 */
public final class ProduitListAction extends AbstractDemoActionSupport {
	private static final long serialVersionUID = 1L;

	@Inject
	private ProduitServices produitServices;

	private final ContextList<Produit> produits = new ContextList<>("produits", this);

	/** {@inheritDoc} */
	@Override
	public void initContext() {
		produits.publish(produitServices.getProduitListByCritere(new ProduitCritere()));
	}

	/** {@inheritDoc} */
	@Override
	public String getPageName() {
		return "Recherche de produit";
	}
}
