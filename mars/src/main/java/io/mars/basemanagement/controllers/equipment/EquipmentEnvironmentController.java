package io.mars.basemanagement.controllers.equipment;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import io.mars.basemanagement.domain.Equipment;
import io.mars.basemanagement.services.equipment.EquipmentEnvironmentServices;
import io.mars.basemanagement.services.equipment.EquipmentServices;
import io.mars.catalog.domain.EquipmentType;
import io.vertigo.database.timeseries.TimeSeriesDataBaseManager;
import io.vertigo.ui.core.ViewContext;
import io.vertigo.ui.core.ViewContextKey;
import io.vertigo.ui.impl.springmvc.controller.AbstractVSpringMvcController;

@Controller
@RequestMapping("/basemanagement/equipment/environment")
public class EquipmentEnvironmentController extends AbstractVSpringMvcController {

	@Inject
	private TimeSeriesDataBaseManager timeSeriesDataBaseManager;
	@Inject
	private EquipmentServices equipmentServices;
	@Inject
	private EquipmentEnvironmentServices equipmentEnvironmentServices;

	private final ViewContextKey<Equipment> equipmentKey = ViewContextKey.of("equipment");
	private final ViewContextKey<EquipmentType> equipmentTypeKey = ViewContextKey.of("equipmentType");
	private final ViewContextKey<Double> TemperatureTotalMeasured = ViewContextKey.of("TemperatureTotalMeasured");

	@GetMapping("/{equipmentId}")
	public void initContext(final ViewContext viewContext, @PathVariable("equipmentId") final Long equipmentId) {
		//--
		final Equipment equipment = equipmentServices.get(equipmentId);
		viewContext.publishDto(equipmentKey, equipment);
		viewContext.publishDto(equipmentTypeKey, equipment.equipmentType().get());
		viewContext.publishRef(TemperatureTotalMeasured, equipmentEnvironmentServices.getTotalTemperatureMeasured());
		//---
		toModeReadOnly();
	}

}
