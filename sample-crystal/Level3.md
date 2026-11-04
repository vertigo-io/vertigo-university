# Level 3 - Liste de référence statique

Nous allons transformer la liste des sexes en liste de référence statique, manipulable en Java sous forme d'enum, et l'utiliser dans une règle de gestion : compter les acteurs masculins d'un film.

## Eléments

- Définitions (`src/main/resources/`) :
  - `alter.ksp` (à créer) et `application.kpr`
  - `masterDataValues.json` (à créer) et `studio-config.yaml` (à la racine du module)
  - `dao.ksp` : la tâche `TkGetActorsByMovie`
- Code généré : l'enum `SexeEnum` et l'accesseur `Actor.sexe()` ; `ActorDAO.getActorsByMovie(Long movId)`
- Service : `MovieServices.countMaleActorsInMovie(Long movId)` : retourne `long`
- Classe main à créer : `src/main/java/io/vertigo/samples/crystal/run/Level3.java`

### A connaitre : Les listes de référence statiques

Une liste de référence statique est une liste fermée de valeurs, qui peut évoluer avec l'application mais n'est pas administrable par l'IHM : un état de dossier, un type d'élément, un sexe… Ses éléments sont des entités, et les règles de gestion les manipulent souvent.

Pour en faire une enum Java :

1. on pose le stéréotype `StaticMasterData` sur l'entité. Ici l'entité `DtSexe` est déclarée dans `model.ksp` ; on la complète dans un fichier à part avec `alter` :
   ```
   package io.vertigo.samples.crystal.domain

   alter DtDefinition DtSexe {
     stereotype : "StaticMasterData"
   }
   ```
2. on liste les valeurs dans un fichier JSON, déclaré dans `studio-config.yaml` (`type: staticMasterData`). Chaque valeur a un nom (le nom de la constante de l'enum) et les champs de l'entité :
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

Le générateur produit alors l'enum `SexeEnum` (`male`, `female`), et l'accesseur `actor.sexe()` devient un `EnumStoreVAccessor` qui donne directement la valeur de l'enum : `actor.sexe().getEnumValue() == SexeEnum.male`.

Les valeurs du JSON (`M` / `Masculin`, `F` / `Feminin`) correspondent aux lignes de la table `SEXE`, insérées par `sql/initdata.sql`. Les identifiants doivent correspondre : c'est par eux que l'enum retrouve l'entité.

## Etapes

1. Créez `src/main/resources/alter.ksp` (contenu ci-dessus) et ajoutez-le à la liste de `application.kpr` (une ligne `alter.ksp`, en fin de fichier).
2. Créez `src/main/resources/masterDataValues.json` (contenu ci-dessus) et déclarez-le dans les `resources` de `studio-config.yaml` : `- { type: staticMasterData, path: src/main/resources/masterDataValues.json }`.
3. Dans `dao.ksp`, déclarez la tâche `TkGetActorsByMovie` : même requête que `TkGetActorsIdsByMovie` mais en `select act.*`, avec la sortie `actors` de domaine `DoDtActor` et de cardinalité `"*"`.
4. Lancez `Studio` depuis la racine du module.
   **Vérification** : `src/main/javagen/io/vertigo/samples/crystal/domain/SexeEnum.java` existe et contient les constantes `male("M")` et `female("F")`, `Actor.sexe()` retourne un `EnumStoreVAccessor`, et `ActorDAO` contient `getActorsByMovie`. Si `SexeEnum` est vide (aucune constante, Studio ne signale rien), le JSON n'est pas déclaré dans `studio-config.yaml` : l'erreur n'apparaîtrait qu'à la compilation de l'étape 5 (`cannot find symbol ... variable male`).
5. Ajoutez `long countMaleActorsInMovie(Long movId)` à l'interface `MovieServices` et implémentez-le dans `MovieServicesImpl` : injectez `ActorDAO`, chargez les acteurs du film avec `actorDAO.getActorsByMovie(movId)`, puis comptez ceux dont `actor.sexe().getEnumValue() == SexeEnum.male`.
6. `MovieServicesImpl` dépend désormais de `ActorDAO` : déclarez-le (`.addComponent(ActorDAO.class)`) dans le module `stepDao` de **toutes** vos classes main qui utilisent `MovieServices` (`CrystalSample`, `CrystalSearchSample`, `Level1`, `Level2`). Sinon leur démarrage échoue avec `Components or params not found :actorDAO (referenced by movieServices<MovieServicesImpl>)` (le programme ne s'arrête alors pas de lui-même : arrêtez-le à la main).
7. Créez `Level3` en copiant `CrystalSample` (avec `ActorDAO` dans `stepDao`) :
   - `step1()` : affichez `"male actors : " + movieServices.countMaleActorsInMovie(FORREST_GUMP_ID)`.
8. Lancez `Level3`.
   **Vérification** : sur la ligne du logger `Level3`, la console affiche `male actors : 1` (Tom Hanks ; Meryl Streep est `F`). Relancez `Level1` : il démarre toujours.

## Optionnel : pour aller plus loin

- Ajoutez une valeur à `masterDataValues.json`, régénérez et observez la nouvelle constante de `SexeEnum` (pensez à ajouter la ligne correspondante dans `sql/initdata.sql`).

# [Suite : Level 4 - DAO proxy](./Level4.md)
