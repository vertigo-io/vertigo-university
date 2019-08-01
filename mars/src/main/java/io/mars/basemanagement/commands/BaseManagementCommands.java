package io.mars.basemanagement.commands;

import javax.inject.Inject;

import io.mars.basemanagement.services.equipment.EquipmentEnvironmentServices;
import io.vertigo.commons.command.Command;
import io.vertigo.commons.command.CommandResponse;
import io.vertigo.commons.command.CommandResponseStatus;
import io.vertigo.core.component.Component;

public class BaseManagementCommands implements Component {

	@Inject
	private EquipmentEnvironmentServices equipmentEnvironmentServices;

	@Command(
			handle = "/bm/startAlert",
			description = "Starts a general alert",
			questions = { "Start alert" })
	public CommandResponse<String> startAlert() {
		equipmentEnvironmentServices.sendBaseAlert();
		return CommandResponse.<String> builder()
				.withStatus(CommandResponseStatus.OK)
				.withDisplay("Alert started")
				.build();
	}

	@Command(
			handle = "/bm/stopAlert",
			description = "Stops a general alert",
			questions = { "Stop alert" })
	public CommandResponse<String> stopAlert() {
		equipmentEnvironmentServices.stopBaseAlert();
		return CommandResponse.<String> builder()
				.withStatus(CommandResponseStatus.OK)
				.withDisplay("Alert stopped")
				.build();
	}

	@Command(
			handle = "/bm/sendGlobalMessage",
			description = "Sends a message to everyone",
			questions = { "Send {0} to everyone" })
	public CommandResponse<String> sendGlobalMessage(final String message) {
		equipmentEnvironmentServices.displayMessage(message);
		return CommandResponse.<String> builder()
				.withStatus(CommandResponseStatus.OK)
				.withDisplay("Message " + message + " sent to everyone")
				.build();
	}

}
