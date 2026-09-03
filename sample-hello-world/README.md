# sample-hello-world

Premier node Vertigo : un webservice REST « hello world » publié via Vega.

## Démarrage

- Lancer la classe main `io.vertigo.samples.hello.HelloWorld` (Eclipse : *Run As → Java Application*)
- Un serveur Javalin démarre sur le port 8080, le node attend ensuite sur la console
- Tester `http://localhost:8080/hello/` dans un navigateur

## Contenu

- `hello.HelloWorld` : la classe main (démarrage du node, arrêt sur EOF)
- `hello.config.HelloConfigurator` : la configuration du node (Javalin, Vega)
- `hello.webservices.HelloWebServices` : le webservice REST
