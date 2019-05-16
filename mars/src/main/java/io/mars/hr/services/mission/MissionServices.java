package io.mars.hr.services.mission;

import java.util.Optional;

import javax.inject.Inject;

import io.mars.hr.HrPAO;
import io.mars.hr.dao.MissionDAO;
import io.mars.hr.dao.PersonDAO;
import io.mars.hr.domain.Mission;
import io.mars.hr.domain.MissionDisplay;
import io.mars.hr.domain.Person;
import io.vertigo.commons.eventbus.EventBusManager;
import io.vertigo.commons.transaction.Transactional;
import io.vertigo.commons.transaction.VTransactionManager;
import io.vertigo.core.component.Component;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.lang.Assertion;

@Transactional
public class MissionServices implements Component {

	@Inject
	private MissionDAO missionDAO;
	@Inject
	private PersonDAO personDAO;
	@Inject
	private HrPAO hrPAO;
	@Inject
	private VTransactionManager transactionManager;
	@Inject
	private EventBusManager eventBusManager;

	public Mission get(final Long missionId) {
		return missionDAO.get(missionId);
	}

	public void save(final Mission mission) {
		missionDAO.save(mission);
	}

	public void createMission(final Mission mission) {
		Assertion.checkArgument(mission.getMissionId() == null, "No id should be provided for a new ticket");
		//---
		missionDAO.save(mission);
		// Loading person, base and business entities for the mission event
		mission.person().load();
		mission.base().load();
		mission.business().load();
		transactionManager.getCurrentTransaction()
				.addAfterCompletion(txCommited -> {
					if (txCommited) {
						eventBusManager.post(new MissionEvent(mission));
					}
				});
	}

	public Optional<Person> getBaseManager(final Long baseId) {
		Assertion.checkNotNull(baseId);
		//---
		return personDAO.getBaseManager(baseId);
	}

	public DtList<MissionDisplay> getMissionsByPersonId(final Long personId) {
		Assertion.checkNotNull(personId);
		//---
		return hrPAO.getMissionsDisplayByPersonId(personId);
	}
}
