package io.mars.command.webservices.bot;

import javax.inject.Inject;

import io.mars.command.services.bot.generation.BotCommandGenerationServices;
import io.vertigo.vega.webservice.WebServices;
import io.vertigo.vega.webservice.stereotype.AnonymousAccessAllowed;
import io.vertigo.vega.webservice.stereotype.POST;
import io.vertigo.vega.webservice.stereotype.PathPrefix;
import io.vertigo.vega.webservice.stereotype.SessionLess;

@PathPrefix("/vertigo/commands/bot")
public class BotCommandWebServices implements WebServices {

	@Inject
	private BotCommandGenerationServices commandGenerationServices;

	@POST("/_generateDomain")
	@AnonymousAccessAllowed
	@SessionLess
	public String generateDomain() {
		return commandGenerationServices.generateRasaDomain();
	}

	@POST("/_generateStories")
	@AnonymousAccessAllowed
	@SessionLess
	public String generateStories() {
		return commandGenerationServices.generateRasaStories();
	}

	@POST("/_generateNlu")
	@AnonymousAccessAllowed
	@SessionLess
	public String generateNlu() {
		return commandGenerationServices.generateNlu();
	}

}
