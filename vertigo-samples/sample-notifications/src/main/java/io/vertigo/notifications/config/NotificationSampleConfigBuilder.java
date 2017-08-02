package io.vertigo.notifications.config;

import io.vertigo.app.config.AppConfig;
import io.vertigo.app.config.ModuleConfig;
import io.vertigo.commons.impl.CommonsFeatures;
import io.vertigo.core.param.Param;
import io.vertigo.dynamo.impl.DynamoFeatures;
import io.vertigo.mail.MailManager;
import io.vertigo.mail.impl.MailManagerImpl;
import io.vertigo.mail.plugins.javax.JavaxSendMailPlugin;
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

/**
 * Notification Config Builder
 * @author dt
 *
 */
public class NotificationSampleConfigBuilder {

	public AppConfig build() {
		//@formatter:off
		return  AppConfig.builder()
				.beginBoot()
				.withLocales("fr")
				.endBoot()
				.addModule(new CommonsFeatures().build())
				.addModule(new DynamoFeatures().build())
				.addModule( ModuleConfig.builder("notificationAspects")
						.addComponent(SupervisionManager.class, SupervisionManagerImpl.class)
						.addComponent(TraceManager.class, TraceManagerImpl.class)
						.addAspect(SupervisionAspect.class)
						.addAspect(TraceAspect.class)
						.build())
				.addModule( ModuleConfig.builder("notifications")
						.addComponent(NotificationManager.class, NotificationManagerImpl.class)
							.addPlugin(IftttNotificationPlugin.class,
									Param.of("proxyHost", "172.20.0.9"),
									Param.of("proxyPort", "3128"))
							.addPlugin(TwitterNotificationPlugin.class)
							.addPlugin(MailNotificationPlugin.class)
						.addComponent(MailManager.class, MailManagerImpl.class)
							.addPlugin(JavaxSendMailPlugin.class,
									Param.of("storeProtocol", "smtp"),
									Param.of("host", "localdelivery.klee.lan.net"),
									Param.of("developmentMode", "true"),
									Param.of("developmentMailTo", "prenom.nom@kleegroup.com"))
						.build()
						)
				.build();
	}

}
