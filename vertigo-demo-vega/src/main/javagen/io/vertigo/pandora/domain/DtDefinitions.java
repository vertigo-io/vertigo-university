package io.vertigo.pandora.domain;

import java.util.Arrays;
import java.util.Iterator;
import io.vertigo.dynamo.domain.metamodel.DtFieldName;

/**
 * Attention cette classe est générée automatiquement !
 */
public final class DtDefinitions implements Iterable<Class<?>> {

	/**
	 * Enumération des DtDefinitions.
	 */
	public enum Definitions {
		/** Objet de données ActorRole. */
		ActorRole(io.vertigo.pandora.domain.persons.ActorRole.class),
		/** Objet de données Dummy. */
		Dummy(io.vertigo.pandora.domain.movies.Dummy.class),
		/** Objet de données Movie. */
		Movie(io.vertigo.pandora.domain.movies.Movie.class),
		/** Objet de données MovieCaract. */
		MovieCaract(io.vertigo.pandora.domain.movies.MovieCaract.class),
		/** Objet de données MovieIndex. */
		MovieIndex(io.vertigo.pandora.domain.movies.MovieIndex.class),
		/** Objet de données MovieLink. */
		MovieLink(io.vertigo.pandora.domain.movies.MovieLink.class),
		/** Objet de données MovieSynopsis. */
		MovieSynopsis(io.vertigo.pandora.domain.movies.MovieSynopsis.class),
		/** Objet de données MovieTrailer. */
		MovieTrailer(io.vertigo.pandora.domain.movies.MovieTrailer.class),
		/** Objet de données Person. */
		Person(io.vertigo.pandora.domain.persons.Person.class),
		/** Objet de données PersonActorRoleLink. */
		PersonActorRoleLink(io.vertigo.pandora.domain.persons.PersonActorRoleLink.class),
		/** Objet de données PersonIndex. */
		PersonIndex(io.vertigo.pandora.domain.persons.PersonIndex.class),
		/** Objet de données PersonLink. */
		PersonLink(io.vertigo.pandora.domain.persons.PersonLink.class),
		;

		private final Class<?> clazz;

		private Definitions(final Class<?> clazz) {
			this.clazz = clazz;
		}

		/** 
		 * Classe associée.
		 * @return Class d'implémentation de l'objet 
		 */
		public Class<?> getDtClass() {
			return clazz;
		}
	}

	/**
	 * Enumération des champs de ActorRole.
	 */
	public enum ActorRoleFields implements DtFieldName<io.vertigo.pandora.domain.persons.ActorRole> {
		/** Propriété 'ARO ID'. */
		ARO_ID,
		/** Propriété 'ROLE'. */
		ROLE,
		/** Propriété 'Actor'. */
		PER_ID,
		/** Propriété 'Movie'. */
		MOV_ID,
	}

	/**
	 * Enumération des champs de Dummy.
	 */
	public enum DummyFields implements DtFieldName<io.vertigo.pandora.domain.movies.Dummy> {
		/** Propriété 'value'. */
		VALUE,
	}

	/**
	 * Enumération des champs de Movie.
	 */
	public enum MovieFields implements DtFieldName<io.vertigo.pandora.domain.movies.Movie> {
		/** Propriété 'MOV ID'. */
		MOV_ID,
		/** Propriété 'TITLE'. */
		TITLE,
		/** Propriété 'ORIGINAL TITLE'. */
		ORIGINAL_TITLE,
		/** Propriété 'SYNOPSIS'. */
		SYNOPSIS,
		/** Propriété 'SHORT SYNOPSIS'. */
		SHORT_SYNOPSIS,
		/** Propriété 'KEYWORDS'. */
		KEYWORDS,
		/** Propriété 'POSTER'. */
		POSTER,
		/** Propriété 'TRAILER NAME'. */
		TRAILER_NAME,
		/** Propriété 'TRAILER HREF'. */
		TRAILER_HREF,
		/** Propriété 'RUNTIME'. */
		RUNTIME,
		/** Propriété 'MOVIE TYPE'. */
		MOVIE_TYPE,
		/** Propriété 'PRODUCTION YEAR'. */
		PRODUCTION_YEAR,
		/** Propriété 'USER RATING'. */
		USER_RATING,
		/** Propriété 'PRESS RATING'. */
		PRESS_RATING,
	}

	/**
	 * Enumération des champs de MovieCaract.
	 */
	public enum MovieCaractFields implements DtFieldName<io.vertigo.pandora.domain.movies.MovieCaract> {
		/** Propriété 'TITLE'. */
		TITLE,
		/** Propriété 'ORIGINAL TITLE'. */
		ORIGINAL_TITLE,
		/** Propriété 'KEYWORDS'. */
		KEYWORDS,
		/** Propriété 'RUNTIME'. */
		RUNTIME,
		/** Propriété 'MOVIE TYPE'. */
		MOVIE_TYPE,
		/** Propriété 'PRODUCTION YEAR'. */
		PRODUCTION_YEAR,
		/** Propriété 'MOV ID'. */
		MOV_ID,
	}

