# Parcours Démarrage - Créer une application Vertigo de zéro

Ce parcours est une **liste de formations à suivre dans l'ordre** : chaque formation est un sujet (un module Vertigo), déjà couvert par un sample fonctionnel de ce dépôt. L'exercice consiste à **construire votre propre mini application en suivant les étapes** — le parcours est cumulatif : chaque étape réutilise et étend le projet de la précédente — en lisant le code du sample de référence, plutôt que de copier-coller.

## Formations du parcours

| # | Formation | Formation de référence (sample / Level) |
|---|---|---|
| 1 | Le projet Maven et le node Vertigo | [sample-hello-world](../sample-hello-world), [sample-config](../sample-config) |
| 2 | Le premier webservice REST (Vega) | [sample-hello-world](../sample-hello-world), [sample-vega](../sample-vega) |
| 3 | Le modèle de données et Studio (code génération) | [sample-dao](../sample-dao) (`.ksp`, runner `Studio`) |
| 4 | Le premier service et les DAO | [sample-dao](../sample-dao), [sample-dao-full](../sample-dao-full) |
| 5 | Le premier écran web (Vertigo-UI) | [sample-vertigo-ui-hello](../sample-vertigo-ui-hello) |

## Prérequis

- JDK 17, Maven 3.9+
- Eclipse (recommandé ; plugin [Vertigo Dsl Plugin 3.5.0](https://marketplace.eclipse.org/content/vertigo-3-dsl-plugin) — Eclipse et VSCode — en option)
- La documentation officielle Vertigo en appui : [vertigo-io.github.io/vertigo-docs](https://vertigo-io.github.io/vertigo-docs/#/)
- **À lire avant de commencer** (environ une heure ; vous y reviendrez au fil des étapes) — les principes et le vocabulaire de Vertigo :
  - [Pourquoi Vertigo ?](https://vertigo-io.github.io/vertigo-docs/#/intro/why) et [Philosophie](https://vertigo-io.github.io/vertigo-docs/#/intro/philosophie) : les objectifs et les principes de conception
  - [Concepts](https://vertigo-io.github.io/vertigo-docs/#/basic/concepts) : le glossaire (Module, Component, Manager et Plugin, SmartType, DtObject, Task, `@Transactional`, KSP et KPR)
  - [Règles de codage](https://vertigo-io.github.io/vertigo-docs/#/intro/regles-codage)
- Le dépôt university est installé dans le repo local Maven (`mvn install` à la racine) : le parent des samples (`io.vertigo:vertigo-samples`) n'est pas publié sur Maven Central (le parent d'un projet standalone est `io.vertigo:vertigo-parent`)

## Ce que vous saurez faire à la fin

| Compétence | Etape | Référence |
|---|---|---|
| Créer un projet Maven et démarrer un node Vertigo | 1 | [sample-hello-world](../sample-hello-world), [sample-config](../sample-config) |
| Publier un premier webservice REST (Vega) | 2 | [sample-hello-world](../sample-hello-world), [sample-vega](../sample-vega) |
| Déclarer un modèle de données et générer le code (Studio) | 3 | [sample-dao](../sample-dao) (`.ksp`, runner `Studio`) |
| Écrire un service sur les DAO générés et l'exposer en REST | 4 | [sample-dao](../sample-dao) |
| Afficher un premier écran web (Vertigo-UI) | 5 | [sample-vertigo-ui-hello](../sample-vertigo-ui-hello) |

**Livrable** : une mini application construite étape par étape (un seul projet qui grandit) qui démarre, a un modèle de données, un service exposé en REST et un premier écran web.

**Le fil conducteur** : à l'étape 2, vous publiez un webservice sur du code **écrit à la main et provisoire** (un objet, sa définition et un faux DAO en mémoire), pour obtenir tout de suite un premier rendu. Les étapes 3 et 4 le remplacent par **la méthode normale** : l'objet et le DAO sont générés par Studio depuis le modèle, et le webservice passe par un service. La même URL renvoie alors les données de votre base.

**Important:** les données manipulées dans le parcours sont **créées par votre projet** (base H2 en mémoire, inserts de démonstration) : aucune base préchargée, aucune donnée externe.

## Etapes

### Etape 1 - Le projet Maven et le node Vertigo

*Objectif : un projet vide dont le node démarre.*

Le **node** est l'unité de base d'une application Vertigo : c'est le processus qui porte vos composants. Tout y est piloté par la configuration (`NodeConfig`/`ModuleConfig`, Features activées) avant même le code métier.

À faire :

1. Créer un projet Maven (un module, parent `io.vertigo:vertigo-parent` — la version du parent est celle des samples, cf. leurs poms) avec la même structure que `sample-hello-world`
2. Lire `hello.HelloWorld` : la classe main (démarrage du node, qui attend ensuite sur la console)
3. Lire `hello.config.HelloConfigurator` : la configuration du node (boot locales, Javalin, DataModel, Vega)
4. Lancer le node et vérifier qu'il démarre sans erreur
5. *(Optionnel)* Lire [sample-config](../sample-config) (`ConfigRun`) : la construction programmatique d'un `NodeConfig`/`ModuleConfig` avec les Features (Javalin, DataModel, Vega)

**Vérification** : le node démarre et s'arrête proprement.

**Important:** dans Vertigo, on configure d'abord, on code ensuite : le modèle de données, les composants activés et la sécurité sont déclarés avant d'écrire la moindre logique métier.

**Pour comprendre** : [vertigo-docs : Configuration](https://vertigo-io.github.io/vertigo-docs/#/basic/configuration) et [Composants](https://vertigo-io.github.io/vertigo-docs/#/basic/composants).

**Bon à savoir** :

- le port du serveur est un paramètre de `JavalinFeatures` (`Param.of("port", …)`) : à changer pour faire démarrer plusieurs samples en parallèle
- le node Javalin s'arrête proprement à la fermeture du stdin — pratique pour les scripts
- lancement en ligne de commande : `mvn compile` puis `java` avec le classpath produit par `dependency:build-classpath` ; si le classpath dépasse la longueur de ligne (Windows), le passer par un fichier d'arguments Java (`@fichier`) — **dans l'argfile, remplacez `\` par `/`** : le backslash est un caractère d'échappement dans les `@argfile` Java, un classpath Windows non converti casse le lancement silencieusement (`Could not find or load main class` alors que la classe existe)

### Etape 2 - Le premier webservice REST (Vega)

*Objectif : répondre à une requête HTTP par une liste de films en JSON.*

Un webservice Vega est un composant (`implements WebServices`) dont les méthodes annotées (`@GET`, `@POST`…) deviennent des routes REST ; Vega convertit en JSON les objets retournés.

À faire :

1. Lire `hello.webservices.HelloWebServices` dans `sample-hello-world`, démarrer le sample (cf. étape 1) et tester `http://localhost:8080/hello/` dans un navigateur
2. Lire [sample-vega](../sample-vega) (`SampleVega`, Javalin :8080) : `MovieWebServices` publie `/movies` sur l'objet `Movie` et le `MovieDAO` du package `vega.domain`
3. Dans votre projet, publier `GET /movies/` et `GET /movies/{id}` de la même façon, en reprenant de `sample-vega`, **dans un package à part** (par exemple `….mock`) :
   - `Movie` : l'objet, **avec les champs `movId` et `name`** — les noms qu'il aura dans le modèle de l'étape 3 (l'identifiant est préfixé par le trigramme de l'objet)
   - `VegaDefinitionProvider` : la définition de `Movie` (`DtMovie`) et de ses SmartTypes, avec les mêmes noms de champs ; il s'enregistre par `addDefinitionProvider`
   - `MovieDAO` : quelques films gardés en mémoire
   - `MovieWebServices`, qui s'enregistre par `addComponent`

**Vérification** : `http://localhost:8080/movies/` affiche vos films en JSON (`movId`, `name`).

**Important:** ce `Movie`, sa définition et ce `MovieDAO` écrits à la main sont **provisoires** : ils donnent un premier rendu. Dans une application Vertigo, on ne les écrit pas : l'objet et sa définition sont générés par Studio depuis le modèle (étape 3), et le DAO généré accède à la base (étape 4). Le package à part évite qu'ils entrent en conflit avec le code généré à l'étape 3, qui utilise le package `….domain`.

**Pour comprendre** : [vertigo-docs : WebServices](https://vertigo-io.github.io/vertigo-docs/#/basic/webservices).

### Etape 3 - Le modèle de données et Studio (code génération)

*Objectif : générer l'objet `Movie`, son DAO et le SQL de la base depuis le modèle.*

Studio est l'environnement de modélisation propre à Vertigo : vous déclarez votre modèle dans des fichiers de définitions, et la génération produit le code Java (domaine, DAO, requêtes SQL nommées, définitions) dans `src/main/javagen`.

À faire :

1. Dans [sample-dao](../sample-dao), étudier :
   - `studio-config.yaml` : la configuration de la génération (package du projet, répertoire cible, SGBD cible `H2`)
   - `src/main/resources/application.kpr` : la liste des fichiers de définitions — **les objets sont dans les `.ksp`** : `domains.ksp` (les domaines, c'est-à-dire les types des champs), `model.ksp` (les objets et leurs associations), `dao.ksp`
   - `src/main/javagen/` : le code généré — classes du domaine (`Movie`, `Actor`…), DAO (`MovieDAO`…), définitions (`DtDefinitions`), SQL de création de base (`sqlgen/crebas.sql`)
2. Lancer le runner `io.vertigo.samples.dao.run.Studio` (classe main, **à lancer depuis la racine du projet** : il lit `studio-config.yaml` en chemin relatif) : `mvn compile` du module, puis `java` avec le classpath du module (cf. étape 1), et observer la régénération — **sur une copie du sample** : lancer le runner dans le dépôt réécrit les fichiers générés suivis par git
3. Dans votre projet, **mettre en place la génération** (pom de `sample-dao` en référence) :
    - copier le runner `Studio` dans votre projet (une classe main de quelques lignes qui appelle `VertigoStudioMda`)
    - ajouter la dépendance `io.vertigo:vertigo-studio`
    - ajouter la section de build Maven qui compile `src/main/javagen` en source (build-helper) et les resources — sans elle le code généré ne compile pas
4. Dans votre projet : déclarer un `studio-config.yaml`, un `.kpr`, vos domaines et l'objet `DtMovie` dans des `.ksp` — **avec l'identifiant `movId` et le champ `name`**, comme le `Movie` écrit à la main à l'étape 2 — puis générer
5. Lire le code généré et **le comparer à ce que vous avez écrit à la main à l'étape 2** : le `Movie` généré porte ses champs et leurs annotations, `DtDefinitions` remplace la déclaration écrite dans `VegaDefinitionProvider`, et le `MovieDAO` généré accède à la base, dont `sqlgen/crebas.sql` crée la table

**Vérification** : le code généré est présent dans `src/main/javagen`, compile et est cohérent avec votre modèle ; le node démarre toujours et `/movies/` répond encore avec le code provisoire (le code généré n'est pas encore branché). Le SQL de création de base est rejouable **une fois** `NON_KEYWORDS=YEAR,KEY,USER` ajouté à l'URL JDBC le cas échéant — **attention** : en H2 2.x, une colonne `YEAR` est un mot réservé (cf. `SampleConfigBuilder` de `sample-dao`).

**Important:** le code généré ne se modifie jamais à la main : si le code est faux, c'est le modèle qui est faux. Modifier le modèle puis régénérer est le geste central du développement Vertigo.

**Pour comprendre** : [vertigo-docs : MDA](https://vertigo-io.github.io/vertigo-docs/#/basic/mda).

### Etape 4 - Le premier service et les DAO

*Objectif : remplacer le code provisoire de l'étape 2 par la méthode normale : un service sur le DAO généré, avec des données en base.*

À faire :

1. Lire [sample-dao](../sample-dao) (README, puis `DaoSample`) : des services écrits sur les DAO générés — la base H2 est en mémoire et **le sample crée lui-même sa donnée de démonstration** au démarrage (initializer de schéma + insert via le service)
2. Dans votre projet :
   - enregistrer les features du node — `CommonsFeatures` (script **janino**), `DataModelFeatures`, `DatabaseFeatures` (driver H2 + C3p0), `DataStoreFeatures` (entityStore sql) et le module « ressources » avec le `ModelDefinitionProvider` (`DtDefinitions` générées + SmartTypes) : `SampleConfigBuilder` de `sample-dao` en référence — sans elles, le boot échoue avec des erreurs DI (`component info with id '…' not found`)
   - enregistrer votre initializer de création de base — un `ComponentInitializer` s'enregistre avec `addInitializer`, **pas** `addComponent`
   - écrire un service qui liste, cherche et enregistre votre objet — `MovieServicesImpl` de `sample-dao` en référence : `save`/`get` sur le DAO généré, et **liste/recherche/pagination** avec `Criterions.startsWith(DtDefinitions.MovieFields.name, …)` / `Criterions.alwaysTrue()`, un `DtListState` (`DtListState.defaultOf(Movie.class)`, `withDefault`) et les méthodes `findAll(critères, listState)` (tri + pagination) / `count(critères)` — héritées de la classe de base `DAO` (le `MovieDAO` généré est une sous-classe vide, elles n'y sont pas écrites)
3. **Remplacer le code provisoire de l'étape 2** :
   - supprimer le package `….mock` (`Movie`, `MovieDAO`, `VegaDefinitionProvider`) et l'enregistrement du provider : la définition `DtMovie` vient maintenant des `DtDefinitions` générées — gardé, le provider la déclarerait une deuxième fois
   - `MovieWebServices` reçoit votre service par injection (`@Inject`) et l'appelle
4. *(Optionnel)* Étudier [sample-dao-full](../sample-dao-full) (Level2→Level7, `Reprise`) : DAO personnalisés, projections, reprise par lots

**Vérification** : la même URL `http://localhost:8080/movies/` renvoie maintenant les films **créés par votre projet en base** ; la forme du JSON (`movId`, `name`) n'a pas changé.

**Important:** un webservice ne crée pas ses dépendances avec `new` et n'accède jamais directement à un DAO : il passe par un service, qui porte la transaction (`@Transactional`). C'est la méthode normale : modèle → génération → service → webservice.

**Pour comprendre** : [vertigo-docs : Accès aux données](https://vertigo-io.github.io/vertigo-docs/#/basic/dao).

### Etape 5 - Le premier écran web (Vertigo-UI)

*Objectif : afficher une page dans le navigateur.*

Vertigo-UI est construit sur Vue.js, Quasar et SpringMVC avec Thymeleaf.

À faire :

1. Réaliser l'exercice [sample-vertigo-ui-hello](../sample-vertigo-ui-hello/Exercice.md) — le boot web **minimal** (sans base de données, sans Elasticsearch, sans donnée préchargée). Le squelette est fourni (boot `BootSampleUiHello` via `JettyBoot`, initialisation SpringMVC, le layout `sampleLayout`) : vous construisez les briques du node et la première page,
   - `web.xml` : le listener et le paramètre `boot.applicationConfiguration` — à ajouter
   - `sample-ui-hello.yaml` : les briques du node VUI — `CommonsFeatures`, `DataModelFeatures`, `VegaFeatures`, `DataStoreFeatures` (kvStore des vues, **sans cache**), `DataFactoryFeatures` (**sans lucene**), `AccountFeatures` (security + authorization + userSession) — à ajouter
   - `SampleUiHelloUserSession`, `HomeController`, `home.html` (la page dans le layout fourni) — à créer
2. Comparer avec la solution [sample-vertigo-ui-hello-full](../sample-vertigo-ui-hello-full) : la démarrer et vérifier [http://localhost:18081/uihello/home/](http://localhost:18081/uihello/home/) (200, « Hello Vertigo-UI !! »)
3. Dans votre projet : intégrer le boot web et afficher votre première page. Fichiers à copier/adapter **depuis l'exercice** :
   - le boot : votre classe main (`JettyBoot`/`JettyBootParams`, cf. `BootSampleUiHello`), `MultipartConfigInjectionHandler`, `*VSpringWebConfig` (**`@ComponentScan` sur votre package `.controllers`**), `*VSpringWebApplicationInitializer`
    - la webapp : `web.xml` (complété comme à l'étape 1 de l'exercice), `META-INF/*.yaml` (les features de l'exercice), `sampleLayout.html` et `static/` (+ `index.html` si vous gardez le `welcome-file-list`)
   - votre `UserSession`, votre controller, votre page. **Convention de nom de vue** : la vue est trouvée depuis le package du controller — `.../uihello/controllers/HomeController` → `WEB-INF/views/uihello/home.html` (le segment de package juste avant `.controllers`)

**Vérification** : votre écran s'affiche dans le navigateur.

**Pour comprendre** : [vertigo-docs : UI](https://vertigo-io.github.io/vertigo-docs/#/basic/ui).

**Bon à savoir** : le node web ne lit pas le stdin (contrairement au node Javalin) : l'arrêt est Ctrl+C. La racine du context (`/uihello/`) renvoie 404 sous Jetty 11 (welcome file) : c'est cosmétique, viser `/uihello/home/`.

## Suite

- [sample-vertigo-ui](../sample-vertigo-ui) Levels 0→6 — la formation Vertigo-UI complète : base de données, listes, écran de détail, recherche, tableaux éditables, controllers, modales (solutions dans [sample-vertigo-ui-full](../sample-vertigo-ui-full))
- [Parcours Débutant](parcours-debutant.md) — rejoindre une équipe expérimentée et viser l'autonomie sur le courant (le travail standard de l'équipe)
- [Parcours Autonomie](parcours-autonomie.md) — les approfondissements, à consulter au fil de l'eau
