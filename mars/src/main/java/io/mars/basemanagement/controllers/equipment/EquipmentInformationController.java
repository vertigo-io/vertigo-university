package io.mars.basemanagement.controllers.equipment;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.mars.basemanagement.domain.Equipment;
import io.mars.basemanagement.services.equipment.EquipmentServices;
import io.vertigo.ui.core.ViewContext;
import io.vertigo.ui.core.ViewContextKey;
import io.vertigo.ui.impl.springmvc.argumentresolvers.ViewAttribute;
import io.vertigo.ui.impl.springmvc.controller.AbstractVSpringMvcController;

@Controller
@RequestMapping("/basemanagement/equipment/information")
public class EquipmentInformationController extends AbstractVSpringMvcController {

	@Inject
	private EquipmentServices equipmentServices;

	private final ViewContextKey<Equipment> equipmentKey = ViewContextKey.of("equipment");

	@GetMapping("/{equipmentId}")
	public void initContext(final ViewContext viewContext, @PathVariable("equipmentId") final Long equipmentId) {
		viewContext.publishDto(equipmentKey, equipmentServices.get(equipmentId));
		//---
		toModeReadOnly();
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
		return "redirect:/basemanagement/equipment/information/" + equipment.getEquipmentId();
	}

}
