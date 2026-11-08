package io.vertigo.samples.vui.webservices;

import javax.inject.Inject;

import io.vertigo.datamodel.data.model.DtList;
import io.vertigo.samples.vui.domain.MovieDisplay;
import io.vertigo.samples.vui.services.MovieServices;
import io.vertigo.vega.webservice.WebServices;
import io.vertigo.vega.webservice.stereotype.GET;
import io.vertigo.vega.webservice.stereotype.PathParam;
import io.vertigo.vega.webservice.stereotype.PathPrefix;

@PathPrefix("/movies")
public class MovieWebServices implements WebServices {

	@Inject
	private MovieServices movieServices;

	@GET("/display")
	public DtList<MovieDisplay> getMoviesDisplay() {
		return movieServices.getMoviesDisplay();
	}

	@GET("/display/{couId}")
	public DtList<MovieDisplay> getMoviesDisplayByCountry(@PathParam("couId") final Long couId) {
		return movieServices.getMoviesDisplayByCountry(couId);
	}
}
