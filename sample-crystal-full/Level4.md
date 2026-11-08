# Level 4 - Web services REST et recherche à facettes

Nous allons publier la recherche de films en REST.
Le client pourra déclencher la réindexation, piloter la pagination et le tri, et sélectionner des facettes, dont une à sélection multiple.

À la fin de ce Level, vous saurez :
- expliquer ce qu'apporte un moteur de recherche par rapport à SQL ;
- choisir comment indexer un champ selon l'usage : recherche, tri ou facette ;
- exposer une recherche paginée et facettée dans un web service, et déclencher la réindexation.

## Eléments

- Routes : [http://localhost:8081/movies/search/_reindex](http://localhost:8081/movies/search/_reindex) *(à créer)* et [http://localhost:8081/movies/_search?q=the](http://localhost:8081/movies/_search?q=the)
- Classe main : `/src/main/java/io/vertigo/samples/crystal/run/Level4`
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

Une **facette** regroupe les résultats par valeur, avec le nombre de résultats de chacune. Il en existe deux types courants, qui ne prennent pas leurs libellés au même endroit :
- les facettes **term** (`FctMovieCountry`, par pays) : leurs valeurs, et donc leurs libellés, viennent **des données** de l'index ;
- les facettes **range** (`FctMovieName` par tranche de titre, `FctMovieYear` par décennie) : leurs tranches et leurs libellés viennent **de la définition** (`label:"a-f"`). Le filtre de chaque tranche est une requête écrite dans la syntaxe du moteur de recherche : il nomme donc lui-même le champ à interroger (`name.keyword:[a TO g]`).

Deux requêtes de recherche sont déclarées, `QryMovie` (sans facette) et `QryMovieWithFacets` ; Studio génère pour chacune une méthode dans `MovieIndexSearchClient` (`createSearchQueryBuilderMovie…`).

Une facette déclarée `multiSelectable : "true"` accepte plusieurs valeurs sélectionnées à la fois. Quand une valeur est sélectionnée, elle continue d'afficher ses **autres valeurs avec leurs compteurs**, pour que l'utilisateur puisse en ajouter ; une facette simple, elle, ne garde que la valeur choisie.
**Attention**, la documentation le précise : les facettes multi-sélectionnables ont un coût en performances. Ne les activez que si le besoin est réel.

### A connaitre : Indexer un champ selon son usage

Le SmartType d'un champ d'index précise comment il est indexé, avec sa propriété `indexType` :
- `text_fr` : un texte analysé en français, découpé en mots réduits à leur racine, pour la **recherche plein texte** ;
- `text_fr:sortable` : en plus, Vertigo ajoute une version `keyword` du champ (`name.keyword`), qui conserve la valeur entière, mise en minuscules et sans accents (normalizer `sortable`). Vertigo l'utilise de manière transparente pour le **tri** ; les filtres des tranches s'en servent aussi ;
- `code:keyword` : le champ est indexé comme une **valeur exacte**, telle qu'elle est dans les données (le normalizer `code` n'applique aucun filtre). C'est ce qu'il faut pour une **facette term** dont les libellés doivent rester lisibles : `Etats-Unis` plutôt que `etats-unis`.

À l'inverse, une facette sur un texte analysé regroupe les **mots** analysés, pas les valeurs : en `text_fr:facetable`, la facette pays renverrait `etat`, `unis`, `allemagn`, `royaum`…

### A connaitre : La pagination et le tri

