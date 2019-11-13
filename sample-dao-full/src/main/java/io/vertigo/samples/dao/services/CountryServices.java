package io.vertigo.samples.dao.services;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.store.StoreServices;
import io.vertigo.samples.dao.domain.Country;

public interface CountryServices extends StoreServices {

	DtList<Country> getCountriesByName(String prefix);

}
