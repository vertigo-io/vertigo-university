package io.mars.basemanagement.controllers.equipment;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.mars.basemanagement.domain.Base;
import io.mars.basemanagement.domain.Business;
import io.mars.basemanagement.domain.Equipment;
import io.mars.basemanagement.domain.EquipmentMaintenanceOverview;
import io.mars.basemanagement.domain.Tag;
import io.mars.basemanagement.services.base.BaseServices;
import io.mars.basemanagement.services.equipment.EquipmentServices;
import io.mars.catalog.domain.EquipmentType;
import io.vertigo.dynamo.domain.model.DtListState;
import io.vertigo.ui.core.ViewContext;
import io.vertigo.ui.core.ViewContextKey;
import io.vertigo.ui.impl.springmvc.argumentresolvers.ViewAttribute;
import io.vertigo.ui.impl.springmvc.controller.AbstractVSpringMvcController;

@Controller
@RequestMapping("/basemanagement/equipment/")
public class EquipmentInformationController extends AbstractVSpringMvcController {

	@Inject
	private EquipmentServices equipmentServices;
	@Inject
	private BaseServices baseServices;
	@Inject
	private EquipmentDetailController equipmentDetailController;

	private static final ViewContextKey<Equipment> equipmentKey = ViewContextKey.of("equipment");
	private static final ViewContextKey<EquipmentMaintenanceOverview> equipmentMaintenanceOverview = ViewContextKey.of("equipmentMaintenanceOverview");

	@GetMapping("/new")
	public void initContext(final ViewContext viewContext) {
		loadLists(viewContext);
		//---
		viewContext.publishDto(equipmentKey, new Equipment());
		//---
		toModeCreate();
	}

	@GetMapping("/{equipmentId}")
	public void initContext(final ViewContext viewContext, @PathVariable("equipmentId") final Long equipmentId) {
		equipmentDetailController.initCommonContext(viewContext, equipmentId);
		loadLists(viewContext);
		//---
		viewContext.publishDto(equipmentKey, equipmentServices.get(equipmentId));
		viewContext.publishDto(equipmentMaintenanceOverview, equipmentServices.getMaintenanceOverviewByEquipment(equipmentId));
		//---
		toModeReadOnly();
	}

	private void loadLists(final ViewContext viewContext) {
		viewContext.publishDtList(ViewContextKey.of("bases"), baseServices.getBases(DtListState.defaultOf(Base.class)));
		//---
		viewContext.publishMdl(ViewContextKey.of("businesses"), Business.class, null);
		viewContext.publishMdl(ViewContextKey.of("equipmentTypes"), EquipmentType.class, null);
		viewContext.publishMdl(ViewContextKey.of("tags"), Tag.class, null);
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
	public String doSave(@ViewAttribute("equipment") final Equipment equipment) {
		equipmentServices.save(equipment);
		return "redirect:/basemanagement/equipment/" + equipment.getEquipmentId();
	}

	@PostMapping("/_create")
	public String doCreate(@ViewAttribute("equipment") final Equipment equipment) {
		equipmentServices.save(equipment);
		return "redirect:/basemanagement/equipment/" + equipment.getEquipmentId();
	}

}
