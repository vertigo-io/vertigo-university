package io.vertigo.config;

import io.vertigo.app.config.AppConfig;
import io.vertigo.app.config.AppConfigBuilder;
import io.vertigo.commons.impl.CommonsFeatures;
import io.vertigo.dynamo.impl.DynamoFeatures;
import io.vertigo.tempo.impl.mail.MailManagerImpl;
import io.vertigo.tempo.mail.MailManager;
import io.vertigo.tempo.plugins.mail.javaxmail.JavaxSendMailPlugin;

/**
 * Mail Sample Config Builder
 * @author dt
 *
 */
public class MailSampleConfigBuilder {

	public AppConfig build() {
		//@formatter:off
		return new AppConfigBuilder()
				.beginBootModule("fr").endModule()
				.beginModule(CommonsFeatures.class).endModule()
				.beginModule(DynamoFeatures.class).endModule()
				.beginModule("mail")
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
