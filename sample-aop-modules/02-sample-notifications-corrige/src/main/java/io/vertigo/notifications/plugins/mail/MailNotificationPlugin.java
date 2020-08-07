package io.vertigo.notifications.plugins.mail;

import javax.inject.Inject;

import io.vertigo.notifications.impl.NotificationPlugin;
import io.vertigo.social.mail.Mail;
import io.vertigo.social.mail.MailManager;

/**
 *
 * @author dt
 *
 */
public class MailNotificationPlugin implements NotificationPlugin {
	private static final String DT_MAIL = "Direction Technique<direction.technique@yopmail.com>";
	private final MailManager mailManager;

	@Inject
	public MailNotificationPlugin(final MailManager mailManager) {
		this.mailManager = mailManager;
	}

	@Override
	public void sendMessage(final String message) {
		final Mail mail = Mail.builder()
				.from(DT_MAIL)
				.to(DT_MAIL)
				.withSubject("Message from MailNotificationPlugin")
				.replyTo(DT_MAIL)
				.withTextContent(message)
				.build();

		mailManager.sendMail(mail);
	}

	@Override
	public String getChannel() {
		return "mail";
	}
}
