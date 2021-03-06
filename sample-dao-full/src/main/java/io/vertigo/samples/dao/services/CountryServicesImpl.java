package io.vertigo.samples.dao.services;

import javax.inject.Inject;

import io.vertigo.commons.transaction.Transactional;
import io.vertigo.core.lang.Assertion;
import io.vertigo.datamodel.criteria.Criterions;
import io.vertigo.datamodel.structure.model.DtList;
import io.vertigo.datamodel.structure.model.DtListState;
import io.vertigo.samples.dao.dao.CountryDAO;
import io.vertigo.samples.dao.domain.Country;
import io.vertigo.samples.dao.domain.DtDefinitions.CountryFields;

@Transactional
public class CountryServicesImpl implements CountryServices {

	@Inject
	private CountryDAO countryDAO;

	@Override
	public DtList<Country> getCountriesByName(final String prefix) {
		Assertion.check().isNotBlank(prefix);
		// ---

		return countryDAO.findAll(Criterions.startsWith(CountryFields.name, prefix), DtListState.of(50));
	}

}
