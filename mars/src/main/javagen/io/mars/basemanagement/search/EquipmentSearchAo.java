package io.mars.basemanagement.search;

import javax.inject.Inject;

import java.util.Arrays;
import io.vertigo.app.Home;
import io.vertigo.core.component.Component;
import io.vertigo.core.component.di.injector.DIInjector;
import io.vertigo.dynamo.search.SearchManager;
import io.vertigo.dynamo.search.metamodel.SearchIndexDefinition;
import io.vertigo.dynamo.search.model.SearchQuery;
import io.vertigo.dynamo.search.model.SearchQueryBuilder;
import io.vertigo.dynamo.domain.model.DtListState;
import io.vertigo.dynamo.collections.ListFilter;
import io.vertigo.dynamo.collections.metamodel.FacetedQueryDefinition;
import io.vertigo.dynamo.collections.metamodel.ListFilterBuilder;
import io.vertigo.dynamo.collections.model.FacetedQueryResult;
import io.vertigo.dynamo.collections.model.SelectedFacetValues;
import io.vertigo.commons.transaction.VTransactionManager;
import io.mars.basemanagement.search.EquipmentIndex;
import io.vertigo.dynamo.domain.model.UID;
import io.mars.basemanagement.domain.Equipment;
import io.vertigo.lang.Generated;

/**
 * This class is automatically generated.
 * DO NOT EDIT THIS FILE DIRECTLY.
 */
@Generated
public final class EquipmentSearchAo implements Component {

	private final SearchManager searchManager;
	private final VTransactionManager transactionManager;
	
	/**
	 * Contructeur.
	 * @param searchManager Search Manager
	 * @param transactionManager Transaction Manager
	 */
	@Inject
	public EquipmentSearchAo(final SearchManager searchManager, final VTransactionManager transactionManager) {
		this.searchManager = searchManager;
		this.transactionManager = transactionManager;
	}

	/**
	 * Création d'une SearchQuery de type : Equipment.
	 * @param criteria Critères de recherche
	 * @param selectedFacetValues Liste des facettes sélectionnées à appliquer
	 * @return SearchQueryBuilder pour ce type de recherche
	 */
	public SearchQueryBuilder createSearchQueryBuilderEquipment(final String criteria, final SelectedFacetValues selectedFacetValues) {
		final FacetedQueryDefinition facetedQueryDefinition = Home.getApp().getDefinitionSpace().resolve("QryEquipment", FacetedQueryDefinition.class);
		final ListFilterBuilder<String> listFilterBuilder = DIInjector.newInstance(facetedQueryDefinition.getListFilterBuilderClass(), Home.getApp().getComponentSpace());
		final ListFilter criteriaListFilter = listFilterBuilder.withBuildQuery(facetedQueryDefinition.getListFilterBuilderQuery()).withCriteria(criteria).build();
		return SearchQuery.builder(criteriaListFilter).withFacetStrategy(facetedQueryDefinition, selectedFacetValues);
	}

	/**
	 * Récupération du résultat issu d'une requête.
	 * @param searchQuery critères initiaux
	 * @param listState Etat de la liste (tri et pagination)
	 * @return Résultat correspondant à la requête (de type EquipmentIndex) 
	 */
	public FacetedQueryResult<EquipmentIndex, SearchQuery> loadList(final SearchQuery searchQuery, final DtListState listState) {
		final SearchIndexDefinition indexDefinition = searchManager.findFirstIndexDefinitionByKeyConcept(Equipment.class);
		return searchManager.loadList(indexDefinition, searchQuery, listState);
	}
	
	/**
	 * Mark an entity as dirty. Index of these elements will be reindexed if Tx commited.
	 * Reindexation isn't synchrone, strategy is dependant of plugin's parameters.
	 *
	 * @param entityUID Key concept's UID
	 */
	public void markAsDirty(final UID<Equipment> entityUID) {
		transactionManager.getCurrentTransaction().addAfterCompletion((final boolean txCommitted) -> {
			if (txCommitted) {// reindex only is tx successful
				searchManager.markAsDirty(Arrays.asList(entityUID));
			}
		});
	}
	
	/**
	 * Mark an entity as dirty. Index of these elements will be reindexed if Tx commited.
	 * Reindexation isn't synchrone, strategy is dependant of plugin's parameters.
	 * 
	 * @param entity Key concept
	 */
	public void markAsDirty(final Equipment entity) {
		markAsDirty(UID.of(entity));
	}
}
