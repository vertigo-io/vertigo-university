package io.vertigo.notifications.plugins.twitter;

import org.apache.log4j.Logger;

import io.vertigo.lang.WrappedException;
import io.vertigo.notifications.impl.NotificationPlugin;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

/**
 * Send a tweet
 * https://twitter.com/Vertigo_Twit
 * @author dt
 *
 */
public class TwitterNotificationPlugin implements NotificationPlugin {
	private static final Logger LOGGER = Logger.getLogger(TwitterNotificationPlugin.class);
	private static final Twitter TWITTER = TwitterFactory.getSingleton();

	@Override
	public void sendMessage(final String message) {
		try {
			final Status status = TWITTER.updateStatus("Message from TwitterNotificationPlugin:" + message);
			LOGGER.info("Successfully updated the status to [" + (status != null ? status.getText() : "") + "].");
		} catch (final TwitterException e) {
			LOGGER.error("Error while sending new status.", e);
			throw new WrappedException(e);
		}
	}

	@Override
	public String getChannel() {
		return "twitter";
	}

}
