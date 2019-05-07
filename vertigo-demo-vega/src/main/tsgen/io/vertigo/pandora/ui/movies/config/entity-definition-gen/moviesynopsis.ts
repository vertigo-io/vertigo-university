/*
    Ce fichier a été généré automatiquement.
    Toute modification sera perdue.
*/

/* tslint:disable */
import { EntityField, StoreNode } from "focus4/entity";
import * as domains from "../../../../00-core/domain"

export interface MovieSynopsis {
	synopsis?: string;
	shortSynopsis?: string;
	movId: number;
}

export interface MovieSynopsisNode extends StoreNode<MovieSynopsis> {
	synopsis: EntityField<string, typeof domains.DoText>;
	shortSynopsis: EntityField<string, typeof domains.DoText>;
	movId: EntityField<number, typeof domains.DoIdentity>;
}

export const MovieSynopsisEntity = {
    name: "movieSynopsis",
    fields: {
        synopsis: {
            name: "synopsis",
            type: "field" as "field",
            domain: domains.DoText,
            isRequired: false,
            translationKey: "movies.movieSynopsis.synopsis"
        },
        shortSynopsis: {
            name: "shortSynopsis",
            type: "field" as "field",
            domain: domains.DoText,
            isRequired: false,
            translationKey: "movies.movieSynopsis.shortSynopsis"
        },
        movId: {
            name: "movId",
            type: "field" as "field",
            domain: domains.DoIdentity,
            isRequired: true,
            translationKey: "movies.movieSynopsis.movId"
        }
    }
};
