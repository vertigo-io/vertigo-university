package io.mars.base.services;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.domain.model.DtListState;
import io.mars.base.domain.*;
import io.vertigo.core.component.Component;

public interface BaseServices extends Component {

	DtList<Base> getBases(DtListState dtListState);

	void save(Base base);

	Base get(Long baseId);
}
