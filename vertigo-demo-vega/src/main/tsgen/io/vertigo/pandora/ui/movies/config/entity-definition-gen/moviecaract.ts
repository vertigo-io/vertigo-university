/*
    Ce fichier a été généré automatiquement.
    Toute modification sera perdue.
*/

/* tslint:disable */
import { EntityField, StoreNode } from "focus4/entity";
import * as domains from "../../../../00-core/domain"

export interface MovieCaract {
	title?: string;
	originalTitle?: string;
	keywords?: string;
	runtime?: number;
	movieType?: string;
	productionYear?: number;
	movId: number;
}

export interface MovieCaractNode extends StoreNode<MovieCaract> {
	title: EntityField<string, typeof domains.DoLabel>;
	originalTitle: EntityField<string, typeof domains.DoLabel>;
	keywords: EntityField<string, typeof domains.DoLabel>;
	runtime: EntityField<number, typeof domains.DoRuntime>;
	movieType: EntityField<string, typeof domains.DoLabelShort>;
	productionYear: EntityField<number, typeof domains.DoYear>;
	movId: EntityField<number, typeof domains.DoIdentity>;
}

export const MovieCaractEntity = {
    name: "movieCaract",
    fields: {
        title: {
            name: "title",
            type: "field" as "field",
            domain: domains.DoLabel,
            isRequired: false,
            translationKey: "movies.movieCaract.title"
        },
        originalTitle: {
            name: "originalTitle",
            type: "field" as "field",
            domain: domains.DoLabel,
            isRequired: false,
            translationKey: "movies.movieCaract.originalTitle"
        },
        keywords: {
            name: "keywords",
            type: "field" as "field",
            domain: domains.DoLabel,
            isRequired: false,
            translationKey: "movies.movieCaract.keywords"
        },
        runtime: {
            name: "runtime",
            type: "field" as "field",
            domain: domains.DoRuntime,
            isRequired: false,
            translationKey: "movies.movieCaract.runtime"
        },
        movieType: {
            name: "movieType",
            type: "field" as "field",
            domain: domains.DoLabelShort,
            isRequired: false,
            translationKey: "movies.movieCaract.movieType"
        },
        productionYear: {
            name: "productionYear",
            type: "field" as "field",
            domain: domains.DoYear,
            isRequired: false,
            translationKey: "movies.movieCaract.productionYear"
        },
        movId: {
            name: "movId",
            type: "field" as "field",
            domain: domains.DoIdentity,
            isRequired: true,
            translationKey: "movies.movieCaract.movId"
        }
    }
};
