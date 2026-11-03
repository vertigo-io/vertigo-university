# Level 2 - Accesseurs

Nous allons naviguer d'un film vers son pays et vers ses rôles avec les accesseurs générés.

## Eléments

- Code généré : `Movie.country()` (accesseur vers une entité) et `Movie.role()` (accesseur vers une liste d'entités)
- Services (`src/main/java/io/vertigo/samples/crystal/services/`) :
  - `MovieServices.manipulateAccessors(Long movId)`
  - `MovieServices.loadMovieWithRoles(Long movId)` : retourne `Movie`
  - `MovieServices.loadMovieWithRolesAndReset(Long movId)` : retourne `Movie`
  - `MovieServices.getRolesByMovie(Long movId)` : existe déjà, il utilise l'accesseur `role()`
- Classe main à créer : `src/main/java/io/vertigo/samples/crystal/run/Level2.java`

### A connaitre : Les accesseurs

Pour chaque association navigable du modèle (`model.ksp`), le générateur ajoute à l'entité une méthode qui retourne un **accesseur** :

- `movie.country()` : un `StoreVAccessor<Country>`, vers une entité ;
- `movie.role()` : un `StoreListVAccessor<Role>`, vers une liste d'entités (en lecture seule).

Un accesseur a un état : l'entité distante n'est chargée qu'à la demande.

- `isLoaded()` : l'entité distante est-elle chargée ?
- `load()` : charge l'entité (ou la liste) distante depuis la base ;
- `get()` : retourne l'entité chargée (à appeler après `load()`) ;
- `set(entity)` / `setId(id)` : affecte l'entité distante (accesseur vers une entité uniquement) ; affecter un identifiant rend l'accesseur non chargé ;
- `reset()` : oublie la liste chargée.

Le chargement est explicite : on écrit `load()` puis `get()` en deux instructions, ce qui évite les chargements transparents en boucle.

## Etapes

1. Implémentez `manipulateAccessors(movId)` dans `MovieServicesImpl` : chargez le film avec `getMovieById(movId)`, puis affichez `movie.country().isLoaded()` à chaque étape :
   - avant tout chargement ;
   - après `movie.country().load()` ;
   - après avoir conservé le pays (`movie.country().get()`) puis appelé `movie.country().setId(null)` ;
   - après `movie.country().set(country)`.
   Terminez par `movie.role().load()` puis `movie.role().get()`.
2. Implémentez `loadMovieWithRoles(movId)` : le film, avec sa liste de rôles chargée (`movie.role().load()`).
3. Implémentez `loadMovieWithRolesAndReset(movId)` : appelez `loadMovieWithRoles`, puis `movie.role().reset()`.
4. Créez `Level2` en copiant `CrystalSample`, avec la constante `FORREST_GUMP_ID = 1000L` :
   - `step1()` : appelez `movieServices.manipulateAccessors(FORREST_GUMP_ID)` ;
   - `step2()` : affichez les rôles du film retourné par `loadMovieWithRoles` (`movie.role().get()`), puis `isLoaded()` sur le film retourné par `loadMovieWithRolesAndReset` ;
   - `step3()` : affichez `movieServices.getRolesByMovie(FORREST_GUMP_ID)`.
5. Lancez `Level2`.
   **Vérification** : la console affiche, dans l'ordre, `country accessor isLoaded : false`, `true`, `false`, `true` ; puis les 2 rôles de Forrest Gump (`Forrest Gump` et `Jenny Curran`) ; puis `false` après le `reset` ; puis une liste de taille 2.

**Bon à savoir** : avec Log4j, un paramètre de message n'est affiché que si le message contient un marqueur `{}` : écrivez `info("movie roles : {}", movie.role().get())`.

# [Suite : Level 3 - Liste de référence statique](./Level3.md)
