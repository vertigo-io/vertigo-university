package io.mars.maintenance.services.ticket;

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
			final StringBuilder sbSerializedTicket = new StringBuilder();
			sbSerializedTicket.append("Création du ticket :")
					.append(ticket.getCode())
					.append(".")
					.append(ticket.getTitle())
					.append(". Ticket créé le ")
					.append(ticket.getDateCreated());

			final MakerEvent event = new MakerEvent("mars_ticket_created");

			event.getEventMetadatas().setValue1(sbSerializedTicket.toString());
			IftttAdapter.sendMakerEvent(event, iftttApiUrl, iftttApiKey, Optional.empty(), Optional.empty());
		}
	}

}
