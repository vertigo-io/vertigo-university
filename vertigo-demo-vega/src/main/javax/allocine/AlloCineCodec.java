package allocine;

import io.vertigo.pandora.domain.movies.Movie;
import io.vertigo.pandora.domain.persons.Person;
import io.vertigo.pandora.domain.persons.PersonLink;
import io.vertigo.util.ListBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.moviejukebox.allocine.model.FilmographyInfos;
import com.moviejukebox.allocine.model.MovieInfos;
import com.moviejukebox.allocine.model.MoviePerson;
import com.moviejukebox.allocine.model.Participance;
import com.moviejukebox.allocine.model.PersonInfos;

public class AlloCineCodec {

	private static String toSex(final int gender) {
		if (gender == 1) {
			return "M";
		}
		if (gender == 2) {
			return "F";
		}
		return "?";

	}

	static Person toPandora(final PersonInfos personInfos, final FilmographyInfos filmographyInfos) {
		final List<String> movies = new ArrayList<>();
		for (final Participance p : filmographyInfos.getParticipances()) {
			movies.add("" + p.getCode());
		}
		return new Person()
				.setCode(personInfos.getCode())
				.setFullName(personInfos.getFullName())
				.setFirstName(personInfos.getFirstName())
				.setPhotoURL(personInfos.getPhotoURL())
				.setBiography(personInfos.getBiography())
				.setShortBiography(personInfos.getBiographyShort())
				.setSex(toSex(personInfos.getGender()))
				.setActivity(personInfos.getPerson().getActivityShort())
				.setBirthDate(personInfos.getBirthDate())
				.setBirthPlace(personInfos.getBirthPlace())
				.setMovies(movies.toString());
	}

	private static List<PersonLink> buildPersonLinks(final Set<MoviePerson> moviePersons) {
		final ListBuilder<PersonLink> personLinksBuilder = new ListBuilder();
		for (final MoviePerson moviePerson : moviePersons) {
			personLinksBuilder.add(new PersonLink()
					.setCode(Long.valueOf(moviePerson.getCode()).intValue())
					.setLeadActor(moviePerson.isLeadActor())
					.setPhotoURL(moviePerson.getPhotoURL())
					.setName(moviePerson.getName())
					.setRole(moviePerson.getRole()));
		}
		return personLinksBuilder.build();
	}

	static Movie toPandora(final MovieInfos movieInfos) {
		final String poster = movieInfos.getMovie().getPoster() == null ? null : movieInfos.getMovie().getPoster().getHref();
		return new Movie()
				.setCode(movieInfos.getCode())
				.setSynopsis(movieInfos.getSynopsis())
				.setShortSynopsis(movieInfos.getSynopsisShort())
				.setTitle(movieInfos.getTitle())
				.setOriginalTitle(movieInfos.getOriginalTitle())
				.setKeywords(movieInfos.getMovie().getKeywords())
				.setTrailerName(movieInfos.getMovie().getTrailer() == null ? null : movieInfos.getMovie().getTrailer().getName())
				.setTrailerHRef(movieInfos.getMovie().getTrailer() == null ? null : movieInfos.getMovie().getTrailer().getHref())
				.setRuntime(movieInfos.getMovie().getRuntime() > 0 ? movieInfos.getMovie().getRuntime() : null)
				.setMovieType(movieInfos.getMovie().getMovieType().getName())
				.setProductionYear(movieInfos.getMovie().getProductionYear())
				.setPoster(poster)
				//>>			.setActors(actors.toString());
				.setActors(buildPersonLinks(movieInfos.getActors()))
				.setProducers(buildPersonLinks(movieInfos.getProducers()))
				.setWriters(buildPersonLinks(movieInfos.getWriters()))
				.setCamera(buildPersonLinks(movieInfos.getCamera()))
				.setDirectors(buildPersonLinks(movieInfos.getDirectors()))
				.setUserRating(movieInfos.getUserRating())
				.setPressRating(movieInfos.getPressRating());
	}
}
