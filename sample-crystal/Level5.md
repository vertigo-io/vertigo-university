# Level 5 - Web services REST et recherche à facettes

Nous allons publier la recherche de films en REST.
Le client pourra déclencher la réindexation, piloter la pagination et le tri, et sélectionner des facettes, dont une à sélection multiple.

À la fin de ce Level, vous saurez :
- expliquer ce qu'apporte un moteur de recherche par rapport à SQL ;
- rendre un champ d'index triable et utilisable en facette ;
- exposer une recherche paginée et facettée dans un web service, et déclencher la réindexation.

## Eléments

- Routes : [http://localhost:8081/movies/search/_reindex](http://localhost:8081/movies/search/_reindex) *(à créer)* et [http://localhost:8081/movies/_search?q=the](http://localhost:8081/movies/_search?q=the)
- Classe main : `/src/main/java/io/vertigo/samples/crystal/run/Level5`
- Web services : `/src/main/java/io/vertigo/samples/crystal/webservices/MovieWebServices`
- Service : `MovieServices.searchMovies`
- Définitions de recherche : `/src/main/resources/searchMovie.ksp`
- SmartTypes : `/src/main/java/io/vertigo/samples/crystal/config/SampleCrystalSmartTypes`
- Documentation : [vertigo-docs : Recherche](https://vertigo-io.github.io/vertigo-docs/#/basic/recherche) et [vertigo-docs : Vega](https://vertigo-io.github.io/vertigo-docs/#/extensions/vega) *(tri et pagination des listes)*

### A connaitre : Pourquoi un moteur de recherche ?

Une recherche dans la base, même bien écrite, reste limitée. Le moteur de recherche (Elasticsearch) apporte une recherche riche et performante : plein texte, facettes avec leurs compteurs, tri par pertinence, avec des temps de réponse de quelques millisecondes, incomparables avec des requêtes SQL.

Deux principes à garder en tête :
- **la recherche est un filtre** : plus l'utilisateur donne d'informations, plus le champ des résultats se réduit, et il affine avec les facettes ;
- la recherche *plain text* cherche les documents qui contiennent un mot **qui commence par** le critère, pas un mot qui le *contient* : l'index est un index des mots, comme celui d'un livre. `q=gum` trouve Forrest Gump, pas `q=ump`.

### A connaitre : L'index et les facettes

L'index `IdxMovie` (`searchMovie.ksp`) contient des `MovieIndex` (`movId`, `name`, `year`, `country`), chargés depuis la base par `MovieSearchLoader`. La recherche plein texte porte sur le champ `allText`, qui regroupe les autres champs.

Une **facette** regroupe les résultats par valeur, avec le nombre de résultats de chacune. Il en existe deux types courants :
- les facettes **term**, dont les valeurs sont tirées des données de l'index (`FctMovieCountry`, par pays) ;
- les facettes **range**, dont les tranches sont prédéfinies (`FctMovieName` par tranche de titre, `FctMovieYear` par décennie).

Deux requêtes de recherche sont déclarées : `QryMovie`, sans facette, et `QryMovieWithFacets`, avec les trois facettes. Studio génère dans `MovieIndexSearchClient` une méthode par requête : `createSearchQueryBuilderMovie` et `createSearchQueryBuilderMovieWithFacets`.

Une facette déclarée `multiSelectable : "true"` accepte plusieurs valeurs sélectionnées à la fois. **Attention**, les facettes multi-sélectionnables ont un coût en performances : ne les activez que si le besoin est réel.

### A connaitre : Trier et facetter un champ

Le SmartType d'un champ d'index précise comment il est indexé, avec sa propriété `indexType` :
- `text_fr` : un texte analysé en français, pour la recherche plein texte. L'analyseur `text_fr` (déclaré dans `elasticsearch.yml`) découpe le texte en mots, les met en minuscules, retire les accents et réduit chaque mot à sa racine ;
- `text_fr:sortable` : en plus, Vertigo ajoute automatiquement une version `keyword` du champ (`name.keyword`). Elle conserve la valeur **entière**, sans la découper en mots, simplement normalisée pour le tri (le normalizer `sortable` de `elasticsearch.yml` la met en minuscules et retire les accents). Vertigo l'utilise de manière transparente pour les **tris** et pour les **facettes** ;
- `facetable` existe aussi : il permet de facetter le champ analysé lui-même. Il n'est pas nécessaire sur un champ déjà `sortable`.

Pourquoi préférer `sortable` pour une facette de pays ? Parce qu'une facette sur un texte analysé regroupe les **mots** analysés, pas les valeurs. Avec `text_fr:facetable`, la facette pays renvoie `etat`, `unis`, `allemagn`, `royaum`… Avec `text_fr:sortable`, elle renvoie `etats-unis`, `allemagne` et `royaume-uni`.

### A connaitre : La pagination et le tri

Pour ajouter la pagination et le tri à un web service, on ajoute un paramètre `@QueryParam("") final DtListState dtListState` et on le transmet au service. Le client passe alors dans l'url : `top` (nombre maximum d'éléments), `skip` (position du premier), `sortFieldName` (champ de tri) et `sortDesc` (`true` pour un tri décroissant).
Par exemple : `/movies/_search?q=the&top=2&skip=2`. En `Post`, la pagination passe aussi par l'url ; le corps porte les autres paramètres (`@InnerBodyParam`).

### A connaitre : La réindexation

L'index est vide au premier lancement et la réindexation n'est pas automatique. Vertigo ne fournit pas d'écran d'administration de la réindexation : le web service ou l'écran qui la déclenche est à créer dans l'application.
Le service `MovieServices.indexMovies()` existe déjà : il appelle `reindexAll` du `SearchManager` et retourne le nombre de films indexés. Il ne reste qu'à le publier sur une route.

Une fois réindexé, l'index est conservé d'un lancement à l'autre *(dans `target/classes/search/data`, à supprimer, programme arrêté, pour repartir d'un index vide)*.
Au démarrage, la plateforme vérifie la structure de l'index : ajouter un champ `keyword` (passage en `sortable`) est accepté ; un changement incompatible impose de supprimer l'index puis de réindexer.

