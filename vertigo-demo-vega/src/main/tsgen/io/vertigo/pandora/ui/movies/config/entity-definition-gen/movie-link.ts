/*
    Ce fichier a été généré automatiquement.
    Toute modification sera perdue.
*/

/* tslint:disable */
import { EntityField, StoreNode } from "focus4/entity";
import * as domains from "../../../common/domain"

export interface MovieLink {
	title?: string;
	poster?: string;
	movieType?: string;
	productionYear?: number;
	movId: number;
}

export interface MovieLinkNode extends StoreNode<MovieLink> {
	title: EntityField<string>;
	poster: EntityField<string>;
	movieType: EntityField<string>;
	productionYear: EntityField<number>;
	movId: EntityField<number>;
}

export const MovieLinkEntity = {
    name: "movieLink",
    fields: {
        title: {
            name: "title",
            type: "field" as "field",
            domain: domains.DO_LABEL,
            isRequired: false,
            translationKey: "movies.movieLink.title"
        },
        poster: {
            name: "poster",
            type: "field" as "field",
            domain: domains.DO_HREF,
            isRequired: false,
            translationKey: "movies.movieLink.poster"
        },
        movieType: {
            name: "movieType",
            type: "field" as "field",
            domain: domains.DO_LABEL_SHORT,
            isRequired: false,
            translationKey: "movies.movieLink.movieType"
        },
        productionYear: {
            name: "productionYear",
            type: "field" as "field",
            domain: domains.DO_YEAR,
            isRequired: false,
            translationKey: "movies.movieLink.productionYear"
        },
        movId: {
            name: "movId",
            type: "field" as "field",
            domain: domains.DO_IDENTITY,
            isRequired: true,
            translationKey: "movies.movieLink.movId"
        }
    }
};
