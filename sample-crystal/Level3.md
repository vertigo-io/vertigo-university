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
   			"label" : "Male"
   		},
   		"female" : {
   			"sexCd" : "F",
   			"label" : "Female"
   		}		
   	}
   }
   ```

Le générateur produit alors l'enum `SexeEnum` (`male`, `female`), et l'accesseur `actor.sexe()` devient un `EnumStoreVAccessor` qui donne directement la valeur de l'enum : `actor.sexe().getEnumValue() == SexeEnum.male`.

Les identifiants du JSON (`M`, `F`) doivent correspondre aux lignes de la table `SEXE` : elles sont insérées par `sql/initdata.sql`.

## Etapes

1. Créez `src/main/resources/alter.ksp` (contenu ci-dessus) et ajoutez-le à `application.kpr`.
2. Créez `src/main/resources/masterDataValues.json` (contenu ci-dessus) et déclarez-le dans les `resources` de `studio-config.yaml` : `- { type: staticMasterData, path: src/main/resources/masterDataValues.json }`.
3. Dans `dao.ksp`, déclarez la tâche `TkGetActorsByMovie` : même requête que `TkGetActorsIdsByMovie` mais en `select act.*`, avec la sortie `actors` de domaine `DoDtActor` et de cardinalité `"*"`.
4. Lancez `Studio` depuis la racine du module.
   **Vérification** : `src/main/javagen/io/vertigo/samples/crystal/domain/SexeEnum.java` existe, `Actor.sexe()` retourne un `EnumStoreVAccessor`, et `ActorDAO` contient `getActorsByMovie`.
5. Implémentez `countMaleActorsInMovie(movId)` dans `MovieServicesImpl` : injectez `ActorDAO`, chargez les acteurs du film avec `actorDAO.getActorsByMovie(movId)`, puis comptez ceux dont `actor.sexe().getEnumValue() == SexeEnum.male`.
6. `MovieServicesImpl` dépend désormais de `ActorDAO` : déclarez-le (`.addComponent(ActorDAO.class)`) dans le module `stepDao` de **toutes** vos classes main qui utilisent `MovieServices` (`CrystalSample`, `CrystalSearchSample`, `Level1`, `Level2`), sinon leur démarrage échoue sur la dépendance manquante.
7. Créez `Level3` en copiant `CrystalSample` (avec `ActorDAO` dans `stepDao`) :
   - `step1()` : affichez `"male actors : " + movieServices.countMaleActorsInMovie(FORREST_GUMP_ID)`.
8. Lancez `Level3`.
   **Vérification** : la console affiche `Level3 - male actors : 1` (Tom Hanks ; Meryl Streep est `F`). Relancez `Level1` : il démarre toujours.

## Optionnel : pour aller plus loin

- Ajoutez une valeur à `masterDataValues.json`, régénérez et observez la nouvelle constante de `SexeEnum` (pensez à ajouter la ligne correspondante dans `sql/initdata.sql`).

# [Suite : Level 4 - DAO proxy](./Level4.md)
