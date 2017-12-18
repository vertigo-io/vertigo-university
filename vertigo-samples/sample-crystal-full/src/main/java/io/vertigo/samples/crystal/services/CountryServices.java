package io.vertigo.samples.crystal.services;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.store.StoreServices;
import io.vertigo.samples.crystal.domain.Country;

public interface CountryServices extends StoreServices {

	DtList<Country> getCountriesByName(String prefix);

}
