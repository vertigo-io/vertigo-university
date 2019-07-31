package io.mars.basemanagement.controllers.equipment;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import io.mars.maintenance.domain.Ticket;
import io.mars.maintenance.domain.TicketStatus;
import io.mars.maintenance.services.ticket.TicketServices;
import io.vertigo.ui.core.ViewContext;
import io.vertigo.ui.core.ViewContextKey;
import io.vertigo.ui.impl.springmvc.controller.AbstractVSpringMvcController;

@Controller
@RequestMapping("/basemanagement/equipment/maintenance")
public class EquipmentMaintenanceController extends AbstractVSpringMvcController {

	@Inject
	private TicketServices ticketServices;

	private static final ViewContextKey<Ticket> openedTicketsKey = ViewContextKey.of("openedTickets");
	private static final ViewContextKey<Ticket> closedTicketsKey = ViewContextKey.of("closedTickets");
	private static final ViewContextKey<TicketStatus> ticketStatusKey = ViewContextKey.of("ticketStatus");

	@Inject
	private EquipmentDetailController equipmentDetailController;

	@GetMapping("/{equipmentId}")
	public void initContext(final ViewContext viewContext, @PathVariable("equipmentId") final Long equipmentId) {
		equipmentDetailController.initCommonContext(viewContext, equipmentId);
		//---
		viewContext.publishMdl(ticketStatusKey, TicketStatus.class, null); //all
		viewContext.publishDtList(openedTicketsKey, ticketServices.getOpenedTicketsByEquipment(equipmentId));
		viewContext.publishDtList(closedTicketsKey, ticketServices.getClosedTicketsByEquipment(equipmentId));
		//---
		toModeReadOnly();
	}

}
