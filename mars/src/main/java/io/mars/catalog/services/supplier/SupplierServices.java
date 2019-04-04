package io.mars.catalog.services.supplier;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import io.mars.catalog.domain.Supplier;
import io.mars.catalog.search.SupplierSearchClient;
import io.vertigo.commons.transaction.Transactional;
import io.vertigo.core.component.Component;
import io.vertigo.dynamo.collections.ListFilter;
import io.vertigo.dynamo.collections.model.FacetedQueryResult;
import io.vertigo.dynamo.collections.model.SelectedFacetValues;
import io.vertigo.dynamo.domain.model.DtListState;
import io.vertigo.dynamo.domain.model.UID;
import io.vertigo.dynamo.search.SearchManager;
import io.vertigo.dynamo.search.metamodel.SearchIndexDefinition;
import io.vertigo.dynamo.search.model.SearchIndex;
import io.vertigo.dynamo.search.model.SearchQuery;

@Transactional
public class SupplierServices implements Component {

	@Inject
	private SearchManager searchManager;
	@Inject
	private SupplierSearchClient supplierSearchClient;

	public void indexChunk(final List<Supplier> suppliers) {
		final SearchIndexDefinition searchIndexDefinition = searchManager.findFirstIndexDefinitionByKeyConcept(Supplier.class);
		final List<SearchIndex<Supplier, Supplier>> indexes = suppliers
				.stream()
				.map(supplier -> SearchIndex.createIndex(searchIndexDefinition, UID.of(Supplier.class, supplier.getSiren()), supplier))
				.collect(Collectors.toList());
		searchManager.putAll(searchIndexDefinition, indexes);
	}

	public void cleanAll() {
		final SearchIndexDefinition searchIndexDefinition = searchManager.findFirstIndexDefinitionByKeyConcept(Supplier.class);
		searchManager.removeAll(searchIndexDefinition, ListFilter.of("*"));
	}

	public FacetedQueryResult<Supplier, SearchQuery> searchSuppliers(final String criteria, final SelectedFacetValues selectedFacetValues, final DtListState dtListState) {
		final SearchQuery searchQuery = supplierSearchClient.createSearchQueryBuilderSupplier(criteria, selectedFacetValues).build();
		return supplierSearchClient.loadList(searchQuery, dtListState);
	}
}
