package io.mars.basemanagement.search;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.mars.basemanagement.domain.Equipment;
import io.mars.basemanagement.services.equipment.EquipmentServices;
import io.vertigo.app.Home;
import io.vertigo.commons.transaction.VTransactionManager;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.domain.model.UID;
import io.vertigo.dynamo.search.metamodel.SearchChunk;
import io.vertigo.dynamo.search.metamodel.SearchIndexDefinition;
import io.vertigo.dynamo.search.model.SearchIndex;
import io.vertigo.dynamo.task.TaskManager;
import io.vertigo.dynamox.search.AbstractSqlSearchLoader;

public final class EquipmentSearchLoader extends AbstractSqlSearchLoader<Long, Equipment, EquipmentIndex> {

	private final EquipmentServices myEquipmentServices;

	@Inject
	public EquipmentSearchLoader(final TaskManager taskManager, final VTransactionManager transactionManager, final EquipmentServices equipmentServices) {
		super(taskManager, transactionManager);
		myEquipmentServices = equipmentServices;
	}

	@Override
	public List<SearchIndex<Equipment, EquipmentIndex>> loadData(final SearchChunk<Equipment> searchChunk) {
		final SearchIndexDefinition indexDefinition = Home.getApp().getDefinitionSpace().resolve("IdxEquipment", SearchIndexDefinition.class);
		final List<Long> equipmentIds = new ArrayList<>();
		for (final UID<Equipment> uid : searchChunk.getAllUIDs()) {
			equipmentIds.add((Long) uid.getId());
		}
		final DtList<EquipmentIndex> equipmentIndexes = myEquipmentServices.getEquipmentIndex(equipmentIds);
		final List<SearchIndex<Equipment, EquipmentIndex>> equipmentSearchIndexes = new ArrayList<>(searchChunk.getAllUIDs().size());
		for (final EquipmentIndex equipmentIndex : equipmentIndexes) {
			equipmentSearchIndexes.add(SearchIndex.<Equipment, EquipmentIndex> createIndex(indexDefinition,
					UID.of(indexDefinition.getKeyConceptDtDefinition(), equipmentIndex.getEquipmentId()), equipmentIndex));
		}
		return equipmentSearchIndexes;
	}

}
