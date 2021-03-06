package io.vertigo.notifications;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.vertigo.core.node.AutoCloseableNode;
import io.vertigo.notifications.config.NotificationSampleConfigBuilder;

/**
 *
 * @author dt
 *
 */
public class NotificationSample {

	public static void main(final String[] args) {
		try (AutoCloseableNode node = new AutoCloseableNode(new NotificationSampleConfigBuilder().build())) {

			final NotificationManager notificationManager = node.getComponentSpace().resolve(NotificationManager.class);

			final Date now = new Date();
			final DateFormat format = new SimpleDateFormat("dd/MM HH:mm:ss");
			final String message = "Test NotificationSample all channels " + format.format(now);

			notificationManager.sendMessage(message);

			final String messageChannels = "Test NotificationSample selected channels " + format.format(now);

			notificationManager.sendMessage(messageChannels, "mail", "twitter");
		}

	}

}
