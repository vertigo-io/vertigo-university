package io.mars.maintenance.services.ticket;

import java.time.format.DateTimeFormatter;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.inject.Inject;

import io.mars.maintenance.domain.Ticket;
import io.vertigo.commons.daemon.DaemonScheduled;
import io.vertigo.commons.eventbus.EventBusSubscribed;
import io.vertigo.core.component.Component;
import io.vertigo.ledger.services.LedgerManager;

public class BlockchainTicketEventSubscriber implements Component {

	private final ConcurrentLinkedQueue<String> messageQueue = new ConcurrentLinkedQueue<>();
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	@Inject
	private LedgerManager ledgerManager;

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
			}
		}
	}
}
