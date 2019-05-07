/*
    Ce fichier a été généré automatiquement.
    Toute modification sera perdue.
*/

/* tslint:disable */
import { EntityField, StoreNode } from "focus4/entity";
import * as domains from "../../../../00-core/domain"

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
	perId: EntityField<number, typeof domains.DoIdentity>;
	fullName: EntityField<string, typeof domains.DoLabel>;
	fullNameSortOnly: EntityField<string, typeof domains.DoTextNotTokenized>;
	biography: EntityField<string, typeof domains.DoText>;
	sex: EntityField<string, typeof domains.DoLabelShort>;
	photoUrl: EntityField<string, typeof domains.DoHref>;
	birthDate: EntityField<string, typeof domains.DoDate>;
	birthPlace: EntityField<string, typeof domains.DoLabel>;
	activity: EntityField<string, typeof domains.DoMultiValues>;
	movies: EntityField<string, typeof domains.DoMultiValues>;
}

export const PersonIndexEntity = {
    name: "personIndex",
    fields: {
        perId: {
            name: "perId",
            type: "field" as "field",
            domain: domains.DoIdentity,
            isRequired: true,
            translationKey: "persons.personIndex.perId"
        },
        fullName: {
            name: "fullName",
            type: "field" as "field",
            domain: domains.DoLabel,
            isRequired: false,
            translationKey: "persons.personIndex.fullName"
        },
        fullNameSortOnly: {
            name: "fullNameSortOnly",
            type: "field" as "field",
            domain: domains.DoTextNotTokenized,
            isRequired: false,
            translationKey: "persons.personIndex.fullNameSortOnly"
        },
        biography: {
            name: "biography",
            type: "field" as "field",
            domain: domains.DoText,
            isRequired: false,
            translationKey: "persons.personIndex.biography"
        },
        sex: {
            name: "sex",
            type: "field" as "field",
            domain: domains.DoLabelShort,
            isRequired: false,
            translationKey: "persons.personIndex.sex"
        },
        photoUrl: {
            name: "photoUrl",
            type: "field" as "field",
            domain: domains.DoHref,
            isRequired: false,
            translationKey: "persons.personIndex.photoUrl"
        },
        birthDate: {
            name: "birthDate",
            type: "field" as "field",
            domain: domains.DoDate,
            isRequired: false,
            translationKey: "persons.personIndex.birthDate"
        },
        birthPlace: {
            name: "birthPlace",
            type: "field" as "field",
            domain: domains.DoLabel,
            isRequired: false,
            translationKey: "persons.personIndex.birthPlace"
        },
        activity: {
            name: "activity",
            type: "field" as "field",
            domain: domains.DoMultiValues,
            isRequired: false,
            translationKey: "persons.personIndex.activity"
        },
        movies: {
            name: "movies",
            type: "field" as "field",
            domain: domains.DoMultiValues,
            isRequired: false,
            translationKey: "persons.personIndex.movies"
        }
    }
};
