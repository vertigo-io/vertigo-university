# sample-dao-full

Version complète du sample DAO : multi-tables (Movie/Actor/Role/Country), DAO composites et personnalisés, projections, ingestion par lots (reprise avec offset).

## Démarrage

- `dao.run.Reprise` : recrée la base H2 et remplit countries/actors/movies/roles
- Puis les runners progressifs `dao.run.Level2` → `dao.run.Level7` (et `dao.run.DaoSample` pour un tour d'horizon)

## Contenu

- `dao.run` : les runners de niveau
- `dao.services` : les services Movie/Actor/Country/Reprise
- `dao.reprise.ReprisePAO` : l'ingestion par lots avec offset

## Code généré

`src/main/javagen` : DAO générés (y compris les DAO personnalisés `My*`) et projections (MovieDisplay, MovieByYear). Le runner `dao.run.Studio` régénère l'ensemble à partir de `studio-config.yaml`.