Pour ajouter la pagination et le tri à un web service, on ajoute un paramètre `@QueryParam("") final DtListState dtListState` et on le transmet au service. Le client passe alors dans l'url : `top` (nombre maximum d'éléments), `skip` (position du premier), `sortFieldName` (champ de tri) et `sortDesc` (`true` pour un tri décroissant, obligatoire dès qu'on passe `sortFieldName`, sinon la réponse est un 400).
Par exemple : `/movies/_search?q=the&top=2&skip=2`. En `Post`, la pagination passe aussi par l'url ; le corps porte les autres paramètres (`@InnerBodyParam`).

### A connaitre : La réindexation

L'index est vide au premier lancement et la réindexation n'est pas automatique. Vertigo ne fournit pas d'écran d'administration de la réindexation : le web service ou l'écran qui la déclenche est à créer dans l'application.
Le service `MovieServices.indexMovies()` existe déjà : il appelle `reindexAll` du `SearchManager` et retourne le nombre de films indexés. Il ne reste qu'à le publier sur une route.

L'index est créé dès le premier démarrage d'une classe main avec la recherche, puis conservé d'un lancement à l'autre (`target/classes/search/data`). Quand vous modifiez sa structure (un `indexType`), deux cas se présentent :
- un changement **compatible**, comme ajouter `sortable` (un sous-champ en plus), est accepté au démarrage : **réindexez** pour que les documents aient le nouveau champ ;
- un changement **incompatible**, comme passer un champ de texte (`text_fr`) à une valeur exacte (`code:keyword`), fait échouer le démarrage (`mapper [country] cannot be changed from type [text] to [keyword]`) : programme arrêté, **supprimez l'index** (`target/classes/search/data`), relancez, puis réindexez.

## Etapes

1. Dans `SampleCrystalSmartTypes`, passez l'`indexType` du SmartType `LabelLong` (le titre) de `text_fr` à `text_fr:sortable`, et celui de `Label` (le pays) de `text_fr` à `code:keyword`.
*(dans `modelSearch.ksp`, le champ `name` a le domain `DoLabelLong` et le champ `country` le domain `DoLabel` : chaque domain `DoXxx` correspond au SmartType `Xxx`)*
1. Dans `searchMovie.ksp`, faites porter `FctMovieName` sur le champ `name`, avec des tranches sur `name.keyword` à la place de `nameSortOnly` *(`range r1 { filter:"name.keyword:[* TO a]", label:"#"}`, etc. ; le champ calculé `nameSortOnly` n'est alors plus utilisé, vous pouvez le laisser)*.
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
8. Créez la classe `Level4` en copiant `CrystalSearchSample` : elle contient déjà tout le nécessaire.
9. Le pays change de type d'indexation, un changement incompatible avec l'index créé aux Levels précédents : supprimez d'abord le dossier `target/classes/search/data` *(sinon le démarrage échoue, voir « La réindexation »)*. Lancez ensuite `Level4`, attendez la fin du démarrage, puis appelez [http://localhost:8081/movies/search/_reindex](http://localhost:8081/movies/search/_reindex) : vous devez obtenir `6`.
10. Testez la recherche :
    - [`/movies/_search?q=gump`](http://localhost:8081/movies/_search?q=gump) retourne Forrest Gump ;
    - [`/movies/_search?q=the`](http://localhost:8081/movies/_search?q=the) retourne 4 films, avec la facette `FctMovieCountry` (`"isMultiSelectable": true`) et ses valeurs `Etats-Unis` (2), `Allemagne` (1), `Royaume-Uni` (1) ;
    - [`/movies/_search?q=the&top=2`](http://localhost:8081/movies/_search?q=the&top=2) retourne les 2 premiers films, et [`&top=2&skip=2`](http://localhost:8081/movies/_search?q=the&top=2&skip=2) les 2 suivants ; `totalCount` reste à 4.
11. Testez la sélection de facettes en `Post` *(par exemple avec curl depuis Git Bash)* : en sélectionnant `Etats-Unis` et `Allemagne`, vous devez obtenir 3 films.
*(une facette term se sélectionne par sa valeur, `Etats-Unis` ; une facette range par le code de sa tranche, `r9`)*
*(**observez** aussi : en sélectionnant seulement `Etats-Unis`, vous obtenez 2 films et la facette pays garde `Allemagne` (1) et `Royaume-Uni` (1) ; en sélectionnant la décennie `r9` de `FctMovieYear`, facette simple, il ne reste qu'elle)*
```
curl -X POST -H "Content-Type: application/json" -d '{"criteria":"the","facets":{"FctMovieCountry":["Etats-Unis","Allemagne"]}}' http://localhost:8081/movies/_search
```
12. **Question** : dans la réponse de `q=the`, la facette `FctMovieName` n'affiche que `t-z` *(seules les tranches non vides sont renvoyées)*. Si l'on ajoutait sur l'année une facette term, puis une facette range par décennie comme `FctMovieYear`, quels libellés afficherait chacune, et d'où viendraient-ils ?
13. Arrêtez `Level4` *(Ctrl+C)*.

**Attention** :
- une facette sur un champ qui n'est ni `keyword`, ni `sortable`, ni `facetable` (par exemple, sur un index neuf, avec `Label` laissé en `text_fr`) fait répondre la recherche en **422** : `Search index structure error. Sorted or aggregated fields must be declared as keyword, sortable or facetable in your SmartType.` ;
- en `Post`, le critère ne doit pas être vide et la clé `facets` est obligatoire *(`"facets":{}` si aucune facette n'est sélectionnée)*, sinon la réponse est un **500** `RestParam not found`.

### Ce qu'il faut retenir

- La recherche est un filtre, et la recherche plain text cherche des mots qui *commencent par* le critère.
- Un champ s'indexe selon son usage : `text_fr` pour chercher, `sortable` pour trier ou découper en tranches, `code:keyword` pour une facette par valeur aux libellés lisibles.
- Une facette term prend ses libellés dans les données, une facette range dans sa définition.
- La pagination et le tri se passent par un `@QueryParam("") DtListState` : `top`, `skip`, `sortFieldName`, `sortDesc`.
- La réindexation est un service à exposer par l'application : Vertigo ne la déclenche pas seule.

# [Suite : la sécurité (connexion, autorisations) se travaille dans sample-account](../sample-account)

### Optionnel : Le tri

Triez les résultats : [`/movies/_search?q=the&sortFieldName=name&sortDesc=true`](http://localhost:8081/movies/_search?q=the&sortFieldName=name&sortDesc=true) retourne les 4 films par titre décroissant (`The Iron Giant` en premier). Le tri sur `name` est possible parce que son SmartType est `sortable`.

Comparez aussi avec la recherche du [Level Ui 3 de sample-vertigo-ui](../sample-vertigo-ui/LevelUi3.md), qui construit un index et des facettes équivalents, utilisés depuis un écran.

### Pour aller plus loin

[Level 5 - DAO proxy](./Level5.md) : une façon de déclarer une tâche hors des définitions, à connaître mais **hors du parcours normal**.
