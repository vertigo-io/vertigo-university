package io.vertigo.config;

import io.vertigo.app.config.AppConfig;
import io.vertigo.app.config.ModuleConfig;
import io.vertigo.commons.CommonsFeatures;
import io.vertigo.core.param.Param;
import io.vertigo.dynamo.DynamoFeatures;
import io.vertigo.mail.MailManager;
import io.vertigo.mail.impl.MailManagerImpl;
import io.vertigo.mail.plugins.javax.JavaxSendMailPlugin;

/**
 * Mail Sample Config Builder
 * @author dt
 *
 */
public class MailSampleConfigBuilder {

	public AppConfig build() {
		//@formatter:off
		return  AppConfig.builder()
				.beginBoot()
				.withLocales("fr")
				.endBoot()
				.addModule(new CommonsFeatures().build())
				.addModule(new DynamoFeatures().build())
				.addModule( ModuleConfig.builder("mail")
						.addComponent(MailManager.class, MailManagerImpl.class)
						.addPlugin(JavaxSendMailPlugin.class,
								Param.of("storeProtocol", "smtp"),
								Param.of("host", "localdelivery.klee.lan.net"),
								Param.of("developmentMode", "true"),
								Param.of("developmentMailTo", "prenom.nom@kleegroup.com"))
						.build())
				.build();
	}

}
