/*
    Ce fichier a été généré automatiquement.
    Toute modification sera perdue.
*/

/* tslint:disable */
import { EntityField, StoreNode } from "focus4/entity";
import * as domains from "../../../common/domain"

export interface ActorRole {
	aroId: number;
	role?: string;
	perId?: number;
	movId?: number;
}

export interface ActorRoleNode extends StoreNode<ActorRole> {
	aroId: EntityField<number>;
	role: EntityField<string>;
	perId: EntityField<number>;
	movId: EntityField<number>;
}

export const ActorRoleEntity = {
    name: "actorRole",
    fields: {
        aroId: {
            name: "aroId",
            type: "field" as "field",
            domain: domains.DO_IDENTITY,
            isRequired: true,
            translationKey: "persons.actorRole.aroId"
        },
        role: {
            name: "role",
            type: "field" as "field",
            domain: domains.DO_LABEL,
            isRequired: false,
            translationKey: "persons.actorRole.role"
        },
        perId: {
            name: "perId",
            type: "field" as "field",
            domain: domains.DO_IDENTITY,
            isRequired: false,
            translationKey: "persons.actorRole.perId"
        },
        movId: {
            name: "movId",
            type: "field" as "field",
            domain: domains.DO_IDENTITY,
            isRequired: false,
            translationKey: "persons.actorRole.movId"
        }
    }
};
