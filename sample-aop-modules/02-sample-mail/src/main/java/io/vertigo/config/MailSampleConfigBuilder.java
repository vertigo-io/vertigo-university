package io.vertigo.config;

import io.vertigo.commons.CommonsFeatures;
import io.vertigo.core.node.config.NodeConfig;
import io.vertigo.core.param.Param;
import io.vertigo.dynamo.DynamoFeatures;
import io.vertigo.social.SocialFeatures;

/**
 * Mail Sample Config Builder
 * @author dt
 *
 */
public class MailSampleConfigBuilder {

	public NodeConfig build() {
		return NodeConfig.builder()
				.beginBoot()
				.withLocales("fr")
				.endBoot()
				.addModule(new CommonsFeatures().build())
				.addModule(new DynamoFeatures().build())
				.addModule(new SocialFeatures()
						.withMails()
						.withJavaxMail(
								Param.of("storeProtocol", "smtp"),
								Param.of("host", "localdelivery.klee.lan.net"),
								Param.of("developmentMode", "true"),
								Param.of("developmentMailTo", "prenom.nom@kleegroup.com"))
						.build())
				.build();
	}

}
