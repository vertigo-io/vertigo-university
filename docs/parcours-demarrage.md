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
- Les samples se compilent sur place (`mvn compile` dans leur dossier) : leur parent `io.vertigo:vertigo-samples`, non publié sur Maven Central, est résolu par le `pom.xml` à la racine du dépôt. Pour compiler une copie de sample placée **hors** du dépôt, installez d'abord ce parent dans votre repo local Maven (`mvn -N install` à la racine). Votre propre projet a pour parent `io.vertigo:vertigo-parent`, publié sur Maven Central : rien à installer

## Ce que vous saurez faire à la fin

| Compétence | Etape | Référence |
|---|---|---|
| Créer un projet Maven et démarrer un node Vertigo | 1 | [sample-hello-world](../sample-hello-world), [sample-config](../sample-config) |
| Publier un premier webservice REST (Vega) | 2 | [sample-hello-world](../sample-hello-world), [sample-vega](../sample-vega) |
| Déclarer un modèle de données et générer le code (Studio) | 3 | [sample-dao](../sample-dao) (`.ksp`, runner `Studio`) |
| Écrire un service sur les DAO générés et l'exposer en REST | 4 | [sample-dao](../sample-dao) |
| Afficher un premier écran web (Vertigo-UI) | 5 | [sample-vertigo-ui-hello](../sample-vertigo-ui-hello) |

