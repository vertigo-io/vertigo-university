package io.vertigo.notifications;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import io.vertigo.app.AutoCloseableApp;
import io.vertigo.notifications.config.NotificationSampleConfigBuilder;

/**
 *
 * @author dt
 *
 */
public class NotificationSample {

	private static final Logger LOGGER = Logger.getLogger(NotificationSample.class);

	public static void main(final String[] args) {

		try (AutoCloseableApp app = new AutoCloseableApp(new NotificationSampleConfigBuilder().build())) {

			final NotificationManager notifManager = app.getComponentSpace().resolve(NotificationManager.class);

			final Date now = new Date();
			final DateFormat format = new SimpleDateFormat("dd/MM HH:mm:ss");
			final String message = "Test NotificationSample all channels " + format.format(now);

			notifManager.sendMessage(message);

			final String messageChannels = "Test NotificationSample selected channels " + format.format(now);
			notifManager.sendMessage("mail;twitter", messageChannels);

		} catch (final Exception e) {
			LOGGER.error("Erreur :", e);
		}

	}

}
