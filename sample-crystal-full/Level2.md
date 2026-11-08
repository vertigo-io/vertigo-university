# Level 2 - Accesseurs

Nous allons naviguer d'un film vers son pays et vers ses rôles.
Pour cela, Vertigo génère sur chaque entité des **accesseurs**, qui rendent la navigation explicite : on voit dans le code chaque accès à la base.

À la fin de ce Level, vous saurez :
- naviguer d'une entité vers une autre avec un accesseur ;
- savoir quand un accesseur accède à la base, et où faire ce chargement ;
- lire dans la console les requêtes déclenchées par la navigation.

## Eléments

- Classe main : `/src/main/java/io/vertigo/samples/crystal/run/Level2`
- Code généré : `Movie.country()` et `Movie.role()`
- Services : `MovieServices.manipulateAccessors`, `MovieServices.loadMovieWithRoles`, `MovieServices.loadMovieWithRolesAndReset`
- Documentation : [vertigo-docs : FAQ](https://vertigo-io.github.io/vertigo-docs/#/extensions/faq) *(cherchez « accessor » dans la page : les questions [DataStore] sur le chargement des accesseurs)*

### A connaitre : Les accesseurs

Pour chaque association navigable du modèle (`model.ksp`), Studio ajoute à l'entité une méthode qui retourne un accesseur. Le **rôle** de l'association lui donne son nom :
- `movie.country()` : un `StoreVAccessor<Country>`, vers une entité ;
- `movie.role()` : un `StoreListVAccessor<Role>`, vers une liste d'entités *(en lecture seule)*.

Un accesseur ne charge rien automatiquement :
- `isLoaded()` : indique si l'entité distante est chargée ;
- `load()` : la charge depuis la base ;
- `get()` : la retourne, une fois chargée ;
- `set(entity)` et `setId(id)` : affectent l'entité distante *(accesseur vers une entité uniquement)* ; affecter un identifiant rend l'accesseur non chargé ;
- `reset()` : oublie la liste chargée.

Pourquoi ce chargement explicite ? Parce qu'un `load()` est **un accès à la base** : ce n'est pas une opération à prendre à la légère. Un chargement transparent, caché derrière un simple `get()`, multiplierait les requêtes sans qu'on le voie, par exemple au milieu d'une boucle. On charge donc les données dont le traitement a besoin, au moment où il en a besoin.

Par convention, le `load()` est fait **dans un service** : c'est un accès SQL, il doit être exécuté dans une transaction (`@Transactional`, porté par les services). On écrit `load()` puis `get()` en deux instructions.

**Attention** : appeler `get()` sur un accesseur non chargé lève l'erreur `Accessor is not loaded, you must load it before calling get method`.

## Etapes

1. Ajoutez `void manipulateAccessors(Long movId)` à `MovieServices` et implémentez-le dans `MovieServicesImpl`.
1. Dans cette méthode, chargez le film puis affichez l'état de l'accesseur `country()` à chaque étape *(`LogManager.getLogger(this.getClass()).info("country accessor isLoaded : " + movie.country().isLoaded())`)* :
   - avant tout chargement,
   - après `movie.country().load()`,
   - après avoir conservé le pays (`movie.country().get()`) puis appelé `movie.country().setId(null)`,
   - après `movie.country().set(country)`.

   Terminez par `movie.role().load()`, puis affichez `movie.role().get()`.
1. Ajoutez `loadMovieWithRoles(movId)`, qui retourne le film avec sa liste de rôles chargée.
1. Ajoutez `loadMovieWithRolesAndReset(movId)`, qui appelle `loadMovieWithRoles` puis `movie.role().reset()`.
1. Créez la classe `Level2` en copiant `CrystalSample`, avec la constante `FORREST_GUMP_ID = 1000L`.
1. Dans un `step1`, appelez `manipulateAccessors` ; dans un `step2`, affichez les rôles du film retourné par `loadMovieWithRoles`, puis `isLoaded()` sur celui retourné par `loadMovieWithRolesAndReset` ; dans un `step3`, affichez `getRolesByMovie` *(ce service existe déjà et utilise l'accesseur `role()`)*.
*(rappel : marqueur `{}` dans le message Log4j, voir le Level 1)*
1. Lancez `Level2`. Vous devez voir l'état de l'accesseur passer de `false` à `true`, puis `false`, puis `true` ; puis les 3 rôles de Forrest Gump (`<1>Forrest Gump`, `<2>Jenny Curran` et `<3>Lieutenant Dan Taylor`, le préfixe vient des données) ; puis `false` après le `reset` ; enfin une liste de 3 rôles.
1. **Observez** la console autour des traces `country accessor isLoaded` : entre `false` et `true`, une requête `TkSelectCountryByUri` est exécutée. C'est le `load()` qui lit le pays en base. En revanche, le `set(country)` ne déclenche aucune requête. De même, `role().load()` exécute `TkSelectListRoleByCriteria`.

### Ce qu'il faut retenir

- Une association navigable donne un accesseur sur l'entité : `load()` puis `get()`.
- Un `load()` est un accès à la base : on le fait explicitement, dans un service transactionnel, quand le traitement en a besoin.
- La console montre chaque requête déclenchée : c'est le moyen de vérifier ce que coûte une navigation.

# [Suite : Level 3 - Liste de référence statique](./Level3.md)
