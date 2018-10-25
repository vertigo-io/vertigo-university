package io.mars.base.services;

import javax.inject.Inject;

import io.mars.base.dao.BaseDAO;
import io.mars.base.domain.Base;
import io.vertigo.commons.transaction.Transactional;
import io.vertigo.dynamo.criteria.Criterions;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.domain.model.DtListState;

@Transactional
public class BaseServicesImpl implements BaseServices {

	@Inject
	private BaseDAO baseDAO;

	@Override
	public Base get(final Long baseId) {
		return baseDAO.get(baseId);
	}

	@Override
	public void save(final Base base) {
		baseDAO.save(base);
	}

	@Override
	public DtList<Base> getBases(final DtListState dtListState) {
		return baseDAO.findAll(Criterions.alwaysTrue(), dtListState.getMaxRows().orElse(50));
	}
}
