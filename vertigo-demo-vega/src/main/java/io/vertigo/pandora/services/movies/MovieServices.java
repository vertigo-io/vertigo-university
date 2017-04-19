package io.vertigo.pandora.services.movies;

import java.util.List;
import java.util.Optional;

import io.vertigo.dynamo.collections.ListFilter;
import io.vertigo.dynamo.collections.model.FacetedQueryResult;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.domain.model.DtListState;
import io.vertigo.dynamo.domain.model.Fragment;
import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.dynamo.search.model.SearchQuery;
import io.vertigo.lang.Component;
import io.vertigo.pandora.domain.movies.Movie;
import io.vertigo.pandora.domain.movies.MovieIndex;
import io.vertigo.pandora.domain.persons.ActorRole;
import io.vertigo.pandora.domain.persons.Person;
import io.vertigo.pandora.domain.persons.PersonActorRoleLink;

public interface MovieServices extends Component {

	Movie getMovie(final long id);

	<M extends Fragment<Movie>> M getMovie(long id, Class<M> movieFragment);

	FacetedQueryResult<MovieIndex, SearchQuery> searchMovies(final String criteria, final List<ListFilter> listFilters, final DtListState dtListState, final Optional<String> group);

	List<Movie> getMarkRanking();

	List<Movie> getDateRanking();

	void save(final Movie movie);

	<M extends Fragment<Movie>> void saveFragment(M movieFragment);

	DtList<PersonActorRoleLink> getActors(final long id);

	void saveActors(final DtList<ActorRole> actors);

	DtList<Person> getWriters(final long id);

	void saveWriters(final Movie movie, final List<URI> personURIs);

	DtList<Person> getCamera(final long id);

	void saveCamera(final Movie movie, final List<URI> personURIs);

	DtList<Person> getProducers(final long id);

	void saveProducers(final Movie movie, final List<URI> personURIs);

	DtList<Person> getDirectors(final long id);

	void saveDirectors(final Movie movie, final List<URI> personURIs);

	//-------------
	//  Private
	//-------------

	DtList<MovieIndex> getMovieIndex(final List<Long> movieIds);

}
