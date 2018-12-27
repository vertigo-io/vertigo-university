package io.mars.maintenance.webservices;

import javax.inject.Inject;

import io.mars.maintenance.domain.Ticket;
import io.mars.maintenance.services.ticket.TicketServices;
import io.vertigo.vega.webservice.WebServices;
import io.vertigo.vega.webservice.stereotype.AnonymousAccessAllowed;
import io.vertigo.vega.webservice.stereotype.POST;

public class TicketWebServices implements WebServices {

	@Inject
	private TicketServices ticketServices;
	
	
	@AnonymousAccessAllowed
	@POST("/ticket")
	public void createTicket(Ticket ticket) {
		ticketServices.createTicket(ticket);
	}

}
