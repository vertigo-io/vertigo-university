package io.vertigo.notifications.config;

import io.vertigo.app.config.AppConfig;
import io.vertigo.app.config.AppConfigBuilder;
import io.vertigo.commons.impl.CommonsFeatures;
import io.vertigo.dynamo.impl.DynamoFeatures;
import io.vertigo.notifications.NotificationManager;
import io.vertigo.notifications.aspects.supervision.SupervisionAspect;
import io.vertigo.notifications.aspects.supervision.SupervisionManager;
import io.vertigo.notifications.aspects.supervision.SupervisionManagerImpl;
import io.vertigo.notifications.aspects.trace.TraceAspect;
import io.vertigo.notifications.aspects.trace.TraceManager;
import io.vertigo.notifications.aspects.trace.TraceManagerImpl;
import io.vertigo.notifications.impl.NotificationManagerImpl;
import io.vertigo.notifications.plugins.ifttt.IftttNotificationPlugin;
import io.vertigo.notifications.plugins.mail.MailNotificationPlugin;
import io.vertigo.notifications.plugins.twitter.TwitterNotificationPlugin;
import io.vertigo.tempo.impl.mail.MailManagerImpl;
import io.vertigo.tempo.mail.MailManager;
import io.vertigo.tempo.plugins.mail.javaxmail.JavaxSendMailPlugin;

/**
 * Notification Config Builder
 * @author dt
 *
 */
public class NotificationSampleConfigBuilder {

	public AppConfig build() {
		//@formatter:off
		return new AppConfigBuilder()
				.beginBootModule("fr").endModule()
				.beginModule(CommonsFeatures.class).endModule()
				.beginModule(DynamoFeatures.class).endModule()
				.beginModule("notificationAspects")
					.addComponent(SupervisionManager.class, SupervisionManagerImpl.class)
					.addComponent(TraceManager.class, TraceManagerImpl.class)
					.addAspect(SupervisionAspect.class)
					.addAspect(TraceAspect.class)
				.endModule()
				.beginModule("notifications")
					.addComponent(NotificationManager.class, NotificationManagerImpl.class)
						.beginPlugin(IftttNotificationPlugin.class)
							.addParam("proxyHost", "172.20.0.9")
							.addParam("proxyPort", "3128")
						.endPlugin()
						.addPlugin(TwitterNotificationPlugin.class)
						.addPlugin(MailNotificationPlugin.class)
					.addComponent(MailManager.class, MailManagerImpl.class)
						.beginPlugin(JavaxSendMailPlugin.class)
							.addParam("storeProtocol", "smtp")
							.addParam("host", "localdelivery.klee.lan.net")
							.addParam("developmentMode", "true")
							.addParam("developmentMailTo", "prenom.nom@kleegroup.com")
						.endPlugin()
				.endModule()
				.build();
	}

}
