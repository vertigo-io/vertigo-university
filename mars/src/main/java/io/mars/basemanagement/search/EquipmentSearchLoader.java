package io.mars.basemanagement.search;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.mars.basemanagement.domain.Equipment;
import io.mars.basemanagement.services.equipment.EquipmentServices;
import io.vertigo.commons.transaction.VTransactionManager;
import io.vertigo.core.component.Activeable;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.domain.model.UID;
import io.vertigo.dynamo.search.SearchManager;
import io.vertigo.dynamo.search.metamodel.SearchChunk;
import io.vertigo.dynamo.search.metamodel.SearchIndexDefinition;
import io.vertigo.dynamo.search.model.SearchIndex;
import io.vertigo.dynamo.task.TaskManager;
import io.vertigo.dynamox.search.AbstractSqlSearchLoader;

public final class EquipmentSearchLoader extends AbstractSqlSearchLoader<Long, Equipment, EquipmentIndex> implements Activeable {

	private final EquipmentServices myEquipmentServices;
	private final SearchManager searchManager;
	private SearchIndexDefinition indexDefinition;

	@Inject
	public EquipmentSearchLoader(final TaskManager taskManager, final SearchManager searchManager, final VTransactionManager transactionManager, final EquipmentServices equipmentServices) {
		super(taskManager, transactionManager);
		this.searchManager = searchManager;
		myEquipmentServices = equipmentServices;
	}

	@Override
	public void start() {
		indexDefinition = searchManager.findFirstIndexDefinitionByKeyConcept(Equipment.class);
	}

	@Override
	public void stop() {
		indexDefinition = null;
	}

	@Override
	public List<SearchIndex<Equipment, EquipmentIndex>> loadData(final SearchChunk<Equipment> searchChunk) {
		final List<Long> equipmentIds = new ArrayList<>();
		for (final UID<Equipment> uri : searchChunk.getAllURIs()) {
			equipmentIds.add((Long) uri.getId());
		}
		final DtList<EquipmentIndex> equipmentIndexes = myEquipmentServices.getEquipmentIndex(equipmentIds);
		final List<SearchIndex<Equipment, EquipmentIndex>> equipmentSearchIndexes = new ArrayList<>(searchChunk.getAllURIs().size());
		for (final EquipmentIndex equipmentIndex : equipmentIndexes) {
			equipmentSearchIndexes.add(SearchIndex.<Equipment, EquipmentIndex> createIndex(indexDefinition,
					UID.of(indexDefinition.getKeyConceptDtDefinition(), equipmentIndex.getEquipmentId()), equipmentIndex));
		}
		return equipmentSearchIndexes;
	}

}
