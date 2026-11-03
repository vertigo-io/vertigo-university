# sample-crystal-full

Version complète de Crystal : niveaux progressifs, authorization (entités sécurisées) et login, recherche Elasticsearch.

## Démarrage

- `crystal.run.Level1` → `crystal.run.Level7` : la progression
  - Level 1 : tâches avec listes en paramètres (`getActorsIdsByMovie`, `getMoviesInCountries`)
  - Level 2 : accesseurs (`load`, `get`, `set`, `reset`)
  - Level 3 : liste de référence statique (`Sexe` → `SexeEnum`)
  - Level 4 : DAO proxy (`MovieProxyDAO`)
  - Level 5 : web services REST et recherche à facettes (Javalin :8081). **L'index démarre vide** : appeler `GET /movies/search/_reindex` avant de chercher (`GET /movies/_search?q=the&listState.top=2`)
- `crystal.run.CrystalSample` / `crystal.run.CrystalSearchSample` : CRUD et recherche
- Aucune donnée préchargée : la base H2 est créée en mémoire au démarrage (`crystal.boot.DataBaseInitializer` : `sqlgen/crebas.sql` puis `sql/initdata.sql`)
- Le login s'appuie sur les utilisateurs de la base H2 (`crystal.services.LoginServices`)

## Contenu

- `crystal.services` : les services (Movie, Login)
- `crystal.dao.MovieProxyDAO` : un DAO de proxy
- `crystal.webservices.TestUserSession` : la session de test
- `crystal.authorization` : les définitions d'authorisation

## Code généré

`src/main/javagen` : DAO/domaine/search + authorizations. Le runner `crystal.run.Studio` régénère l'ensemble à partir de `studio-config.yaml`.
