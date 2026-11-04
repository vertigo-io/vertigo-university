# sample-crystal

Vertigo DataFactory « Crystal » : accès typé aux données (H2) et moteur de recherche Elasticsearch embarqué.

## Démarrage

- `crystal.run.CrystalSample` : CRUD (lecture d'un film)
- `crystal.run.CrystalSearchSample` : web services REST (Javalin :8081) : `GET /movies/{id}`, `GET /movies/{id}/roles`, recherche `GET|POST /movies/_search` (au départ, le `GET` renvoie au plus 5 résultats et le `POST` lit sa pagination dans les paramètres d'URL `listState.top`, `listState.skip` ; le Level 5 étend cette pagination au `GET`)
- Lancement : depuis l'IDE (*Run As > Java Application* sur la classe main), avec la **racine du module** `sample-crystal` comme répertoire de travail (nécessaire pour `Studio`). En ligne de commande : `mvn compile`, puis `java` avec le classpath produit par `mvn dependency:build-classpath` (voir le « Bon à savoir » de l'étape 1 du [parcours Démarrage](../docs/parcours-demarrage.md))
- Aucune donnée préchargée : la base H2 est créée en mémoire au démarrage (`crystal.boot.DataBaseInitializer` : `sqlgen/crebas.sql` puis les données de démo `sql/initdata.sql`) ; Elasticsearch est embarqué (données d'index sous `target/classes/search`)
- L'index de recherche démarre **vide** : la réindexation n'est pas automatique, la route `GET /movies/search/_reindex` est ajoutée au Level 5 (voir `sample-crystal-full`)

## Exercices

Le module est le point de départ des exercices, à suivre dans l'ordre ; la solution est dans [sample-crystal-full](../sample-crystal-full).

- [Level 0 - Prise en main](./Level0.md)
- [Level 1 - Tâches et listes en paramètres](./Level1.md)
- [Level 2 - Accesseurs](./Level2.md)
- [Level 3 - Liste de référence statique](./Level3.md)
- [Level 4 - DAO proxy](./Level4.md)
- [Level 5 - Web services REST et recherche à facettes](./Level5.md)

## Contenu

- `crystal.services` : MovieServices + MovieSearchLoader (l'indexation)
- `crystal.webservices` : MovieWebServices + TestUserSession
- `crystal.config` : la configuration du node

## Code généré

`src/main/javagen` : DAO/domaine/search générés par Vertigo Studio. Le runner `crystal.run.Studio` régénère l'ensemble à partir de `studio-config.yaml`.
