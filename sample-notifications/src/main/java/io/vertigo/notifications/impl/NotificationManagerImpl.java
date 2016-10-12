package io.vertigo.notifications.impl;

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
	public void sendMessage(String message) {

		for (NotificationPlugin notificationPlugin : notificationPlugins) {
			notificationPlugin.sendMessage(message);
		}
		
	}
	
	
}