# sample-dao-full

Version complète du sample DAO : multi-tables (Movie/Actor/Role/Country), DAO composites et personnalisés, projections, ingestion par lots (reprise avec offset).

## Démarrage

Chaque runner crée au démarrage deux bases H2 en mémoire (`DataBaseInitializer`) : aucune base n'est à préparer.

- la base principale reçoit son schéma (`sqlgen/crebas.sql`) et les données de démonstration (`sql/initdata.sql`) : 20 films réels, leurs acteurs et leurs rôles, 5 pays, les mêmes que dans les autres samples ;
- la base `mine` reçoit le seul schéma `sqlgen/crebas_mine.sql` : c'est la cible de la reprise.

Les runners progressifs : `dao.run.Level2` → `dao.run.Level7` (et `dao.run.DaoSample` pour un tour d'horizon) ; `dao.run.Reprise` recopie par lots les pays, acteurs, films et rôles de la base principale vers la base `mine`.

## Contenu

- `dao.run` : les runners de niveau
- `dao.services` : les services Movie/Actor/Country/Reprise
- `dao.reprise.ReprisePAO` : l'ingestion par lots avec offset

## Code généré

`src/main/javagen` : DAO générés (y compris les DAO personnalisés `My*`) et projections (MovieDisplay, MovieByYear). Le runner `dao.run.Studio` régénère l'ensemble à partir de `studio-config.yaml`.
