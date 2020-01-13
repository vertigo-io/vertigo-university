package io.vertigo.samples.dao.services;

import io.vertigo.datastore.impl.dao.StoreServices;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.samples.dao.domain.Country;

public interface CountryServices extends StoreServices {

	DtList<Country> getCountriesByName(String prefix);

}
