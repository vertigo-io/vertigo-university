package io.mars.base.services;

import javax.inject.Inject;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.domain.model.DtListState;
import io.vertigo.dynamo.criteria.Criterions;
import io.mars.dao.base.domain.*;
import io.mars.base.domain.*;

import io.vertigo.commons.transaction.Transactional;

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
