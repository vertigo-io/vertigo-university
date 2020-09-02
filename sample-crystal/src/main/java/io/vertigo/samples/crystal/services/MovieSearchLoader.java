package io.vertigo.samples.crystal.services;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.vertigo.commons.transaction.VTransactionManager;
import io.vertigo.core.node.component.Activeable;
import io.vertigo.datafactory.impl.search.loader.AbstractSqlSearchLoader;
import io.vertigo.datafactory.search.SearchManager;
import io.vertigo.datafactory.search.definitions.SearchChunk;
import io.vertigo.datafactory.search.definitions.SearchIndexDefinition;
import io.vertigo.datafactory.search.model.SearchIndex;
import io.vertigo.datamodel.structure.model.DtList;
import io.vertigo.datamodel.structure.model.UID;
import io.vertigo.datamodel.task.TaskManager;
import io.vertigo.samples.crystal.domain.Movie;
import io.vertigo.samples.crystal.domain.MovieIndex;

public final class MovieSearchLoader extends AbstractSqlSearchLoader<Long, Movie, MovieIndex> implements Activeable {

	private final MovieServices movieServices;
	private final SearchManager searchManager;
	private SearchIndexDefinition indexDefinition;

	@Inject
	public MovieSearchLoader(final TaskManager taskManager, final SearchManager searchManager, final VTransactionManager transactionManager, final MovieServices movieServices) {
		super(taskManager, transactionManager);
		this.searchManager = searchManager;
		this.movieServices = movieServices;
	}

	@Override
	public void start() {
		indexDefinition = searchManager.findFirstIndexDefinitionByKeyConcept(Movie.class);
	}

	@Override
	public void stop() {
		indexDefinition = null;
	}

	@Override
	public List<SearchIndex<Movie, MovieIndex>> loadData(final SearchChunk<Movie> searchChunk) {
		final List<Long> movieIds = new ArrayList<>();
		for (final UID<Movie> uid : searchChunk.getAllUIDs()) {
			movieIds.add((Long) uid.getId());
		}
		final DtList<MovieIndex> movieIndexes = movieServices.getMovieIndex(movieIds);
		final List<SearchIndex<Movie, MovieIndex>> movieSearchIndexes = new ArrayList<>(searchChunk.getAllUIDs().size());
		for (final MovieIndex movieIndex : movieIndexes) {
			movieSearchIndexes.add(SearchIndex.createIndex(indexDefinition,
					UID.of(indexDefinition.getKeyConceptDtDefinition(), movieIndex.getMovId()), movieIndex));
		}
		return movieSearchIndexes;
	}

}
