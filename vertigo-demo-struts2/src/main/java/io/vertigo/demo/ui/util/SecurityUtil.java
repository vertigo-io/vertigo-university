package io.vertigo.demo.ui.util;

import io.vertigo.core.locale.MessageText;
import io.vertigo.persona.security.VSecurityManager;
import io.vertigo.struts2.core.ComponentRef;
import io.vertigo.vega.webservice.exception.VSecurityException;

/**
 * Utilitaire de vérification des accés sécurisés.
 * @author npiedeloup
 * @version $Id: SecurityUtil.java,v 1.2 2014/07/07 16:40:26 pchretien Exp $
 */
public final class SecurityUtil {

	private static ComponentRef<VSecurityManager> securityManager = ComponentRef.makeLazyRef(VSecurityManager.class);

	/**
	 * Vérifie l'accés à une ressource sécurisé.
	 * La class de l'objet détermine la SecurityResourceFactory à utiliser.
	 * @param value Object à tester
	 * @param operation Type d'opération à tester
	 * @return Si l'opération est autorisée sur cet Objet
	 */
	public static boolean hasAccess(final Object value, final String operation) {
		return securityManager.get().isAuthorized(value.getClass().getSimpleName(), value, operation);
	}

	/**
	 * Vérifie l'accés à une ressource sécurisé.
	 * La class de l'objet détermine la SecurityResourceFactory à utiliser.
	 * @param value Object à tester
	 * @param operation Type d'opération à tester
	 * @throws VSecurityException Si l'opération n'est pas autorisée sur cet Objet
	 */
	public static void checkAccess(final Object value, final String operation) throws VSecurityException {
		if (!hasAccess(value, operation)) {
			throw new VSecurityException(new MessageText("Vous ne posséder pas les droits suffisant pour réaliser cette opération. (" + operation + " sur " + value.getClass().getSimpleName() + ")", null));
		}
	}

}