## Etapes

1. Dans `SampleCrystalSmartTypes`, passez les SmartTypes `Label` (le pays) et `LabelLong` (le titre) de `text_fr` à `text_fr:sortable`.
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
7. Dans le `@GET("/_search")`, remplacez la pagination fixe `DtListState.of(5)` par un paramètre `@QueryParam("") final DtListState dtListState`.
8. Créez la classe `Level5` en copiant `CrystalSearchSample` : elle contient déjà tout le nécessaire.
9. Lancez `Level5`, attendez la fin du démarrage, puis appelez [http://localhost:8081/movies/search/_reindex](http://localhost:8081/movies/search/_reindex) : vous devez obtenir `6`.
10. Testez la recherche :
    - [`/movies/_search?q=gump`](http://localhost:8081/movies/_search?q=gump) retourne Forrest Gump ;
    - [`/movies/_search?q=the`](http://localhost:8081/movies/_search?q=the) retourne 4 films, avec la facette `FctMovieCountry` (`"isMultiSelectable": true`) et ses valeurs `etats-unis` (2), `allemagne` (1), `royaume-uni` (1) ;
    - [`/movies/_search?q=the&top=2`](http://localhost:8081/movies/_search?q=the&top=2) retourne les 2 premiers films, et [`&top=2&skip=2`](http://localhost:8081/movies/_search?q=the&top=2&skip=2) les 2 suivants ; `totalCount` reste à 4.
11. Testez la sélection de facettes en `Post` *(par exemple avec curl depuis Git Bash)* : en sélectionnant `etats-unis` et `allemagne`, vous devez obtenir 3 films.
```
curl -X POST -H "Content-Type: application/json" -d '{"criteria":"the","facets":{"FctMovieCountry":["etats-unis","allemagne"]}}' http://localhost:8081/movies/_search
```
12. **Question** : pourquoi la facette pays donne-t-elle `etats-unis` et non `Etats-Unis`, la valeur en base ? *(indice : relisez ce que fait la version `keyword` d'un champ `sortable`, puis regardez le normalizer `sortable` dans `elasticsearch.yml`)*
13. Arrêtez `Level5` *(Ctrl+C)*.

**Attention** :
- une facette sur un champ ni `sortable` ni `facetable` (par exemple en laissant `Label` en `text_fr`) fait répondre la recherche en **422** : `Search index structure error. Sorted or aggregated fields must be declared as keyword, sortable or facetable in your SmartType.` ;
- en `Post`, le critère ne doit pas être vide et la clé `facets` est obligatoire *(`"facets":{}` si aucune facette n'est sélectionnée)*, sinon la réponse est un **500** `RestParam not found`.

### Ce qu'il faut retenir

- La recherche est un filtre, et la recherche plain text cherche des mots qui *commencent par* le critère.
- Pour trier ou facetter une valeur entière, on rend le champ `sortable` : Vertigo utilise sa version `keyword`.
- La pagination et le tri se passent par un `@QueryParam("") DtListState` : `top`, `skip`, `sortFieldName`, `sortDesc`.
- La réindexation est un service à exposer par l'application : Vertigo ne la déclenche pas seule.

# [Suite : la sécurité (connexion, autorisations) se travaille dans sample-account](../sample-account)

### Optionnel : Le tri

Triez les résultats : [`/movies/_search?q=the&sortFieldName=name&sortDesc=true`](http://localhost:8081/movies/_search?q=the&sortFieldName=name&sortDesc=true) retourne les 4 films par titre décroissant (`The Iron Giant` en premier). Le tri sur `name` est possible parce que son SmartType est `sortable`.

Comparez aussi avec la recherche du [Level 3 de sample-vertigo-ui](../sample-vertigo-ui/Level3.md), qui construit un index et des facettes équivalents, utilisés depuis un écran.
