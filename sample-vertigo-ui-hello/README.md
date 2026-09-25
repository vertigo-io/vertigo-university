# sample-vertigo-ui-hello (exo)

Premier écran Vertigo-UI : le boot web minimal (Jetty), une page rendue dans un layout. Sans base de données, sans Elasticsearch, sans BerkeleyDB et sans donnée préchargée.

Ce module est un **exercice** : le squelette (boot Jetty, SpringMVC, layout, squelettes yaml/web.xml) est fourni — vous construisez les briques du node et la première page.

## Exercice

- [Exercice.md](Exercice.md) : l'énoncé complet (éléments, cours à connaitre, étapes avec vérifications, optionnel)
- Correction : [sample-vertigo-ui-hello-corrige](../sample-vertigo-ui-hello-corrige)

## Démarrage du squelette

- Lancer la classe main `io.vertigo.samples.uihello.BootSampleUiHello` (Jetty :18081, context `/uihello`)
- Le squelette (sans les briques de l'exercice) échoue au démarrage : c'est normal, c'est la brique de l'étape 1
- Arrêter avec Ctrl+C (le node web ne lit pas le stdin)

## Suite

- [sample-vertigo-ui](../sample-vertigo-ui) : la formation Vertigo-UI complète (Levels 0→6 : base de données, recherche, listes, écran de détail, modales)
