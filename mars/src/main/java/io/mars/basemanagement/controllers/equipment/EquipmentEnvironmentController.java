package io.mars.basemanagement.controllers.equipment;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import io.mars.basemanagement.services.equipment.EquipmentEnvironmentServices;
import io.vertigo.ui.core.ViewContext;
import io.vertigo.ui.core.ViewContextKey;
import io.vertigo.ui.impl.springmvc.controller.AbstractVSpringMvcController;

@Controller
@RequestMapping("/basemanagement/equipment/environment")
public class EquipmentEnvironmentController extends AbstractVSpringMvcController {

	@Inject
	private EquipmentEnvironmentServices equipmentEnvironmentServices;
	@Inject
	private EquipmentDetailController equipmentDetailController;

	private static final ViewContextKey<Integer> TemperatureTotalMeasured = ViewContextKey.of("TemperatureTotalMeasured");

	@GetMapping("/{equipmentId}")
	public void initContext(final ViewContext viewContext, @PathVariable("equipmentId") final Long equipmentId) {
		//--
		equipmentDetailController.initCommonContext(viewContext, equipmentId);
		viewContext.publishRef(TemperatureTotalMeasured, equipmentEnvironmentServices.getTotalTemperatureMeasured());
		//---
		toModeReadOnly();
	}

}
