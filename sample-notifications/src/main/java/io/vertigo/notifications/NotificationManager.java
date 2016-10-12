package io.vertigo.notifications;

import io.vertigo.lang.Manager;

/**
 *
 * @author dt
 *
 */
public interface NotificationManager extends Manager {

	/**
	 * Send a message to all channels
	 */
	public void sendMessage(String message);

	/**
	 * Send a message to a specified list of channels
	 * @param strChannels one or more channels. the channels must be delimited by ';'
	 * @param message
	 */
	public void sendMessage(final String strChannels, final String message);

}
