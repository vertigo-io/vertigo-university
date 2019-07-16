package io.mars.hr.commands;

import java.util.Optional;

import javax.inject.Inject;

import io.mars.basemanagement.domain.Base;
import io.mars.hr.domain.Person;
import io.mars.hr.services.mission.MissionServices;
import io.vertigo.commons.command.Command;
import io.vertigo.commons.command.CommandResponse;
import io.vertigo.commons.command.CommandResponseStatus;
import io.vertigo.commons.command.GenericUID;
import io.vertigo.core.component.Component;

public class HrCommands implements Component {

	@Inject
	private MissionServices missionServices;

	@Command(handle = "/hr/baseManager", description = "Find the manager of a base")
	public CommandResponse<Optional<Person>> checkTicket(final GenericUID<Base> baseUID) {
		final Long baseId = (Long) baseUID.getId();
		final Optional<Person> managerOpt = missionServices.getBaseManager(baseId);
		return CommandResponse.<Optional<Person>> builder()
				.withStatus(CommandResponseStatus.OK)
				.withDisplay(managerOpt.isPresent() ? "Manager is " + managerOpt.get().getFullName() : "There is no manager")
				.withPayload(managerOpt)
				.withTargetUrl(managerOpt.isPresent() ? "/hr/person/" + managerOpt.get().getPersonId() : null)
				.build();
	}

}
