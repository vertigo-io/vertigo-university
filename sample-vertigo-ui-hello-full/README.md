# sample-vertigo-ui-hello-full

Solution de l'exercice [sample-vertigo-ui-hello](../sample-vertigo-ui-hello) : le boot web minimal d'un node Vertigo-UI, complet. Sans base de données, sans Elasticsearch, sans BerkeleyDB et sans donnée préchargée.

## Démarrage

- Lancer la classe main `io.vertigo.samples.uihello.BootSampleUiHello` (Jetty :18081, context `/uihello`)
- Tester `http://localhost:18081/uihello/home/` dans un navigateur (attendu : 200, « Hello Vertigo-UI !! »)
- Arrêter avec Ctrl+C (le node web ne lit pas le stdin)

## Contenu (la solution, brique par brique)

- `BootSampleUiHello` : le boot web (`JettyBoot` + `MultipartConfigInjectionHandler`)
- `support.config` : l'initialisation SpringMVC (`VSpringWebConfig`)
- `support.SampleUiHelloUserSession` : la session utilisateur (sécurité Vertigo-UI)
- `controllers.HomeController` : le controller qui rend la vue `uihello/home`
- `webapp/META-INF/sample-ui-hello.yaml` : la configuration du node — les 6 features, **sans cache ni lucene**
- `webapp/WEB-INF/web.xml` : le listener `AppServletContextListener` et le paramètre `boot.applicationConfiguration`
- `webapp/WEB-INF/views/` : le layout `sampleLayout` (fourni dans l'exo) et la page `uihello/home`

## Suite

- [sample-vertigo-ui](../sample-vertigo-ui) : la formation Vertigo-UI complète (Levels 0→6 : base de données, recherche, listes, écran de détail, modales)
