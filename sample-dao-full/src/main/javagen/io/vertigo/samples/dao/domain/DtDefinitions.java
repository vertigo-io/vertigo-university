package io.vertigo.samples.dao.domain;

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
			/** Objet de données Actor. */
			Actor(io.vertigo.samples.dao.domain.Actor.class),
			/** Objet de données Country. */
			Country(io.vertigo.samples.dao.domain.Country.class),
			/** Objet de données Movie. */
			Movie(io.vertigo.samples.dao.domain.Movie.class),
			/** Objet de données MovieByYear. */
			MovieByYear(io.vertigo.samples.dao.domain.MovieByYear.class),
			/** Objet de données MovieDisplay. */
			MovieDisplay(io.vertigo.samples.dao.domain.MovieDisplay.class),
			/** Objet de données MyActor. */
			MyActor(io.vertigo.samples.dao.domain.MyActor.class),
			/** Objet de données MyCountry. */
			MyCountry(io.vertigo.samples.dao.domain.MyCountry.class),
			/** Objet de données MyMovie. */
			MyMovie(io.vertigo.samples.dao.domain.MyMovie.class),
			/** Objet de données MyRole. */
			MyRole(io.vertigo.samples.dao.domain.MyRole.class),
			/** Objet de données Role. */
			Role(io.vertigo.samples.dao.domain.Role.class),
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
	 * Enumération des champs de Actor.
	 */
	public enum ActorFields implements DtFieldName {
		/** Propriété 'Id'. */
		ACT_ID,
		/** Propriété 'Nom'. */
		NAME,
		/** Propriété 'Sexe'. */
		SEXE,
	}

	/**
	 * Enumération des champs de Country.
	 */
	public enum CountryFields implements DtFieldName {
		/** Propriété 'Id'. */
		COU_ID,
		/** Propriété 'Code du pays'. */
		NAME,
	}

	/**
	 * Enumération des champs de Movie.
	 */
	public enum MovieFields implements DtFieldName {
		/** Propriété 'Id'. */
		MOV_ID,
		/** Propriété 'Code du pays'. */
		NAME,
		/** Propriété 'Année'. */
		YEAR,
		/** Propriété 'Id Imdb'. */
		IMDBID,
		/** Propriété 'Country'. */
		COU_ID,
	}

	/**
	 * Enumération des champs de MovieByYear.
	 */
	public enum MovieByYearFields implements DtFieldName {
		/** Propriété 'Année'. */
		YEAR,
		/** Propriété 'Nombre de film'. */
		MOVIES_COUNT,
	}

	/**
	 * Enumération des champs de MovieDisplay.
	 */
	public enum MovieDisplayFields implements DtFieldName {
		/** Propriété 'Titre'. */
		NAME,
		/** Propriété 'Année'. */
		YEAR,
		/** Propriété 'Pays'. */
		COUNTRY,
		/** Propriété 'Nombre d'acteur'. */
		ACTORS_COUNT,
	}

	/**
	 * Enumération des champs de MyActor.
	 */
	public enum MyActorFields implements DtFieldName {
		/** Propriété 'Id'. */
		ACT_ID,
		/** Propriété 'Nom'. */
		NAME,
		/** Propriété 'Sexe'. */
		SEXE,
	}

	/**
	 * Enumération des champs de MyCountry.
	 */
	public enum MyCountryFields implements DtFieldName {
		/** Propriété 'Id'. */
		COU_ID,
		/** Propriété 'Code du pays'. */
		NAME,
	}

	/**
	 * Enumération des champs de MyMovie.
	 */
	public enum MyMovieFields implements DtFieldName {
		/** Propriété 'Id'. */
		MOV_ID,
		/** Propriété 'Code du pays'. */
		NAME,
		/** Propriété 'Année'. */
		YEAR,
		/** Propriété 'Id Imdb'. */
		IMDBID,
		/** Propriété 'Country'. */
		COU_ID,
	}

	/**
	 * Enumération des champs de MyRole.
	 */
	public enum MyRoleFields implements DtFieldName {
		/** Propriété 'Id'. */
		ROL_ID,
		/** Propriété 'Dans le role de'. */
		AS_CHARACTER,
		/** Propriété 'Movie'. */
		MOV_ID,
		/** Propriété 'Actor'. */
		ACT_ID,
	}

	/**
	 * Enumération des champs de Role.
	 */
	public enum RoleFields implements DtFieldName {
		/** Propriété 'Id'. */
		ROL_ID,
		/** Propriété 'Dans le role de'. */
		AS_CHARACTER,
		/** Propriété 'Movie'. */
		MOV_ID,
		/** Propriété 'Actor'. */
		ACT_ID,
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
