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
	perId: EntityField<number, typeof domains.DoIdentity>;
	fullName: EntityField<string, typeof domains.DoLabel>;
	firstName: EntityField<string, typeof domains.DoLabel>;
	lastName: EntityField<string, typeof domains.DoLabel>;
	biography: EntityField<string, typeof domains.DoText>;
	shortBiography: EntityField<string, typeof domains.DoText>;
	sex: EntityField<string, typeof domains.DoCode>;
	photoHref: EntityField<string, typeof domains.DoHref>;
	birthDate: EntityField<string, typeof domains.DoDate>;
	birthPlace: EntityField<string, typeof domains.DoLabel>;
	activity: EntityField<string, typeof domains.DoMultiValues>;
}

export const PersonEntity = {
    name: "person",
    fields: {
        perId: {
            name: "perId",
            type: "field" as "field",
            domain: domains.DoIdentity,
            isRequired: true,
            translationKey: "persons.person.perId"
        },
        fullName: {
            name: "fullName",
            type: "field" as "field",
            domain: domains.DoLabel,
            isRequired: false,
            translationKey: "persons.person.fullName"
        },
        firstName: {
            name: "firstName",
            type: "field" as "field",
            domain: domains.DoLabel,
            isRequired: false,
            translationKey: "persons.person.firstName"
        },
        lastName: {
            name: "lastName",
            type: "field" as "field",
            domain: domains.DoLabel,
            isRequired: false,
            translationKey: "persons.person.lastName"
        },
        biography: {
            name: "biography",
            type: "field" as "field",
            domain: domains.DoText,
            isRequired: false,
            translationKey: "persons.person.biography"
        },
        shortBiography: {
            name: "shortBiography",
            type: "field" as "field",
            domain: domains.DoText,
            isRequired: false,
            translationKey: "persons.person.shortBiography"
        },
        sex: {
            name: "sex",
            type: "field" as "field",
            domain: domains.DoCode,
            isRequired: false,
            translationKey: "persons.person.sex"
        },
        photoHref: {
            name: "photoHref",
            type: "field" as "field",
            domain: domains.DoHref,
            isRequired: false,
            translationKey: "persons.person.photoHref"
        },
        birthDate: {
            name: "birthDate",
            type: "field" as "field",
            domain: domains.DoDate,
            isRequired: false,
            translationKey: "persons.person.birthDate"
        },
        birthPlace: {
            name: "birthPlace",
            type: "field" as "field",
            domain: domains.DoLabel,
            isRequired: false,
            translationKey: "persons.person.birthPlace"
        },
        activity: {
            name: "activity",
            type: "field" as "field",
            domain: domains.DoMultiValues,
            isRequired: false,
            translationKey: "persons.person.activity"
        }
    }
};
