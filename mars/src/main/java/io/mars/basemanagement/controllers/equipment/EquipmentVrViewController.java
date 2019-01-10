package io.mars.basemanagement.controllers.equipment;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import io.vertigo.ui.core.ViewContext;
import io.vertigo.ui.impl.springmvc.controller.AbstractVSpringMvcController;

@Controller
@RequestMapping("/basemanagement/equipment/vr")
public class EquipmentVrViewController extends AbstractVSpringMvcController {

	@Inject
	private EquipmentDetailController equipmentDetailController;

	@GetMapping("/{equipmentId}")
	public void initContext(final ViewContext viewContext, @PathVariable("equipmentId") final Long equipmentId) {
		equipmentDetailController.initCommonContext(viewContext, equipmentId);
		toModeReadOnly();
	}

}
