/*
    Ce fichier a été généré automatiquement.
    Toute modification sera perdue.
*/

/* tslint:disable */
import { EntityField, StoreNode } from "focus4/entity";
import * as domains from "../../../../00-core/domain"

export interface Movie {
	movId: number;
	title?: string;
	originalTitle?: string;
	synopsis?: string;
	shortSynopsis?: string;
	keywords?: string;
	poster?: string;
	trailerName?: string;
	trailerHref?: string;
	runtime?: number;
	movieType?: string;
	productionYear?: number;
	userRating?: number;
	pressRating?: number;
}

export interface MovieNode extends StoreNode<Movie> {
	movId: EntityField<number, typeof domains.DoIdentity>;
	title: EntityField<string, typeof domains.DoLabel>;
	originalTitle: EntityField<string, typeof domains.DoLabel>;
	synopsis: EntityField<string, typeof domains.DoText>;
	shortSynopsis: EntityField<string, typeof domains.DoText>;
	keywords: EntityField<string, typeof domains.DoLabel>;
	poster: EntityField<string, typeof domains.DoHref>;
	trailerName: EntityField<string, typeof domains.DoLabel>;
	trailerHref: EntityField<string, typeof domains.DoHref>;
	runtime: EntityField<number, typeof domains.DoRuntime>;
	movieType: EntityField<string, typeof domains.DoLabelShort>;
	productionYear: EntityField<number, typeof domains.DoYear>;
	userRating: EntityField<number, typeof domains.DoRating>;
	pressRating: EntityField<number, typeof domains.DoRating>;
}

export const MovieEntity = {
    name: "movie",
    fields: {
        movId: {
            name: "movId",
            type: "field" as "field",
            domain: domains.DoIdentity,
            isRequired: true,
            translationKey: "movies.movie.movId"
        },
        title: {
            name: "title",
            type: "field" as "field",
            domain: domains.DoLabel,
            isRequired: false,
            translationKey: "movies.movie.title"
        },
        originalTitle: {
            name: "originalTitle",
            type: "field" as "field",
            domain: domains.DoLabel,
            isRequired: false,
            translationKey: "movies.movie.originalTitle"
        },
        synopsis: {
            name: "synopsis",
            type: "field" as "field",
            domain: domains.DoText,
            isRequired: false,
            translationKey: "movies.movie.synopsis"
        },
        shortSynopsis: {
            name: "shortSynopsis",
            type: "field" as "field",
            domain: domains.DoText,
            isRequired: false,
            translationKey: "movies.movie.shortSynopsis"
        },
        keywords: {
            name: "keywords",
            type: "field" as "field",
            domain: domains.DoLabel,
            isRequired: false,
            translationKey: "movies.movie.keywords"
        },
        poster: {
            name: "poster",
            type: "field" as "field",
            domain: domains.DoHref,
            isRequired: false,
            translationKey: "movies.movie.poster"
        },
        trailerName: {
            name: "trailerName",
            type: "field" as "field",
            domain: domains.DoLabel,
            isRequired: false,
            translationKey: "movies.movie.trailerName"
        },
        trailerHref: {
            name: "trailerHref",
            type: "field" as "field",
            domain: domains.DoHref,
            isRequired: false,
            translationKey: "movies.movie.trailerHref"
        },
        runtime: {
            name: "runtime",
            type: "field" as "field",
            domain: domains.DoRuntime,
            isRequired: false,
            translationKey: "movies.movie.runtime"
        },
        movieType: {
            name: "movieType",
            type: "field" as "field",
            domain: domains.DoLabelShort,
            isRequired: false,
            translationKey: "movies.movie.movieType"
        },
        productionYear: {
            name: "productionYear",
            type: "field" as "field",
            domain: domains.DoYear,
            isRequired: false,
            translationKey: "movies.movie.productionYear"
        },
        userRating: {
            name: "userRating",
            type: "field" as "field",
            domain: domains.DoRating,
            isRequired: false,
            translationKey: "movies.movie.userRating"
        },
        pressRating: {
            name: "pressRating",
            type: "field" as "field",
            domain: domains.DoRating,
            isRequired: false,
            translationKey: "movies.movie.pressRating"
        }
    }
};
