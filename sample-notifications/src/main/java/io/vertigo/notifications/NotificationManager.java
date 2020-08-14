package io.vertigo.notifications;

import io.vertigo.core.node.component.Manager;

/**
 *
 * @author dt
 *
 */
public interface NotificationManager extends Manager {

	/**
	 * Send a message to all channels
	 */
	void sendMessage(String message);

	/**
	 * Send a message to a specified list of channels
	 * @param channels one or more channels.
	 * @param message
	 */
	void sendMessage(final String message, final String... channels);

}
