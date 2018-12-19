package io.mars.maintenance.controllers.workorder;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.mars.maintenance.domain.WorkOrder;
import io.mars.maintenance.services.workorder.WorkOrderServices;
import io.vertigo.ui.core.ViewContext;
import io.vertigo.ui.core.ViewContextKey;
import io.vertigo.ui.impl.springmvc.argumentresolvers.ViewAttribute;
import io.vertigo.ui.impl.springmvc.controller.AbstractVSpringMvcController;

@Controller
@RequestMapping("/maintenance/workorder")
public class WorkOrderDetailController extends AbstractVSpringMvcController {

	@Inject
	private WorkOrderServices workOrderServices;

	private final ViewContextKey<WorkOrder> workOrderKey = ViewContextKey.of("workOrder");

	@GetMapping("/new")
	public void initContextCreation(final ViewContext viewContext, @RequestParam("ticketId") final Long ticketId) {
		final WorkOrder workOrder = new WorkOrder();
		workOrder.ticket().setId(ticketId);
		//---
		viewContext.publishDto(workOrderKey, workOrder);
		//---
		toModeCreate();
	}

	@GetMapping("/{workOrderId}")
	public void initContext(final ViewContext viewContext, @PathVariable("workOrderId") final Long workOrderId) {
		viewContext.publishDto(workOrderKey, workOrderServices.getWorkOrderFromId(workOrderId));
		//---
		toModeEdit();
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
	public String doSave(@ViewAttribute("workOrder") final WorkOrder workOrder) {
		workOrderServices.save(workOrder);
		return "redirect:/maintenance/workorder/" + workOrder.getWoId();
	}

	@PostMapping("/_create")
	public String doCreate(@ViewAttribute("workOrder") final WorkOrder workOrder) {
		workOrderServices.save(workOrder);
		return "redirect:/maintenance/workorder/" + workOrder.getWoId();
	}

}
