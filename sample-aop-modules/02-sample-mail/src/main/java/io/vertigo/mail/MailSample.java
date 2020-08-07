package io.vertigo.mail;

import io.vertigo.config.MailSampleConfigBuilder;
import io.vertigo.core.node.AutoCloseableNode;
import io.vertigo.social.mail.Mail;
import io.vertigo.social.mail.MailManager;

/**
 * Send a mail.
 * @author dt
 *
 */
public class MailSample {

	private static final String DT_MAIL = "Direction Technique<direction.technique@yopmail.com>";

	public static void main(final String[] args) {
		final String myMail = "My Mail !";

		try (AutoCloseableNode node = new AutoCloseableNode(new MailSampleConfigBuilder().build())) {

			final MailManager mailManager = node.getComponentSpace().resolve(MailManager.class);

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
