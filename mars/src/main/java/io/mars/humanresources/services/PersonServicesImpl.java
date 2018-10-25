package io.mars.humanresources.services;

import javax.inject.Inject;

import io.mars.humanresources.dao.PersonDAO;
import io.mars.humanresources.domain.Person;
import io.vertigo.commons.transaction.Transactional;
import io.vertigo.dynamo.criteria.Criterions;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.domain.model.DtListState;

@Transactional
public class PersonServicesImpl implements PersonServices {

	@Inject
	private PersonDAO personDAO;

	@Override
	public Person get(final Long personId) {
		return personDAO.get(personId);
	}

	@Override
	public void save(final Person person) {
		personDAO.save(person);
	}

	@Override
	public DtList<Person> getPersons(final DtListState dtListState) {
		return personDAO.findAll(Criterions.alwaysTrue(), dtListState.getMaxRows().orElse(50));
	}
}
