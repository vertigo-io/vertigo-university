# sample-dao

Accès aux données (SQL) : Vertigo Datastore, DAO générés par Vertigo Studio, services au-dessus des DAO et aspect de supervision.

## Démarrage

- Lancer la classe main `io.vertigo.samples.dao.run.DaoSample` : base H2 en mémoire, schéma créé au démarrage par `DataBaseInitializer`
- Le sample crée son film de démonstration (aucune donnée préchargée), puis le requiert via `MovieServices` ; la sortie console montre l'aspect de supervision

## Contenu

- `dao.run.DaoSample` : la classe main
- `dao.services` : les services au-dessus des DAO
- `dao.aspect.Supervision` : l'aspect de supervision des DAO
- `dao.boot.DataBaseInitializer` : la création/initialisation de la base H2
- `dao.config` : la configuration du node

## Code généré

`src/main/javagen` : DAO et domaine générés par Vertigo Studio. Le runner `dao.run.Studio` régénère l'ensemble à partir de `studio-config.yaml`.
