package io.mars.maintenance.controllers.workorder;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.mars.maintenance.domain.WorkOrder;
import io.mars.maintenance.domain.WorkOrderStatus;
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

	private static final ViewContextKey<WorkOrder> workOrderKey = ViewContextKey.of("workOrder");
	private static final ViewContextKey<WorkOrderStatus> workOrderStatusKey = ViewContextKey.of("workOrderStatus");
	private static final ViewContextKey<String> successCallbackKey = ViewContextKey.of("successCallback");
	private static final ViewContextKey<Boolean> closeSuccessKey = ViewContextKey.of("closeSuccess");

	@GetMapping("/new")
	public void initContextCreation(final ViewContext viewContext, @RequestParam("ticketId") final Long ticketId, @RequestParam("successCallback") final String successCallback) {
		final WorkOrder workOrder = new WorkOrder();
		workOrder.ticket().setId(ticketId);
		//---
		viewContext.publishDto(workOrderKey, workOrder);
		viewContext.publishRef(successCallbackKey, successCallback);
		viewContext.publishRef(closeSuccessKey, Boolean.FALSE);
		//---
		toModeCreate();
	}

	@GetMapping("/{workOrderId}")
	public void initContext(final ViewContext viewContext, @PathVariable("workOrderId") final Long workOrderId, @RequestParam("successCallback") final String successCallback) {
		viewContext.publishDto(workOrderKey, workOrderServices.getWorkOrderFromId(workOrderId));
		viewContext.publishRef(successCallbackKey, successCallback);
		viewContext.publishRef(closeSuccessKey, Boolean.FALSE);
		viewContext.publishMdl(workOrderStatusKey, WorkOrderStatus.class, null);//all
		//---
		toModeEdit();
	}

	@PostMapping("/_save")
	public void doSave(final ViewContext viewContext, @ViewAttribute("workOrder") final WorkOrder workOrder) {
		workOrderServices.save(workOrder);
		viewContext.publishRef(closeSuccessKey, Boolean.TRUE);
	}

	@PostMapping("/_create")
	public void doCreate(final ViewContext viewContext, @ViewAttribute("workOrder") final WorkOrder workOrder) {
		//workOrderServices.save(workOrder);
		workOrderServices.createWorkOrder(workOrder);
		viewContext.publishRef(closeSuccessKey, Boolean.TRUE);
	}

}
