package io.vertigo.demo.ui.util;

/**
 * Message terminal (ex :JSP, PDF, report...).
 * Il a pour responsabilit� de traiter le message. 
 * 
 * La consommation des messages s'effectue par le onMessage.
 * 
 * Si une erreur intervient, c'est au consommateur de r�gler le probl�me.
 *
 * @author  npiedeloup
 * @version $Id: TerminalMessage.java,v 1.1 2013/07/18 17:36:33 npiedeloup Exp $
 */
public interface TerminalMessage {
	/**
	 * Traitement du message invoqu� automatiquement lors de la r�ception du message.
	 * @return Code de retour JSF
	 */
	String doMessage();
}
