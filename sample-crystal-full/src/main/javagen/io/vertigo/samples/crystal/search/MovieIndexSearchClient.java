package io.vertigo.samples.crystal.search;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import io.vertigo.commons.transaction.VTransactionManager;
import io.vertigo.core.lang.Generated;
import io.vertigo.core.node.component.Component;
import io.vertigo.core.node.definition.DefinitionProvider;
import io.vertigo.core.node.definition.DefinitionSpace;
import io.vertigo.core.node.definition.DefinitionSupplier;
import io.vertigo.core.util.ListBuilder;
import io.vertigo.datafactory.collections.definitions.FacetDefinition.FacetOrder;
import io.vertigo.datafactory.collections.definitions.FacetRangeDefinitionSupplier;
import io.vertigo.datafactory.collections.definitions.FacetTermDefinitionSupplier;
import io.vertigo.datafactory.collections.definitions.FacetedQueryDefinitionSupplier;
import io.vertigo.datafactory.collections.model.FacetedQueryResult;
import io.vertigo.datafactory.collections.model.SelectedFacetValues;
import io.vertigo.datafactory.search.SearchManager;
import io.vertigo.datafactory.search.definitions.SearchIndexDefinition;
import io.vertigo.datafactory.search.definitions.SearchIndexDefinitionSupplier;
import io.vertigo.datafactory.search.model.SearchQuery;
import io.vertigo.datafactory.search.model.SearchQueryBuilder;
import io.vertigo.datamodel.structure.model.DtListState;
import io.vertigo.datamodel.structure.model.UID;
import io.vertigo.samples.crystal.domain.MovieIndex;

/**
 * This class is automatically generated.
 * DO NOT EDIT THIS FILE DIRECTLY.
 */
@Generated
public final class MovieIndexSearchClient implements Component, DefinitionProvider {

	private final SearchManager searchManager;
	private final VTransactionManager transactionManager;

	/**
	 * Contructeur.
	 * @param searchManager Search Manager
	 * @param transactionManager Transaction Manager
	 */
	@Inject
	public MovieIndexSearchClient(final SearchManager searchManager, final VTransactionManager transactionManager) {
		this.searchManager = searchManager;
		this.transactionManager = transactionManager;
	}

	/**
	 * Création d'une SearchQuery de type : Movie.
	 * @param criteria Critères de recherche
	 * @param selectedFacetValues Liste des facettes sélectionnées à appliquer
	 * @return SearchQueryBuilder pour ce type de recherche
	 */
	public SearchQueryBuilder createSearchQueryBuilderMovie(final String criteria, final SelectedFacetValues selectedFacetValues) {
		return SearchQuery.builder("QryMovie")
				.withCriteria(criteria)
				.withFacet(selectedFacetValues);
	}
	/**
	 * Création d'une SearchQuery de type : MovieWithFacets.
	 * @param criteria Critères de recherche
	 * @param selectedFacetValues Liste des facettes sélectionnées à appliquer
	 * @return SearchQueryBuilder pour ce type de recherche
	 */
	public SearchQueryBuilder createSearchQueryBuilderMovieWithFacets(final String criteria, final SelectedFacetValues selectedFacetValues) {
		return SearchQuery.builder("QryMovieWithFacets")
				.withCriteria(criteria)
				.withFacet(selectedFacetValues);
	}

	/**
	 * Récupération du résultat issu d'une requête.
	 * @param searchQuery critères initiaux
	 * @param listState Etat de la liste (tri et pagination)
	 * @return Résultat correspondant à la requête (de type MovieIndex)
	 */
	public FacetedQueryResult<MovieIndex, SearchQuery> loadListIdxMovie(final SearchQuery searchQuery, final DtListState listState) {
		final SearchIndexDefinition indexDefinition = io.vertigo.core.node.Node.getNode().getDefinitionSpace().resolve("IdxMovie",SearchIndexDefinition.class);
		return searchManager.loadList(indexDefinition, searchQuery, listState);
	}
		
