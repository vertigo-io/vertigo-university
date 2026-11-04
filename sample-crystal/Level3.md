# Level 3 - Liste de référence statique

Nous allons transformer la liste des sexes en **liste de référence statique**, manipulable en Java sous forme d'enum.
Nous l'utiliserons dans une règle de gestion : compter les acteurs masculins d'un film.

## Eléments

- Classe main : `/src/main/java/io/vertigo/samples/crystal/run/Level3`
- Définitions : `/src/main/resources/alter.ksp` *(à créer)*, `application.kpr`, `dao.ksp`
- Valeurs de la liste : `/src/main/resources/masterDataValues.json` *(à créer)*, déclaré dans `/studio-config.yaml`
- Service : `MovieServices.countMaleActorsInMovie`

### A connaitre : Les listes de référence statiques

Une liste de référence statique est une liste fermée de valeurs, qui peut évoluer avec l'application mais n'est pas administrable par l'IHM : un état de dossier, un type d'élément, un sexe…
Ces listes sont souvent utilisées dans les règles de gestion : les manipuler sous forme d'enum Java rend le code plus simple et plus sûr.

Pour cela, on pose le stéréotype `StaticMasterData` sur l'entité. Ici, `DtSexe` est déclarée dans `model.ksp` : on la complète dans un fichier à part avec un `alter`.

```
package io.vertigo.samples.crystal.domain

alter DtDefinition DtSexe {
  stereotype : "StaticMasterData"
}
```

Puis on liste les valeurs dans un fichier JSON : chaque valeur porte le nom de la constante de l'enum et les champs de l'entité.

```
{"io.vertigo.samples.crystal.domain.Sexe" : 
	{
		"male" : {
			"sexCd" : "M",
			"label" : "Masculin"
		},
		"female" : {
			"sexCd" : "F",
			"label" : "Feminin"
		}		
	}
}
```

Studio génère alors l'enum `SexeEnum` (`male`, `female`), et l'accesseur `actor.sexe()` donne directement la valeur de l'enum : `actor.sexe().getEnumValue() == SexeEnum.male`.

**Rappel** : les valeurs du JSON correspondent aux lignes de la table `SEXE`, insérées par `sql/initdata.sql`. Les identifiants doivent correspondre : c'est par eux que l'enum retrouve l'entité.

## Etapes

1. Créez `alter.ksp` *(contenu ci-dessus)* et ajoutez-le à la fin de la liste de `application.kpr`.
1. Créez `masterDataValues.json` *(contenu ci-dessus)* et déclarez-le dans les `resources` de `studio-config.yaml` : `- { type: staticMasterData, path: src/main/resources/masterDataValues.json }`.
1. Dans `dao.ksp`, déclarez une tâche `TkGetActorsByMovie` : la même requête que `TkGetActorsIdsByMovie`, mais en `select act.*`, avec en sortie `actors` de domaine `DoDtActor` et de cardinalité `"*"`.
1. Lancez `Studio`, puis vérifiez le code généré : `SexeEnum` contient `male("M")` et `female("F")`, `Actor.sexe()` retourne un `EnumStoreVAccessor` et `ActorDAO` contient `getActorsByMovie`.
*(si `SexeEnum` est vide, le JSON n'est pas déclaré dans `studio-config.yaml` : Studio ne signale rien)*
1. Ajoutez `long countMaleActorsInMovie(Long movId)` à `MovieServices`. Dans `MovieServicesImpl`, injectez `ActorDAO`, chargez les acteurs du film avec `getActorsByMovie` et comptez ceux dont le sexe est `SexeEnum.male`.
1. `MovieServicesImpl` a maintenant besoin de `ActorDAO` : déclarez-le dans le module `stepDao` de **toutes** vos classes main *(`.addComponent(ActorDAO.class)`, dans `CrystalSample`, `CrystalSearchSample`, `Level1` et `Level2`)*.
1. Créez la classe `Level3` en copiant `CrystalSample`, et affichez dans un `step1` le nombre d'acteurs masculins de Forrest Gump.
1. Lancez `Level3` : vous devez obtenir `male actors : 1` (Tom Hanks ; Meryl Streep est `F`). Relancez `Level1` pour vérifier qu'il démarre toujours.

**Attention** : si une classe main ne déclare pas `ActorDAO`, son démarrage échoue avec `Components or params not found :actorDAO (referenced by movieServices<MovieServicesImpl>)`.

# [Suite : Level 4 - DAO proxy](./Level4.md)

### Optionnel : Une nouvelle valeur

Ajoutez une valeur dans `masterDataValues.json`, régénérez et observez la nouvelle constante de `SexeEnum` *(pensez à ajouter la ligne correspondante dans `sql/initdata.sql`)*.