	/**
	 * Enumération des champs de MovieIndex.
	 */
	public enum MovieIndexFields implements DtFieldName<io.vertigo.pandora.domain.movies.MovieIndex> {
		/** Propriété 'id'. */
		MOV_ID,
		/** Propriété 'Title'. */
		TITLE,
		/** Propriété 'Title'. */
		TITLE_SORT_ONLY,
		/** Propriété 'Original Title'. */
		ORIGINAL_TITLE,
		/** Propriété 'Synopsis'. */
		SYNOPSIS,
		/** Propriété 'shortSynopsis'. */
		SHORT_SYNOPSIS,
		/** Propriété 'keywords'. */
		KEYWORDS,
		/** Propriété 'poster'. */
		POSTER,
		/** Propriété 'runtime'. */
		RUNTIME,
		/** Propriété 'Movie type'. */
		MOVIE_TYPE,
		/** Propriété 'productionYear'. */
		PRODUCTION_YEAR,
		/** Propriété 'userRating'. */
		USER_RATING,
		/** Propriété 'presRating'. */
		PRESS_RATING,
		/** Propriété 'Roles'. */
		ACTOR_ROLES,
		/** Propriété 'Writers'. */
		WRITERS,
		/** Propriété 'Camera'. */
		CAMERA,
		/** Propriété 'Producers'. */
		PRODUCERS,
		/** Propriété 'Directors'. */
		DIRECTORS,
	}

	/**
	 * Enumération des champs de MovieLink.
	 */
	public enum MovieLinkFields implements DtFieldName<io.vertigo.pandora.domain.movies.MovieLink> {
		/** Propriété 'TITLE'. */
		TITLE,
		/** Propriété 'POSTER'. */
		POSTER,
		/** Propriété 'MOVIE TYPE'. */
		MOVIE_TYPE,
		/** Propriété 'PRODUCTION YEAR'. */
		PRODUCTION_YEAR,
		/** Propriété 'MOV ID'. */
		MOV_ID,
	}

	/**
	 * Enumération des champs de MovieSynopsis.
	 */
	public enum MovieSynopsisFields implements DtFieldName<io.vertigo.pandora.domain.movies.MovieSynopsis> {
		/** Propriété 'SYNOPSIS'. */
		SYNOPSIS,
		/** Propriété 'SHORT SYNOPSIS'. */
		SHORT_SYNOPSIS,
		/** Propriété 'MOV ID'. */
		MOV_ID,
	}

	/**
	 * Enumération des champs de MovieTrailer.
	 */
	public enum MovieTrailerFields implements DtFieldName<io.vertigo.pandora.domain.movies.MovieTrailer> {
		/** Propriété 'TRAILER NAME'. */
		TRAILER_NAME,
		/** Propriété 'TRAILER HREF'. */
		TRAILER_HREF,
		/** Propriété 'MOV ID'. */
		MOV_ID,
	}

	/**
	 * Enumération des champs de Person.
	 */
	public enum PersonFields implements DtFieldName<io.vertigo.pandora.domain.persons.Person> {
		/** Propriété 'PER ID'. */
		PER_ID,
		/** Propriété 'FULL NAME'. */
		FULL_NAME,
		/** Propriété 'FIRST NAME'. */
		FIRST_NAME,
		/** Propriété 'LAST NAME'. */
		LAST_NAME,
		/** Propriété 'BIOGRAPHY'. */
		BIOGRAPHY,
		/** Propriété 'SHORT BIOGRAPHY'. */
		SHORT_BIOGRAPHY,
		/** Propriété 'SEX'. */
		SEX,
		/** Propriété 'PHOTO HREF'. */
		PHOTO_HREF,
		/** Propriété 'BIRTH DATE'. */
		BIRTH_DATE,
		/** Propriété 'BIRTH PLACE'. */
		BIRTH_PLACE,
		/** Propriété 'ACTIVITY'. */
		ACTIVITY,
	}

	/**
	 * Enumération des champs de PersonActorRoleLink.
	 */
	public enum PersonActorRoleLinkFields implements DtFieldName<io.vertigo.pandora.domain.persons.PersonActorRoleLink> {
		/** Propriété 'FULL NAME'. */
		FULL_NAME,
		/** Propriété 'PHOTO HREF'. */
		PHOTO_HREF,
		/** Propriété 'Role'. */
		ROLE,
		/** Propriété 'PER ID'. */
		PER_ID,
	}

	/**
	 * Enumération des champs de PersonIndex.
	 */
	public enum PersonIndexFields implements DtFieldName<io.vertigo.pandora.domain.persons.PersonIndex> {
		/** Propriété 'id'. */
		PER_ID,
		/** Propriété 'Nom'. */
		FULL_NAME,
		/** Propriété 'Nom'. */
		FULL_NAME_SORT_ONLY,
		/** Propriété 'Biographie'. */
		BIOGRAPHY,
		/** Propriété 'Sexe'. */
		SEX,
		/** Propriété 'photo'. */
		PHOTO_URL,
		/** Propriété 'Date de naissance'. */
		BIRTH_DATE,
		/** Propriété 'Lieu de naissance'. */
		BIRTH_PLACE,
		/** Propriété 'Activity'. */
		ACTIVITY,
		/** Propriété 'Movies'. */
		MOVIES,
	}

	/**
	 * Enumération des champs de PersonLink.
	 */
	public enum PersonLinkFields implements DtFieldName<io.vertigo.pandora.domain.persons.PersonLink> {
		/** Propriété 'FULL NAME'. */
		FULL_NAME,
		/** Propriété 'PHOTO HREF'. */
		PHOTO_HREF,
		/** Propriété 'Movie type'. */
		EXISTS_IN_BDD,
		/** Propriété 'PER ID'. */
		PER_ID,
	}

	/** {@inheritDoc} */
	@Override
	public Iterator<Class<?>> iterator() {
		return new Iterator<Class<?>>() {
			private Iterator<Definitions> it = Arrays.asList(Definitions.values()).iterator();

			/** {@inheritDoc} */
			@Override
			public boolean hasNext() {
				return it.hasNext();
			}

			/** {@inheritDoc} */
			@Override
			public Class<?> next() {
				return it.next().getDtClass();
			}

			/** {@inheritDoc} */
			@Override
			public void remove() {
				//unsupported
			}
		};
	}
}