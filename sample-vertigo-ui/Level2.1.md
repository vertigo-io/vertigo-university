# Level 2.1 - Ecran de détail - Liste de référence des pays

Nous allons completer l'écran de détail de film pour référencer la liste des pays.

## Eléments

- Route : [http://localhost:18080/sample/movie/{movId}](http://localhost:18080/sample/movie/3678598)
- Controller : `/src/main/java/io/vertigo/samples/vui/controllers/MovieController`
- Vue : `/src/main/resources/webapp/WEB-INF/views/vui/movie.html`
- Service : 

### A connaitre : Liste de référence

Vertigo permet de déclarer les listes de référence de l'application (MDL : MasterDataList). 
Cela permet de simplifier leur usage dans le projet, car il y a une résolution et un chargement automatique des relations. 
Les données peuvent être mise en cache (et le sont généralement). 
Nous préconisons un cache court pour absorber les pics de charge sans poser de pb important de cohérence.

Les listes de référence, sont des listes nommées que le système sait charger seul. L'objet doit être une Entity et doit être enregistré comme MasterData.
Cela est fait dans un `DefinitionProvider` du module.


### A connaitre : Composants

Le composant `vu:select` : Pose un champ référencant une liste. A minima, nécessite les attributs `object` et `field` pour préciser quel champ remplir, et un attribut `list` pour la liste. Ce composant supporte plusieurs manières pour déclarer la liste associée. 
Elle peut être directement dans le context si elle est assez courte ou référencer une liste de référence.

## Etapes

1. Vérifiez que `Country` est bien enregistré comme Entity de référence dans `SampleVuiMasterDataDefinitionProvider`. Ce `DefinitionProvider` est référencé dans les `Features` du module **Support**.
2. Remarquez que l'objet Java `Country` à son champ `name` déclaré comme `@DisplayField`. Retrouvez où cela a été déclaré dans la définition de l'objet (`model.ksp` ou `alter.ksp`)
3. Dans le controller déclarez une clé de context "countries" de type Country.
4. Dans le initContext publiez la liste des pays comme une MasterDataList. (Utilisez `null` en dernier paramètre)
*Ce dernier paramètre permet de passer un code, dans la cas ou la liste de ref propose des filtres (actif par exemple)*
4. Dans la vue, remplacer le champ text de `couId` par un `vu:select` *(conserver `object` et `field`, puis ajoutez `list`)*
7. Consulter la page de détail d'un film. Consulter le contenu du `vueData`

# [Suite : Level 2.2 - Ecran de détail - Edition](./Level2.2.md)
