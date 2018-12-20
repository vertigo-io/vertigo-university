package io.mars.basemanagement.controllers.base;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import io.mars.basemanagement.domain.Base;
import io.mars.basemanagement.domain.Equipment;
import io.mars.basemanagement.services.base.BaseServices;
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
	private BaseServices baseServices;

	private final ViewContextKey<Equipment> equipmentKey = ViewContextKey.of("equipment");
	private final ViewContextKey<Base> baseKey = ViewContextKey.of("base");

	@GetMapping("/{baseId}")
	public void initContext(final ViewContext viewContext, @PathVariable("baseId") final Long baseId) {
		viewContext.publishDto(baseKey, baseServices.get(baseId));
		viewContext.publishDto(equipmentKey, equipmentServices.get(baseId));
		toModeReadOnly();
	}
}
