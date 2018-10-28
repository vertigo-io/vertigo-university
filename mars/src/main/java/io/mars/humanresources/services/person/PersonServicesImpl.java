package io.mars.humanresources.services.person;

import javax.inject.Inject;

import io.mars.humanresources.dao.PersonDAO;
import io.mars.humanresources.domain.Person;
import io.vertigo.commons.transaction.Transactional;
import io.vertigo.core.component.Component;
import io.vertigo.dynamo.criteria.Criterions;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.domain.model.DtListState;

@Transactional
public class PersonServicesImpl implements Component {

	@Inject
	private PersonDAO personDAO;

	public Person getPerson(final Long personId) {
		return personDAO.get(personId);
	}

	public void updatePerson(final Person person) {
		personDAO.update(person);
	}

	public Person createPerson(final Person person) {
		return personDAO.create(person);
	}

	public Person initPerson() {
		return new Person();
	}

	public DtList<Person> getPersons(final DtListState dtListState) {
		return personDAO.findAll(Criterions.alwaysTrue(), dtListState.getMaxRows().orElse(50));
	}
}
