package io.vertigo.notifications;

import io.vertigo.lang.Manager;

/**
 * 
 * @author dt
 *
 */
public interface NotificationManager extends Manager {
	
	
	/**
	 * Send a message
	 */
	public void sendMessage(String message);
	
}

