package io.vertigo.notifications.impl;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import io.vertigo.notifications.NotificationManager;
import io.vertigo.notifications.aspects.supervision.Supervision;
import io.vertigo.notifications.aspects.trace.Trace;

/**
 *
 * @author dt
 *
 */
@Supervision
@Trace
public class NotificationManagerImpl implements NotificationManager {

	@Inject
	private List<NotificationPlugin> notificationPlugins;

	@Override
	public void sendMessage(final String message) {
		notificationPlugins.stream()
				.forEach(p -> p.sendMessage(message));

	}

	@Override
	public void sendMessage(final String message, final String... arrChannels) {
		final List<String> channels = Arrays.asList(arrChannels);
		notificationPlugins.stream()
				.filter(p -> channels.contains(p.getChannel()))
				.forEach(p -> p.sendMessage(message));
	}

}
