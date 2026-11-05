# sample-crystal-full

Version complète de Crystal : niveaux progressifs, authorization (entités sécurisées) et login, recherche Elasticsearch.

## Démarrage

- `crystal.run.Level1` → `crystal.run.Level7` : la progression (solution des exercices ; les énoncés `LevelN.md` sont dans [sample-crystal](../sample-crystal/README.md#exercices))
  - Level 1 : tâches avec listes en paramètres (`getActorsIdsByMovie`, `getMoviesInCountries`)
  - Level 2 : accesseurs (`load`, `get`, `set`, `reset`)
  - Level 3 : liste de référence statique (`Sexe` → `SexeEnum`)
  - Level 4 : web services REST et recherche à facettes (Javalin :8081). **L'index est vide au premier lancement** : appeler `GET /movies/search/_reindex` avant de chercher (`GET /movies/_search?q=the&top=2`)
  - Level 5 (pour aller plus loin, hors parcours) : DAO proxy (`MovieProxyDAO`)
  - Level 6 et 7 (hors parcours, sans énoncé) : login puis authorization (entités sécurisées)
- `crystal.run.CrystalSample` / `crystal.run.CrystalSearchSample` : CRUD et recherche
- Aucune donnée préchargée : la base H2 est créée en mémoire au démarrage (`crystal.boot.DataBaseInitializer` : `sqlgen/crebas.sql` puis `sql/initdata.sql`)
- Le login (`crystal.services.LoginServices`) lit les comptes dans la base H2 (`DT_USER`), mais le mot de passe est vérifié par un serveur LDAP du réseau interne : les Levels 6 et 7 ne tournent pas hors de ce réseau et ne sont pas vérifiés

## Contenu

- `crystal.services` : les services (Movie, Login)
- `crystal.dao.MovieProxyDAO` : un DAO de proxy
- `crystal.webservices.TestUserSession` : la session de test
- `crystal.authorization` : les définitions d'authorisation

## Code généré

`src/main/javagen` : DAO/domaine/search + authorizations. Le runner `crystal.run.Studio` régénère l'ensemble à partir de `studio-config.yaml`.
