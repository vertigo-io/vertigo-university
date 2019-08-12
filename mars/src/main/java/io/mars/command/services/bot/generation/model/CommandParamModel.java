package io.mars.command.services.bot.generation.model;

import io.vertigo.commons.command.CommandParam;
import io.vertigo.lang.Assertion;

public class CommandParamModel {

	private final CommandParam commandParam;

	public CommandParamModel(final CommandParam commandParam) {
		Assertion.checkNotNull(commandParam);
		//---
		this.commandParam = commandParam;
	}

	public CommandParam getCommandParam() {
		return commandParam;
	}

}
