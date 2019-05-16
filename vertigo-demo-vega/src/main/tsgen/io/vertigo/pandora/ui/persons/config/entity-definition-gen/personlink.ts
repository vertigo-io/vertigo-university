/*
    Ce fichier a été généré automatiquement.
    Toute modification sera perdue.
*/

/* tslint:disable */
import { EntityField, StoreNode } from "focus4/entity";
import * as domains from "../../../../00-core/domain"

export interface PersonLink {
	fullName?: string;
	photoHref?: string;
	existsInBdd?: boolean;
	perId: number;
}

export interface PersonLinkNode extends StoreNode<PersonLink> {
	fullName: EntityField<string, typeof domains.DoLabel>;
	photoHref: EntityField<string, typeof domains.DoHref>;
	existsInBdd: EntityField<boolean, typeof domains.DoActive>;
	perId: EntityField<number, typeof domains.DoIdentity>;
}

export const PersonLinkEntity = {
    name: "personLink",
    fields: {
        fullName: {
            name: "fullName",
            type: "field" as "field",
            domain: domains.DoLabel,
            isRequired: false,
            translationKey: "persons.personLink.fullName"
        },
        photoHref: {
            name: "photoHref",
            type: "field" as "field",
            domain: domains.DoHref,
            isRequired: false,
            translationKey: "persons.personLink.photoHref"
        },
        existsInBdd: {
            name: "existsInBdd",
            type: "field" as "field",
            domain: domains.DoActive,
            isRequired: false,
            translationKey: "persons.personLink.existsInBdd"
        },
        perId: {
            name: "perId",
            type: "field" as "field",
            domain: domains.DoIdentity,
            isRequired: true,
            translationKey: "persons.personLink.perId"
        }
    }
};
