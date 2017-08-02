/*
    Ce fichier a été généré automatiquement.
    Toute modification sera perdue.
*/

/* tslint:disable */
import { EntityField, StoreNode } from "focus4/entity";
import * as domains from "../../../common/domain"

export interface PersonActorRoleLink {
	fullName?: string;
	photoHref?: string;
	role?: string;
	perId: number;
}

export interface PersonActorRoleLinkNode extends StoreNode<PersonActorRoleLink> {
	fullName: EntityField<string>;
	photoHref: EntityField<string>;
	role: EntityField<string>;
	perId: EntityField<number>;
}

export const PersonActorRoleLinkEntity = {
    name: "personActorRoleLink",
    fields: {
        fullName: {
            name: "fullName",
            type: "field" as "field",
            domain: domains.DO_LABEL,
            isRequired: false,
            translationKey: "persons.personActorRoleLink.fullName"
        },
        photoHref: {
            name: "photoHref",
            type: "field" as "field",
            domain: domains.DO_HREF,
            isRequired: false,
            translationKey: "persons.personActorRoleLink.photoHref"
        },
        role: {
            name: "role",
            type: "field" as "field",
            domain: domains.DO_LABEL,
            isRequired: false,
            translationKey: "persons.personActorRoleLink.role"
        },
        perId: {
            name: "perId",
            type: "field" as "field",
            domain: domains.DO_IDENTITY,
            isRequired: true,
            translationKey: "persons.personActorRoleLink.perId"
        }
    }
};
