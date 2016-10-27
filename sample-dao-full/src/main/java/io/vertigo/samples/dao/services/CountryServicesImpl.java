package io.vertigo.samples.dao.services;

import javax.inject.Inject;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.store.criteria.FilterCriteriaBuilder;
import io.vertigo.dynamo.transaction.Transactional;
import io.vertigo.lang.Assertion;
import io.vertigo.samples.dao.dao.CountryDAO;
import io.vertigo.samples.dao.domain.Country;

@Transactional
public class CountryServicesImpl implements CountryServices {

	@Inject
	private CountryDAO countryDAO;

	@Override
	public DtList<Country> getCountriesByName(final String prefix) {
		Assertion.checkArgNotEmpty(prefix);
		// ---

		return countryDAO.findAll(new FilterCriteriaBuilder<Country>().withPrefix("NAME", prefix).build(), 50);
	}

}
