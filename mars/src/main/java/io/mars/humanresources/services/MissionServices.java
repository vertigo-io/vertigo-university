package io.mars.humanresources.services;

import io.mars.humanresources.domain.Mission;
import io.vertigo.core.component.Component;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.domain.model.DtListState;

public interface MissionServices extends Component {

	DtList<Mission> getMissions(DtListState dtListState);

	void save(Mission person);

	Mission get(Long missionId);
}
