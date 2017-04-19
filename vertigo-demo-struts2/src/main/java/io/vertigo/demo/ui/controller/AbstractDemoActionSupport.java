package io.vertigo.demo.ui.controller;

import io.vertigo.struts2.core.AbstractActionSupport;
import io.vertigo.struts2.impl.MethodUtil;
import io.vertigo.struts2.impl.servlet.RequestContainerWrapper;

import org.apache.struts2.ServletActionContext;

/**
 * Super class des Actions struts Demo.
 *
 * @author npiedeloup
 * @version $Id: AbstractDemoActionSupport.java,v 1.2 2013/11/18 10:26:13 npiedeloup Exp $
 */
public abstract class AbstractDemoActionSupport extends AbstractActionSupport {

	private static final long serialVersionUID = 374760712087148984L;

	/**
	* Constructeur.
	*/
	protected AbstractDemoActionSupport() {
		//rien
	}

	/** {@inheritDoc} */
	@Override
	protected void initContext() {
		final RequestContainerWrapper requestContainerWrapper = new RequestContainerWrapper(ServletActionContext.getRequest());
		MethodUtil.invoke(this, "initContext", requestContainerWrapper);
	}

	/**
	 * Retourne le nom de la page.
	 * @return Nom de la page.
	 */
	public abstract String getPageName();
}
