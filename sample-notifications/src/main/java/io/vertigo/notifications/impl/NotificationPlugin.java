package io.vertigo.notifications.impl;

import io.vertigo.lang.Plugin;

/**
 * Plugin permettant de gérer l'envoi de notifications
 * @author dt
 *
 */
public interface NotificationPlugin extends Plugin {
	
	/**
	 * Send a message
	 * @param message the message to send
	 */
	void sendMessage(String message);
	
}