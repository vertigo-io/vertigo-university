package io.mars.maintenance.services.ticket;

import io.mars.maintenance.domain.Ticket;
import io.vertigo.commons.eventbus.Event;
import io.vertigo.lang.Assertion;

/**
 * This class defines the event that is emitted when the store deals with an object identified by an uri.
 *
 * @author pchretien
 */
public final class TicketEvent implements Event {
	/**
	 * Type of event.
	 */
	public enum Type {
		/** Creation. */
		CREATE,
		/** Closing. */
		CLOSE
	}

	private final Type type;
	private final Ticket ticket;

	/**
	 * Constructor.
	 * @param type Store type
	 * @param ticket The ticket
	 */
	public TicketEvent(final Type type, final Ticket ticket) {
		Assertion.checkNotNull(type);
		Assertion.checkNotNull(ticket);
		//-----
		this.type = type;
		this.ticket = ticket;
	}

	/**
	 * @return Ticket The ticket
	 */
	public Ticket getTicket() {
		return ticket;
	}

	/**
	 * @return Store type
	 */
	public Type getType() {
		return type;
	}
}
