# Parcours Démarrage - Créer une application Vertigo de zéro

Ce parcours est une **liste de formations à suivre dans l'ordre** : chaque formation est un sujet (un module Vertigo), déjà couvert par un sample fonctionnel de ce dépôt. L'exercice consiste à **construire votre propre mini application en suivant les étapes** — le parcours est cumulatif : chaque étape réutilise et étend le projet de la précédente — en lisant le code du sample de référence, plutôt que de copier-coller.

## Formations du parcours

| # | Formation | Formation de référence (sample / Level) |
|---|---|---|
| 1 | Le projet Maven et le node Vertigo | [sample-hello-world](../sample-hello-world), [sample-config](../sample-config) |
| 2 | Le premier webservice REST (Vega) | [sample-hello-world](../sample-hello-world), [sample-vega](../sample-vega) |
| 3 | Le modèle de données et Studio (code génération) | [sample-vertigo-ui](../sample-vertigo-ui) (`definitions/`, `StudioGenerate`) |
| 4 | Le premier service et les DAO | [sample-dao](../sample-dao), [sample-dao-full](../sample-dao-full) |
| 5 | Le premier écran web (Vertigo-UI) | [sample-vertigo-ui-hello](../sample-vertigo-ui-hello) |

## Prérequis

- JDK 17, Maven 3.9+
- Eclipse (recommandé ; plugin [Vertigo Dsl Plugin 3.5.0](https://marketplace.eclipse.org/content/vertigo-3-dsl-plugin) — Eclipse et VSCode — en option)
- La documentation officielle Vertigo en appui : [vertigo-io.github.io/vertigo-docs](https://vertigo-io.github.io/vertigo-docs/#/)
- Le parent Maven d'un projet standalone est `io.vertigo:vertigo-parent` : le parent des samples (`io.vertigo:vertigo-samples`) n'est pas publié sur Maven Central

## Ce que vous saurez faire à la fin

| Compétence | Etape | Référence |
|---|---|---|
| Créer un projet Maven et démarrer un node Vertigo | 1 | [sample-hello-world](../sample-hello-world), [sample-config](../sample-config) |
| Publier un premier webservice REST (Vega) | 2 | [sample-hello-world](../sample-hello-world), [sample-vega](../sample-vega) |
| Déclarer un modèle de données et générer le code (Studio) | 3 | [sample-vertigo-ui](../sample-vertigo-ui) (`definitions/`, `StudioGenerate`) |
| Écrire un service sur les DAO générés | 4 | [sample-dao](../sample-dao) |
| Afficher un premier écran web (Vertigo-UI) | 5 | [sample-vertigo-ui-hello](../sample-vertigo-ui-hello) |

**Livrable** : une mini application construite étape par étape (un seul projet qui grandit) qui démarre, a un modèle de données, un service et un premier écran web.

**Important:** les données manipulées dans le parcours sont **créées par votre projet** (base H2 en mémoire, inserts de démonstration) : aucune base préchargée, aucune donnée externe.

## Etapes

### Etape 1 - Le projet Maven et le node Vertigo

*Objectif : un projet vide dont le node démarre.*

Le **node** est l'unité de base d'une application Vertigo : c'est le processus qui porte vos composants. Tout y est piloté par la configuration (`NodeConfig`/`ModuleConfig`, Features activées) avant même le code métier.

À faire :

1. Créer un projet Maven (un module, parent `io.vertigo:vertigo-parent`) avec la même structure que `sample-hello-world`
2. Lire `hello.HelloWorld` : la classe main (démarrage du node, qui attend ensuite sur la console)
3. Lire `hello.config.HelloConfigurator` : la configuration du node (boot locales, Javalin, DataModel, Vega)
4. Lancer le node et vérifier qu'il démarre sans erreur
5. *(Optionnel)* Lire [sample-config](../sample-config) (`ConfigRun`) : la construction programmatique d'un `NodeConfig`/`ModuleConfig` avec les Features (Javalin, DataModel, Vega)

**Vérification** : le node démarre et s'arrête proprement.

**Important:** dans Vertigo, on configure d'abord, on code ensuite : le modèle de données, les composants activés et la sécurité sont déclarés avant d'écrire la moindre logique métier.

**Bon à savoir** :

- le port du serveur est un paramètre de `JavalinFeatures` (`Param.of("port", …)`) : à changer pour faire démarrer plusieurs samples en parallèle
- le node Javalin s'arrête proprement à la fermeture du stdin — pratique pour les scripts
- lancement en ligne de commande : `mvn compile` puis `java` avec le classpath produit par `dependency:build-classpath` ; si le classpath dépasse la longueur de ligne (Windows), le passer par un fichier d'arguments Java (`@fichier`)

### Etape 2 - Le premier webservice REST (Vega)

*Objectif : répondre à une requête HTTP.*

À faire :

1. Lire `hello.webservices.HelloWebServices` dans `sample-hello-world`
2. Tester `http://localhost:8080/hello/` dans un navigateur
3. Créer votre propre webservice dans votre projet et le tester
4. *(Optionnel)* Étudier [sample-vega](../sample-vega) (`SampleVega`, Jetty :8080) pour des webservice REST plus complets

**Vérification** : votre réponse s'affiche dans le navigateur.

### Etape 3 - Le modèle de données et Studio (code génération)

*Objectif : comprendre comment Vertigo génère le domaine, les DAO et le SQL depuis le modèle.*

Studio est l'environnement de modélisation propre à Vertigo : vous déclarez votre modèle dans des fichiers de définitions, et la génération produit le code Java (domaine, DAO, requêtes SQL nommées, définitions) dans `src/main/javagen`.

À faire :

1. Dans [sample-vertigo-ui](../sample-vertigo-ui), étudier :
   - `studio-config.yaml` : la configuration de la génération
   - `src/main/resources/definitions/` : les fichiers de définitions — **les objets sont dans les `.ksp`** (domaines, modèle, DAO), le `.kpr` n'agrège que la liste des `.ksp`
   - `src/main/javagen/` : le code généré — classes du domaine (`Movie`, `Actor`…), DAO (`MovieDAO`), requêtes SQL nommées (les classes `*PAO`, produites pour les tâches orphelines), définitions (`DtDefinitions`), SQL (`sqlgen/`)
2. Lancer le runner `io.vertigo.samples.support.mda.StudioGenerate` (classe main, **à lancer depuis la racine du projet** : il lit `studio-config.yaml` en chemin relatif) et observer la régénération
3. Dans votre projet : déclarer un `studio-config.yaml`, un `.kpr` et un premier objet (une table, quelques champs) dans un `.ksp`, puis générer
4. Lire le code généré produit : la classe du domaine, son DAO, le SQL de création de base

**Vérification** : le code généré est présent dans `src/main/javagen` et cohérent avec votre modèle ; le SQL de création de base est rejouable.

**Important:** le code généré ne se modifie jamais à la main : si le code est faux, c'est le modèle qui est faux. Modifier le modèle puis régénérer est le geste central du développement Vertigo.

### Etape 4 - Le premier service et les DAO

*Objectif : écrire de la logique métier sur les DAO générés.*

À faire :

1. Lire [sample-dao](../sample-dao) (README, puis `DaoSample`) : des services écrits sur les DAO générés — la base H2 est en mémoire et **le sample crée lui-même sa donnée de démonstration** au démarrage (initializer de schéma + insert via le service)
2. Dans votre projet :
   - enregistrer votre initializer de création de base — un `ComponentInitializer` s'enregistre avec `addInitializer`, **pas** `addComponent`
   - écrire un service qui liste, cherche et enregistre votre objet (critères, tri, pagination)
3. Observer les **tasks auto-générées** au runtime (`TkInsert…`, `TkSelect…ByUri`, `TkSelectList…ByCriteria`…) : le DataStore produit tout seul les requêtes CRUD — c'est ce que vous n'avez PAS à écrire
4. *(Optionnel)* Étudier [sample-dao-full](../sample-dao-full) (Level2→Level7, `Reprise`) : DAO personnalisés, projections, reprise par lots

**Vérification** : votre service fonctionne (appel depuis la console ou un webservice), sur des données créées par votre projet.

### Etape 5 - Le premier écran web (Vertigo-UI)

*Objectif : afficher une page dans le navigateur.*

Vertigo-UI est construit sur Vue.js, Quasar et SpringMVC avec Thymeleaf.

À faire :

1. Réaliser l'exercice [sample-vertigo-ui-hello](../sample-vertigo-ui-hello/Exercice.md) — le boot web **minimal** (sans base de données, sans Elasticsearch, sans donnée préchargée). Le squelette est fourni (boot `BootSampleUiHello` via `JettyBoot`, initialisation SpringMVC, le layout `sampleLayout`) : vous construisez les briques du node et la première page,
   - `web.xml` : le listener et le paramètre `boot.applicationConfiguration` — à ajouter
   - `sample-ui-hello.yaml` : les briques du node VUI — `CommonsFeatures`, `DataModelFeatures`, `VegaFeatures`, `DataStoreFeatures` (kvStore des vues, **sans cache**), `DataFactoryFeatures` (**sans lucene**), `AccountFeatures` (security + authorization + userSession) — à ajouter
   - `SampleUiHelloUserSession`, `HomeController`, `home.html` (la page dans le layout fourni) — à créer
2. Comparer avec la solution [sample-vertigo-ui-hello-full](../sample-vertigo-ui-hello-full) : la démarrer et vérifier [http://localhost:18081/uihello/home/](http://localhost:18081/uihello/home/) (200, « Hello Vertigo-UI !! »)
3. Dans votre projet : intégrer le boot web (webapp, `web.xml`, configuration SpringMVC) et afficher votre première page

**Vérification** : votre écran s'affiche dans le navigateur.

**Bon à savoir** : le node web ne lit pas le stdin (contrairement au node Javalin) : l'arrêt est Ctrl+C. La racine du context (`/uihello/`) renvoie 404 sous Jetty 11 (welcome file) : c'est cosmétique, viser `/uihello/home/`.

## Suite

- [sample-vertigo-ui](../sample-vertigo-ui) Levels 0→6 — la formation Vertigo-UI complète : base de données, listes, écran de détail, recherche, tableaux éditables, controllers, modales (solutions dans [sample-vertigo-ui-full](../sample-vertigo-ui-full))
- [Parcours Débutant](parcours-debutant.md) — rejoindre une équipe expérimentée et viser l'autonomie sur le courant (le travail standard de l'équipe)
- [Parcours Autonomie](parcours-autonomie.md) — les approfondissements, à consulter au fil de l'eau
