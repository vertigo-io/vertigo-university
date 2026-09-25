# Parcours Démarrage - Créer une application Vertigo de zéro

Ce parcours vous guide pas à pas dans la création d'une mini application Vertigo **depuis zéro** : projet Maven, node Vertigo, premier webservice REST, modèle de données généré par Studio, premier service et premier écran web.

C'est un parcours « bac à sable » : chaque étape existe déjà en sample fonctionnel dans ce dépôt. L'exercice consiste à **reproduire chaque étape dans votre propre projet** en lisant le code du sample de référence, plutôt que de copier-coller.

## Prérequis

- JDK 17, Maven 3.9+
- Eclipse (recommandé ; plugin [KSP Plugin](https://github.com/sebez/vertigo-chroma-kspplugin) en option)
- La documentation officielle Vertigo en appui : [vertigo-io.github.io/vertigo-docs](https://vertigo-io.github.io/vertigo-docs/#/)

## Ce que vous saurez faire à la fin

| Compétence | Etape | Référence |
|---|---|---|
| Créer un projet Maven et démarrer un node Vertigo | 1 | [sample-hello-world](../sample-hello-world), [sample-config](../sample-config) |
| Publier un premier webservice REST (Vega) | 2 | [sample-hello-world](../sample-hello-world), [sample-vega](../sample-vega) |
| Déclarer un modèle de données et générer le code (Studio) | 3 | [sample-vertigo-ui](../sample-vertigo-ui) (`definitions/`, `StudioGenerate`) |
| Écrire un service sur les DAO générés | 4 | [sample-dao](../sample-dao) |
| Afficher un premier écran web (Vertigo-UI) | 5 | [sample-vertigo-ui](../sample-vertigo-ui) Level 0 |

**Livrable** : une mini application créée de zéro qui démarre, a une base de données, un service et un écran.

## Etapes

### Etape 1 - Le projet Maven et le node Vertigo

*Objectif : un projet vide dont le node démarre.*

Le **node** est l'unité de base d'une application Vertigo : c'est le processus qui porte vos composants. Tout y est piloté par la configuration (`NodeConfig`/`ModuleConfig`, Features activées) avant même le code métier.

À faire :

1. Créer un projet Maven (un module) avec la même structure que `sample-hello-world`
2. Lire `hello.HelloWorld` : la classe main (démarrage du node, qui attend ensuite sur la console)
3. Lire `hello.config.HelloConfigurator` : la configuration du node (Javalin, DataModel, Vega)
4. Lancer le node et vérifier qu'il démarre sans erreur
5. *(Optionnel)* Lire [sample-config](../sample-config) (`ConfigRun`) : la construction programmatique d'un `NodeConfig`/`ModuleConfig` avec les Features (Javalin, DataModel, Vega)

**Vérification** : le node démarre et s'arrête proprement.

**Important:** dans Vertigo, on configure d'abord, on code ensuite : le modèle de données, les composants activés et la sécurité sont déclarés avant d'écrire la moindre logique métier.

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
   - `src/main/resources/definitions/` : les fichiers de définitions (`application.kpr`, `domains.ksp`, `model.ksp`, `dao.ksp`, `search/*.ksp`)
   - `src/main/javagen/` : le code généré — classes du domaine (`Movie`, `Actor`…), DAO (`MovieDAO`), requêtes SQL nommées (`VuiPAO`), définitions (`DtDefinitions`), SQL (`sqlgen/`)
2. Lancer le runner `io.vertigo.samples.support.mda.StudioGenerate` (classe main) et observer la régénération
3. Dans votre projet : déclarer un `studio-config.yaml`, un premier `.kpr` et un premier objet (une table, quelques champs), puis générer
4. Lire le code généré produit : la classe du domaine, son DAO, le SQL de création de base

**Vérification** : le code généré est présent dans `src/main/javagen` et cohérent avec votre modèle ; la base est créée au démarrage (H2 mémoire) ou initialisée par scripts SQL, selon le datastore choisi.

**Important:** le code généré ne se modifie jamais à la main : si le code est faux, c'est le modèle qui est faux. Modifier le modèle puis régénérer est le geste central du développement Vertigo.

### Etape 4 - Le premier service et les DAO

*Objectif : écrire de la logique métier sur les DAO générés.*

À faire :

1. Lire [sample-dao](../sample-dao) (README, puis `DaoSample`) : des services écrits sur les DAO générés
2. Dans votre projet : écrire un service qui liste, cherche et enregistre votre objet (critères, tri, pagination)
3. *(Optionnel)* Étudier [sample-dao-full](../sample-dao-full) (Level2→Level7, `Reprise`) : DAO personnalisés, projections, reprise par lots

**Vérification** : votre service fonctionne (appel depuis la console ou un webservice).

### Etape 5 - Le premier écran web (Vertigo-UI)

*Objectif : afficher une page dans le navigateur.*

Vertigo-UI est construit sur Vue.js, Quasar et SpringMVC avec Thymeleaf — lire les prérequis de lecture du [README de sample-vertigo-ui](../sample-vertigo-ui/README.md) avant de commencer.

À faire :

1. Suivre le **Level 0** de [sample-vertigo-ui](../sample-vertigo-ui) (préparation de l'environnement) :
   - le boot web `BootSampleVui` (Jetty :18080)
   - le `web.xml` : le Listener et le paramètre `boot.applicationConfiguration`
   - `VuiVSpringWebApplicationInitializer` → `VuiVSpringWebConfig` : l'initialisation SpringMVC et ses hooks Vertigo-UI
   - `HomeController` + `home.html` : le premier écran
2. Vérifier [http://localhost:18080/sample/home/](http://localhost:18080/sample/home/)
3. Dans votre projet : intégrer le boot web (webapp, `web.xml`, configuration SpringMVC) et afficher votre première page

**Vérification** : votre écran s'affiche dans le navigateur.

## Suite

- [Parcours Débutant](parcours-debutant.md) — rejoindre une équipe expérimentée et viser l'autonomie sur le courant (le travail standard de l'équipe)
- [sample-vertigo-ui](../sample-vertigo-ui) Levels 1→6 — pour maîtriser Vertigo-UI : listes, écran de détail, recherche, tableaux éditables, controllers, modales
- [Parcours Autonomie](parcours-autonomie.md) — les approfondissements, à consulter au fil de l'eau
