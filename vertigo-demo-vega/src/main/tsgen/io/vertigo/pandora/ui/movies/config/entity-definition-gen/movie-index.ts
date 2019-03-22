/*
    Ce fichier a été généré automatiquement.
    Toute modification sera perdue.
*/

/* tslint:disable */
import { EntityField, StoreNode } from "focus4/entity";
import * as domains from "../../../../00-core/domain"

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
	movId: EntityField<number, typeof domains.DO_IDENTITY>;
	title: EntityField<string, typeof domains.DO_LABEL>;
	titleSortOnly: EntityField<string, typeof domains.DO_TEXT_NOT_TOKENIZED>;
	originalTitle: EntityField<string, typeof domains.DO_LABEL>;
	synopsis: EntityField<string, typeof domains.DO_TEXT>;
	shortSynopsis: EntityField<string, typeof domains.DO_TEXT>;
	keywords: EntityField<string, typeof domains.DO_LABEL>;
	poster: EntityField<string, typeof domains.DO_TEXT_NOT_TOKENIZED>;
	runtime: EntityField<number, typeof domains.DO_RUNTIME>;
	movieType: EntityField<string, typeof domains.DO_LABEL_SHORT>;
	productionYear: EntityField<number, typeof domains.DO_YEAR>;
	userRating: EntityField<number, typeof domains.DO_RATING>;
	pressRating: EntityField<number, typeof domains.DO_RATING>;
	actorRoles: EntityField<string, typeof domains.DO_MULTI_VALUES>;
	writers: EntityField<string, typeof domains.DO_MULTI_VALUES>;
	camera: EntityField<string, typeof domains.DO_MULTI_VALUES>;
	producers: EntityField<string, typeof domains.DO_MULTI_VALUES>;
	directors: EntityField<string, typeof domains.DO_MULTI_VALUES>;
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
