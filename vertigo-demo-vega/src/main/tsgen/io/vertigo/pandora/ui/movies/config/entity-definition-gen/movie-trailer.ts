/*
    Ce fichier a été généré automatiquement.
    Toute modification sera perdue.
*/

/* tslint:disable */
import { EntityField, StoreNode } from "focus4/entity";
import * as domains from "../../../common/domain"

export interface MovieTrailer {
	trailerName?: string;
	trailerHref?: string;
	movId: number;
}

export interface MovieTrailerNode extends StoreNode<MovieTrailer> {
	trailerName: EntityField<string>;
	trailerHref: EntityField<string>;
	movId: EntityField<number>;
}

export const MovieTrailerEntity = {
    name: "movieTrailer",
    fields: {
        trailerName: {
            name: "trailerName",
            type: "field" as "field",
            domain: domains.DO_LABEL,
            isRequired: false,
            translationKey: "movies.movieTrailer.trailerName"
        },
        trailerHref: {
            name: "trailerHref",
            type: "field" as "field",
            domain: domains.DO_HREF,
            isRequired: false,
            translationKey: "movies.movieTrailer.trailerHref"
        },
        movId: {
            name: "movId",
            type: "field" as "field",
            domain: domains.DO_IDENTITY,
            isRequired: true,
            translationKey: "movies.movieTrailer.movId"
        }
    }
};
