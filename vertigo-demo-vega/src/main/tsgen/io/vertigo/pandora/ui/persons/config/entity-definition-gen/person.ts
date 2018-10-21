/*
    Ce fichier a été généré automatiquement.
    Toute modification sera perdue.
*/

/* tslint:disable */
import { EntityField, StoreNode } from "focus4/entity";
import * as domains from "../../../../00-core/domain"

export interface Person {
	perId: number;
	fullName?: string;
	firstName?: string;
	lastName?: string;
	biography?: string;
	shortBiography?: string;
	sex?: string;
	photoHref?: string;
	birthDate?: string;
	birthPlace?: string;
	activity?: string;
}

export interface PersonNode extends StoreNode<Person> {
	perId: EntityField<number, typeof domains.DO_IDENTITY>;
	fullName: EntityField<string, typeof domains.DO_LABEL>;
	firstName: EntityField<string, typeof domains.DO_LABEL>;
	lastName: EntityField<string, typeof domains.DO_LABEL>;
	biography: EntityField<string, typeof domains.DO_TEXT>;
	shortBiography: EntityField<string, typeof domains.DO_TEXT>;
	sex: EntityField<string, typeof domains.DO_CODE>;
	photoHref: EntityField<string, typeof domains.DO_HREF>;
	birthDate: EntityField<string, typeof domains.DO_DATE>;
	birthPlace: EntityField<string, typeof domains.DO_LABEL>;
	activity: EntityField<string, typeof domains.DO_MULTI_VALUES>;
}

export const PersonEntity = {
    name: "person",
    fields: {
        perId: {
            name: "perId",
            type: "field" as "field",
            domain: domains.DO_IDENTITY,
            isRequired: true,
            translationKey: "persons.person.perId"
        },
        fullName: {
            name: "fullName",
            type: "field" as "field",
            domain: domains.DO_LABEL,
            isRequired: false,
            translationKey: "persons.person.fullName"
        },
        firstName: {
            name: "firstName",
            type: "field" as "field",
            domain: domains.DO_LABEL,
            isRequired: false,
            translationKey: "persons.person.firstName"
        },
        lastName: {
            name: "lastName",
            type: "field" as "field",
            domain: domains.DO_LABEL,
            isRequired: false,
            translationKey: "persons.person.lastName"
        },
        biography: {
            name: "biography",
            type: "field" as "field",
            domain: domains.DO_TEXT,
            isRequired: false,
            translationKey: "persons.person.biography"
        },
        shortBiography: {
            name: "shortBiography",
            type: "field" as "field",
            domain: domains.DO_TEXT,
            isRequired: false,
            translationKey: "persons.person.shortBiography"
        },
        sex: {
            name: "sex",
            type: "field" as "field",
            domain: domains.DO_CODE,
            isRequired: false,
            translationKey: "persons.person.sex"
        },
        photoHref: {
            name: "photoHref",
            type: "field" as "field",
            domain: domains.DO_HREF,
            isRequired: false,
            translationKey: "persons.person.photoHref"
        },
        birthDate: {
            name: "birthDate",
            type: "field" as "field",
            domain: domains.DO_DATE,
            isRequired: false,
            translationKey: "persons.person.birthDate"
        },
        birthPlace: {
            name: "birthPlace",
            type: "field" as "field",
            domain: domains.DO_LABEL,
            isRequired: false,
            translationKey: "persons.person.birthPlace"
        },
        activity: {
            name: "activity",
            type: "field" as "field",
            domain: domains.DO_MULTI_VALUES,
            isRequired: false,
            translationKey: "persons.person.activity"
        }
    }
};
