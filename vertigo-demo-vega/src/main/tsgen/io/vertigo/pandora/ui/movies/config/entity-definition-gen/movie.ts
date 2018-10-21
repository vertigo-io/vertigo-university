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
	movId: EntityField<number, typeof domains.DO_IDENTITY>;
	title: EntityField<string, typeof domains.DO_LABEL>;
	originalTitle: EntityField<string, typeof domains.DO_LABEL>;
	synopsis: EntityField<string, typeof domains.DO_TEXT>;
	shortSynopsis: EntityField<string, typeof domains.DO_TEXT>;
	keywords: EntityField<string, typeof domains.DO_LABEL>;
	poster: EntityField<string, typeof domains.DO_HREF>;
	trailerName: EntityField<string, typeof domains.DO_LABEL>;
	trailerHref: EntityField<string, typeof domains.DO_HREF>;
	runtime: EntityField<number, typeof domains.DO_RUNTIME>;
	movieType: EntityField<string, typeof domains.DO_LABEL_SHORT>;
	productionYear: EntityField<number, typeof domains.DO_YEAR>;
	userRating: EntityField<number, typeof domains.DO_RATING>;
	pressRating: EntityField<number, typeof domains.DO_RATING>;
}

export const MovieEntity = {
    name: "movie",
    fields: {
        movId: {
            name: "movId",
            type: "field" as "field",
            domain: domains.DO_IDENTITY,
            isRequired: true,
            translationKey: "movies.movie.movId"
        },
        title: {
            name: "title",
            type: "field" as "field",
            domain: domains.DO_LABEL,
            isRequired: false,
            translationKey: "movies.movie.title"
        },
        originalTitle: {
            name: "originalTitle",
            type: "field" as "field",
            domain: domains.DO_LABEL,
            isRequired: false,
            translationKey: "movies.movie.originalTitle"
        },
        synopsis: {
            name: "synopsis",
            type: "field" as "field",
            domain: domains.DO_TEXT,
            isRequired: false,
            translationKey: "movies.movie.synopsis"
        },
        shortSynopsis: {
            name: "shortSynopsis",
            type: "field" as "field",
            domain: domains.DO_TEXT,
            isRequired: false,
            translationKey: "movies.movie.shortSynopsis"
        },
        keywords: {
            name: "keywords",
            type: "field" as "field",
            domain: domains.DO_LABEL,
            isRequired: false,
            translationKey: "movies.movie.keywords"
        },
        poster: {
            name: "poster",
            type: "field" as "field",
            domain: domains.DO_HREF,
            isRequired: false,
            translationKey: "movies.movie.poster"
        },
        trailerName: {
            name: "trailerName",
            type: "field" as "field",
            domain: domains.DO_LABEL,
            isRequired: false,
            translationKey: "movies.movie.trailerName"
        },
        trailerHref: {
            name: "trailerHref",
            type: "field" as "field",
            domain: domains.DO_HREF,
            isRequired: false,
            translationKey: "movies.movie.trailerHref"
        },
        runtime: {
            name: "runtime",
            type: "field" as "field",
            domain: domains.DO_RUNTIME,
            isRequired: false,
            translationKey: "movies.movie.runtime"
        },
        movieType: {
            name: "movieType",
            type: "field" as "field",
            domain: domains.DO_LABEL_SHORT,
            isRequired: false,
            translationKey: "movies.movie.movieType"
        },
        productionYear: {
            name: "productionYear",
            type: "field" as "field",
            domain: domains.DO_YEAR,
            isRequired: false,
            translationKey: "movies.movie.productionYear"
        },
        userRating: {
            name: "userRating",
            type: "field" as "field",
            domain: domains.DO_RATING,
            isRequired: false,
            translationKey: "movies.movie.userRating"
        },
        pressRating: {
            name: "pressRating",
            type: "field" as "field",
            domain: domains.DO_RATING,
            isRequired: false,
            translationKey: "movies.movie.pressRating"
        }
    }
};
