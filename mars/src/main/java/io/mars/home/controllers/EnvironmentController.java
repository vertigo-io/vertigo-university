package io.mars.home.controllers;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.mars.basemanagement.services.equipment.EquipmentEnvironmentServices;
import io.vertigo.ui.core.ViewContext;
import io.vertigo.ui.core.ViewContextKey;
import io.vertigo.ui.impl.springmvc.controller.AbstractVSpringMvcController;

@Controller
@RequestMapping("/environment")
public class EnvironmentController extends AbstractVSpringMvcController {

	@Inject
	private EquipmentEnvironmentServices equipmentEnvironmentServices;

	private static final ViewContextKey<Double> lastTemperature = ViewContextKey.of("lastTemperature");
	private static final ViewContextKey<Double> lastHumidity = ViewContextKey.of("lastHumidity");
	private static final ViewContextKey<Integer> totalAlert = ViewContextKey.of("totalAlert");
	private static final ViewContextKey<String> farmsToWater = ViewContextKey.of("farmsToWater");

	@GetMapping("/")
	public void initContext(final ViewContext viewContext) {
		viewContext.publishRef(lastTemperature, equipmentEnvironmentServices.getLastTemperature());
		viewContext.publishRef(lastHumidity, equipmentEnvironmentServices.getLastHumidity());
		viewContext.publishRef(totalAlert, equipmentEnvironmentServices.getWeeklyTriggeredAlarm());
		viewContext.publishRef(farmsToWater, equipmentEnvironmentServices.actionMoistureLevel());

		toModeReadOnly();
	}

	@PostMapping("/_alert")
	public void doAlert() {
		equipmentEnvironmentServices.sendBaseAlert();
	}

	@PostMapping("/_stop")
	public void stopAlert() {
		equipmentEnvironmentServices.stopBaseAlert();
	}

	@PostMapping("/_sendMessage")
	public void doSend(final String message) {
		equipmentEnvironmentServices.displayMessage(message);
	}

	@PostMapping("/_onFan")
	public void onFan() {
		equipmentEnvironmentServices.turnOnFan();
	}

	@PostMapping("/_offFan")
	public void offFan() {
		equipmentEnvironmentServices.turnOffFan();
	}
}
