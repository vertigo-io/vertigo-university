package io.mars.basemanagement.services.base;

import javax.inject.Inject;

import io.mars.basemanagement.dao.BaseDAO;
import io.mars.basemanagement.domain.Base;
import io.vertigo.commons.transaction.Transactional;
import io.vertigo.core.component.Component;
import io.vertigo.dynamo.criteria.Criterions;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.domain.model.DtListState;

@Transactional
public class BaseServices implements Component {

	@Inject
	private BaseDAO baseDAO;

	public Base get(final Long baseId) {
		return baseDAO.get(baseId);
	}

	public void save(final Base base) {
		baseDAO.save(base);
	}

	public DtList<Base> getBases(final DtListState dtListState) {
		return baseDAO.findAll(Criterions.alwaysTrue(), dtListState.getMaxRows().orElse(50));
	}
}
