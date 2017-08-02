/*
    Ce fichier a été généré automatiquement.
    Toute modification sera perdue.
*/

/* tslint:disable */
import { EntityField, StoreNode } from "focus4/entity";
import * as domains from "../../../common/domain"

export interface PersonIndex {
	perId: number;
	fullName?: string;
	fullNameSortOnly?: string;
	biography?: string;
	sex?: string;
	photoUrl?: string;
	birthDate?: string;
	birthPlace?: string;
	activity?: string;
	movies?: string;
}

export interface PersonIndexNode extends StoreNode<PersonIndex> {
	perId: EntityField<number>;
	fullName: EntityField<string>;
	fullNameSortOnly: EntityField<string>;
	biography: EntityField<string>;
	sex: EntityField<string>;
	photoUrl: EntityField<string>;
	birthDate: EntityField<string>;
	birthPlace: EntityField<string>;
	activity: EntityField<string>;
	movies: EntityField<string>;
}

export const PersonIndexEntity = {
    name: "personIndex",
    fields: {
        perId: {
            name: "perId",
            type: "field" as "field",
            domain: domains.DO_IDENTITY,
            isRequired: true,
            translationKey: "persons.personIndex.perId"
        },
        fullName: {
            name: "fullName",
            type: "field" as "field",
            domain: domains.DO_LABEL,
            isRequired: false,
            translationKey: "persons.personIndex.fullName"
        },
        fullNameSortOnly: {
            name: "fullNameSortOnly",
            type: "field" as "field",
            domain: domains.DO_TEXT_NOT_TOKENIZED,
            isRequired: false,
            translationKey: "persons.personIndex.fullNameSortOnly"
        },
        biography: {
            name: "biography",
            type: "field" as "field",
            domain: domains.DO_TEXT,
            isRequired: false,
            translationKey: "persons.personIndex.biography"
        },
        sex: {
            name: "sex",
            type: "field" as "field",
            domain: domains.DO_LABEL_SHORT,
            isRequired: false,
            translationKey: "persons.personIndex.sex"
        },
        photoUrl: {
            name: "photoUrl",
            type: "field" as "field",
            domain: domains.DO_HREF,
            isRequired: false,
            translationKey: "persons.personIndex.photoUrl"
        },
        birthDate: {
            name: "birthDate",
            type: "field" as "field",
            domain: domains.DO_DATE,
            isRequired: false,
            translationKey: "persons.personIndex.birthDate"
        },
        birthPlace: {
            name: "birthPlace",
            type: "field" as "field",
            domain: domains.DO_LABEL,
            isRequired: false,
            translationKey: "persons.personIndex.birthPlace"
        },
        activity: {
            name: "activity",
            type: "field" as "field",
            domain: domains.DO_MULTI_VALUES,
            isRequired: false,
            translationKey: "persons.personIndex.activity"
        },
        movies: {
            name: "movies",
            type: "field" as "field",
            domain: domains.DO_MULTI_VALUES,
            isRequired: false,
            translationKey: "persons.personIndex.movies"
        }
    }
};
