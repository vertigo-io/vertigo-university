# sample-crystal

Vertigo DataFactory « Crystal » : accès typé aux données (H2) et moteur de recherche Elasticsearch embarqué.

## Démarrage

- `crystal.run.CrystalSample` : CRUD (lecture d'un film)
- `crystal.run.CrystalSearchSample` : web services REST (Javalin :8081) : `GET /movies/{id}`, `GET /movies/{id}/roles`, recherche `GET|POST /movies/_search` (pagination par les paramètres `listState.top`, `listState.skip`)
- Aucune donnée préchargée : la base H2 est créée en mémoire au démarrage (`crystal.boot.DataBaseInitializer` : `sqlgen/crebas.sql` puis les données de démo `sql/initdata.sql`) ; Elasticsearch est embarqué (données d'index sous `target/classes/search`)
- L'index de recherche démarre **vide** : la réindexation n'est pas automatique, la route `GET /movies/search/_reindex` est ajoutée au Level 5 (voir `sample-crystal-full`)

## Contenu

- `crystal.services` : MovieServices + MovieSearchLoader (l'indexation)
- `crystal.webservices` : MovieWebServices + TestUserSession
- `crystal.config` : la configuration du node

## Code généré

`src/main/javagen` : DAO/domaine/search générés par Vertigo Studio. Le runner `crystal.run.Studio` régénère l'ensemble à partir de `studio-config.yaml`.
