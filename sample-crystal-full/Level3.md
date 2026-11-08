# Level 3 - Liste de référence statique

Nous allons transformer la liste des sexes en **liste de référence statique**, manipulable en Java sous forme d'enum.
Nous l'utiliserons dans une règle de gestion : compter les acteurs masculins d'un film.

À la fin de ce Level, vous saurez :
- distinguer une liste de référence statique d'une liste administrable ;
- déclarer une liste statique et ses valeurs, et en générer l'enum ;
- écrire une règle de gestion sur une valeur de cette liste.

## Eléments

- Classe main : `/src/main/java/io/vertigo/samples/crystal/run/Level3`
- Définitions : `/src/main/resources/alter.ksp` *(à créer)*, `application.kpr`, `dao.ksp`
- Valeurs de la liste : `/src/main/resources/masterDataValues.json` *(à créer)*, déclaré dans `/studio-config.yaml`
- Service : `MovieServices.countMaleActorsInMovie`
- Documentation : [vertigo-docs : DataModel](https://vertigo-io.github.io/vertigo-docs/#/extensions/datamodel) *(stéréotypes, StaticMasterData)*

### A connaitre : Les listes de référence

Une application manipule beaucoup de **listes de référence** : des listes de valeurs qui servent à qualifier les données (un pays, un type de dossier, un état…). Vertigo distingue deux cas, par le stéréotype de la définition :
- `MasterData` : une liste de référence **administrable**, dont les valeurs sont gérées par l'application (ajoutées ou modifiées par un écran) ;
- `StaticMasterData` : une liste de référence **figée**. Elle peut évoluer avec l'application, mais pas par l'IHM : un état de dossier, un type d'élément, un sexe… Son identifiant est de type `String`.

L'intérêt d'une liste statique : ses valeurs sont connues au développement, et les règles de gestion s'appuient souvent sur elles. Vertigo en génère une **enum Java**, ce qui rend le code plus lisible et vérifié à la compilation : on écrit `SexeEnum.male` plutôt que la chaîne `"M"`.

### A connaitre : Déclarer une liste statique

Le stéréotype se pose sur la définition. Ici, `DtSexe` est déjà déclarée dans `model.ksp` : plutôt que de la modifier, on la complète dans un fichier à part avec un `alter`, qui permet de modifier ou de compléter une définition existante.

```
package io.vertigo.samples.crystal.domain

alter DtDefinition DtSexe {
  stereotype : "StaticMasterData"
}
```

Les valeurs sont listées dans un fichier JSON, déclaré dans `studio-config.yaml`. Chaque valeur porte le nom de la constante de l'enum et les champs de l'entité :

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

Studio génère alors l'enum `SexeEnum` (`male`, `female`), et l'accesseur `actor.sexe()` devient un `EnumStoreVAccessor` qui donne directement la valeur de l'enum : `actor.sexe().getEnumValue() == SexeEnum.male`.

**Rappel** : les valeurs du JSON correspondent aux lignes de la table `SEXE`, insérées ici par `sql/initdata.sql`. Les identifiants doivent correspondre : c'est par eux que l'enum retrouve l'entité.

## Etapes

1. Créez `alter.ksp` *(contenu ci-dessus)* et ajoutez-le à la fin de la liste de `application.kpr`.
1. Créez `masterDataValues.json` *(contenu ci-dessus)* et déclarez-le dans les `resources` de `studio-config.yaml` : `- { type: staticMasterData, path: src/main/resources/masterDataValues.json }`.
1. Dans `dao.ksp`, déclarez une tâche `TkGetActorsByMovie` : la même requête que `TkGetActorsIdsByMovie`, mais en `select act.*`, avec en sortie `actors` de domain `DoDtActor` et de cardinalité `"*"`.
1. **Question** : dans quelle classe Studio va-t-il placer la méthode de cette tâche ? Vérifiez votre réponse à l'étape suivante.
1. Lancez `Studio`, puis vérifiez le code généré : `SexeEnum` contient `male("M")` et `female("F")`, `Actor.sexe()` retourne un `EnumStoreVAccessor` et `ActorDAO` contient `getActorsByMovie`.
*(si `SexeEnum` est vide, le JSON n'est pas déclaré dans `studio-config.yaml` : Studio ne signale rien)*
1. Ajoutez `long countMaleActorsInMovie(Long movId)` à `MovieServices`. Dans `MovieServicesImpl`, injectez `ActorDAO`, chargez les acteurs du film avec `getActorsByMovie` et comptez ceux dont le sexe est `SexeEnum.male`.
1. `MovieServicesImpl` a maintenant besoin de `ActorDAO` : déclarez-le dans le module `stepDao` de **toutes** vos classes main *(`.addComponent(ActorDAO.class)`, dans `CrystalSample`, `CrystalSearchSample`, `Level1` et `Level2`)*.
1. Créez la classe `Level3` en copiant `CrystalSample`, et affichez dans un `step1` le nombre d'acteurs masculins de Forrest Gump.
1. Lancez `Level3` : vous devez obtenir `male actors : 2` (Tom Hanks et Gary Sinise ; Robin Wright est `F`). Relancez `Level1` pour vérifier qu'il démarre toujours.
1. **Observez** la console : après `TkGetActorsByMovie`, aucune autre requête n'est exécutée avant le résultat. `actor.sexe().getEnumValue()` lit la valeur de l'enum sans accès à la base, contrairement au `load()` d'un accesseur vers une entité (Level 2).

**Attention** : si une classe main ne déclare pas `ActorDAO`, son démarrage échoue avec `Components or params not found :actorDAO (referenced by movieServices<MovieServicesImpl>)`. Un composant ne peut être injecté que s'il est déclaré dans la configuration du node.

### Ce qu'il faut retenir

- Une liste de référence est soit administrable (`MasterData`), soit figée (`StaticMasterData`) ; seule la liste figée devient une enum.
- Les règles de gestion s'écrivent sur l'enum (`SexeEnum.male`), jamais sur la valeur brute ; lire cette valeur ne coûte aucun accès à la base.
- Un `alter` complète une définition existante sans la réécrire.
- Tout composant injecté doit être déclaré dans la configuration du node.

# [Suite : Level 4 - Web services REST et recherche à facettes](./Level4.md)

### Optionnel : Une nouvelle valeur

Ajoutez une valeur dans `masterDataValues.json`, régénérez et observez la nouvelle constante de `SexeEnum` *(pensez à ajouter la ligne correspondante dans `sql/initdata.sql`)*.
