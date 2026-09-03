# sample-crystal-full

Version complète de Crystal : niveaux progressifs, authorization (entités sécurisées) et login, recherche Elasticsearch.

## Démarrage

- `crystal.run.Level1` → `crystal.run.Level7` : la progression
- `crystal.run.CrystalSample` / `crystal.run.CrystalSearchSample` : CRUD et recherche
- Le login s'appuie sur les utilisateurs de la base H2 (`crystal.services.LoginServices`)

## Contenu

- `crystal.services` : les services (Movie, Login)
- `crystal.dao.MovieProxyDAO` : un DAO de proxy
- `crystal.webservices.TestUserSession` : la session de test
- `crystal.authorization` : les définitions d'authorisation

## Code généré

`src/main/javagen` : DAO/domaine/search + authorizations. Le runner `crystal.run.Studio` régénère l'ensemble à partir de `studio-config.yaml`.