	/**
	 * Récupération du résultat issu d'une requête.
	 * @param searchQuery critères initiaux
	 * @param listState Etat de la liste (tri et pagination)
	 * @return Résultat correspondant à la requête (de type MovieIndex)
	 */
	public FacetedQueryResult<MovieIndex, SearchQuery> loadList(final SearchQuery searchQuery, final DtListState listState) {
		final List<SearchIndexDefinition> indexDefinitions = List.of( 
				io.vertigo.core.node.Node.getNode().getDefinitionSpace().resolve("IdxMovie",SearchIndexDefinition.class));
		return searchManager.loadList(indexDefinitions, searchQuery, listState);
	}

	/**
	 * Mark an entity as dirty. Index of these elements will be reindexed if Tx commited.
	 * Reindexation isn't synchrone, strategy is dependant of plugin's parameters.
	 *
	 * @param entityUID Key concept's UID
	 */
	public void markAsDirty(final UID entityUID) {
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
	public void markAsDirty(final io.vertigo.samples.crystal.domain.Movie entity) {
		markAsDirty(UID.of(entity));
	}
	

	/** {@inheritDoc} */
	@Override
	public List<DefinitionSupplier> get(final DefinitionSpace definitionSpace) {
		return new ListBuilder<DefinitionSupplier>()
				//---
				// SearchIndexDefinition
				//-----
				.add(new SearchIndexDefinitionSupplier("IdxMovie")
						.withIndexDtDefinition("DtMovieIndex")
						.withKeyConcept("DtMovie")
						.withLoaderId("MovieSearchLoader"))
				
				//---
				// FacetTermDefinition
				//-----
				.add(new FacetTermDefinitionSupplier("FctMovieCountry")
						.withDtDefinition("DtMovieIndex")
						.withFieldName("country")
						.withLabel("Par pays")
						.withMultiSelectable()
						.withOrder(FacetOrder.count))
				.add(new FacetRangeDefinitionSupplier("FctMovieName")
						.withDtDefinition("DtMovieIndex")
						.withFieldName("name")
						.withLabel("Par titre")
						.withRange("r1", "name.keyword:[* TO a]", "#")
						.withRange("r2", "name.keyword:[a TO g]", "a-f")
						.withRange("r3", "name.keyword:[g TO n]", "g-m")
						.withRange("r4", "name.keyword:[n TO t]", "n-s")
						.withRange("r4", "name.keyword:[t TO *]", "t-z")
						.withOrder(FacetOrder.definition))
				.add(new FacetRangeDefinitionSupplier("FctMovieYear")
						.withDtDefinition("DtMovieIndex")
						.withFieldName("year")
						.withLabel("Par année")
						.withRange("r1", "year:[* TO 1930]", "< années 30")
						.withRange("r2", "year:[1930 TO 1940]", "années 30")
						.withRange("r3", "year:[1940 TO 1950]", "années 40")
						.withRange("r4", "year:[1950 TO 1960]", "années 50")
						.withRange("r5", "year:[1960 TO 1970]", "années 60")
						.withRange("r6", "year:[1970 TO 1980]", "années 70")
						.withRange("r7", "year:[1980 TO 1990]", "années 80")
						.withRange("r8", "year:[1990 TO 2000]", "années 90")
						.withRange("r9", "year:[2000 TO 2010]", "années 2000")
						.withRange("r10", "year:[2010 TO *]", "> années 2010")
						.withOrder(FacetOrder.definition))

				//---
				// FacetedQueryDefinition
				//-----
				.add(new FacetedQueryDefinitionSupplier("QryMovie")
						.withListFilterBuilderClass(io.vertigo.datafactory.impl.search.dsl.DslListFilterBuilder.class)
						.withListFilterBuilderQuery("_all:#+query*#")
						.withCriteriaSmartType("STyLabel"))
				.add(new FacetedQueryDefinitionSupplier("QryMovieWithFacets")
						.withFacet("FctMovieName")
						.withFacet("FctMovieYear")
						.withFacet("FctMovieCountry")
						.withListFilterBuilderClass(io.vertigo.datafactory.impl.search.dsl.DslListFilterBuilder.class)
						.withListFilterBuilderQuery("_all:#+query*#")
						.withCriteriaSmartType("STyLabel"))
				.build();
	}
}