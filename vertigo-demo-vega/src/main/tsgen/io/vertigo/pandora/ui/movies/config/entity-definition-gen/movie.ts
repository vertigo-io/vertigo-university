/*
    Ce fichier a été généré automatiquement.
    Toute modification sera perdue.
*/

/* tslint:disable */
import { EntityField, StoreNode } from "focus4/entity";
import * as domains from "../../../common/domain"

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
	movId: EntityField<number>;
	title: EntityField<string>;
	originalTitle: EntityField<string>;
	synopsis: EntityField<string>;
	shortSynopsis: EntityField<string>;
	keywords: EntityField<string>;
	poster: EntityField<string>;
	trailerName: EntityField<string>;
	trailerHref: EntityField<string>;
	runtime: EntityField<number>;
	movieType: EntityField<string>;
	productionYear: EntityField<number>;
	userRating: EntityField<number>;
	pressRating: EntityField<number>;
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
