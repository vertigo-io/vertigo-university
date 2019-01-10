package io.mars.basemanagement.controllers.base;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import io.mars.basemanagement.domain.Equipment;
import io.mars.basemanagement.services.equipment.EquipmentEnvironmentServices;
import io.mars.basemanagement.services.equipment.EquipmentServices;
import io.vertigo.ui.core.ViewContext;
import io.vertigo.ui.core.ViewContextKey;
import io.vertigo.ui.impl.springmvc.controller.AbstractVSpringMvcController;

@Controller
@RequestMapping("/basemanagement/base/environment")
public class BaseEnvironmentController extends AbstractVSpringMvcController {

	@Inject
	private EquipmentServices equipmentServices;

	@Inject
	private EquipmentEnvironmentServices equipmentEnvironmentServices;

	@Inject
	private BaseDetailController baseDetailController;

	private final ViewContextKey<Equipment> equipmentKey = ViewContextKey.of("equipment");
	private final ViewContextKey<Double> lastTemperature = ViewContextKey.of("lastTemperature");
	private final ViewContextKey<Double> lastHumidity = ViewContextKey.of("lastHumidity");

	@GetMapping("/{baseId}")
	public void initContext(final ViewContext viewContext, @PathVariable("baseId") final Long baseId) {
		baseDetailController.initCommonContext(viewContext, baseId);
		viewContext.publishDto(equipmentKey, equipmentServices.get(baseId));
		viewContext.publishRef(lastTemperature, equipmentEnvironmentServices.getLastTemperature());
		viewContext.publishRef(lastHumidity, equipmentEnvironmentServices.getLastHumidity());
		toModeReadOnly();
	}
}
