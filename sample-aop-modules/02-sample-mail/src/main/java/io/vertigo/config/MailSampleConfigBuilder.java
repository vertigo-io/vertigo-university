package io.vertigo.config;

import io.vertigo.commons.CommonsFeatures;
import io.vertigo.connectors.mail.MailFeatures;
import io.vertigo.core.node.config.BootConfig;
import io.vertigo.core.node.config.NodeConfig;
import io.vertigo.core.param.Param;
import io.vertigo.datastore.DataStoreFeatures;
import io.vertigo.social.SocialFeatures;

/**
 * Mail Sample Config Builder
 * @author dt
 *
 */
public class MailSampleConfigBuilder {

	public NodeConfig build() {
		return NodeConfig.builder()
				.withBoot(BootConfig.builder()
						.withLocales("fr")
						.build())
				.addModule(new MailFeatures()
						.withNativeMailConnector(
								Param.of("storeProtocol", "smtp"),
								Param.of("host", "localdelivery.klee.lan.net"))
						.build())
				.addModule(new CommonsFeatures().build())
				.addModule(new DataStoreFeatures().build())
				.addModule(new SocialFeatures()
						.withMails()
						.withJavaxMail(
								Param.of("developmentMode", "true"),
								Param.of("developmentMailTo", "matthieu.laroche@kleegroup.com"))
						.build())
				.build();
	}

}
