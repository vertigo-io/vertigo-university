# sample-config

Construction manuelle d'un `NodeConfig`/`ModuleConfig` avec les Features (Javalin, DataModel, Vega).

## Démarrage

- Lancer la classe main `io.vertigo.samples.config.ConfigRun` : build programmatique du node (Javalin :8080 + webservice Hello)
- Un serveur Javalin démarre sur le port 8080, le node attend ensuite sur la console (arrêt propre à l'EOF du stdin)
- Tester `http://localhost:8080/hello/` dans un navigateur

## Contenu

- `config.ConfigRun` : la construction du node en code (démarrage du node, arrêt sur EOF)
- `config.HelloWebServices` : le webservice REST
