# sample-vega

Vega : créer et publier des web services REST dans une application Vertigo.

## Démarrage

- Lancer la classe main `io.vertigo.samples.vega.SampleVega`
- Un serveur Jetty démarre sur le port 8080 ; appeler les REST Hello/Movie/Token depuis un navigateur ou curl

## Contenu

- `vega.webservices` : les webservices REST (`@PathPrefix`, `@GET`…)
- `vega.domain` : le domaine Movie et son DAO
- `vega.config` : la configuration du node (Jetty, websockets)
