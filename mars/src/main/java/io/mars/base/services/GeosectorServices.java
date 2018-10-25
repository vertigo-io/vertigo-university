package io.mars.base.services;

import io.mars.base.domain.Geosector;
import io.vertigo.core.component.Component;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.domain.model.DtListState;

public interface GeosectorServices extends Component {

	DtList<Geosector> getGeosectors(DtListState dtListState);

	void save(Geosector geosector);

	Geosector get(Long geosectorId);
}
