package io.vertigo.samples.vega.webservices;

import io.vertigo.vega.webservice.WebServices;
import io.vertigo.vega.webservice.stereotype.AnonymousAccessAllowed;
import io.vertigo.vega.webservice.stereotype.GET;
import io.vertigo.vega.webservice.stereotype.PathPrefix;

@PathPrefix("/hello")
public class HelloWebServices implements WebServices {

	@AnonymousAccessAllowed
	@GET("/")
	public String hello() {
		return "hello world";
	}

}
