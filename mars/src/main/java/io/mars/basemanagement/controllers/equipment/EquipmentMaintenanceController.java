package io.mars.basemanagement.controllers.equipment;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import io.mars.basemanagement.domain.Equipment;
import io.mars.basemanagement.services.equipment.EquipmentServices;
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
	@Inject
	private EquipmentServices equipmentServices;

	private final ViewContextKey<Equipment> equipmentKey = ViewContextKey.of("equipment");
	private final ViewContextKey<Ticket> ticketsKey = ViewContextKey.of("tickets");
	private final ViewContextKey<TicketStatus> ticketStatusKey = ViewContextKey.of("ticketStatus");

	@GetMapping("/{equipmentId}")
	public void initContext(final ViewContext viewContext, @PathVariable("equipmentId") final Long equipmentId) {
		viewContext.publishMdl(ticketStatusKey, TicketStatus.class, null); //all
		viewContext.publishDtList(ticketsKey, ticketServices.getTicketsByEquipment(equipmentId));
		viewContext.publishDto(equipmentKey, equipmentServices.get(equipmentId));
		//---
		toModeReadOnly();
	}

}
