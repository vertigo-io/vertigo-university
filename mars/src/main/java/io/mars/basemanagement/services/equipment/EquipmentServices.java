package io.mars.basemanagement.services.equipment;

import java.util.List;

import javax.inject.Inject;

import io.mars.basemanagement.BasemanagementPAO;
import io.mars.basemanagement.dao.EquipmentDAO;
import io.mars.basemanagement.domain.Equipment;
import io.mars.basemanagement.domain.EquipmentOverview;
import io.mars.basemanagement.search.EquipmentIndex;
import io.vertigo.commons.transaction.Transactional;
import io.vertigo.core.component.Component;
import io.vertigo.dynamo.collections.model.FacetedQueryResult;
import io.vertigo.dynamo.collections.model.SelectedFacetValues;
import io.vertigo.dynamo.criteria.Criterions;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.domain.model.DtListState;
import io.vertigo.dynamo.search.model.SearchQuery;
import io.vertigo.lang.Assertion;

@Transactional
public class EquipmentServices implements Component {

	@Inject
	private BasemanagementPAO basemanagementPAO;

	@Inject
	private EquipmentDAO equipmentDAO;

	public Equipment get(final Long equipmentId) {
		return equipmentDAO.get(equipmentId);
	}

	public void save(final Equipment equipment) {
		equipmentDAO.save(equipment);
	}

	public DtList<Equipment> getEquipments(final DtListState dtListState) {
		return equipmentDAO.findAll(Criterions.alwaysTrue(), dtListState.getMaxRows().orElse(50));
	}

	public DtList<EquipmentIndex> getEquipmentIndex(final List<Long> equipmentIds) {
		return basemanagementPAO.loadEquipmentIndex(equipmentIds);
	}

	public FacetedQueryResult<EquipmentIndex, SearchQuery> searchEquipments(final String criteria, final SelectedFacetValues selectedFacetValues, final DtListState dtListState) {
		final SearchQuery searchQuery = equipmentDAO.createSearchQueryBuilderEquipment(criteria, selectedFacetValues).build();
		return equipmentDAO.loadList(searchQuery, dtListState);
	}

	public DtList<EquipmentOverview> getEquipmentOverviewByBaseId(final Long baseId) {
		Assertion.checkNotNull(baseId);
		//---
		return basemanagementPAO.getEquipmentsOverview(baseId);
	}

	public DtList<Equipment> getLastPurchasedEquipmentsByBase(final Long baseId) {
		Assertion.checkNotNull(baseId);
		//---
		return equipmentDAO.getLastPurchasedEquipmentsByBaseId(baseId);
	}

	public DtList<Equipment> getEquipmentByBase(final String baseCode) {
		Assertion.checkNotNull(baseCode);
		//---
		return equipmentDAO.getEquipmentsByBaseCode(baseCode);
	}
}
