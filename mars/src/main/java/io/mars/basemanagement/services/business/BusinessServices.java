package io.mars.basemanagement.services.business;

import javax.inject.Inject;

import io.mars.basemanagement.dao.BusinessDAO;
import io.mars.basemanagement.domain.Business;
import io.vertigo.commons.transaction.Transactional;
import io.vertigo.core.component.Component;
import io.vertigo.dynamo.criteria.Criterions;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.domain.model.DtListState;

@Transactional
public class BusinessServices implements Component {

	@Inject
	private BusinessDAO businessDAO;

	public Business get(final Long businessId) {
		return businessDAO.get(businessId);
	}

	public void save(final Business business) {
		businessDAO.save(business);
	}

	public DtList<Business> getBusinesss(final DtListState dtListState) {
		return businessDAO.findAll(Criterions.alwaysTrue(), dtListState);
	}
}
