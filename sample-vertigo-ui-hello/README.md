# sample-vertigo-ui-hello

Premier écran Vertigo-UI : le boot web minimal (Jetty), un layout, une page. Sans base de données, sans Elasticsearch, sans BerkeleyDB et sans donnée préchargée.

## Démarrage

- Lancer la classe main `io.vertigo.samples.uihello.BootSampleUiHello` (Jetty :18081, context `/uihello`)
- Tester `http://localhost:18081/uihello/home/` dans un navigateur
- Arrêter avec Ctrl+C (le node web ne lit pas le stdin, contrairement à un node Javalin)

## Contenu

- `BootSampleUiHello` : le boot web (`JettyBoot` + `MultipartConfigInjectionHandler`)
- `support.config` : l'initialisation SpringMVC (`VSpringWebConfig`)
- `support.SampleUiHelloUserSession` : la session utilisateur (sécurité Vertigo-UI)
- `controllers.HomeController` : le controller qui rend la vue `uihello/home`
- `webapp/META-INF/sample-ui-hello.yaml` : la configuration du node (boot, features)
- `webapp/WEB-INF/web.xml` : le Listener et le paramètre `boot.applicationConfiguration`
- `webapp/WEB-INF/views/` : le layout `mmcLayout` et la page `uihello/home`

## Suite

- [sample-vertigo-ui](../sample-vertigo-ui) : la formation Vertigo-UI complète (Levels 0→6 : base de données, recherche, listes, écran de détail, modales)
