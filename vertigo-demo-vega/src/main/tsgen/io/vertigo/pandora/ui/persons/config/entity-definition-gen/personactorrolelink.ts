/*
    Ce fichier a été généré automatiquement.
    Toute modification sera perdue.
*/

/* tslint:disable */
import { EntityField, StoreNode } from "focus4/entity";
import * as domains from "../../../../00-core/domain"

export interface PersonActorRoleLink {
	fullName?: string;
	photoHref?: string;
	role?: string;
	perId: number;
}

export interface PersonActorRoleLinkNode extends StoreNode<PersonActorRoleLink> {
	fullName: EntityField<string, typeof domains.DoLabel>;
	photoHref: EntityField<string, typeof domains.DoHref>;
	role: EntityField<string, typeof domains.DoLabel>;
	perId: EntityField<number, typeof domains.DoIdentity>;
}

export const PersonActorRoleLinkEntity = {
    name: "personActorRoleLink",
    fields: {
        fullName: {
            name: "fullName",
            type: "field" as "field",
            domain: domains.DoLabel,
            isRequired: false,
            translationKey: "persons.personActorRoleLink.fullName"
        },
        photoHref: {
            name: "photoHref",
            type: "field" as "field",
            domain: domains.DoHref,
            isRequired: false,
            translationKey: "persons.personActorRoleLink.photoHref"
        },
        role: {
            name: "role",
            type: "field" as "field",
            domain: domains.DoLabel,
            isRequired: false,
            translationKey: "persons.personActorRoleLink.role"
        },
        perId: {
            name: "perId",
            type: "field" as "field",
            domain: domains.DoIdentity,
            isRequired: true,
            translationKey: "persons.personActorRoleLink.perId"
        }
    }
};
