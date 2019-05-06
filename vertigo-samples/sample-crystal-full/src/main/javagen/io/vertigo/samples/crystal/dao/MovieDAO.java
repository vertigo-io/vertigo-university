package io.vertigo.samples.crystal.dao;

import java.util.Arrays;

import javax.inject.Inject;

import io.vertigo.app.Home;
import io.vertigo.commons.transaction.VTransactionManager;
import io.vertigo.core.component.di.DIInjector;
import io.vertigo.dynamo.collections.ListFilter;
import io.vertigo.dynamo.collections.metamodel.FacetedQueryDefinition;
import io.vertigo.dynamo.collections.metamodel.ListFilterBuilder;
import io.vertigo.dynamo.collections.model.FacetedQueryResult;
import io.vertigo.dynamo.collections.model.SelectedFacetValues;
import io.vertigo.dynamo.domain.model.DtListState;
import io.vertigo.dynamo.domain.model.UID;
import io.vertigo.dynamo.impl.store.util.DAO;
import io.vertigo.dynamo.search.SearchManager;
import io.vertigo.dynamo.search.metamodel.SearchIndexDefinition;
import io.vertigo.dynamo.search.model.SearchQuery;
import io.vertigo.dynamo.search.model.SearchQueryBuilder;
import io.vertigo.dynamo.store.StoreManager;
import io.vertigo.dynamo.store.StoreServices;
import io.vertigo.dynamo.task.TaskManager;
import io.vertigo.dynamo.task.metamodel.TaskDefinition;
import io.vertigo.dynamo.task.model.Task;
import io.vertigo.dynamo.task.model.TaskBuilder;
import io.vertigo.lang.Generated;
import io.vertigo.samples.crystal.domain.Movie;
import io.vertigo.samples.crystal.domain.MovieIndex;

/**
 * This class is automatically generated.
 * DO NOT EDIT THIS FILE DIRECTLY.
 */
@Generated
public final class MovieDAO extends DAO<Movie, java.lang.Long> implements StoreServices {
	private final SearchManager searchManager;
	private final VTransactionManager transactionManager;

	/**
	 * Contructeur.
	 * @param storeManager Manager de persistance
	 * @param taskManager Manager de Task
	 * @param searchManager Search Manager
	 * @param transactionManager Transaction Manager
	 */
	@Inject
	public MovieDAO(final StoreManager storeManager, final TaskManager taskManager, final SearchManager searchManager, final VTransactionManager transactionManager) {
		super(Movie.class, storeManager, taskManager);
		this.searchManager = searchManager;
		this.transactionManager = transactionManager;
	}

	/**
	 * Indique que le keyConcept associé à cette UID va être modifié.
	 * Techniquement cela interdit les opérations d'ecriture en concurrence 
	 * et envoie un évenement de modification du keyConcept (à la fin de transaction eventuellement) 
	 * @param UID UID du keyConcept modifié
	 * @return KeyConcept à modifier
	 */
	public Movie readOneForUpdate(final UID<Movie> uid) {
		return dataStore.readOneForUpdate(uid);
	}

	/**
	 * Indique que le keyConcept associé à cet id va être modifié.
	 * Techniquement cela interdit les opérations d'ecriture en concurrence 
	 * et envoie un évenement de modification du keyConcept (à la fin de transaction eventuellement) 
	 * @param id Clé du keyConcept modifié
	 * @return KeyConcept à modifier
	 */
	public Movie readOneForUpdate(final java.lang.Long id) {
		return readOneForUpdate(createDtObjectUID(id));
	}

	/**
	 * Création d'une SearchQuery de type : Movie.
	 * @param criteria Critères de recherche
	 * @param selectedFacetValues Liste des facettes sélectionnées à appliquer
	 * @return SearchQueryBuilder pour ce type de recherche
	 */
	public SearchQueryBuilder createSearchQueryBuilderMovie(final String criteria, final SelectedFacetValues selectedFacetValues) {
		final FacetedQueryDefinition facetedQueryDefinition = Home.getApp().getDefinitionSpace().resolve("QryMovie", FacetedQueryDefinition.class);
		final ListFilterBuilder<String> listFilterBuilder = DIInjector.newInstance(facetedQueryDefinition.getListFilterBuilderClass());
		final ListFilter criteriaListFilter = listFilterBuilder.withBuildQuery(facetedQueryDefinition.getListFilterBuilderQuery()).withCriteria(criteria).build();
		return SearchQuery.builder(criteriaListFilter).withFacet(facetedQueryDefinition, selectedFacetValues);
	}

	/**
	 * Création d'une SearchQuery de type : MovieWithFacets.
	 * @param criteria Critères de recherche
	 * @param selectedFacetValues Liste des facettes sélectionnées à appliquer
	 * @return SearchQueryBuilder pour ce type de recherche
	 */
	public SearchQueryBuilder createSearchQueryBuilderMovieWithFacets(final String criteria, final SelectedFacetValues selectedFacetValues) {
		final FacetedQueryDefinition facetedQueryDefinition = Home.getApp().getDefinitionSpace().resolve("QryMovieWithFacets", FacetedQueryDefinition.class);
		final ListFilterBuilder<String> listFilterBuilder = DIInjector.newInstance(facetedQueryDefinition.getListFilterBuilderClass());
		final ListFilter criteriaListFilter = listFilterBuilder.withBuildQuery(facetedQueryDefinition.getListFilterBuilderQuery()).withCriteria(criteria).build();
		return SearchQuery.builder(criteriaListFilter).withFacet(facetedQueryDefinition, selectedFacetValues);
	}

	/**
	 * Récupération du résultat issu d'une requête.
	 * @param searchQuery critères initiaux
	 * @param listState Etat de la liste (tri et pagination)
	 * @return Résultat correspondant à la requête (de type MovieIndex) 
	 */
	public FacetedQueryResult<MovieIndex, SearchQuery> loadList(final SearchQuery searchQuery, final DtListState listState) {
		final SearchIndexDefinition indexDefinition = searchManager.findFirstIndexDefinitionByKeyConcept(Movie.class);
		return searchManager.loadList(indexDefinition, searchQuery, listState);
	}

	/**
		 * Mark an entity as dirty. Index of these elements will be reindexed if Tx commited.
		 * Reindexation isn't synchrone, strategy is dependant of plugin's parameters.
		 *
		 * @param entityUID Key concept's UID
		 */
	public void markAsDirty(final UID<Movie> entityUID) {
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
	public void markAsDirty(final Movie entity) {
		markAsDirty(UID.of(entity));
	}

	/**
	 * Creates a taskBuilder.
	 * @param name  the name of the task
	 * @return the builder 
	 */
	private static TaskBuilder createTaskBuilder(final String name) {
		final TaskDefinition taskDefinition = Home.getApp().getDefinitionSpace().resolve(name, TaskDefinition.class);
		return Task.builder(taskDefinition);
	}

	/**
	 * Execute la tache TkGetMoviesInCountries.
	 * @param countriesIds java.util.List<Long> 
	 * @return io.vertigo.dynamo.domain.model.DtList<io.vertigo.samples.crystal.domain.Movie> movies
	*/
	public io.vertigo.dynamo.domain.model.DtList<io.vertigo.samples.crystal.domain.Movie> getMoviesInCountries(final java.util.List<Long> countriesIds) {
		final Task task = createTaskBuilder("TkGetMoviesInCountries")
				.addValue("countriesIds", countriesIds)
				.build();
		return getTaskManager()
				.execute(task)
				.getResult();
	}

}
