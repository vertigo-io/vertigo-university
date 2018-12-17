package io.mars.basemanagement.services.base;

import java.util.List;

import javax.inject.Inject;

import io.mars.basemanagement.BasemanagementPAO;
import io.mars.basemanagement.dao.BaseDAO;
import io.mars.basemanagement.dao.GeosectorDAO;
import io.mars.basemanagement.domain.Base;
import io.mars.basemanagement.domain.BasesSummary;
import io.mars.basemanagement.domain.Geosector;
import io.mars.basemanagement.search.BaseIndex;
import io.vertigo.commons.transaction.Transactional;
import io.vertigo.core.component.Component;
import io.vertigo.dynamo.criteria.Criterions;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.domain.model.DtListState;

@Transactional
public class BaseServices implements Component {

	@Inject
	private BaseDAO baseDAO;
	@Inject
	private GeosectorDAO geosectorDAO;

	@Inject
	private BasemanagementPAO basemanagementPAO;

	public Base get(final Long baseId) {
		return baseDAO.get(baseId);
	}

	public void save(final Base base) {
		baseDAO.save(base);
	}

	public DtList<Base> getBases(final DtListState dtListState) {
		return baseDAO.findAll(Criterions.alwaysTrue(), dtListState.getMaxRows().orElse(50));
	}

	public DtList<Geosector> getAllGeosectors() {
		return geosectorDAO.findAll(Criterions.alwaysTrue(), Integer.MAX_VALUE);
	}

	public DtList<BaseIndex> getBaseIndex(final List<Long> baseIds) {
		return basemanagementPAO.loadBaseIndex(baseIds);
	}

	public BasesSummary getBaseSummary() {
		return basemanagementPAO.getBasesSummary();
	}
}
