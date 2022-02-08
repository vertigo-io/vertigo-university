package io.vertigo.notifications.config;

import io.vertigo.commons.CommonsFeatures;
import io.vertigo.connectors.mail.MailFeatures;
import io.vertigo.core.node.config.BootConfig;
import io.vertigo.core.node.config.ModuleConfig;
import io.vertigo.core.node.config.NodeConfig;
import io.vertigo.core.param.Param;
import io.vertigo.datamodel.DataModelFeatures;
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
import io.vertigo.social.impl.mail.MailManagerImpl;
import io.vertigo.social.mail.MailManager;
import io.vertigo.social.plugins.mail.javax.JavaxMailPlugin;

/**
 * Notification Config Builder
 * @author dt
 *
 */
public class NotificationSampleConfigBuilder {

	public NodeConfig build() {
		return NodeConfig.builder()
				.withBoot(BootConfig.builder()
						.withLocales("fr")
						.build())
				.addModule(new MailFeatures().withNativeMailConnector(
						Param.of("storeProtocol", "smtp"),
						Param.of("host", "localdelivery.klee.lan.net"))
						.build())
				.addModule(new CommonsFeatures().build())
				.addModule(new DataModelFeatures().build())
				.addModule(new DataStoreFeatures().build())
				.addModule(ModuleConfig.builder("notificationAspects")
						.addComponent(SupervisionManager.class, SupervisionManagerImpl.class)
						.addComponent(TraceManager.class, TraceManagerImpl.class)
						.addAspect(SupervisionAspect.class)
						.addAspect(TraceAspect.class)
						.build())
				.addModule(ModuleConfig.builder("notifications")
						.addComponent(NotificationManager.class, NotificationManagerImpl.class)
						.addPlugin(IftttNotificationPlugin.class,
								Param.of("proxyHost", "172.20.0.9"),
								Param.of("proxyPort", "3128"))
						.addPlugin(TwitterNotificationPlugin.class)
						.addPlugin(MailNotificationPlugin.class)
						.addComponent(MailManager.class, MailManagerImpl.class)
						.addPlugin(JavaxMailPlugin.class,
								Param.of("developmentMode", "true"),
								Param.of("developmentMailTo", "prenom.nom@kleegroup.com"))
						.build())
				.build();
	}

}
