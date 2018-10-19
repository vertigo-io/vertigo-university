package io.vertigo.pandora.services.common;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import io.vertigo.commons.transaction.Transactional;
import io.vertigo.dynamo.collections.model.FacetedQueryResult;
import io.vertigo.dynamo.collections.model.FacetedQueryResultMerger;
import io.vertigo.dynamo.collections.model.SelectedFacetValues;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.domain.model.DtListState;
import io.vertigo.dynamo.domain.model.DtObject;
import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.dynamo.domain.util.DtObjectUtil;
import io.vertigo.dynamo.search.SearchManager;
import io.vertigo.dynamo.search.model.SearchQuery;
import io.vertigo.lang.WrappedException;
import io.vertigo.pandora.dao.movies.MovieDAO;
import io.vertigo.pandora.dao.movies.MoviesPAO;
import io.vertigo.pandora.dao.persons.PersonsPAO;
import io.vertigo.pandora.domain.movies.Movie;
import io.vertigo.pandora.domain.movies.MovieIndex;
import io.vertigo.pandora.domain.persons.ActorRole;
import io.vertigo.pandora.domain.persons.Person;
import io.vertigo.pandora.domain.persons.PersonIndex;
import io.vertigo.pandora.services.movies.MovieServices;
import io.vertigo.pandora.services.persons.PersonServices;
import io.vertigo.util.BeanUtil;
import io.vertigo.vega.engines.webservice.json.GoogleJsonEngine;

@Transactional
public class CommonServicesImpl implements CommonServices {
	private final GoogleJsonEngine gson = new GoogleJsonEngine(Optional.<String> empty());
	@Inject
	private SearchManager searchManager;

	@Inject
	private MovieServices movieServices;
	@Inject
	private PersonServices personServices;
	@Inject
	private MoviesPAO moviesPAO;
	@Inject
	private PersonsPAO personsPAO;
	@Inject
	private MovieDAO movieDAO;

	/** {@inheritDoc} */
	@Override
	public FacetedQueryResult<DtObject, SearchQuery> searchAll(final String criteria, final DtListState dtListState) {
		final FacetedQueryResult<MovieIndex, SearchQuery> movies = movieServices.searchMovies(criteria, SelectedFacetValues.empty().build(), dtListState, Optional.<String> empty());
		final FacetedQueryResult<PersonIndex, SearchQuery> persons = personServices.searchPersons(criteria, SelectedFacetValues.empty().build(), dtListState, Optional.<String> empty());
		return new FacetedQueryResultMerger<DtObject, SearchQuery>()
				.add(movies, "MOVIE", "SCOPE:MOVIE", "Films", null)
				.add(persons, "PERSON", "SCOPE:PERSON", "Acteurs", null)
				.withFacet("FCT_SCOPE")
				.toFacetedQueryResult();
	}

	@Override
	public long reloadAll() {
		final long persons = reloadPersons();
		final long movies = reloadMovies();
		return persons + movies;
	}

	@Override
	public long reindexAll() {
		final long persons = indexPersons();
		System.out.println("REINDEX " + persons + " persons");

		final long movies = indexMovies();
		System.out.println("REINDEX " + movies + " movies");

		return persons + movies;
	}

	private long reloadPersons() {
		final DtList<Person> persons = new DtList<>(Person.class);
		final Map<Long, Person> personMaps = new HashMap<>();
		readPersonLinks(personMaps);
		readPersons(personMaps); //on complete avec ceux qui sont complets

		persons.addAll(personMaps.values());
		personsPAO.removeAllPersons();
		personsPAO.importPersons(persons);

		System.out.println("RELOAD ALL persons " + persons.size());
		return persons.size();
	}

	private void readPersons(final Map<Long, Person> personMaps) {
		final String fileContent = readFileAsString("D:\\pandora\\sources\\persons.json");
		final JsonPerson[] jsonPersons = gson.fromJson(fileContent, JsonPerson[].class);
		for (final JsonPerson jsonPerson : jsonPersons) {
			System.out.println("RELOAD DB person " + jsonPerson.getCode());
			personMaps.put(jsonPerson.getCode(), createPerson(jsonPerson));
		}
	}

	private void readPersonLinks(final Map<Long, Person> personMaps) {
		final String movieContent = readFileAsString("D:\\pandora\\sources\\movies.json");
		final JsonMovie[] jsonMovies = gson.fromJson(movieContent, JsonMovie[].class);
		for (final JsonMovie jsonMovie : jsonMovies) {
			final Collection<Person> personLinks = extractPersonLinks(jsonMovie);
			for (final Person personLink : personLinks) {
				System.out.println("RELOAD DB personLink " + personLink.getPerId());
				personMaps.put(personLink.getPerId(), personLink);
			}
		}
	}

