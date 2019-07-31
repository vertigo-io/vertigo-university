package io.mars.basemanagement.controllers.base;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import io.mars.basemanagement.domain.Equipment;
import io.mars.basemanagement.domain.EquipmentOverview;
import io.mars.basemanagement.services.equipment.EquipmentServices;
import io.mars.catalog.domain.EquipmentType;
import io.mars.domain.DtDefinitions.EquipmentOverviewFields;
import io.mars.maintenance.domain.Ticket;
import io.mars.maintenance.services.ticket.TicketServices;
import io.vertigo.ui.core.ViewContext;
import io.vertigo.ui.core.ViewContextKey;
import io.vertigo.ui.impl.springmvc.controller.AbstractVSpringMvcController;

@Controller
@RequestMapping("/basemanagement/base/equipment")
public class BaseEquipmentController extends AbstractVSpringMvcController {

	@Inject
	private EquipmentServices equipmentServices;
	@Inject
	private TicketServices ticketServices;
	@Inject
	private BaseDetailController baseDetailController;

	private static final ViewContextKey<EquipmentOverview> equipmentOverviewsKey = ViewContextKey.of("equipmentOverviews");
	private static final ViewContextKey<EquipmentType> equipmentTypesKey = ViewContextKey.of("equipmentTypes");
	private static final ViewContextKey<Equipment> lastEquipmentsKey = ViewContextKey.of("lastEquipments");
	private static final ViewContextKey<Ticket> lastTicketsKey = ViewContextKey.of("lastTickets");

	@GetMapping("/{baseId}")
	public void initContext(final ViewContext viewContext, @PathVariable("baseId") final Long baseId) {
		baseDetailController.initCommonContext(viewContext, baseId);
		viewContext.publishMdl(equipmentTypesKey, EquipmentType.class, null);
		viewContext.publishDtList(equipmentOverviewsKey, EquipmentOverviewFields.businessId, equipmentServices.getEquipmentOverviewByBaseId(baseId));
		viewContext.publishDtList(lastEquipmentsKey, equipmentServices.getLastPurchasedEquipmentsByBase(baseId));
		viewContext.publishDtList(lastTicketsKey, ticketServices.getLastestTicketsByBase(baseId));
		toModeReadOnly();
	}

}
