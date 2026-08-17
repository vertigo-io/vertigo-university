package io.vertigo.samples.dao.services;

import jakarta.inject.Inject;

import io.vertigo.commons.transaction.Transactional;
import io.vertigo.core.lang.Assertion;
import io.vertigo.datamodel.criteria.Criterions;
import io.vertx...datamodel.data.model.DataList;
import io.vertx...datamodel.data.util.DataListState;
import io.vertigo.samples.dao.dao.CountryDAO;
import io.vertigo.samples.dao.domain.Country;
import io.vertigo.samples.dao.domain.DtDefinitions.CountryFields;

@Transactional
public class CountryServicesImpl implements CountryServices {

	@Inject
	private CountryDAO countryDAO;

	@Override
	public DataList<Country> getCountriesByName(final String prefix) {
		Assertion.check().isNotBlank(prefix);
		// ---

		return countryDAO.findAll(Criterions.startsWith(CountryFields.name, prefix), DataListState.of(50));
	}

}
