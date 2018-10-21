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
	fullName: EntityField<string, typeof domains.DO_LABEL>;
	photoHref: EntityField<string, typeof domains.DO_HREF>;
	existsInBdd: EntityField<boolean, typeof domains.DO_ACTIVE>;
	perId: EntityField<number, typeof domains.DO_IDENTITY>;
}

export const PersonLinkEntity = {
    name: "personLink",
    fields: {
        fullName: {
            name: "fullName",
            type: "field" as "field",
            domain: domains.DO_LABEL,
            isRequired: false,
            translationKey: "persons.personLink.fullName"
        },
        photoHref: {
            name: "photoHref",
            type: "field" as "field",
            domain: domains.DO_HREF,
            isRequired: false,
            translationKey: "persons.personLink.photoHref"
        },
        existsInBdd: {
            name: "existsInBdd",
            type: "field" as "field",
            domain: domains.DO_ACTIVE,
            isRequired: false,
            translationKey: "persons.personLink.existsInBdd"
        },
        perId: {
            name: "perId",
            type: "field" as "field",
            domain: domains.DO_IDENTITY,
            isRequired: true,
            translationKey: "persons.personLink.perId"
        }
    }
};
