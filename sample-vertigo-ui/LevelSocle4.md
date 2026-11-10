# Level Socle 4 - Les tâches

Nous allons lire les requêtes SQL nommées de l'application : les **tâches**.
Puis nous en écrirons une, avec un paramètre, et nous retrouverons la méthode que Studio génère pour l'exécuter.

À la fin de ce Level, vous saurez :
- choisir entre les méthodes du DAO et une tâche ;
- lire une tâche et prévoir où Studio place la méthode générée ;
- écrire une tâche avec un paramètre.

## Eléments

- Définitions : `/src/main/resources/definitions/dao.ksp` et `/src/main/resources/definitions/search/daoSearch.ksp`
- Code généré : `/src/main/javagen/io/vertigo/samples/vui/VuiPAO.java` et les DAO de `/src/main/javagen/io/vertigo/samples/vui/dao`
- Concept illustré : Task *(voir le glossaire [vertigo-docs : Concepts](https://vertigo-io.github.io/vertigo-docs/#/basic/concepts))*
- Documentation : [vertigo-docs : Accès aux données](https://vertigo-io.github.io/vertigo-docs/#/basic/dao)

### A connaitre : DAO ou tâche ?

Les DAO générés offrent déjà les accès simples à une entité : la lecture par identifiant, l'enregistrement, la suppression et la recherche par critères (`findAll`). Ils couvrent une grande partie des besoins pour un effort très faible.

Ces méthodes se limitent volontairement à **une seule entité** : c'est l'assurance de bonnes performances. Dès que la requête concerne plusieurs entités (une jointure, un agrégat), on écrit une **tâche**.

### A connaitre : Les tâches

Une tâche est définie par :
- un nom, qui commence par convention par `Tk` ;
- un moteur d'exécution (`className`) : `TaskEngineSelect` pour une sélection, `TaskEngineProc` pour une mise à jour, `TaskEngineProcBatch` pour une mise à jour de toute une liste en un seul lot ;
- une requête, dans laquelle un paramètre d'entrée s'écrit `#nomParametre#`, et une liste de valeurs `#nomParametre.rownum#` (dans un `in (...)`) ;
- des paramètres d'entrée (`in`) et un paramètre de sortie optionnel (`out`), chacun avec un domain et une cardinalité (`"1"`, `"?"` ou `"*"`). Pour un objet, le domain se nomme `Do` suivi du nom de sa définition : `DoDtMovie`, `DoDtMovieDisplay`.

Studio génère la méthode qui exécute la tâche. Il la place dans le **DAO de l'entité concernée** par les paramètres (en entrée ou en sortie), sinon dans le **PAO** (*Package Access Object*) du package : ici `VuiPAO`. Un DTO n'a pas de DAO : une tâche qui renvoie un DTO va donc dans le PAO. Ce sont les **paramètres** qui décident, pas les tables de la requête.

## Etapes

1. Dans `dao.ksp`, lisez les cinq tâches.
**Question** : pour chacune, prévoyez où Studio a placé la méthode générée, puis vérifiez dans `VuiPAO` et dans les DAO. Pourquoi `getMoviesInCountries` est-elle dans `MovieDAO`, et `getMoviesDisplay` dans `VuiPAO` ?
1. Ajoutez dans `dao.ksp` la tâche `TkGetMoviesDisplayByCountry` : les films d'un pays, avec le nom de ce pays.
*(même requête que `TkGetMoviesDisplay`, avec `join` au lieu de `left join`, et la condition `where mov.cou_id = #couId#` ; en entrée `couId` de domain `DoId` et de cardinalité `"1"` ; en sortie `moviesDisplay` de domain `DoDtMovieDisplay` et de cardinalité `"*"`)*
1. Régénérez le code *(voir le Level Socle 2)* et retrouvez la méthode `getMoviesDisplayByCountry` *(son paramètre `couId` est précédé d'une annotation générée, `@TaskInput`, qui décrit le paramètre de la tâche)*.
**Question** : dans quelle classe Studio l'a-t-il placée, et pourquoi ?
1. Recompilez : le projet compile. Le service appellera cette méthode au Level Socle 6.

### Ce qu'il faut retenir

- Les méthodes du DAO suffisent pour une seule entité ; dès que plusieurs entités sont concernées, on écrit une tâche.
- Une requête se déclare comme une tâche dans les définitions, jamais dans le code Java : Studio génère la méthode.
- La méthode va dans le DAO de l'entité concernée par les paramètres, sinon dans le PAO du package.

# [Suite : Level Socle 5 - Modules et packages](./LevelSocle5.md)
