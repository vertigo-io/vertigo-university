package io.mars.humanresources.services;

import io.mars.humanresources.domain.Person;
import io.vertigo.core.component.Component;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.domain.model.DtListState;

public interface PersonServices extends Component {

	DtList<Person> getPersons(DtListState dtListState);

	void save(Person person);

	Person get(Long personId);
}
