package io.vertigo.samples.vega.webservices;

import io.vertigo.vega.webservice.WebServices;
import io.vertigo.vega.webservice.stereotype.AccessTokenConsume;
import io.vertigo.vega.webservice.stereotype.AccessTokenMandatory;
import io.vertigo.vega.webservice.stereotype.AccessTokenPublish;
import io.vertigo.vega.webservice.stereotype.AnonymousAccessAllowed;
import io.vertigo.vega.webservice.stereotype.POST;
import io.vertigo.vega.webservice.stereotype.PathPrefix;

@PathPrefix("/tokens")
public class TokenWebServices implements WebServices {

	@AccessTokenPublish
	@AnonymousAccessAllowed
	@POST("/")
	public String getAccessToken() {
		return "Token generated";
	}

	@AccessTokenMandatory
	@AnonymousAccessAllowed
	@POST("/test")
	public String testAccessToken() {
		return "Token tested";
	}

	@AccessTokenConsume
	@AnonymousAccessAllowed
	@POST("/invalidate")
	public String consumeAccessToken() {
		return "Token consumed";
	}
}
