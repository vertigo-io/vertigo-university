package io.mars.maintenance.services.ticket;

import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

import javax.inject.Inject;

import io.mars.hr.services.person.PersonServices;
import io.mars.maintenance.domain.Ticket;
import io.vertigo.account.account.Account;
import io.vertigo.commons.daemon.DaemonScheduled;
import io.vertigo.commons.eventbus.EventBusSubscribed;
import io.vertigo.core.component.Component;
import io.vertigo.dynamo.domain.model.DtListState;
import io.vertigo.dynamo.domain.model.UID;
import io.vertigo.ledger.services.LedgerManager;
import io.vertigo.social.services.notification.Notification;
import io.vertigo.social.services.notification.NotificationServices;

public class BlockchainTicketEventSubscriber implements Component {

	private final ConcurrentLinkedQueue<String> messageQueue = new ConcurrentLinkedQueue<>();
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	@Inject
	private LedgerManager ledgerManager;
	
	@Inject
	private PersonServices personServices;
	
	@Inject
	private NotificationServices notificationServices;


	@EventBusSubscribed
	public void onTicketEvent(final TicketEvent ticketEvent) {
		if (ticketEvent.getType() == TicketEvent.Type.CREATE) {
			final Ticket ticket = ticketEvent.getTicket();
			
			String strDateCreated = ticket.getDateCreated().format(FORMATTER);
			
			final StringBuilder sbSerializedTicket = new StringBuilder();
			sbSerializedTicket.append("Création du ticket ")
					.append(ticket.getCode())
					.append(".")
					.append(ticket.getTitle())
					.append(". Ticket créé le ")
					.append(strDateCreated);

			messageQueue.add(sbSerializedTicket.toString());
			
			Notification notification = Notification.builder()
													.withSender("System")
													.withTitle("Writing new ticket to the ledger")		
													.withContent(sbSerializedTicket.toString())
													.withTTLInSeconds(600)
													.withType("MARS-TICKET-LEDGER") //should prefix by app, in case of multi-apps notifications
													.withTargetUrl("#")
													.build();
			sendNotificationToAll(notification);
		}
	}

	/**
	 * Daemon to unstack processes to end them
	 */
	@DaemonScheduled(name = "DMN_FLUSH_LEDGER_MESSAGES_TICKET", periodInSeconds = 10)
	public void pollQueue() {
		while (!messageQueue.isEmpty()) {
			final String message = messageQueue.poll();
			if (message != null) {
				ledgerManager.sendData(message);
				
				Notification notification = Notification.builder()
						.withSender("System")
						.withTitle("New ticket sucessfully written to the ledger")		
						.withContent(message)
						.withTTLInSeconds(600)
						.withType("MARS-MISSION-LEDGER") //should prefix by app, in case of multi-apps notifications
						.withTargetUrl("#")
						.build();
				sendNotificationToAll(notification);
			}
		}
	}

	private void sendNotificationToAll(final Notification notification) {
		final Set<UID<Account>> accountUIDs = personServices.getPersons(DtListState.of(null, 0))
				.stream()
				.map((person) -> UID.of(Account.class, String.valueOf(person.getPersonId())))
				.collect(Collectors.toSet());
		notificationServices.send(notification, accountUIDs);
	}

}
