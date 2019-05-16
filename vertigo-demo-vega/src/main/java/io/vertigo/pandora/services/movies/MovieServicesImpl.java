package io.vertigo.pandora.services.movies;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import io.vertigo.app.Home;
import io.vertigo.commons.transaction.Transactional;
import io.vertigo.dynamo.collections.metamodel.FacetDefinition;
import io.vertigo.dynamo.collections.model.FacetedQueryResult;
import io.vertigo.dynamo.collections.model.SelectedFacetValues;
import io.vertigo.dynamo.domain.metamodel.association.DtListURIForNNAssociation;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.domain.model.DtListState;
import io.vertigo.dynamo.domain.model.Fragment;
import io.vertigo.dynamo.domain.model.UID;
import io.vertigo.dynamo.search.model.SearchQuery;
import io.vertigo.dynamo.search.model.SearchQueryBuilder;
import io.vertigo.dynamo.store.StoreManager;
import io.vertigo.pandora.dao.movies.MovieDAO;
import io.vertigo.pandora.dao.movies.MoviesPAO;
import io.vertigo.pandora.dao.persons.ActorRoleDAO;
import io.vertigo.pandora.domain.movies.Dummy;
import io.vertigo.pandora.domain.movies.Movie;
import io.vertigo.pandora.domain.movies.MovieIndex;
import io.vertigo.pandora.domain.persons.ActorRole;
import io.vertigo.pandora.domain.persons.Person;
import io.vertigo.pandora.domain.persons.PersonActorRoleLink;
import io.vertigo.pandora.search.movies.MovieSearchClient;

@Transactional
public class MovieServicesImpl implements MovieServices {

	@Inject
	private MovieDAO movieDAO;
	@Inject
	private MovieSearchClient movieSearchClient;
	@Inject
	private MoviesPAO moviesPAO;
	@Inject
	private ActorRoleDAO actorRoleDAO;
	@Inject
	private StoreManager storeManager;

	@Override
	public Movie getMovie(final long id) {
		return movieDAO.get(id);
	}

	@Override
	public <M extends Fragment<Movie>> M getMovie(final long id, final Class<M> movieFragment) {
		return movieDAO.get(id, movieFragment);
	}

	/**
	 * Complete movie's actors, producers, directors... informations
	 * @param movie
	 */
	/*public void completeMoviePersonsInfo(final Movie movie) {
		//complete actors info
		setPersonExistsInDB(movie.getActors());
		//complete camera info
		setPersonExistsInDB(movie.getCamera());
		//complete directors info
		setPersonExistsInDB(movie.getDirectors());
		//complete producers info
		setPersonExistsInDB(movie.getProducers());
		//complete writers info
		setPersonExistsInDB(movie.getWriters());
	
	}
	
	public void setPersonExistsInDB(final DtList<PersonLink> persons) {
		if (null != persons) {
			for (final PersonLink link : persons) {
				final Optional<AlloCineData> data = kvStoreManager.find(BDD_PERSONS, String.valueOf(link.getCode()), AlloCineData.class);
				link.setExistsInBdd(data.isPresent());
			}
		}
	}*/

	@Override
	public void save(final Movie movie) {
		movieDAO.save(movie);
	}

	@Override
	public <M extends Fragment<Movie>> void saveFragment(final M movieFragment) {
		final Movie movie = movieDAO.reloadAndMerge(movieFragment);
		movieDAO.update(movie);
	}

	@Override
	public void saveActors(final DtList<ActorRole> actors) {
		for (final ActorRole actor : actors) {
			actorRoleDAO.save(actor);
		}
	}

	@Override
	public void saveWriters(final Movie movie, final List<UID> personURIs) {
		storeManager.getDataStore().getBrokerNN().updateNN((DtListURIForNNAssociation) movie.writers().getDtListURI(), personURIs);
	}

	@Override
	public void saveCamera(final Movie movie, final List<UID> personURIs) {
		storeManager.getDataStore().getBrokerNN().updateNN((DtListURIForNNAssociation) movie.camera().getDtListURI(), personURIs);
	}

	@Override
	public void saveProducers(final Movie movie, final List<UID> personURIs) {
		storeManager.getDataStore().getBrokerNN().updateNN((DtListURIForNNAssociation) movie.producers().getDtListURI(), personURIs);
	}

