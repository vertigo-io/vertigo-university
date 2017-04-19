package io.vertigo.demo.ui.controller.accueil;

import io.vertigo.demo.ui.controller.AbstractDemoActionSupport;

/**
 * @author npiedeloup
 * @version $Id: AccueilAction.java,v 1.3 2014/06/27 12:21:39 pchretien Exp $
 */
public final class AccueilAction extends AbstractDemoActionSupport {
	private static final long serialVersionUID = -7166081954858375515L;
	private static final String PAGE_TITLE = "Accueil";

	@Override
	protected void initContext() {
		//rien
	}

	/** {@inheritDoc} */
	@Override
	public String getPageName() {
		return PAGE_TITLE;
	}

}
