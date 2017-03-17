package io.vertigo.config;

import io.vertigo.app.config.AppConfig;
import io.vertigo.app.config.AppConfigBuilder;
import io.vertigo.app.config.ModuleConfigBuilder;
import io.vertigo.commons.impl.CommonsFeatures;
import io.vertigo.core.param.Param;
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
				.beginBoot()
				.withLocales("fr")
				.endBoot()
				.addModule(new CommonsFeatures().build())
				.addModule(new DynamoFeatures().build())
				.addModule(new ModuleConfigBuilder("mail")
						.addComponent(MailManager.class, MailManagerImpl.class)
						.addPlugin(JavaxSendMailPlugin.class,
								Param.create("storeProtocol", "smtp"),
								Param.create("host", "localdelivery.klee.lan.net"),
								Param.create("developmentMode", "true"),
								Param.create("developmentMailTo", "prenom.nom@kleegroup.com"))
						.build())
				.build();
	}

}
