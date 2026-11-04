# Level 5 - Web services REST et recherche à facettes

Nous allons publier la recherche de films en REST : réindexation à la demande, pagination pilotée par le client et facettes (dont une à sélection multiple).

## Eléments

- Routes (sur [http://localhost:8081](http://localhost:8081/movies/1000)) :
  - `GET /movies/search/_reindex` (à créer) : réindexe les films, retourne le nombre de films indexés
  - `GET /movies/_search?q=...` : recherche, avec la pagination passée par le client
  - `POST /movies/_search` : recherche avec sélection de facettes
- Web services : `src/main/java/io/vertigo/samples/crystal/webservices/MovieWebServices.java`
- Service : `MovieServicesImpl.searchMovies` (`src/main/java/io/vertigo/samples/crystal/services/`)
- Définitions de recherche : `src/main/resources/searchMovie.ksp`
- Smart types : `src/main/java/io/vertigo/samples/crystal/config/SampleCrystalSmartTypes.java`
- Classe main à créer : `src/main/java/io/vertigo/samples/crystal/run/Level5.java`

### A connaitre : L'index et les facettes

L'index `IdxMovie` (`searchMovie.ksp`) indexe des `MovieIndex` (champs `movId`, `name`, `year`, `country`), chargés par `MovieSearchLoader`. La recherche plein texte porte sur le champ `allText`, qui regroupe les autres champs (`indexCopyTo`).

Deux requêtes de recherche sont déclarées : `QryMovie`, sans facette, et `QryMovieWithFacets`, avec les facettes `FctMovieName` (par tranche de titre), `FctMovieYear` (par décennie) et `FctMovieCountry` (par pays). Le client généré `MovieIndexSearchClient` propose une méthode par requête : `createSearchQueryBuilderMovie` et `createSearchQueryBuilderMovieWithFacets`.

Une facette porte sur une valeur exacte, pas sur un texte analysé. Le type d'indexation d'un champ est donné par la propriété `indexType` de son smart type :

- `text_fr` : texte analysé en français, pour la recherche plein texte ;
- `text_fr:sortable` : en plus, la plateforme duplique le champ dans un sous-champ exact `<champ>.keyword`, utilisable pour trier et pour les facettes (par exemple `name.keyword:[a TO g]`).

Une facette déclarée `multiSelectable : "true"` accepte plusieurs valeurs sélectionnées à la fois.

### A connaitre : La pagination passée par le client

Un paramètre `final DtListState dtListState` **sans annotation** dans un web service est lu dans les paramètres d'URL préfixés `listState.` : `listState.top`, `listState.skip`, `listState.sortFieldName`, `listState.sortDesc`. Exemple : `/movies/_search?q=the&listState.top=2`. En `POST` aussi, la pagination se passe dans l'URL ; le corps porte les autres paramètres (`@InnerBodyParam`).

### A connaitre : La réindexation

L'index est vide au premier lancement et n'est pas réindexé automatiquement. Une fois réindexé, il est conservé d'un lancement à l'autre (`target/classes/search/data`). `MovieServices.indexMovies()` existe déjà : il lance la réindexation complète et retourne le nombre de films indexés. Il suffit de le publier dans une route.

## Etapes

1. Dans `SampleCrystalSmartTypes`, passez l'`indexType` des smart types `Label` (le pays) et `LabelLong` (le titre) de `text_fr` à `text_fr:sortable`.
2. Dans `searchMovie.ksp` :
   - `FctMovieName` : utilisez le champ `name` et ses tranches sur `name.keyword` (`range r1 { filter:"name.keyword:[* TO a]", label:"#"}`, etc.) à la place de `nameSortOnly` ;
   - `FctMovieCountry` : ajoutez `multiSelectable : "true"`, sur une ligne à part :
     ```
     create FacetDefinition FctMovieCountry {  
       dtDefinition:DtMovieIndex, fieldName:"country", label:"Par pays"
       multiSelectable : "true"
     }
     ```
3. Lancez `Studio` depuis la racine du module.
   **Vérification** : dans `MovieIndexSearchClient`, les tranches de `FctMovieName` portent sur `name.keyword` et `FctMovieCountry` déclare `.withMultiSelectable()`.
4. Dans `MovieServicesImpl.searchMovies`, utilisez `movieIndexSearchClient.createSearchQueryBuilderMovieWithFacets(criteria, selectedFacetValues)` au lieu de `createSearchQueryBuilderMovie`.
5. Dans `MovieWebServices` :
   - ajoutez la route `@AnonymousAccessAllowed @GET("/search/_reindex")` qui retourne `movieServices.indexMovies()` ;
   - dans `GET /_search`, remplacez la pagination fixe `DtListState.of(5)` par un paramètre `final DtListState dtListState`.
6. Créez `Level5` en copiant `CrystalSearchSample` sous ce nouveau nom : il contient déjà tout le nécessaire (web services activés par `createNodeConfigBuilder(true, true)`, `ActorDAO` dans `stepDao` depuis le Level 3, un `step1()` qui attend avec `Thread.currentThread().join()`).
7. Lancez `Level5` et attendez la fin des traces de démarrage (voir le Level 0), puis appelez (navigateur pour les `GET`, ou `curl` ; les commandes ci-dessous sont écrites pour Git Bash) :
   - `curl http://localhost:8081/movies/search/_reindex` : **200**, `6`
   - `curl "http://localhost:8081/movies/_search?q=gump"` : **200**, Forrest Gump
   - `curl "http://localhost:8081/movies/_search?q=the&listState.top=2"` : **200**, 2 films
   - `curl "http://localhost:8081/movies/_search?q=the"` : **200**, 4 films, et la facette `FctMovieCountry` (`"isMultiSelectable": true`) avec les valeurs `etats-unis` (2), `allemagne` (1), `royaume-uni` (1)
   - le `POST`, avec un corps JSON (en-tête `Content-Type: application/json` recommandé) : **200**, 3 films
     ```
     curl -X POST -H "Content-Type: application/json" -d '{"criteria":"the","facets":{"FctMovieCountry":["etats-unis","allemagne"]}}' http://localhost:8081/movies/_search
     ```

   **Bon à savoir** : l'index est créé au démarrage à partir des smart types ; après les avoir modifiés, la plateforme met à jour sa structure au démarrage suivant, il suffit de réindexer. Pour repartir d'un index complètement vide, supprimez `target/classes/search/data` (programme arrêté).
8. Arrêtez `Level5` (Ctrl+C).

**Les erreurs et pièges que vous pouvez rencontrer** :

- une recherche avant toute réindexation (index vide, au premier lancement ou après suppression de `target/classes/search/data`) : `"totalCount": 0` ;
- une facette sur un champ qui n'est pas `sortable` (par exemple en laissant `Label` en `text_fr`, pour la facette `FctMovieCountry`) : **422**, avec dans le JSON `"Search index structure error.\nSorted or aggregated fields must be declared as keyword, sortable or facetable in your SmartType."` ;
- `top=2` au lieu de `listState.top=2` : le paramètre est ignoré, toute la liste revient ;
- un `POST` avec un critère vide (`"criteria":""`) : **500** `RestParam not found : class java.lang.String :InnerBody:criteria` ;
- un `POST` sans la clé `facets` : **500** `RestParam not found : class io.vertigo.datafactory.collections.model.SelectedFacetValues :InnerBody:facets` (envoyez `"facets":{}` si aucune facette n'est sélectionnée).

## Optionnel : pour aller plus loin

- Triez les résultats : `GET /movies/_search?q=the&listState.sortFieldName=name&listState.sortDesc=true` retourne les 4 films par titre décroissant (`The Iron Giant` en premier). Le tri sur `name` est possible parce que son smart type est `sortable`.
- Comparez avec la recherche du Level 3 de [sample-vertigo-ui](../sample-vertigo-ui/Level3.md), qui construit un index et des facettes équivalents, utilisés depuis un écran.

# [Suite : la sécurité (connexion, autorisations) se travaille dans sample-account](../sample-account)
