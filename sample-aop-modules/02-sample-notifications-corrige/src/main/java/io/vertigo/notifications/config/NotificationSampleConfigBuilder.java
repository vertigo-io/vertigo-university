package io.vertigo.notifications.config;

import io.vertigo.commons.CommonsFeatures;
import io.vertigo.core.node.config.ModuleConfig;
import io.vertigo.core.node.config.NodeConfig;
import io.vertigo.core.param.Param;
import io.vertigo.datastore.DataStoreFeatures;
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
import io.vertigo.social.SocialFeatures;

/**
 * Notification Config Builder
 * @author dt
 *
 */
public class NotificationSampleConfigBuilder {

	public NodeConfig build() {
		return NodeConfig.builder()
				.beginBoot()
				.withLocales("fr")
				.endBoot()
				.addModule(new CommonsFeatures().build())
				.addModule(new DataStoreFeatures().build())
				.addModule(ModuleConfig.builder("notificationAspects")
						.addComponent(SupervisionManager.class, SupervisionManagerImpl.class)
						.addComponent(TraceManager.class, TraceManagerImpl.class)
						.addAspect(SupervisionAspect.class)
						.addAspect(TraceAspect.class)
						.build())
				.addModule(new SocialFeatures()
						.withMails()
						.withJavaxMail(
								Param.of("storeProtocol", "smtp"),
								Param.of("host", "localdelivery.klee.lan.net"),
								Param.of("developmentMode", "true"),
								Param.of("developmentMailTo", "prenom.nom@kleegroup.com"))
						.build())
				.addModule(ModuleConfig.builder("notifications")
						.addComponent(NotificationManager.class, NotificationManagerImpl.class)
						.addPlugin(IftttNotificationPlugin.class,
								Param.of("proxyHost", "172.20.0.9"),
								Param.of("proxyPort", "3128"))
						.addPlugin(TwitterNotificationPlugin.class)
						.addPlugin(MailNotificationPlugin.class)
						.build())
				.build();
	}

}
