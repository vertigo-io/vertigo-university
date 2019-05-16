/*
    Ce fichier a été généré automatiquement.
    Toute modification sera perdue.
*/

/* tslint:disable */
import { EntityField, StoreNode } from "focus4/entity";
import * as domains from "../../../../00-core/domain"

export interface MovieTrailer {
	trailerName?: string;
	trailerHref?: string;
	movId: number;
}

export interface MovieTrailerNode extends StoreNode<MovieTrailer> {
	trailerName: EntityField<string, typeof domains.DoLabel>;
	trailerHref: EntityField<string, typeof domains.DoHref>;
	movId: EntityField<number, typeof domains.DoIdentity>;
}

export const MovieTrailerEntity = {
    name: "movieTrailer",
    fields: {
        trailerName: {
            name: "trailerName",
            type: "field" as "field",
            domain: domains.DoLabel,
            isRequired: false,
            translationKey: "movies.movieTrailer.trailerName"
        },
        trailerHref: {
            name: "trailerHref",
            type: "field" as "field",
            domain: domains.DoHref,
            isRequired: false,
            translationKey: "movies.movieTrailer.trailerHref"
        },
        movId: {
            name: "movId",
            type: "field" as "field",
            domain: domains.DoIdentity,
            isRequired: true,
            translationKey: "movies.movieTrailer.movId"
        }
    }
};