	@Override
	public void saveDirectors(final Movie movie, final List<UID> personURIs) {
		storeManager.getDataStore().getBrokerNN().updateNN((DtListURIForNNAssociation) movie.directors().getDtListURI(), personURIs);
	}

	@Override
	public FacetedQueryResult<MovieIndex, SearchQuery> searchMovies(final String criteria, final SelectedFacetValues listFilters, final DtListState dtListState, final Optional<String> group) {
		final SearchQueryBuilder searchQueryBuilder = movieSearchClient.createSearchQueryBuilderMovie(criteria, listFilters);
		return searchMovieCommon(dtListState, group, searchQueryBuilder);
	}

	private FacetedQueryResult<MovieIndex, SearchQuery> searchMovieCommon(
			final DtListState dtListState, final Optional<String> group,
			final SearchQueryBuilder searchQueryBuilder) {
		if (group.isPresent()) {
			final FacetDefinition clusteringFacetDefinition = Home.getApp().getDefinitionSpace().resolve(group.get(), FacetDefinition.class);
			searchQueryBuilder.withFacetClustering(clusteringFacetDefinition);
		}
		return movieSearchClient.loadList(searchQueryBuilder.build(), dtListState);
	}

	@Override
	public DtList<MovieIndex> getMovieIndex(final List<Long> movieIds) {
		final DtList<Dummy> ids = new DtList<>(Dummy.class);
		for (final Long movieId : movieIds) {
			final Dummy id = new Dummy();
			id.setValue(movieId);
			ids.add(id);
		}
		return moviesPAO.loadMovieIndex(ids);
	}

	@Override
	public List<Movie> getMarkRanking() {
		return getRankingByField("USER_RATING");
	}

	@Override
	public List<Movie> getDateRanking() {
		return getRankingByField("PRODUCTION_YEAR");
	}

	public List<Movie> getRankingByField(final String fieldName) {
		final DtListState dtListState = DtListState.of(6, 0, fieldName, true);
		final SelectedFacetValues listFilters = SelectedFacetValues.empty().build();
		final Optional<String> group = Optional.empty();
		final SearchQueryBuilder searchQueryBuilder = movieSearchClient.createSearchQueryBuilderMovieWithPoster("", listFilters);
		final FacetedQueryResult<MovieIndex, SearchQuery> result = searchMovieCommon(dtListState, group, searchQueryBuilder);
		final List<Movie> movies = new ArrayList<>();
		for (final MovieIndex movieIndex : result.getDtList()) {
			movies.add(getMovie(movieIndex.getMovId()));
		}
		return movies;
	}

	@Override
	public DtList<PersonActorRoleLink> getActors(final long id) {
		final DtList<PersonActorRoleLink> actorRoleLinks = new DtList<>(PersonActorRoleLink.class);
		final Movie movie = getMovie(id);
		movie.roles().load();
		for (final ActorRole actorRole : movie.roles().get()) {
			actorRole.actor().load();
			final Person person = actorRole.actor().get(); //load actor in ActorRole
			final PersonActorRoleLink actorRoleLink = new PersonActorRoleLink();
			actorRoleLink.setPerId(person.getPerId());
			actorRoleLink.setFullName(person.getFullName());
			actorRoleLink.setPhotoHref(person.getPhotoHref());
			actorRoleLink.setRole(actorRole.getRole());
			actorRoleLinks.add(actorRoleLink);
		}
		return actorRoleLinks;
	}

	@Override
	public DtList<Person> getWriters(final long id) {
		final Movie movie = getMovie(id);
		movie.writers().load();
		return movie.writers().get();
	}

	@Override
	public DtList<Person> getCamera(final long id) {
		final Movie movie = getMovie(id);
		movie.camera().load();
		return movie.camera().get();
	}

	@Override
	public DtList<Person> getProducers(final long id) {
		final Movie movie = getMovie(id);
		movie.producers().load();
		return movie.producers().get();
	}

	@Override
	public DtList<Person> getDirectors(final long id) {
		final Movie movie = getMovie(id);
		movie.directors().load();
		return movie.directors().get();
	}

}
