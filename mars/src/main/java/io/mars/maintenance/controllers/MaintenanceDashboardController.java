package io.mars.maintenance.controllers;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.mars.maintenance.domain.Ticket;
import io.mars.maintenance.domain.TicketStatus;
import io.mars.maintenance.domain.WorkOrderStatus;
import io.mars.maintenance.services.ticket.TicketServices;
import io.vertigo.ui.core.ViewContext;
import io.vertigo.ui.core.ViewContextKey;
import io.vertigo.ui.impl.springmvc.controller.AbstractVSpringMvcController;

@Controller
@RequestMapping("/maintenance/dashboard")
public class MaintenanceDashboardController extends AbstractVSpringMvcController {

	@Inject
	private TicketServices ticketServices;

	private static final ViewContextKey<Ticket> lastTickets = ViewContextKey.of("lastTickets");

	@GetMapping("/")
	public void initContext(final ViewContext viewContext) {
		viewContext.publishMdl(ViewContextKey.of("ticketStatus"), TicketStatus.class, null);
		viewContext.publishMdl(ViewContextKey.of("workOrderStatus"), WorkOrderStatus.class, null);
		//---
		viewContext.publishDtList(lastTickets, ticketServices.getLastestTickets());
		//---
		toModeReadOnly();
	}

}
