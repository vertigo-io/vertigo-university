package io.mars.basemanagement.services.equipment;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import io.mars.basemanagement.BasemanagementPAO;
import io.mars.basemanagement.dao.EquipmentDAO;
import io.mars.basemanagement.domain.Equipment;
import io.mars.basemanagement.domain.EquipmentMaintenanceOverview;
import io.mars.basemanagement.domain.EquipmentOverview;
import io.mars.basemanagement.search.EquipmentIndex;
import io.vertigo.account.account.Account;
import io.vertigo.account.authentication.AuthenticationManager;
import io.vertigo.account.authorization.VSecurityException;
import io.vertigo.commons.impl.connectors.redis.RedisConnector;
import io.vertigo.commons.transaction.Transactional;
import io.vertigo.core.component.Component;
import io.vertigo.core.locale.MessageText;
import io.vertigo.dynamo.collections.model.FacetedQueryResult;
import io.vertigo.dynamo.collections.model.SelectedFacetValues;
import io.vertigo.dynamo.criteria.Criterions;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.domain.model.DtListState;
import io.vertigo.dynamo.domain.model.UID;
import io.vertigo.dynamo.search.model.SearchQuery;
import io.vertigo.lang.Assertion;
import io.vertigo.social.services.comment.Comment;
import io.vertigo.social.services.comment.CommentServices;

@Transactional
public class EquipmentServices implements Component {

	@Inject
	private BasemanagementPAO basemanagementPAO;
	@Inject
	private EquipmentDAO equipmentDAO;
	@Inject
	private CommentServices commentServices;
	@Inject
	private RedisConnector redisConnector;
	@Inject
	private AuthenticationManager authenticationManager;

	public Equipment get(final Long equipmentId) {
		final Equipment equipment = equipmentDAO.get(equipmentId);
		equipment.equipmentType().load();
		return equipment;
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

	public EquipmentMaintenanceOverview getMaintenanceOverviewByEquipment(final Long equipmentId) {
		Assertion.checkNotNull(equipmentId);
		//---
		return basemanagementPAO.getEquipmentMaintenanceOverview(equipmentId);
	}

	public void addCommentToEquipment(final String commentString, final Long equipmentId) {
		Assertion.checkNotNull(equipmentId);
		//

		final Account currentAccount = authenticationManager.getLoggedAccount().orElseThrow(() -> new VSecurityException(MessageText.of("No user currently logged in")));
		final UID<Account> currentAccountUID = currentAccount.getUID();

		final Comment comment = Comment.builder()
				.withAuthor(currentAccountUID)
				.withMsg(commentString)
				.build();

		final Equipment equipment = get(equipmentId);

		commentServices.publish(currentAccountUID, comment, equipment.getUID());

	}

	public List<String> getComments(final Long equipmentId) {
		return commentServices.getComments(UID.of(Equipment.class, equipmentId)).stream()
				.map((c) -> c.getMsg())
				.collect(Collectors.toList());
	}
}
