# sample-vega

Vega : créer et publier des web services REST dans une application Vertigo.

## Démarrage

- Lancer la classe main `io.vertigo.samples.vega.SampleVega`
- Un serveur Javalin démarre sur le port 8080 ; appeler les REST Hello/Movie/Token depuis un navigateur ou curl (par exemple `http://localhost:8080/hello/`, `http://localhost:8080/movies/`)
- Le node s'arrête à la fermeture du stdin (touche Entrée dans la console)

## Contenu

- `vega.webservices` : les webservices REST (`@PathPrefix`, `@GET`…)
- `vega.domain` : le domaine Movie et son DAO
- `vega.config` : la configuration du node (Javalin embarqué, webservices Vega, tokens)
