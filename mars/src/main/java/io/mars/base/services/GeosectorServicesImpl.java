package io.mars.base.services;

import javax.inject.Inject;

import io.mars.base.dao.GeosectorDAO;
import io.mars.base.domain.Geosector;
import io.vertigo.commons.transaction.Transactional;
import io.vertigo.dynamo.criteria.Criterions;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.domain.model.DtListState;

@Transactional
public class GeosectorServicesImpl implements GeosectorServices {

	@Inject
	private GeosectorDAO geosectorDAO;

	@Override
	public Geosector get(final Long geosectorId) {
		return geosectorDAO.get(geosectorId);
	}

	@Override
	public void save(final Geosector geosector) {
		geosectorDAO.save(geosector);
	}

	@Override
	public DtList<Geosector> getGeosectors(final DtListState dtListState) {
		return geosectorDAO.findAll(Criterions.alwaysTrue(), dtListState.getMaxRows().orElse(50));
	}
}
