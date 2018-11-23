package io.mars.hr.services.mission;

import java.util.Optional;

import javax.inject.Inject;

import io.mars.hr.dao.MissionDAO;
import io.mars.hr.dao.PersonDAO;
import io.mars.hr.domain.Mission;
import io.mars.hr.domain.Person;
import io.vertigo.commons.transaction.Transactional;
import io.vertigo.core.component.Component;
import io.vertigo.dynamo.criteria.Criterions;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.domain.model.DtListState;
import io.vertigo.lang.Assertion;

@Transactional
public class MissionServices implements Component {

	@Inject
	private MissionDAO missionDAO;
	@Inject
	private PersonDAO personDAO;

	public Mission get(final Long missionId) {
		return missionDAO.get(missionId);
	}

	public void save(final Mission mission) {
		missionDAO.save(mission);
	}

	public Optional<Person> getBaseManager(final Long baseId) {
		Assertion.checkNotNull(baseId);
		//---
		return personDAO.getBaseManager(baseId);
	}

	public DtList<Mission> getMissions(final DtListState dtListState) {
		return missionDAO.findAll(Criterions.alwaysTrue(), dtListState.getMaxRows().orElse(50));
	}
}
