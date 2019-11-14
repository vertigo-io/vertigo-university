package io.vertigo.mail;

import io.vertigo.config.MailSampleConfigBuilder;
import io.vertigo.core.node.AutoCloseableApp;
import io.vertigo.social.services.mail.Mail;
import io.vertigo.social.services.mail.MailManager;

/**
 * Send a mail.
 * @author dt
 *
 */
public class MailSample {

	private static final String DT_MAIL = "Direction Technique<direction.technique@yopmail.com>";

	public static void main(final String[] args) {
		final String myMail = "My Mail !";

		try (AutoCloseableApp app = new AutoCloseableApp(new MailSampleConfigBuilder().build())) {

			final MailManager mailManager = app.getComponentSpace().resolve(MailManager.class);

			final Mail mail = Mail.builder()
					.from(DT_MAIL)
					.to(DT_MAIL)
					.withSubject("Message from MailSample")
					.replyTo(DT_MAIL)
					.withTextContent(myMail)
					.build();
			mailManager.sendMail(mail);
		}

	}

}
