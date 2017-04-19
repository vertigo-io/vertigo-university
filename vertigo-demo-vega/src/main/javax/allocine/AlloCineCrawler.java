package allocine;

import io.vertigo.dynamo.kvstore.KVStoreManager;
import io.vertigo.dynamo.transaction.VTransactionManager;
import io.vertigo.dynamo.transaction.VTransactionWritable;
import io.vertigo.pandora.domain.movies.Movie;
import io.vertigo.pandora.domain.persons.Person;
import io.vertigo.pandora.services.movies.MovieServices;
import io.vertigo.pandora.services.persons.PersonServices;

import javax.inject.Inject;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.yamj.api.common.http.HttpClientWrapper;
import org.yamj.api.common.http.WebBrowserUserAgentSelector;

import com.moviejukebox.allocine.AllocineApi;
import com.moviejukebox.allocine.AllocineException;
import com.moviejukebox.allocine.TestLogger;
import com.moviejukebox.allocine.model.FilmographyInfos;
import com.moviejukebox.allocine.model.MovieInfos;
import com.moviejukebox.allocine.model.MoviePerson;
import com.moviejukebox.allocine.model.Participance;
import com.moviejukebox.allocine.model.PersonInfos;

public final class AlloCineCrawler {
	private static final String ALLOCINE_QUEUE_PERSONS = "allocine-queue-persons";
	private static final String ALLOCINE_QUEUE_MOVIES = "allocine-queue-movies";

	private static final int WAITING_TIME = 20000; //ms
	private static final String PARTNER_KEY = "100043982026";
	private static final String SECRET_KEY = "29d185d98c984a359e6e6f26a0474269";

	private final AllocineApi api;

	private final VTransactionManager transactionManager;
	private final MovieServices movieServices;
	private final PersonServices personServices;
	private final Queue queue;

	@Inject
	public AlloCineCrawler(final KVStoreManager kvStoreManager, final VTransactionManager transactionManager, final PersonServices personServices, final MovieServices movieServices) throws Exception {
		this.transactionManager = transactionManager;
		this.movieServices = movieServices;
		this.personServices = personServices;
		queue = new Queue(kvStoreManager);
		api = createAlloCineApi();
	}

	private static AllocineApi createAlloCineApi() throws AllocineException {
		TestLogger.Configure();
		final HttpClientWrapper wrapper = new HttpClientWrapper(HttpClients.createDefault());
		wrapper.setUserAgentSelector(new WebBrowserUserAgentSelector());
		final CloseableHttpClient httpclient = HttpClients.custom()
				//		.setProxy(new HttpHost("172.20.0.9", 3128))
				.build();
		return new AllocineApi(PARTNER_KEY, SECRET_KEY, wrapper);
	}

	public void display() throws Exception {
		try (final VTransactionWritable tx = transactionManager.createCurrentTransaction()) {
			System.out.println("---movies : " + movieServices.size());
			for (final Movie movie : movieServices.getAllMovies()) {
				System.out.println("    |---" + movie.getCode());
				System.out.println("    |   " + movie.getTitle());
				System.out.println("    |   " + movie.getActors());
			}
			System.out.println("---persons : " + personServices.size());
			for (final Person person : personServices.getAllPersons()) {
				System.out.println("    |---" + person.getCode());
			}
		}
	}

	public void crawl(final int max) throws Exception {
		//root 
		int movies = 0;
		int persons = 0;

		try (final VTransactionWritable tx = transactionManager.createCurrentTransaction()) {
			movies = movieServices.size();
			persons = personServices.size();
			//			enqueueMovieIfNx("61282"); //Avatar
			enqueuePersonIfNx("14100"); //tom Hanks
			enqueuePersonIfNx("1192"); //matt Damon
			enqueuePersonIfNx("1193"); //Ben Affleck
			enqueuePersonIfNx("1920"); //Alain Delon
			enqueuePersonIfNx("8"); //De niro
			enqueuePersonIfNx("10"); //Harvey Keitel
			enqueuePersonIfNx("9"); //Meryl Streep 
			enqueuePersonIfNx("27049"); //Daniel Craig
			enqueuePersonIfNx("12973"); //Kate Winslet
			enqueuePersonIfNx("194788"); //Emma Stone 
			enqueuePersonIfNx("10618"); //Rossy de Palma

			tx.commit();
		}

		boolean nextPerson = persons < max;
		boolean nextMovie = movies < max;

		int before = movies + persons;
		boolean active = true;
		while (active & (nextPerson || nextMovie)) {
			before = movies + persons;
			if (nextMovie && dequeueMovie()) {
				movies++;
			}
			if (nextPerson && dequeuePerson()) {
				persons++;
			}
			nextPerson = persons < max;
			nextMovie = movies < max;
			active = movies + persons > before;
			System.out.println("-------");
			try (final VTransactionWritable tx = transactionManager.createCurrentTransaction()) {
				System.out.println("---persons: " + persons);
				System.out.println("---movies : " + movies);
				System.out.println("---persons.size: " + personServices.size());
				System.out.println("---movies.size : " + movieServices.size());
				System.out.println("-------");
			}
		}

	}

	//on retourne ce qui reste a été effectué.
	private boolean dequeueMovie() throws Exception {
		try (final VTransactionWritable tx = transactionManager.createCurrentTransaction()) {
			final String code = queue.dequeue(ALLOCINE_QUEUE_MOVIES);
			if (code == null) {
				tx.commit();
				return false;
			}
			Thread.sleep(WAITING_TIME);
			final MovieInfos movieInfos = api.getMovieInfos(code);
			if (movieInfos.getMovie() == null) {
				tx.commit();
				return false;
			}
			movieServices.save(AlloCineCodec.toPandora(movieInfos));
			for (final MoviePerson moviePerson : movieInfos.getActors()) {
				enqueuePersonIfNx("" + moviePerson.getCode());
			}
			tx.commit();
		}
		return true;
	}

	private void enqueuePersonIfNx(final String theCode) {
		if (!personServices.contains(theCode)) {
			queue.enqueue(ALLOCINE_QUEUE_PERSONS, theCode);
		}
	}

	private boolean dequeuePerson() throws Exception {
		try (final VTransactionWritable tx = transactionManager.createCurrentTransaction()) {
			final String code = queue.dequeue(ALLOCINE_QUEUE_PERSONS);
			if (code == null) {
				tx.commit();
				return false;
			}
			Thread.sleep(WAITING_TIME);
			final PersonInfos personInfos = api.getPersonInfos(code);
			Thread.sleep(WAITING_TIME);
			final FilmographyInfos filmographyInfos = api.getPersonFilmography(personInfos.getCode() + "");

			personServices.save(AlloCineCodec.toPandora(personInfos, filmographyInfos));
			for (final Participance participance : filmographyInfos.getParticipances()) {
				enqueueMovieIfNx("" + participance.getCode());
			}
			tx.commit();
		}
		return true;
	}

	private void enqueueMovieIfNx(final String theCode) {
		if (!movieServices.contains(theCode)) {
			queue.enqueue(ALLOCINE_QUEUE_MOVIES, theCode);
		}
	}
}