**Livrable** : une mini application construite étape par étape (un seul projet qui grandit) qui démarre, a un modèle de données, un service exposé en REST et un premier écran web qui affiche vos films. C'est un prototype : sa configuration est écrite en Java, sauf celle de l'écran web, en YAML (voir l'étape 1).

**Le fil conducteur** : à l'étape 2, vous publiez un webservice sur du code **écrit à la main et provisoire** (un objet, sa définition et un faux DAO en mémoire), pour obtenir tout de suite un premier rendu. Les étapes 3 et 4 le remplacent par **la méthode normale** : l'objet et le DAO sont générés par Studio depuis le modèle, et le webservice passe par un service. La même URL renvoie alors les données de votre base.

**Important:** les données manipulées dans le parcours sont **créées par votre projet** (base H2 en mémoire, inserts de démonstration) : aucune base préchargée, aucune donnée externe.

## Etapes

### Etape 1 - Le projet Maven et le node Vertigo

*Objectif : un projet vide dont le node démarre.*

Le **node** est l'unité de base d'une application Vertigo : c'est le processus qui porte vos composants. Tout y est piloté par la configuration (`NodeConfig`/`ModuleConfig`, Features activées) avant même le code métier.

À faire :

1. Créer un projet Maven (un module, parent `io.vertigo:vertigo-parent` en version `4.4.1`, celle du `pom.xml` à la racine du dépôt) avec la même structure que `sample-hello-world`, et le package **`com.example.cinema`** (imposé : voir le point 6)
2. Lire `hello.HelloWorld` : la classe main (démarrage du node, qui attend ensuite sur la console)
3. Lire `hello.config.HelloConfigurator` : la configuration du node (boot locales, Javalin, DataModel, Vega)
4. Dans votre projet, écrire la configuration du node dans une classe **`com.example.cinema.config.CinemaNodeConfig`**, dont la méthode `config()` renvoie le `NodeConfig` (le contenu de `HelloConfigurator` ; le nom suit la convention du framework, `<…>NodeConfig`), et une classe main qui démarre le node avec, comme `HelloWorld`. Lancer le node et vérifier qu'il démarre sans erreur
5. *(Optionnel)* Lire [sample-config](../sample-config) (`ConfigRun`) : la construction programmatique d'un `NodeConfig`/`ModuleConfig` avec les Features (Javalin, DataModel, Vega)
6. Copier dans votre projet le fichier fourni [`cinema.yaml`](demarrage/cinema.yaml), en `src/main/resources/webapp/META-INF/cinema.yaml`. C'est la configuration complète du node web de l'étape 5 : **il n'est pas lu avant l'étape 5**. Il cite quelques classes que vous créerez au fil des étapes : gardez leurs noms (`CinemaFeatures` à l'étape 2, `boot.DataBaseInitializer` et `boot.DemoDataInitializer` à l'étape 4, `support.CinemaUserSession` à l'étape 5)

**Vérification** : le node démarre et s'arrête proprement.

**Important:** dans Vertigo, on configure d'abord, on code ensuite : le modèle de données, les composants activés et la sécurité sont déclarés avant d'écrire la moindre logique métier.

**Important:** aux étapes 1 à 4, la configuration du node est écrite **en Java** (`NodeConfig`, `ModuleConfig`, Features) et votre classe main démarre elle-même le serveur REST : c'est simple et adapté aux prototypes et aux tests. Ce n'est pas la méthode préconisée pour un vrai projet, où la configuration est déclarée dans un fichier **YAML** : c'est le fichier `cinema.yaml` fourni, que le boot web chargera à l'étape 5. Vous pouvez le relire à chaque étape pour voir l'équivalent YAML de ce que vous configurez en Java ; le [Parcours Débutant](parcours-debutant.md) généralise la configuration YAML.

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
3. Dans votre projet, publier `GET /movies/` et `GET /movies/{id}` de la même façon, en reprenant de `sample-vega`. Le code provisoire va **dans un package à part** (par exemple `….mock`) ; seul le webservice, qui sera conservé, va dans `….webservices` :
   - `Movie` : l'objet, **avec les champs `movId` (un `Long`) et `name`** — les noms et le type qu'il aura dans le modèle de l'étape 3 (l'identifiant est préfixé par le trigramme de l'objet). Dans `sample-vega`, l'identifiant est un `int` : ne le reprenez pas tel quel
   - `VegaDefinitionProvider` : la définition de `Movie` (`DtMovie`) et de ses SmartTypes, avec les mêmes noms de champs ; nommez les SmartTypes comme ceux que Studio générera (`STyId`, `STyLabel`) ; le provider s'enregistre par `addDefinitionProvider`
   - `MovieDAO` : quelques films gardés en mémoire
   - `MovieWebServices`, dans `….webservices`
4. Enregistrer ces composants dans une classe **`com.example.cinema.CinemaFeatures`** : c'est le module de votre application, qui regroupe ses composants. Contrairement aux samples, qui les déclarent directement dans la configuration (`ModuleConfig.builder(…).addComponent(…)`), un vrai projet les range dans une telle classe : le fichier YAML n'a alors qu'à la citer (voir `cinema.yaml`). Sa forme (cf. `SupportFeatures` de [sample-vertigo-ui](../sample-vertigo-ui)) :
   - `public class CinemaFeatures extends Features<CinemaFeatures>`, avec un constructeur qui appelle `super("cinema")`
   - la méthode `buildFeatures()`, qui enregistre les composants : `getModuleConfigBuilder().addDefinitionProvider(VegaDefinitionProvider.class).addComponent(MovieWebServices.class)`
   - dans votre configuration Java : `.addModule(new CinemaFeatures().build())`

   La configuration de l'étape 1 suffit : inutile de reprendre l'account, le KVStore ou les tokens de `sample-vega`.

**Vérification** : `http://localhost:8080/movies/` affiche vos films en JSON (`movId`, `name`).

**Important:** ce `Movie`, sa définition et ce `MovieDAO` écrits à la main sont **provisoires** : ils donnent un premier rendu. Dans une application Vertigo, on ne les écrit pas : l'objet et sa définition sont générés par Studio depuis le modèle (étape 3), et le DAO généré accède à la base (étape 4). Le package à part évite qu'ils entrent en conflit avec le code généré à l'étape 3, qui utilise le package `….domain`.

**Pour comprendre** : [vertigo-docs : WebServices](https://vertigo-io.github.io/vertigo-docs/#/basic/webservices).

### Etape 3 - Le modèle de données et Studio (code génération)

*Objectif : générer l'objet `Movie`, son DAO et le SQL de la base depuis le modèle.*

Studio est l'environnement de modélisation propre à Vertigo : vous déclarez votre modèle dans des fichiers de définitions, et la génération produit le code Java (domaine, DAO, requêtes SQL nommées, définitions) dans `src/main/javagen`.

À faire :

1. Dans [sample-dao](../sample-dao), étudier :
   - `studio-config.yaml` : la configuration de la génération (package du projet, répertoire cible, SGBD cible `H2`)
   - `src/main/resources/application.kpr` : la liste des fichiers de définitions — **les objets sont dans les `.ksp`** : `domains.ksp` (les domaines, c'est-à-dire les types des champs), `model.ksp` (les objets et leurs associations), `dao.ksp` (les requêtes SQL nommées ; vide dans ce sample)
   - `src/main/javagen/` : le code généré — classes du domaine (`Movie`, `Actor`…), DAO (`MovieDAO`…), définitions (`DtDefinitions`), SQL de création de base (`sqlgen/crebas.sql`)
2. Lancer le runner `io.vertigo.samples.dao.run.Studio` (classe main, **à lancer depuis la racine du projet** : il lit `studio-config.yaml` en chemin relatif) : `mvn compile` du module, puis `java` avec le classpath du module (cf. étape 1), et observer la régénération — **sur une copie du sample**, placée à côté dans le dépôt (par exemple `sample-dao-copie/`, pour que son parent se résolve par `../pom.xml`) : lancer le runner sur le sample lui-même réécrit les fichiers générés suivis par git
3. Dans votre projet, **mettre en place la génération** (pom de `sample-dao` en référence) :
   - copier le runner `Studio` dans votre projet (une classe main de quelques lignes qui appelle `VertigoStudioMda`)
   - ajouter la dépendance `io.vertigo:vertigo-studio`
   - ajouter la section de build Maven qui compile `src/main/javagen` en source (build-helper) et les resources — sans elle le code généré ne compile pas
4. Dans votre projet : déclarer un `studio-config.yaml`, un `.kpr`, vos domaines et l'objet `DtMovie` dans des `.ksp` — **avec l'identifiant `movId` (domaine `DoId`) et le champ `name` (domaine `DoLabel`)**, comme le `Movie` écrit à la main à l'étape 2 : ces domaines donneront les SmartTypes `STyId` et `STyLabel`. Dans `sample-dao`, `name` utilise `DoLabelLong` : ne le recopiez pas tel quel. Puis générer
5. Lire le code généré et **le comparer à ce que vous avez écrit à la main à l'étape 2** : le `Movie` généré porte ses champs et leurs annotations, `DtDefinitions` remplace la déclaration écrite dans `VegaDefinitionProvider`, et le `MovieDAO` généré accède à la base, dont `sqlgen/crebas.sql` crée la table

**Vérification** : le code généré est présent dans `src/main/javagen`, compile et est cohérent avec votre modèle ; le node démarre toujours et `/movies/` répond encore avec le code provisoire (le code généré n'est pas encore branché).

**Important:** le code généré ne se modifie jamais à la main : si le code est faux, c'est le modèle qui est faux. Modifier le modèle puis régénérer est le geste central du développement Vertigo.

**Pour comprendre** : [vertigo-docs : MDA](https://vertigo-io.github.io/vertigo-docs/#/basic/mda).

### Etape 4 - Le premier service et les DAO

*Objectif : remplacer le code provisoire de l'étape 2 par la méthode normale : un service sur le DAO généré, avec des données en base.*

À faire :

1. Lire [sample-dao](../sample-dao) (README, puis `DaoSample` et `SampleConfigBuilder`) : des services écrits sur les DAO générés — la base H2 est en mémoire et **le sample crée lui-même sa donnée de démonstration** au démarrage (initializer de schéma, puis insert via le service)
2. Dans votre projet, ajouter au pom les dépendances `vertigo-datastore`, `h2` et `c3p0` (pom de `sample-dao` en référence), puis compléter la configuration du node (`SampleConfigBuilder` de `sample-dao` en référence ; même ordre que dans `cinema.yaml`) :
   - dans le boot : le plugin `ClassPathResourceResolverPlugin` (`BootConfig.builder()…addPlugin(ClassPathResourceResolverPlugin.class)`), qui permet de lire des ressources du classpath, comme le `sqlgen/crebas.sql` de votre initializer — sans lui : `Resource 'sqlgen/crebas.sql' not found` ; c'est le `boot.plugins` de `cinema.yaml`
   - `CommonsFeatures` (script **janino**) et `DataModelFeatures`
   - `DatabaseFeatures` : driver H2 + C3p0 ; l'URL JDBC se termine par `NON_KEYWORDS=YEAR,KEY,USER` — en H2 2.x, `YEAR`, `KEY` et `USER` sont des mots réservés, et le SQL de création de base généré échouerait sur une colonne `YEAR`
   - `DataStoreFeatures` : le cache mémoire **et** l'entityStore sql (`withCache().withMemoryCache().withEntityStore().withSqlEntityStore()`)
   Sans ces features, le boot échoue avec des erreurs d'injection, par exemple `Components or params not found :cacheManager (referenced by entityStoreManager…)` ou `component info with id '…' not found`.
3. Dans `CinemaFeatures`, enregistrer les définitions : un `ModelDefinitionProvider` avec les `DtDefinitions` générées et vos **SmartTypes** (le module « ressources » de `SampleConfigBuilder`). Les SmartTypes ne sont pas générés : c'est une enum écrite à la main (cf. `SampleDaoSmartTypes`), dont chaque constante correspond à un domaine du `.ksp` (le domaine `DoId` → la constante `Id` → le SmartType `STyId`, référencé par le `Movie` généré)
4. Créer la base et ses données, avec deux initializers enregistrés dans votre configuration Java — un `ComponentInitializer` s'enregistre avec `addInitializer`, **pas** `addComponent` :
   - `com.example.cinema.boot.DataBaseInitializer` : la création de la base (le SQL généré `crebas.sql`)
   - `com.example.cinema.boot.DemoDataInitializer`, enregistré après le premier : vos films de démonstration, créés en appelant votre service (le sample, lui, les insère depuis sa classe main)
5. Écrire un service qui liste, cherche et enregistre votre objet, et l'enregistrer dans `CinemaFeatures` avec le `MovieDAO` généré (`addComponent(MovieDAO.class)`, `addComponent(MovieServices.class, MovieServicesImpl.class)`) — `MovieServicesImpl` de `sample-dao` en référence :
   - `save` / `get` sur le DAO généré
   - la liste, la recherche et la pagination : des critères (`Criterions.startsWith(DtDefinitions.MovieFields.name, …)`, `Criterions.alwaysTrue()`), un `DtListState` (`DtListState.defaultOf(Movie.class)`, `withDefault`), puis `findAll(critères, listState)` (tri + pagination) et `count(critères)`
   - ces méthodes sont héritées de la classe de base `DAO` : le `MovieDAO` généré est une sous-classe vide
6. **Remplacer le code provisoire de l'étape 2** :
   - supprimer le package `….mock` (`Movie`, `MovieDAO`, `VegaDefinitionProvider`) et l'enregistrement du provider dans `CinemaFeatures` : les définitions viennent maintenant des `DtDefinitions` générées et de vos SmartTypes — gardé, le provider les déclarerait une deuxième fois (`this definition 'STyId' is already registered`)
   - `MovieWebServices` reçoit votre service par injection (`@Inject`) et l'appelle
7. *(Optionnel)* Étudier [sample-dao-full](../sample-dao-full) (Level2→Level7, `Reprise`) : DAO personnalisés, projections, reprise par lots

**Vérification** : la même URL `http://localhost:8080/movies/` renvoie maintenant les films **créés par votre projet en base** ; la forme du JSON (`movId`, `name`) n'a pas changé. Les identifiants commencent désormais à 1000 : ils viennent de la séquence de la base (`SEQ_MOVIE`, dans `crebas.sql`).

**Important:** un webservice ne crée pas ses dépendances avec `new` et n'accède jamais directement à un DAO : il passe par un service, qui porte la transaction (`@Transactional`). C'est la méthode normale : modèle → génération → service → webservice.

**Pour comprendre** : [vertigo-docs : Accès aux données](https://vertigo-io.github.io/vertigo-docs/#/basic/dao).

### Etape 5 - Le premier écran web (Vertigo-UI)

*Objectif : afficher vos films dans une page web.*

Vertigo-UI est construit sur Vue.js, Quasar et SpringMVC avec Thymeleaf.

À faire :

1. Réaliser l'exercice [sample-vertigo-ui-hello](../sample-vertigo-ui-hello/Exercice.md) par sa **voie rapide** (la voie brique par brique est proposée aux plus curieux) — le boot web **minimal** (sans base de données, sans Elasticsearch, sans donnée préchargée). Le squelette est fourni (boot `BootSampleUiHello` via `JettyBoot`, initialisation SpringMVC, le layout `sampleLayout`) : vous construisez les briques du node et la première page,
   - `web.xml` : le listener et le paramètre `boot.applicationConfiguration` — à ajouter
   - `sample-ui-hello.yaml` : les briques du node VUI — `CommonsFeatures`, `DataModelFeatures`, `VegaFeatures`, `DataStoreFeatures` (kvStore des vues, **sans cache**), `DataFactoryFeatures` (**sans lucene**), `AccountFeatures` (security + authorization + userSession) — à ajouter
   - `SampleUiHelloUserSession`, `HomeController`, `home.html` (la page dans le layout fourni) — à créer
2. Comparer avec la solution [sample-vertigo-ui-hello-full](../sample-vertigo-ui-hello-full) : la démarrer et vérifier [http://localhost:18081/uihello/home/](http://localhost:18081/uihello/home/) (200, « Hello Vertigo-UI !! »)
3. Dans votre projet : intégrer le boot web, qui **active le fichier `cinema.yaml` fourni à l'étape 1**, et afficher vos films. Relisez ce fichier : vous y retrouvez, en YAML, tout ce que vous avez configuré en Java (le socle, la base, le DataStore, `CinemaFeatures`, les initializers), plus les briques Vertigo-UI de l'exercice. Fichiers à copier/adapter **depuis l'exercice** :
   - le pom : les dépendances `io.vertigo:vertigo-ui` (en version `${vertigo.version}` : le `${project.version}` du pom de l'exercice ne vaut que dans le dépôt), `jetty-webapp` et `jetty-annotations` (`11.0.26`)
   - le boot : une seconde classe main, **`com.example.cinema.BootCinema`** (`JettyBoot`/`JettyBootParams`, cf. `BootSampleUiHello` ; le nom suit la convention `Boot<Application>`, comme `BootMars`), `MultipartConfigInjectionHandler`, `*VSpringWebConfig` (**`@ComponentScan` sur votre package `.controllers`**), `*VSpringWebApplicationInitializer`
   - la webapp : `web.xml`, complété comme à l'étape 1 de l'exercice mais avec `boot.applicationConfiguration` = `/webapp/META-INF/cinema.yaml` (le fichier fourni, déjà en place), `sampleLayout.html` et `static/` (+ `index.html` si vous gardez le `welcome-file-list` : adaptez son lien `/uihello/home/` à votre context path)
   - la session utilisateur : `com.example.cinema.support.CinemaUserSession` (le nom cité par `cinema.yaml`)
   - votre controller et votre page. **Convention de nom de vue** : la vue est trouvée depuis le package du controller — `.../cinema/controllers/HomeController` → `WEB-INF/views/cinema/home.html` (le segment de package juste avant `.controllers`)
4. Afficher vos films : le controller reçoit votre service par injection (`@Inject`) et publie la liste dans le contexte de la vue (`viewContext.publishDtList(moviesKey, …)`, avec `ViewContextKey<Movie> moviesKey = ViewContextKey.of("movies")`) ; la page l'affiche dans un tableau (`<vu:table list="movies" componentId="movieTable">` et une `<vu:column field="name" />` par colonne) — `MovieController` et `movie.html` de [sample-vertigo-ui-full](../sample-vertigo-ui-full) en référence

**Vérification** : [http://localhost:18081/cinema/home/](http://localhost:18081/cinema/home/) (selon votre context path) affiche le tableau de vos films de démonstration. Le tableau est construit dans le navigateur (Vue.js, Quasar) : avec `curl`, cherchez vos films dans les données de la page (le bloc `vui-init-data`).

**Important:** le node web démarre sur `cinema.yaml` : il a la base, le DataStore et vos composants (la même classe `CinemaFeatures` qu'en Java). Votre prototype garde pourtant deux points d'entrée : la classe main des étapes 1 à 4, qui sert le REST, et le boot web, qui sert l'écran — chacun avec sa propre base H2 en mémoire. Dans le node web, `MovieWebServices` est enregistré mais pas exposé : publier le REST depuis le node web (Javalin déclaré dans le YAML) est vu dans le [Parcours Autonomie](parcours-autonomie.md). Dans un vrai projet, un seul node web porte les écrans et les webservices ; le [Parcours Débutant](parcours-debutant.md) part de là.

**Pour comprendre** : [vertigo-docs : UI](https://vertigo-io.github.io/vertigo-docs/#/basic/ui).

**Bon à savoir** : le node web ne lit pas le stdin (contrairement au node Javalin) : l'arrêt est Ctrl+C. La racine du context (`/uihello/`) renvoie 404 sous Jetty 11 (welcome file) : c'est cosmétique, viser `/uihello/home/`.

## Suite

- [sample-vertigo-ui](../sample-vertigo-ui) Levels Ui 0→6 — la formation Vertigo-UI complète : base de données, listes, écran de détail, recherche, tableaux éditables, controllers, modales (solutions dans [sample-vertigo-ui-full](../sample-vertigo-ui-full))
- [Parcours Débutant](parcours-debutant.md) — rejoindre une équipe expérimentée et viser l'autonomie sur le courant (le travail standard de l'équipe)
- [Parcours Autonomie](parcours-autonomie.md) — les approfondissements, à consulter au fil de l'eau
