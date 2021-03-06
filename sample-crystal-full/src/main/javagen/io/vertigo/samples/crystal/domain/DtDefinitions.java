package io.vertigo.samples.crystal.domain;

import java.util.Arrays;
import java.util.Iterator;

import io.vertigo.core.lang.Generated;
import io.vertigo.datamodel.structure.definitions.DtFieldName;

/**
 * This class is automatically generated.
 * DO NOT EDIT THIS FILE DIRECTLY.
 */
@Generated
public final class DtDefinitions implements Iterable<Class<?>> {

	/**
	 * Enumération des DtDefinitions.
	 */
	public enum Definitions {
		/** Objet de données Actor. */
		Actor(io.vertigo.samples.crystal.domain.Actor.class),
		/** Objet de données Country. */
		Country(io.vertigo.samples.crystal.domain.Country.class),
		/** Objet de données Movie. */
		Movie(io.vertigo.samples.crystal.domain.Movie.class),
		/** Objet de données MovieIndex. */
		MovieIndex(io.vertigo.samples.crystal.domain.MovieIndex.class),
		/** Objet de données Role. */
		Role(io.vertigo.samples.crystal.domain.Role.class),
		/** Objet de données Sexe. */
		Sexe(io.vertigo.samples.crystal.domain.Sexe.class),
		/** Objet de données User. */
		User(io.vertigo.samples.crystal.domain.User.class),
		/** Objet de données UserGroup. */
		UserGroup(io.vertigo.samples.crystal.domain.UserGroup.class)		;

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
	 * Enumération des champs de Actor.
	 */
	public enum ActorFields implements DtFieldName<io.vertigo.samples.crystal.domain.Actor> {
		/** Propriété 'Id'. */
		actId,
		/** Propriété 'Nom'. */
		name,
		/** Propriété 'Sexe'. */
		sexCd	}

	/**
	 * Enumération des champs de Country.
	 */
	public enum CountryFields implements DtFieldName<io.vertigo.samples.crystal.domain.Country> {
		/** Propriété 'Id'. */
		couId,
		/** Propriété 'Nom du pays'. */
		name	}

	/**
	 * Enumération des champs de Movie.
	 */
	public enum MovieFields implements DtFieldName<io.vertigo.samples.crystal.domain.Movie> {
		/** Propriété 'Id'. */
		movId,
		/** Propriété 'Titre du film'. */
		name,
		/** Propriété 'Année'. */
		year,
		/** Propriété 'Id Imdb'. */
		imdbid,
		/** Propriété 'Country'. */
		couId	}

	/**
	 * Enumération des champs de MovieIndex.
	 */
	public enum MovieIndexFields implements DtFieldName<io.vertigo.samples.crystal.domain.MovieIndex> {
		/** Propriété 'id'. */
		movId,
		/** Propriété 'Titre'. */
		name,
		/** Propriété 'Année'. */
		year,
		/** Propriété 'Pays'. */
		country,
		/** Propriété 'Titre'. */
		nameSortOnly	}

	/**
	 * Enumération des champs de Role.
	 */
	public enum RoleFields implements DtFieldName<io.vertigo.samples.crystal.domain.Role> {
		/** Propriété 'Id'. */
		rolId,
		/** Propriété 'Dans le role de'. */
		asCharacter,
		/** Propriété 'Movie'. */
		movId,
		/** Propriété 'Actor'. */
		actId	}

	/**
	 * Enumération des champs de Sexe.
	 */
	public enum SexeFields implements DtFieldName<io.vertigo.samples.crystal.domain.Sexe> {
		/** Propriété 'Id'. */
		sexCd,
		/** Propriété 'Label'. */
		label	}

	/**
	 * Enumération des champs de User.
	 */
	public enum UserFields implements DtFieldName<io.vertigo.samples.crystal.domain.User> {
		/** Propriété 'Id'. */
		usrId,
		/** Propriété 'Login'. */
		login,
		/** Propriété 'Nom'. */
		name,
		/** Propriété 'email'. */
		email,
		/** Propriété 'Country'. */
		couId,
		/** Propriété 'Group'. */
		grpId	}

	/**
	 * Enumération des champs de UserGroup.
	 */
	public enum UserGroupFields implements DtFieldName<io.vertigo.samples.crystal.domain.UserGroup> {
		/** Propriété 'Id'. */
		grpId,
		/** Propriété 'Nom'. */
		name	}

	/** {@inheritDoc} */
	@Override
	public Iterator<Class<?>> iterator() {
		return new Iterator<>() {
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
		};
	}
}
