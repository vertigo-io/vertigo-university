/*
    Ce fichier a été généré automatiquement.
    Toute modification sera perdue.
*/

/* tslint:disable */
import { EntityField, StoreNode } from "focus4/entity";
import * as domains from "../../../common/domain"

export interface MovieSynopsis {
	synopsis?: string;
	shortSynopsis?: string;
	movId: number;
}

export interface MovieSynopsisNode extends StoreNode<MovieSynopsis> {
	synopsis: EntityField<string>;
	shortSynopsis: EntityField<string>;
	movId: EntityField<number>;
}

export const MovieSynopsisEntity = {
    name: "movieSynopsis",
    fields: {
        synopsis: {
            name: "synopsis",
            type: "field" as "field",
            domain: domains.DO_TEXT,
            isRequired: false,
            translationKey: "movies.movieSynopsis.synopsis"
        },
        shortSynopsis: {
            name: "shortSynopsis",
            type: "field" as "field",
            domain: domains.DO_TEXT,
            isRequired: false,
            translationKey: "movies.movieSynopsis.shortSynopsis"
        },
        movId: {
            name: "movId",
            type: "field" as "field",
            domain: domains.DO_IDENTITY,
            isRequired: true,
            translationKey: "movies.movieSynopsis.movId"
        }
    }
};
