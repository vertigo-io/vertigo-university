/*
    Ce fichier a été généré automatiquement.
    Toute modification sera perdue.
*/

/* tslint:disable */
import { EntityField, StoreNode } from "focus4/entity";
import * as domains from "../../../common/domain"

export interface MovieIndex {
	movId: number;
	title?: string;
	titleSortOnly?: string;
	originalTitle?: string;
	synopsis?: string;
	shortSynopsis?: string;
	keywords?: string;
	poster?: string;
	runtime?: number;
	movieType?: string;
	productionYear?: number;
	userRating?: number;
	pressRating?: number;
	actorRoles?: string;
	writers?: string;
	camera?: string;
	producers?: string;
	directors?: string;
}

export interface MovieIndexNode extends StoreNode<MovieIndex> {
	movId: EntityField<number>;
	title: EntityField<string>;
	titleSortOnly: EntityField<string>;
	originalTitle: EntityField<string>;
	synopsis: EntityField<string>;
	shortSynopsis: EntityField<string>;
	keywords: EntityField<string>;
	poster: EntityField<string>;
	runtime: EntityField<number>;
	movieType: EntityField<string>;
	productionYear: EntityField<number>;
	userRating: EntityField<number>;
	pressRating: EntityField<number>;
	actorRoles: EntityField<string>;
	writers: EntityField<string>;
	camera: EntityField<string>;
	producers: EntityField<string>;
	directors: EntityField<string>;
}

export const MovieIndexEntity = {
    name: "movieIndex",
    fields: {
        movId: {
            name: "movId",
            type: "field" as "field",
            domain: domains.DO_IDENTITY,
            isRequired: true,
            translationKey: "movies.movieIndex.movId"
        },
        title: {
            name: "title",
            type: "field" as "field",
            domain: domains.DO_LABEL,
            isRequired: false,
            translationKey: "movies.movieIndex.title"
        },
        titleSortOnly: {
            name: "titleSortOnly",
            type: "field" as "field",
            domain: domains.DO_TEXT_NOT_TOKENIZED,
            isRequired: false,
            translationKey: "movies.movieIndex.titleSortOnly"
        },
        originalTitle: {
            name: "originalTitle",
            type: "field" as "field",
            domain: domains.DO_LABEL,
            isRequired: false,
            translationKey: "movies.movieIndex.originalTitle"
        },
        synopsis: {
            name: "synopsis",
            type: "field" as "field",
            domain: domains.DO_TEXT,
            isRequired: false,
            translationKey: "movies.movieIndex.synopsis"
        },
        shortSynopsis: {
            name: "shortSynopsis",
            type: "field" as "field",
            domain: domains.DO_TEXT,
            isRequired: false,
            translationKey: "movies.movieIndex.shortSynopsis"
        },
        keywords: {
            name: "keywords",
            type: "field" as "field",
            domain: domains.DO_LABEL,
            isRequired: false,
            translationKey: "movies.movieIndex.keywords"
        },
        poster: {
            name: "poster",
            type: "field" as "field",
            domain: domains.DO_TEXT_NOT_TOKENIZED,
            isRequired: false,
            translationKey: "movies.movieIndex.poster"
        },
        runtime: {
            name: "runtime",
            type: "field" as "field",
            domain: domains.DO_RUNTIME,
            isRequired: false,
            translationKey: "movies.movieIndex.runtime"
        },
        movieType: {
            name: "movieType",
            type: "field" as "field",
            domain: domains.DO_LABEL_SHORT,
            isRequired: false,
            translationKey: "movies.movieIndex.movieType"
        },
        productionYear: {
            name: "productionYear",
            type: "field" as "field",
            domain: domains.DO_YEAR,
            isRequired: false,
            translationKey: "movies.movieIndex.productionYear"
        },
        userRating: {
            name: "userRating",
            type: "field" as "field",
            domain: domains.DO_RATING,
            isRequired: false,
            translationKey: "movies.movieIndex.userRating"
        },
        pressRating: {
            name: "pressRating",
            type: "field" as "field",
            domain: domains.DO_RATING,
            isRequired: false,
            translationKey: "movies.movieIndex.pressRating"
        },
        actorRoles: {
            name: "actorRoles",
            type: "field" as "field",
            domain: domains.DO_MULTI_VALUES,
            isRequired: false,
            translationKey: "movies.movieIndex.actorRoles"
        },
        writers: {
            name: "writers",
            type: "field" as "field",
            domain: domains.DO_MULTI_VALUES,
            isRequired: false,
            translationKey: "movies.movieIndex.writers"
        },
        camera: {
            name: "camera",
            type: "field" as "field",
            domain: domains.DO_MULTI_VALUES,
            isRequired: false,
            translationKey: "movies.movieIndex.camera"
        },
        producers: {
            name: "producers",
            type: "field" as "field",
            domain: domains.DO_MULTI_VALUES,
            isRequired: false,
            translationKey: "movies.movieIndex.producers"
        },
        directors: {
            name: "directors",
            type: "field" as "field",
            domain: domains.DO_MULTI_VALUES,
            isRequired: false,
            translationKey: "movies.movieIndex.directors"
        }
    }
};
