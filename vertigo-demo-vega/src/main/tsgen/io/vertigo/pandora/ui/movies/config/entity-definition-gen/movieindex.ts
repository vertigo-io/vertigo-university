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
	movId: EntityField<number, typeof domains.DoIdentity>;
	title: EntityField<string, typeof domains.DoLabel>;
	titleSortOnly: EntityField<string, typeof domains.DoTextNotTokenized>;
	originalTitle: EntityField<string, typeof domains.DoLabel>;
	synopsis: EntityField<string, typeof domains.DoText>;
	shortSynopsis: EntityField<string, typeof domains.DoText>;
	keywords: EntityField<string, typeof domains.DoLabel>;
	poster: EntityField<string, typeof domains.DoTextNotTokenized>;
	runtime: EntityField<number, typeof domains.DoRuntime>;
	movieType: EntityField<string, typeof domains.DoLabelShort>;
	productionYear: EntityField<number, typeof domains.DoYear>;
	userRating: EntityField<number, typeof domains.DoRating>;
	pressRating: EntityField<number, typeof domains.DoRating>;
	actorRoles: EntityField<string, typeof domains.DoMultiValues>;
	writers: EntityField<string, typeof domains.DoMultiValues>;
	camera: EntityField<string, typeof domains.DoMultiValues>;
	producers: EntityField<string, typeof domains.DoMultiValues>;
	directors: EntityField<string, typeof domains.DoMultiValues>;
}

export const MovieIndexEntity = {
    name: "movieIndex",
    fields: {
        movId: {
            name: "movId",
            type: "field" as "field",
            domain: domains.DoIdentity,
            isRequired: true,
            translationKey: "movies.movieIndex.movId"
        },
        title: {
            name: "title",
            type: "field" as "field",
            domain: domains.DoLabel,
            isRequired: false,
            translationKey: "movies.movieIndex.title"
        },
        titleSortOnly: {
            name: "titleSortOnly",
            type: "field" as "field",
            domain: domains.DoTextNotTokenized,
            isRequired: false,
            translationKey: "movies.movieIndex.titleSortOnly"
        },
        originalTitle: {
            name: "originalTitle",
            type: "field" as "field",
            domain: domains.DoLabel,
            isRequired: false,
            translationKey: "movies.movieIndex.originalTitle"
        },
        synopsis: {
            name: "synopsis",
            type: "field" as "field",
            domain: domains.DoText,
            isRequired: false,
            translationKey: "movies.movieIndex.synopsis"
        },
        shortSynopsis: {
            name: "shortSynopsis",
            type: "field" as "field",
            domain: domains.DoText,
            isRequired: false,
            translationKey: "movies.movieIndex.shortSynopsis"
        },
        keywords: {
            name: "keywords",
            type: "field" as "field",
            domain: domains.DoLabel,
            isRequired: false,
            translationKey: "movies.movieIndex.keywords"
        },
        poster: {
            name: "poster",
            type: "field" as "field",
            domain: domains.DoTextNotTokenized,
            isRequired: false,
            translationKey: "movies.movieIndex.poster"
        },
        runtime: {
            name: "runtime",
            type: "field" as "field",
            domain: domains.DoRuntime,
            isRequired: false,
            translationKey: "movies.movieIndex.runtime"
        },
        movieType: {
            name: "movieType",
            type: "field" as "field",
            domain: domains.DoLabelShort,
            isRequired: false,
            translationKey: "movies.movieIndex.movieType"
        },
        productionYear: {
            name: "productionYear",
            type: "field" as "field",
            domain: domains.DoYear,
            isRequired: false,
            translationKey: "movies.movieIndex.productionYear"
        },
        userRating: {
            name: "userRating",
            type: "field" as "field",
            domain: domains.DoRating,
            isRequired: false,
            translationKey: "movies.movieIndex.userRating"
        },
        pressRating: {
            name: "pressRating",
            type: "field" as "field",
            domain: domains.DoRating,
            isRequired: false,
            translationKey: "movies.movieIndex.pressRating"
        },
        actorRoles: {
            name: "actorRoles",
            type: "field" as "field",
            domain: domains.DoMultiValues,
            isRequired: false,
            translationKey: "movies.movieIndex.actorRoles"
        },
        writers: {
            name: "writers",
            type: "field" as "field",
            domain: domains.DoMultiValues,
            isRequired: false,
            translationKey: "movies.movieIndex.writers"
        },
        camera: {
            name: "camera",
            type: "field" as "field",
            domain: domains.DoMultiValues,
            isRequired: false,
            translationKey: "movies.movieIndex.camera"
        },
        producers: {
            name: "producers",
            type: "field" as "field",
            domain: domains.DoMultiValues,
            isRequired: false,
            translationKey: "movies.movieIndex.producers"
        },
        directors: {
            name: "directors",
            type: "field" as "field",
            domain: domains.DoMultiValues,
            isRequired: false,
            translationKey: "movies.movieIndex.directors"
        }
    }
};