	private long reloadMovies() {
		final DtList<Movie> movies = new DtList<>(Movie.class);
		final Map<Long, Movie> movieMaps = new HashMap<>();
		readMovies(movieMaps);

		movies.addAll(movieMaps.values());
		moviesPAO.removeAllMovies();
		moviesPAO.importMovies(movies);
		System.out.println("RELOAD DB " + movies.size() + " movies");

		final DtList<ActorRole> actorRoles = readActorRoles();
		moviesPAO.removeAllActorRoles();
		moviesPAO.importActorRoles(actorRoles);
		System.out.println("RELOAD DB " + actorRoles.size() + " actors");

		reloadMoviesPersonsNN(movieMaps);

		return movies.size();

	}

	private void readMovies(final Map<Long, Movie> movieMaps) {
		final String movieContent = readFileAsString("D:\\pandora\\sources\\movies.json");
		final JsonMovie[] jsonMovies = gson.fromJson(movieContent, JsonMovie[].class);
		for (final JsonMovie jsonMovie : jsonMovies) {
			System.out.println("RELOAD DB movie " + jsonMovie.getCode());
			movieMaps.put(jsonMovie.getCode(), createMovie(jsonMovie));
		}
	}

	private DtList<ActorRole> readActorRoles() {
		final String movieContent = readFileAsString("D:\\pandora\\sources\\movies.json");
		final JsonMovie[] jsonMovies = gson.fromJson(movieContent, JsonMovie[].class);

		final DtList<ActorRole> actorRoles = new DtList<>(ActorRole.class);
		for (final JsonMovie jsonMovie : jsonMovies) {
			actorRoles.addAll(extractActors(jsonMovie));
		}
		return actorRoles;
	}

	private void reloadMoviesPersonsNN(final Map<Long, Movie> movieMaps) {
		final String movieContent = readFileAsString("D:\\pandora\\sources\\movies.json");
		final JsonMovie[] jsonMovies = gson.fromJson(movieContent, JsonMovie[].class);

		long writersCount = 0;
		long cameraCount = 0;
		long producersCount = 0;
		long directorsCount = 0;

		for (final JsonMovie jsonMovie : jsonMovies) {
			final Movie movie = movieMaps.get(jsonMovie.getCode());
			final List<URI> writersUris = extractURIs(jsonMovie, "writers");
			final List<URI> cameraUris = extractURIs(jsonMovie, "camera");
			final List<URI> producersUris = extractURIs(jsonMovie, "producers");
			final List<URI> directorsUris = extractURIs(jsonMovie, "directors");
			movieDAO.updateNN(movie.getWritersDtListURI(), writersUris);
			movieDAO.updateNN(movie.getCameraDtListURI(), cameraUris);
			movieDAO.updateNN(movie.getProducersDtListURI(), producersUris);
			movieDAO.updateNN(movie.getDirectorsDtListURI(), directorsUris);
			writersCount += writersUris.size();
			cameraCount += cameraUris.size();
			producersCount += producersUris.size();
			directorsCount += directorsUris.size();
		}

		System.out.println("RELOAD DB " + writersCount + " writers");
		System.out.println("RELOAD DB " + cameraCount + " camera");
		System.out.println("RELOAD DB " + producersCount + " producers");
		System.out.println("RELOAD DB " + directorsCount + " directors");
	}

	private static DtList<ActorRole> extractActors(final JsonMovie jsonMovie) {
		final DtList<ActorRole> actors = new DtList<>(ActorRole.class);
		final JsonPersonLink[] jsonPersonLinks = jsonMovie.getActors();
		if (jsonPersonLinks != null) {
			for (final JsonPersonLink jsonPersonLink : jsonPersonLinks) {
				final ActorRole actor = new ActorRole();
				actor.setMovId(jsonMovie.getCode());
				actor.setPerId(jsonPersonLink.getCode());
				actor.setRole(jsonPersonLink.getRole());
				actors.add(actor);
			}
		}
		return actors;
	}

