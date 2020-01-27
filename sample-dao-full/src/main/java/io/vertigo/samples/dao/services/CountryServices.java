package io.vertigo.samples.dao.services;

import io.vertigo.datamodel.structure.model.DtList;
import io.vertigo.datastore.impl.dao.StoreServices;
import io.vertigo.samples.dao.domain.Country;

public interface CountryServices extends StoreServices {

	DtList<Country> getCountriesByName(String prefix);

}
