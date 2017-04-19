package io.vertigo.pandora.webservices.masterdata;

import javax.inject.Inject;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.pandora.domain.masterdata.Gender;
import io.vertigo.pandora.services.masterdata.MasterdataServices;
import io.vertigo.vega.webservice.WebServices;
import io.vertigo.vega.webservice.stereotype.AnonymousAccessAllowed;
import io.vertigo.vega.webservice.stereotype.GET;
import io.vertigo.vega.webservice.stereotype.PathPrefix;

@PathPrefix("/masterdata")
public class MasterdataWebservices implements WebServices {

	@Inject
	private MasterdataServices masterdataServices;
	
	@AnonymousAccessAllowed
	@GET("/genders")
	public DtList<Gender> getGenderList() {
		return masterdataServices.getGenderList();
	}
}
