/*
    Ce fichier a été généré automatiquement.
    Toute modification sera perdue.
*/

/* tslint:disable */
import { EntityField, StoreNode } from "focus4/entity";
import * as domains from "../../../../00-core/domain"

export interface MovieLink {
	title?: string;
	poster?: string;
	movieType?: string;
	productionYear?: number;
	movId: number;
}

export interface MovieLinkNode extends StoreNode<MovieLink> {
	title: EntityField<string, typeof domains.DoLabel>;
	poster: EntityField<string, typeof domains.DoHref>;
	movieType: EntityField<string, typeof domains.DoLabelShort>;
	productionYear: EntityField<number, typeof domains.DoYear>;
	movId: EntityField<number, typeof domains.DoIdentity>;
}

export const MovieLinkEntity = {
    name: "movieLink",
    fields: {
        title: {
            name: "title",
            type: "field" as "field",
            domain: domains.DoLabel,
            isRequired: false,
            translationKey: "movies.movieLink.title"
        },
        poster: {
            name: "poster",
            type: "field" as "field",
            domain: domains.DoHref,
            isRequired: false,
            translationKey: "movies.movieLink.poster"
        },
        movieType: {
            name: "movieType",
            type: "field" as "field",
            domain: domains.DoLabelShort,
            isRequired: false,
            translationKey: "movies.movieLink.movieType"
        },
        productionYear: {
            name: "productionYear",
            type: "field" as "field",
            domain: domains.DoYear,
            isRequired: false,
            translationKey: "movies.movieLink.productionYear"
        },
        movId: {
            name: "movId",
            type: "field" as "field",
            domain: domains.DoIdentity,
            isRequired: true,
            translationKey: "movies.movieLink.movId"
        }
    }
};
