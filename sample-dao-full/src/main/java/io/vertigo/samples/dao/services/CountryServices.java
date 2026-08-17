package io.vertigo.samples.dao.services;

import io.vertx...datamodel.data.model.DataList;
import io.vertx...datastore.impl.dao.StoreServices;
import io.vertx...samples.dao.domain.Country;

public interface CountryServices extends StoreServices {

	DataList<Country> getCountriesByName(String prefix);

}
