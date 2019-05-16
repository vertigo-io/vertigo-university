package io.mars.maintenance.services.ticket;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

import javax.inject.Inject;

import io.mars.maintenance.domain.Ticket;
import io.vertigo.adapters.ifttt.IftttAdapter;
import io.vertigo.adapters.ifttt.MakerEvent;
import io.vertigo.commons.eventbus.EventBusSubscribed;
import io.vertigo.core.component.Component;
import io.vertigo.core.param.ParamManager;

public class SlackTicketEventSubscriber implements Component {

	private final String iftttApiKey;
	private final String iftttApiUrl;

	@Inject
	public SlackTicketEventSubscriber(final ParamManager paramManager) {
		iftttApiKey = paramManager.getParam("iftttApiKey").getValue();
		iftttApiUrl = paramManager.getParam("iftttApiUrl").getValue();

	}

	@EventBusSubscribed
	public void onTicketEvent(final TicketEvent ticketEvent) {
		if (ticketEvent.getType() == TicketEvent.Type.CREATE) {
			final Ticket ticket = ticketEvent.getTicket();

			final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");

			final MakerEvent event = new MakerEvent("mars_ticket_created");

			// Put the ticket code in IFTTT value1
			event.getEventMetadatas().setValue1(ticket.getCode());
			// Put the tocket title in IFTTT value2
			event.getEventMetadatas().setValue2(ticket.getTitle());
			// Put the ticket creation date in IFTTT value 3
			event.getEventMetadatas().setValue3(ticket.getDateCreated().format(formatter));

			IftttAdapter.sendMakerEvent(event, iftttApiUrl, iftttApiKey, Optional.empty(), Optional.empty());
		}
	}

}
