package io.vertigo.demo.boot.initializer;

import javax.inject.Inject;

import io.vertigo.account.security.VSecurityManager;
import io.vertigo.core.component.ComponentInitializer;
import io.vertigo.demo.domain.produit.Produit;
import io.vertigo.persona.impl.security.BeanResourceNameFactory;

/**
 * Initialisation du manager de Securit�.
 * @author npiedeloup
 * @version $Id: SecurityManagerInitializer.java,v 1.4 2014/07/11 10:54:57 npiedeloup Exp $
 */
public final class SecurityManagerInitializer implements ComponentInitializer {

	@Inject
	private VSecurityManager securityManager;

	/** {@inheritDoc} */
	@Override
	public void init() {
		securityManager.registerResourceNameFactory(Produit.class.getSimpleName(), new BeanResourceNameFactory("/DATA/Produit/${prdId}/${famId}"));
	}
}
