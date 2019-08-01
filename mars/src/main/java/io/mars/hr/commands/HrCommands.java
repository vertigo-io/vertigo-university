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

	@Command(
			handle = "/hr/baseManager",
			description = "Find the manager of a base",
			questions = { "Who is the manager of base {0}" })
	public CommandResponse<Optional<Person>> findBaseManager(final GenericUID<Base> baseUID) {
		final Long baseId = (Long) baseUID.getId();
		final Optional<Person> managerOpt = missionServices.getBaseManager(baseId);
		return CommandResponse.<Optional<Person>> builder()
				.withStatus(CommandResponseStatus.OK)
				.withDisplay(managerOpt.isPresent() ? "Manager is " + managerOpt.get().getFullName() : "There is no manager")
				.withPayload(managerOpt)
				.withTargetUrl(managerOpt.isPresent() ? "/hr/person/" + managerOpt.get().getPersonId() : null)
				.build();
	}

	@Command(
			handle = "/m/createPerson",
			description = "Creates a person",
			questions = {
					"Create a person with firstname {0} and lastname {1}",
					"Create a person {0} {1}" })
	public CommandResponse<Person> createPerson(final String firstName, final String lastName) {
		return CommandResponse.<Person> builder()
				.withStatus(CommandResponseStatus.OK)
				.withDisplay("person ok")
				.build();
	}

}
