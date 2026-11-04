# Level 2 - Accesseurs

Nous allons naviguer d'un film vers son pays et vers ses rôles.
Pour cela, Vertigo génère sur chaque entité des **accesseurs**, qui rendent la navigation explicite.

## Eléments

- Classe main : `/src/main/java/io/vertigo/samples/crystal/run/Level2`
- Code généré : `Movie.country()` et `Movie.role()`
- Services : `MovieServices.manipulateAccessors`, `MovieServices.loadMovieWithRoles`, `MovieServices.loadMovieWithRolesAndReset`

### A connaitre : Les accesseurs

Pour chaque association navigable du modèle (`model.ksp`), Studio ajoute à l'entité une méthode qui retourne un accesseur :
- `movie.country()` : un `StoreVAccessor<Country>`, vers une entité ;
- `movie.role()` : un `StoreListVAccessor<Role>`, vers une liste d'entités *(en lecture seule)*.

Un accesseur a un état : l'entité distante n'est chargée qu'à la demande.
- `isLoaded()` : indique si l'entité distante est chargée ;
- `load()` : la charge depuis la base ;
- `get()` : la retourne, une fois chargée ;
- `set(entity)` et `setId(id)` : affectent l'entité distante *(accesseur vers une entité uniquement)* ; affecter un identifiant rend l'accesseur non chargé ;
- `reset()` : oublie la liste chargée.

Par convention, le chargement est explicite : on écrit `load()` puis `get()` en deux instructions. Cela évite les chargements transparents, par exemple au milieu d'une boucle.

### A connaitre : Afficher une valeur dans les traces

Avec Log4j, un paramètre n'est affiché que si le message contient un marqueur `{}` : `info("movie roles : {}", movie.role().get())`.
Sans marqueur, la valeur est ignorée sans erreur. On peut aussi concaténer : `info("country accessor isLoaded : " + movie.country().isLoaded())`.

## Etapes

1. Ajoutez `void manipulateAccessors(Long movId)` à `MovieServices` et implémentez-le dans `MovieServicesImpl`.
1. Dans cette méthode, chargez le film puis affichez l'état de l'accesseur `country()` à chaque étape *(`LogManager.getLogger(this.getClass()).info("country accessor isLoaded : " + movie.country().isLoaded())`)* :
   - avant tout chargement,
   - après `movie.country().load()`,
   - après avoir conservé le pays (`movie.country().get()`) puis appelé `movie.country().setId(null)`,
   - après `movie.country().set(country)`.

   Terminez par `movie.role().load()` puis `movie.role().get()`.
1. Ajoutez `loadMovieWithRoles(movId)`, qui retourne le film avec sa liste de rôles chargée.
1. Ajoutez `loadMovieWithRolesAndReset(movId)`, qui appelle `loadMovieWithRoles` puis `movie.role().reset()`.
1. Créez la classe `Level2` en copiant `CrystalSample`, avec la constante `FORREST_GUMP_ID = 1000L`.
1. Dans un `step1`, appelez `manipulateAccessors` ; dans un `step2`, affichez les rôles du film retourné par `loadMovieWithRoles`, puis `isLoaded()` sur celui retourné par `loadMovieWithRolesAndReset` ; dans un `step3`, affichez `getRolesByMovie` *(ce service existe déjà et utilise l'accesseur `role()`)*.
1. Lancez `Level2`. Vous devez voir l'état de l'accesseur passer de `false` à `true`, puis `false`, puis `true` ; puis les 2 rôles de Forrest Gump (`<1>Forrest Gump` et `<2>Jenny Curran`, le préfixe vient des données) ; puis `false` après le `reset` ; enfin une liste de 2 rôles.

# [Suite : Level 3 - Liste de référence statique](./Level3.md)
