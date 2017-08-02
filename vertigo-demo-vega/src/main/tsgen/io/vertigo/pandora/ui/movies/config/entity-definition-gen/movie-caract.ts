/*
    Ce fichier a été généré automatiquement.
    Toute modification sera perdue.
*/

/* tslint:disable */
import { EntityField, StoreNode } from "focus4/entity";
import * as domains from "../../../common/domain"

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
	title: EntityField<string>;
	originalTitle: EntityField<string>;
	keywords: EntityField<string>;
	runtime: EntityField<number>;
	movieType: EntityField<string>;
	productionYear: EntityField<number>;
	movId: EntityField<number>;
}

export const MovieCaractEntity = {
    name: "movieCaract",
    fields: {
        title: {
            name: "title",
            type: "field" as "field",
            domain: domains.DO_LABEL,
            isRequired: false,
            translationKey: "movies.movieCaract.title"
        },
        originalTitle: {
            name: "originalTitle",
            type: "field" as "field",
            domain: domains.DO_LABEL,
            isRequired: false,
            translationKey: "movies.movieCaract.originalTitle"
        },
        keywords: {
            name: "keywords",
            type: "field" as "field",
            domain: domains.DO_LABEL,
            isRequired: false,
            translationKey: "movies.movieCaract.keywords"
        },
        runtime: {
            name: "runtime",
            type: "field" as "field",
            domain: domains.DO_RUNTIME,
            isRequired: false,
            translationKey: "movies.movieCaract.runtime"
        },
        movieType: {
            name: "movieType",
            type: "field" as "field",
            domain: domains.DO_LABEL_SHORT,
            isRequired: false,
            translationKey: "movies.movieCaract.movieType"
        },
        productionYear: {
            name: "productionYear",
            type: "field" as "field",
            domain: domains.DO_YEAR,
            isRequired: false,
            translationKey: "movies.movieCaract.productionYear"
        },
        movId: {
            name: "movId",
            type: "field" as "field",
            domain: domains.DO_IDENTITY,
            isRequired: true,
            translationKey: "movies.movieCaract.movId"
        }
    }
};
