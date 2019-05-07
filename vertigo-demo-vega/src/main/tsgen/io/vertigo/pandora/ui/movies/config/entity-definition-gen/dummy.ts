/*
    Ce fichier a été généré automatiquement.
    Toute modification sera perdue.
*/

/* tslint:disable */
import { EntityField, StoreNode } from "focus4/entity";
import * as domains from "../../../../00-core/domain"

export interface Dummy {
	value: number;
}

export interface DummyNode extends StoreNode<Dummy> {
	value: EntityField<number, typeof domains.DoIdentity>;
}

export const DummyEntity = {
    name: "dummy",
    fields: {
        value: {
            name: "value",
            type: "field" as "field",
            domain: domains.DoIdentity,
            isRequired: true,
            translationKey: "movies.dummy.value"
        }
    }
};
