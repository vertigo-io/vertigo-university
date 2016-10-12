package io.vertigo.twitter;

import org.apache.log4j.Logger;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

/**
 *
 * @author xdurand
 *
 */
public class TwitterSample {

	private static final Logger LOGGER = Logger.getLogger(TwitterSample.class);

	public static void main(final String[] args) {
		final String myTweet = "Tweet !";

		final Twitter twitter = TwitterFactory.getSingleton();
		Status status = null;

		boolean success = true;
		try {
			status = twitter.updateStatus(myTweet);
		} catch (final TwitterException e) {
			LOGGER.error("Error while sending new status.", e);
			success = false;
		}

		if (success) {
			LOGGER.info("Successfully updated the status to [" + (status != null ? status.getText() : "") + "].");
		}
	}

}
