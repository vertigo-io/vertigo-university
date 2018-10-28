package io.mars.humanresources.services.mission;

import javax.inject.Inject;

import io.mars.humanresources.dao.MissionDAO;
import io.mars.humanresources.domain.Mission;
import io.vertigo.commons.transaction.Transactional;
import io.vertigo.core.component.Component;
import io.vertigo.dynamo.criteria.Criterions;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.domain.model.DtListState;

@Transactional
public class MissionServices implements Component {

	@Inject
	private MissionDAO missionDAO;

	public Mission get(final Long missionId) {
		return missionDAO.get(missionId);
	}

	public void save(final Mission mission) {
		missionDAO.save(mission);
	}

	public DtList<Mission> getMissions(final DtListState dtListState) {
		return missionDAO.findAll(Criterions.alwaysTrue(), dtListState.getMaxRows().orElse(50));
	}
}
