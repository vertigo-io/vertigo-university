package io.mars.home.controllers;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.mars.basemanagement.domain.Base;
import io.mars.basemanagement.domain.BasesSummary;
import io.mars.basemanagement.services.base.BaseServices;
import io.mars.maintenance.domain.WorkOrder;
import io.mars.maintenance.domain.WorkOrderStatus;
import io.mars.maintenance.services.workorder.WorkOrderServices;
import io.vertigo.dynamo.domain.model.DtListState;
import io.vertigo.ui.core.ViewContext;
import io.vertigo.ui.core.ViewContextKey;
import io.vertigo.ui.impl.springmvc.controller.AbstractVSpringMvcController;

@Controller
@RequestMapping("/home")
public class HomeController extends AbstractVSpringMvcController {

	private static final ViewContextKey<BasesSummary> summaryKey = ViewContextKey.of("basesSummary");
	private static final ViewContextKey<Base> basesKey = ViewContextKey.of("bases");
	private static final ViewContextKey<WorkOrder> lastWorkOrdersKey = ViewContextKey.of("lastWorkOrders");
	private static final ViewContextKey<WorkOrderStatus> workOrderStatus = ViewContextKey.of("workOrderStatus");

	@Inject
	private BaseServices baseServices;
	@Inject
	private WorkOrderServices workOrderServices;

	@GetMapping("/")
	public void initContext(final ViewContext viewContext) {
		viewContext.publishMdl(workOrderStatus, WorkOrderStatus.class, null); //all
		viewContext.publishDtList(basesKey, baseServices.getBases(DtListState.defaultOf(Base.class)));
		viewContext.publishDto(summaryKey, baseServices.getBaseSummary());
		viewContext.publishDtList(lastWorkOrdersKey, workOrderServices.getLastWorkOrders());
	}

}
