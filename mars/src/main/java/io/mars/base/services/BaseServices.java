package io.mars.base.services;

import io.mars.base.domain.Base;
import io.vertigo.core.component.Component;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.domain.model.DtListState;

public interface BaseServices extends Component {

	DtList<Base> getBases(DtListState dtListState);

	void save(Base base);

	Base get(Long baseId);
}
