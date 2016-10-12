package io.vertigo.notifications.impl;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import io.vertigo.notifications.NotificationManager;

/**
 *
 * @author dt
 *
 */
public class NotificationManagerImpl implements NotificationManager {

	private final List<NotificationPlugin> notificationPlugins;

	@Inject
	public NotificationManagerImpl(final List<NotificationPlugin> notificationPlugins) {
		this.notificationPlugins = notificationPlugins;
	}

	@Override
	public void sendMessage(final String message) {

		for (final NotificationPlugin notificationPlugin : notificationPlugins) {
			notificationPlugin.sendMessage(message);
		}

	}

	public void sendMessage(final String strChannels, final String message) {
		//@formatter:off
		final List<String> channels = Arrays.asList(strChannels.split(";"));

		notificationPlugins.stream()
						   .filter(p -> channels.contains(p.getChannel()))
						   .forEach(p -> p.sendMessage(message));
	}

}
