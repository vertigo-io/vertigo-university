package io.mars.maintenance.controllers.ticket;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.mars.maintenance.domain.Ticket;
import io.mars.maintenance.domain.TicketStatus;
import io.mars.maintenance.domain.WorkOrder;
import io.mars.maintenance.domain.WorkOrderStatus;
import io.mars.maintenance.services.ticket.TicketServices;
import io.mars.maintenance.services.workorder.WorkOrderServices;
import io.vertigo.ui.core.ViewContext;
import io.vertigo.ui.core.ViewContextKey;
import io.vertigo.ui.impl.springmvc.argumentresolvers.ViewAttribute;
import io.vertigo.ui.impl.springmvc.controller.AbstractVSpringMvcController;

@Controller
@RequestMapping("/maintenance/ticket")
public class TicketDetailController extends AbstractVSpringMvcController {

	@Inject
	private TicketServices ticketServices;
	@Inject
	private WorkOrderServices workOrderServices;

	private final ViewContextKey<Ticket> ticketKey = ViewContextKey.of("ticket");
	private final ViewContextKey<WorkOrder> workOrdersKey = ViewContextKey.of("workOrders");

	@GetMapping("/new")
	public void initContext(final ViewContext viewContext) {
		loadLists(viewContext);
		//---
		viewContext.publishDto(ticketKey, new Ticket());
		//---
		toModeCreate();
	}

	@GetMapping("/{ticketId}")
	public void initContext(final ViewContext viewContext, @PathVariable("ticketId") final Long ticketId) {
		loadLists(viewContext);
		//---
		viewContext.publishDto(ticketKey, ticketServices.getTicketFromId(ticketId));
		viewContext.publishDtList(workOrdersKey, workOrderServices.getWorkOrdersByTicketId(ticketId));
		//---
		toModeReadOnly();
	}

	private void loadLists(final ViewContext viewContext) {
		viewContext.publishMdl(ViewContextKey.of("ticketStatus"), TicketStatus.class, null);
		viewContext.publishMdl(ViewContextKey.of("workOrderStatus"), WorkOrderStatus.class, null);
	}

	@PostMapping("/_edit")
	public void doEdit() {
		toModeEdit();
	}

	@PostMapping("/_cancel")
	public void doCancel() {
		toModeReadOnly();
	}

	@PostMapping("/_save")
	public String doSave(@ViewAttribute("ticket") final Ticket ticket) {
		ticketServices.save(ticket);
		return "redirect:/maintenance/ticket/" + ticket.getTicketId();
	}

	@PostMapping("/_create")
	public String doCreate(@ViewAttribute("ticket") final Ticket ticket) {
		ticketServices.createTicket(ticket);
		return "redirect:/maintenance/ticket/" + ticket.getTicketId();
	}

}