	private Collection<Person> extractPersonLinks(final JsonMovie jsonMovie) {
		final Map<Long, Person> personLinkMap = new HashMap<>();
		populatePersonLinkIndex(personLinkMap, jsonMovie, "actors");
		populatePersonLinkIndex(personLinkMap, jsonMovie, "writers");
		populatePersonLinkIndex(personLinkMap, jsonMovie, "camera");
		populatePersonLinkIndex(personLinkMap, jsonMovie, "producers");
		populatePersonLinkIndex(personLinkMap, jsonMovie, "directors");
		return personLinkMap.values();
	}

	/*private List<URI> idsToURIs(final List<Long> personIds, final List<URI> personURIs) {
	final List<URI> personURIs = new ArrayList<>();
	for (final Long personId : personIds) {
		personURIs.add(URI.of(Person.class, personId));
	}
	return personURIs;
	}*/

	private void populatePersonLinkIndex(final Map<Long, Person> personLinkMap, final JsonMovie jsonMovie, final String propertyName) {
		final JsonPersonLink[] jsonPersonLinks = (JsonPersonLink[]) BeanUtil.getValue(jsonMovie, propertyName);
		if (jsonPersonLinks != null) {
			for (final JsonPersonLink jsonPersonLink : jsonPersonLinks) {
				personLinkMap.put(jsonPersonLink.getCode(), createPerson(jsonPersonLink));
			}
		}
	}

	private List<URI> extractURIs(final JsonMovie jsonMovie, final String propertyName) {
		final JsonPersonLink[] jsonPersonLinks = (JsonPersonLink[]) BeanUtil.getValue(jsonMovie, propertyName);
		final Set<URI> personURIs = new HashSet<>();
		if (jsonPersonLinks != null) {
			for (final JsonPersonLink jsonPersonLink : jsonPersonLinks) {
				personURIs.add(URI.of(Person.class, jsonPersonLink.getCode()));
			}
		}
		return new ArrayList<>(personURIs);
	}

	private long indexMovies() {
		try {
			return searchManager.reindexAll(searchManager.findIndexDefinitionByKeyConcept(Movie.class)).get();
		} catch (InterruptedException | ExecutionException e) {
			throw WrappedException.wrap(e);
		}
	}

	private long indexPersons() {
		try {
			return searchManager.reindexAll(searchManager.findIndexDefinitionByKeyConcept(Person.class)).get();
		} catch (InterruptedException | ExecutionException e) {
			throw WrappedException.wrap(e);
		}
	}

	private static Person createPerson(final JsonPerson jsonPerson) {
		final Person person = new Person();
		person.setPerId(Long.valueOf(jsonPerson.getCode()));
		person.setFullName(jsonPerson.getFullName());
		person.setFirstName(jsonPerson.getFirstName());
		person.setBiography(jsonPerson.getBiography());
		person.setShortBiography(jsonPerson.getShortBiography());
		person.setSex(jsonPerson.getSex());
		person.setPhotoHref(jsonPerson.getPhotoURL());
		person.setBirthDate(jsonPerson.getBirthDate());
		person.setBirthPlace(jsonPerson.getBirthPlace());
		person.setActivity(jsonPerson.getActivity());
		return person;
	}

	private static Person createPerson(final JsonPersonLink jsonPersonLink) {
		final Person person = new Person();
		person.setPerId(Long.valueOf(jsonPersonLink.getCode()));
		person.setFullName(jsonPersonLink.getName());
		person.setPhotoHref(jsonPersonLink.getPhotoURL());
		return person;
	}

	private static Movie createMovie(final JsonMovie jsonMovie) {
		final Movie movie = new Movie();
		movie.setMovId(jsonMovie.getCode());
		movie.setTitle(jsonMovie.getTitle());
		movie.setOriginalTitle(jsonMovie.getOriginalTitle());
		movie.setSynopsis(jsonMovie.getSynopsis());
		movie.setShortSynopsis(jsonMovie.getShortSynopsis());
		movie.setKeywords(jsonMovie.getKeywords());
		movie.setPoster(jsonMovie.getPoster());
		movie.setTrailerName(jsonMovie.getTrailerName());
		movie.setTrailerHref(jsonMovie.getTrailerHRef());
		movie.setRuntime(jsonMovie.getRuntime());
		movie.setMovieType(jsonMovie.getMovieType());
		movie.setProductionYear(jsonMovie.getProductionYear());
		movie.setUserRating(jsonMovie.getUserRating());
		movie.setPressRating(jsonMovie.getPressRating());
		return movie;
	}

	private static String readFileAsString(final String path) {
		try {
			return new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
		} catch (final IOException e) {
			throw WrappedException.wrap(e);
		}
	}

}
