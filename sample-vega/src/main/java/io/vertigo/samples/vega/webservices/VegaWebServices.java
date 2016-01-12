package io.vertigo.samples.vega.webservices;

import io.vertigo.vega.webservice.WebServices;
import io.vertigo.vega.webservice.stereotype.AccessTokenConsume;
import io.vertigo.vega.webservice.stereotype.AccessTokenMandatory;
import io.vertigo.vega.webservice.stereotype.AccessTokenPublish;
import io.vertigo.vega.webservice.stereotype.AnonymousAccessAllowed;
import io.vertigo.vega.webservice.stereotype.GET;
import io.vertigo.vega.webservice.stereotype.POST;
import io.vertigo.vega.webservice.stereotype.PathPrefix;

@PathPrefix("/vega")
public class VegaWebServices implements WebServices {

	@AnonymousAccessAllowed
	@GET("/")
	public String hello() {
		return "hello world";
	}

	@AccessTokenPublish
	@POST("/getToken")
	public String getAccessToken() {
		return "Token generated";
	}

	@AccessTokenMandatory
	@POST("/testToken")
	public String testAccessToken() {
		return "Token tested";
	}

	@AccessTokenConsume
	@POST("/invalidateToken")
	public String consumeAccessToken() {
		return "Token consumed";
	}

}
