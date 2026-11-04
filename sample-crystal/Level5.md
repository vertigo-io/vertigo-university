# Level 5 - Web services REST et recherche à facettes

Nous allons publier la recherche de films en REST.
Le client pourra déclencher la réindexation, piloter la pagination et sélectionner des facettes, dont une à sélection multiple.

## Eléments

- Routes : [http://localhost:8081/movies/search/_reindex](http://localhost:8081/movies/search/_reindex) *(à créer)* et [http://localhost:8081/movies/_search?q=the](http://localhost:8081/movies/_search?q=the)
- Classe main : `/src/main/java/io/vertigo/samples/crystal/run/Level5`
- Web services : `/src/main/java/io/vertigo/samples/crystal/webservices/MovieWebServices`
- Service : `MovieServices.searchMovies`
- Définitions de recherche : `/src/main/resources/searchMovie.ksp`
- Smart types : `/src/main/java/io/vertigo/samples/crystal/config/SampleCrystalSmartTypes`

### A connaitre : L'index et les facettes

L'index `IdxMovie` (`searchMovie.ksp`) contient des `MovieIndex` (`movId`, `name`, `year`, `country`), chargés depuis la base par `MovieSearchLoader`. La recherche plein texte porte sur le champ `allText`, qui regroupe les autres champs.

Deux requêtes de recherche sont déclarées : `QryMovie`, sans facette, et `QryMovieWithFacets`, avec les facettes `FctMovieName` (par tranche de titre), `FctMovieYear` (par décennie) et `FctMovieCountry` (par pays). Le client généré `MovieIndexSearchClient` propose une méthode par requête : `createSearchQueryBuilderMovie` et `createSearchQueryBuilderMovieWithFacets`.

Une facette porte sur une valeur exacte, pas sur un texte analysé. C'est le smart type du champ qui le précise, avec sa propriété `indexType` :
- `text_fr` : un texte analysé en français, pour la recherche plein texte ;
- `text_fr:sortable` : en plus, la plateforme duplique le champ dans un sous-champ exact `<champ>.keyword`, qui sert au tri et aux facettes (par exemple `name.keyword:[a TO g]`).

Une facette déclarée `multiSelectable : "true"` accepte plusieurs valeurs sélectionnées à la fois.

### A connaitre : La pagination

Dans un web service, un paramètre `final DtListState dtListState` **sans annotation** est lu dans les paramètres d'url préfixés par `listState.` : `listState.top`, `listState.skip`, `listState.sortFieldName`, `listState.sortDesc`.
Par exemple : `/movies/_search?q=the&listState.top=2`. En `Post` aussi, la pagination passe par l'url ; le corps porte les autres paramètres (`@InnerBodyParam`).

### A connaitre : La réindexation

L'index est vide au premier lancement et la réindexation n'est pas automatique. Le service `MovieServices.indexMovies()` existe déjà : il réindexe tous les films et retourne leur nombre. Il ne reste qu'à le publier sur une route.
Une fois réindexé, l'index est conservé d'un lancement à l'autre *(dans `target/classes/search/data`, à supprimer, programme arrêté, pour repartir d'un index vide)*.

## Etapes

1. Dans `SampleCrystalSmartTypes`, passez les smart types `Label` (le pays) et `LabelLong` (le titre) de `text_fr` à `text_fr:sortable`.
1. Dans `searchMovie.ksp`, faites porter `FctMovieName` sur le champ `name`, avec des tranches sur `name.keyword` à la place de `nameSortOnly` *(`range r1 { filter:"name.keyword:[* TO a]", label:"#"}`, etc.)*.
1. Rendez `FctMovieCountry` multi-sélectionnable :
```
create FacetDefinition FctMovieCountry {  
  dtDefinition:DtMovieIndex, fieldName:"country", label:"Par pays"
  multiSelectable : "true"
}
```
4. Lancez `Studio` : dans `MovieIndexSearchClient`, les tranches de `FctMovieName` portent maintenant sur `name.keyword` et `FctMovieCountry` déclare `.withMultiSelectable()`.
5. Dans `MovieServicesImpl.searchMovies`, utilisez `createSearchQueryBuilderMovieWithFacets` à la place de `createSearchQueryBuilderMovie`.
6. Dans `MovieWebServices`, ajoutez la route `@GET("/search/_reindex")` *(en `@AnonymousAccessAllowed`)*, qui retourne `movieServices.indexMovies()`.
7. Dans le `@GET("/_search")`, remplacez la pagination fixe `DtListState.of(5)` par un paramètre `final DtListState dtListState`.
8. Créez la classe `Level5` en copiant `CrystalSearchSample` : elle contient déjà tout le nécessaire.
9. Lancez `Level5`, attendez la fin du démarrage, puis appelez [http://localhost:8081/movies/search/_reindex](http://localhost:8081/movies/search/_reindex) : vous devez obtenir `6`.
10. Testez la recherche :
    - [`/movies/_search?q=gump`](http://localhost:8081/movies/_search?q=gump) retourne Forrest Gump ;
    - [`/movies/_search?q=the&listState.top=2`](http://localhost:8081/movies/_search?q=the&listState.top=2) retourne 2 films ;
    - [`/movies/_search?q=the`](http://localhost:8081/movies/_search?q=the) retourne 4 films, avec la facette `FctMovieCountry` (`"isMultiSelectable": true`) et ses valeurs `etats-unis` (2), `allemagne` (1), `royaume-uni` (1).
11. Testez la sélection de facettes en `Post` *(par exemple avec curl depuis Git Bash)* : en sélectionnant `etats-unis` et `allemagne`, vous devez obtenir 3 films.
```
curl -X POST -H "Content-Type: application/json" -d '{"criteria":"the","facets":{"FctMovieCountry":["etats-unis","allemagne"]}}' http://localhost:8081/movies/_search
```
12. Arrêtez `Level5` *(Ctrl+C)*.

**Attention** :
- une facette sur un champ qui n'est pas `sortable` (par exemple en laissant `Label` en `text_fr`) fait répondre la recherche en **422** : `Search index structure error. Sorted or aggregated fields must be declared as keyword, sortable or facetable in your SmartType.` ;
- `top=2` sans le préfixe `listState.` est ignoré : toute la liste revient ;
- en `Post`, le critère ne doit pas être vide et la clé `facets` est obligatoire *(`"facets":{}` si aucune facette n'est sélectionnée)*, sinon la réponse est un **500** `RestParam not found`.

# [Suite : la sécurité (connexion, autorisations) se travaille dans sample-account](../sample-account)

### Optionnel : Le tri

Triez les résultats : [`/movies/_search?q=the&listState.sortFieldName=name&listState.sortDesc=true`](http://localhost:8081/movies/_search?q=the&listState.sortFieldName=name&listState.sortDesc=true) retourne les 4 films par titre décroissant (`The Iron Giant` en premier). Le tri sur `name` est possible parce que son smart type est `sortable`.

Comparez aussi avec la recherche du [Level 3 de sample-vertigo-ui](../sample-vertigo-ui/Level3.md), qui construit un index et des facettes équivalents, utilisés depuis un écran.
