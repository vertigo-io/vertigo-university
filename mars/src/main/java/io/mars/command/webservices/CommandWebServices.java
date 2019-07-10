package io.mars.command.webservices;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import io.vertigo.commons.command.CommandDefinition;
import io.vertigo.commons.command.CommandManager;
import io.vertigo.commons.command.CommandParam;
import io.vertigo.commons.command.CommandResponse;
import io.vertigo.vega.webservice.WebServices;
import io.vertigo.vega.webservice.stereotype.InnerBodyParam;
import io.vertigo.vega.webservice.stereotype.POST;
import io.vertigo.vega.webservice.stereotype.PathPrefix;

@PathPrefix("/vertigo/commands")
public class CommandWebServices implements WebServices {

	@Inject
	private CommandManager commandManager;

	@POST("/_search")
	public List<CommandUi> searchCommands(@InnerBodyParam("prefix") final Optional<String> prefixOpt) {
		return commandManager.searchCommands(prefixOpt.orElse(""))
				.stream()
				.map(CommandWebServices::toCommandUi)
				.collect(Collectors.toList());
	}

	@POST("/_execute")
	public CommandResponse executeCommand(@InnerBodyParam("command") final String command, @InnerBodyParam("params") final String[] params) {
		return commandManager.executeCommand(command, params);
	}

	private static CommandUi toCommandUi(final CommandDefinition commandDefinition) {
		final CommandUi commandUi = new CommandUi();
		commandUi.commandName = commandDefinition.getCommand();
		commandUi.descpription = commandDefinition.getDescription();
		commandUi.commandParams = commandDefinition.getParams();
		return commandUi;
	}

	public static class CommandUi {

		public String commandName;
		public String descpription;
		public List<CommandParam> commandParams;

	}

}
