/*
    Ce fichier a été généré automatiquement.
    Toute modification sera perdue.
*/

/* tslint:disable */
import { EntityField, StoreNode } from "focus4/entity";
import * as domains from "../../../common/domain"

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
	perId: EntityField<number>;
	fullName: EntityField<string>;
	firstName: EntityField<string>;
	lastName: EntityField<string>;
	biography: EntityField<string>;
	shortBiography: EntityField<string>;
	sex: EntityField<string>;
	photoHref: EntityField<string>;
	birthDate: EntityField<string>;
	birthPlace: EntityField<string>;
	activity: EntityField<string>;
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
