# Parcours Débutant - Rejoindre une équipe Vertigo expérimentée

Ce parcours prépare un développeur qui rejoint une équipe expérimentée travaillant sur une application Vertigo existante. L'objectif n'est pas de construire une application (c'est le [Parcours Démarrage](parcours-demarrage.md)), mais d'**atteindre l'autonomie sur le courant** : savoir faire seul le travail standard de l'équipe, en code review.

## Qu'est-ce que « l'autonomie sur le courant » ?

| | Exemples | Posture attendue |
|---|---|---|
| **Le courant** (à traiter seul) | Un champ ajouté au modèle, une requête SQL nommée, un service sur DAO, un écran standard (liste + formulaire), un correctif, un test qui passe le CI | Autonomie, en code review |
| **Les cas complexes** (à escalader) | Les performances, les DAO personnalisés et la reprise par lots, l'architecture de la sécurité, les jobs batch, les incidents de production, tout ce qui « sort de l'ordinaire » | Montrer, demander, apprendre avec un membre de l'équipe |

**Important:** escalader n'est pas un échec : c'est la bonne pratique. Le risque est de s'engager seul sur un sujet hors courant, pas de demander de l'aide.

## Prérequis

- Le [Parcours Démarrage](parcours-demarrage.md) suivi : vous savez ce qu'est un node, un module, un composant, un modèle généré par Studio, un service et un webservice
- Ses lectures préalables, à garder sous la main : [Concepts](https://vertigo-io.github.io/vertigo-docs/#/basic/concepts) (le glossaire) et [Règles de codage](https://vertigo-io.github.io/vertigo-docs/#/intro/regles-codage)
- Un accès à l'application de l'équipe et à son environnement local

## Comment se suit ce parcours

Chaque étape s'appuie sur une formation de ce dépôt, faite sur une **application support** : [sample-vertigo-ui](../sample-vertigo-ui), une petite application déjà construite qui gère des films, leurs acteurs et leurs pays. Son corrigé, [sample-vertigo-ui-full](../sample-vertigo-ui-full), est la même application une fois les exercices faits.

L'application support a deux séries de Levels, qui partent du même point de départ (le [Level Socle 0](../sample-vertigo-ui/LevelSocle0.md)) :
- la **série Socle** : le fonctionnement de l'application, de son démarrage jusqu'aux erreurs ;
- la **série UI** : les écrans.

Chaque étape se termine par un volet **Dans votre équipe** : la même chose, retrouvée dans l'application de l'équipe. C'est là que la formation devient du travail réel : faites-le avec un membre de l'équipe.

**Important:** les premières étapes sont surtout de la **lecture** : se repérer dans un projet est un objectif en soi. Chaque Level Socle vous fait ouvrir, retrouver, répondre à une question dont la réponse est dans le code, puis faire une petite action.

## Formations du parcours

| # | Etape | Formation de référence | Durée indicative | Statut |
|---|---|---|---|---|
| A | Démarrer et configurer l'application | [Levels Socle 0, 1 et 1.1](../sample-vertigo-ui/LevelSocle0.md) | une demi-journée | existante |
| B | Le geste quotidien : modèle → génération | [Levels Socle 2 et 3](../sample-vertigo-ui/LevelSocle2.md) | une demi-journée | existante |
| C | Les requêtes : les tâches | [Level Socle 4](../sample-vertigo-ui/LevelSocle4.md) | 2 heures | existante |
| D | L'organisation du code : modules et packages | [Level Socle 5](../sample-vertigo-ui/LevelSocle5.md) | 2 heures | existante |
| E | Le premier ticket côté serveur | [Levels Socle 6 et 7](../sample-vertigo-ui/LevelSocle6.md) | une demi-journée | existante |
| F | Tester une fonction métier | [Level Socle 8](../sample-vertigo-ui/LevelSocle8.md) | 2 heures | existante |
| G | Les écrans | [Levels Ui 0 à 6](../sample-vertigo-ui/LevelUi0.md) | 3 à 4 jours | existante |
| H | La sécurité et les droits | la documentation, Mars, [sample-account](../sample-account) à lire | une demi-journée | **à compléter** |
| I | Les règles de l'équipe | — (l'équipe : tests, CI, conventions) | au fil des premières semaines | — |

Les durées sont celles de la formation seule ; le volet « Dans votre équipe » dépend de l'application.

## Ce que vous saurez faire à la fin

| Compétence | Etape |
|---|---|
| Démarrer l'application et lire sa configuration : le fichier YAML, ses modules, ses features, ses flags, ses initializers | A |
| Changer un paramètre, le faire venir d'une variable d'environnement, activer un flag | A |
| Régénérer le code et lire ce que la génération produit | B |
| Lire le modèle : entités, DTO, listes de référence, domains, SmartTypes et contraintes ; ajouter un champ | B |
| Choisir entre une méthode de DAO et une tâche ; écrire une tâche | C |
| Situer une classe d'après son module et son package ; enregistrer un nouveau composant | D |
| Écrire un service sur les DAO et les tâches, et le publier par un webservice | E |
| Dire où est la transaction ; distinguer une erreur pour l'utilisateur (`VUserException`) d'une erreur système (`VSystemException`, `WrappedException`, erreurs d'assertion) | E |
| Tester une fonction métier par un test d'intégration | F |
| Construire un écran standard : liste, détail, recherche, tableaux, modales | G |
| Savoir où l'application contrôle les droits, et ce qu'un nouvel écran ou un nouveau service doit contrôler | H |
| Livrer une pull request conforme aux règles de l'équipe | I |

## Etapes

### Etape A - Démarrer et configurer l'application

*Objectif : l'application démarre, et vous savez lire et modifier sa configuration.*

Formation :

1. [Level Socle 0 - Point de départ : démarrer l'application](../sample-vertigo-ui/LevelSocle0.md) : importer le module, lancer l'application, lire l'adresse d'une page
2. [Level Socle 1 - Le démarrage et la configuration](../sample-vertigo-ui/LevelSocle1.md) : de la classe main jusqu'au fichier YAML ; les modules, leurs features, les flags et les initializers ; un premier webservice standard de la plateforme, le healthcheck
3. [Level Socle 1.1 - Paramètres, variables d'environnement et flags](../sample-vertigo-ui/LevelSocle1.1.md) : retrouver d'où vient un paramètre de l'application (le ParamManager et ses plugins), le remplacer par une variable d'environnement, activer un flag (Swagger, pour décrire et appeler les webservices) ; puis revenir à l'état de départ. Ce Level ne change pas l'application pour la suite

Concepts : Module, Component, Manager, Plugin *(glossaire [Concepts](https://vertigo-io.github.io/vertigo-docs/#/basic/concepts))* ; [vertigo-docs : Configuration](https://vertigo-io.github.io/vertigo-docs/#/basic/configuration).

Dans votre équipe :

- [ ] Lire le README de l'application : le build, les dépendances locales (base de données, serveurs, souvent en docker-compose), les identifiants de test
- [ ] Faire le build et démarrer l'application localement, puis vous connecter (l'équipe a toujours un moyen de se connecter en local : comptes de test, annuaire local…)
- [ ] Retrouver le fichier YAML de l'application, ses modules et ses flags
- [ ] Repérer les paramètres qui changent d'un environnement à l'autre, et d'où ils viennent (variables d'environnement, flags actifs)

**Critère d'autonomie** : depuis un clone vierge, vous démarrez l'application et vous vous y connectez en moins d'une heure, sans aide ; vous savez dire quels modules elle déclare.

### Etape B - Le geste quotidien : modèle → génération

*Objectif : maîtriser le geste central du développement Vertigo : modifier le modèle, régénérer, lire le résultat.*

Formation :

1. [Level Socle 2 - Régénérer le code](../sample-vertigo-ui/LevelSocle2.md) : ce que Studio lit (`studio-config.yaml`, le `.kpr` et les `.ksp`) et ce qu'il produit (`src/main/javagen`, le SQL de création de la base)
2. [Level Socle 3 - Le modèle](../sample-vertigo-ui/LevelSocle3.md) : entités, DTO et listes de référence statiques ; d'un champ à son domain, puis à son SmartType et à ses contraintes ; ajouter un champ

Concepts : MDA, KSP, KPR, DtObject, Entity, KeyConcept, Domain, SmartType, Constraint, Formatter ; [vertigo-docs : MDA](https://vertigo-io.github.io/vertigo-docs/#/basic/mda) et [DataModel](https://vertigo-io.github.io/vertigo-docs/#/extensions/datamodel).

Dans votre équipe :

- [ ] Lancer la génération de l'application (son lanceur, de même nature que `StudioGenerate`) : sans modification du modèle, elle ne change rien ; si elle change quelque chose, signalez-le : le code généré du dépôt n'est pas à jour
- [ ] Repérer les fichiers de définitions et leur découpage (souvent un fichier par domaine métier), et l'enum des SmartTypes
- [ ] Faire une petite modification du modèle en pull request, reviewée par un membre de l'équipe

**Critère d'autonomie** : vous savez tout ce que produit une régénération, et vous corrigez le modèle quand le résultat ne convient pas.

**Attention:** le code de `src/main/javagen` ne se modifie jamais à la main : il est écrasé à la prochaine génération.

### Etape C - Les requêtes : les tâches

*Objectif : écrire une requête SQL nommée et savoir où se trouve la méthode qui l'exécute.*

Formation : [Level Socle 4 - Les tâches](../sample-vertigo-ui/LevelSocle4.md) : DAO ou tâche, la déclaration d'une tâche, la méthode générée dans le DAO ou le PAO.

Concepts : Task ; [vertigo-docs : Accès aux données](https://vertigo-io.github.io/vertigo-docs/#/basic/dao).

Dans votre équipe :

- [ ] Retrouver où l'équipe déclare ses tâches, et ses conventions de nommage
- [ ] Vérifier si l'équipe fait générer des tests de tâches (propriété `vertigo.taskTest` du `studio-config.yaml`) : un test est alors produit pour chaque tâche

**Critère d'autonomie** : vous écrivez une tâche avec ses paramètres, et vous prévoyez où Studio placera sa méthode avant de régénérer.

### Etape D - L'organisation du code : modules et packages

*Objectif : situer n'importe quelle classe, et savoir où placer et enregistrer une nouvelle.*

Formation : [Level Socle 5 - Modules et packages](../sample-vertigo-ui/LevelSocle5.md) : les classes `Features` de l'application, le pattern de packages (`controllers`, `services`, `webservices`, et les packages générés), l'enregistrement d'un composant.

Concepts : Module, Component ; [vertigo-docs : Composants](https://vertigo-io.github.io/vertigo-docs/#/basic/composants).

Pour une application réelle, la section [La structure d'un vrai projet](parcours-demarrage.md#la-structure-dun-vrai-projet) du parcours Démarrage fait le tour d'un projet : les modules et leurs classes `Features`, la webapp, le fichier YAML, les définitions Studio, les fichiers de sécurité, et les surcharges de Vertigo.

Dans votre équipe :

- [ ] Lister les modules de l'application (ses classes `Features`) et le domaine métier de chacun
- [ ] Faire la visite guidée du code avec un membre de l'équipe : où vivent les définitions, le code généré, la configuration, les écrans ; la séparation back-office / front-office quand elle existe
- [ ] Retrouver où sont scannés les controllers (`@ComponentScan` de la configuration SpringMVC)
- [ ] Repérer les surcharges de Vertigo du projet (`src/main/java/io/vertigo/...`, `src/main/resources/io/vertigo/...`), et la raison de chacune

**Critère d'autonomie** : vous situez n'importe quel type de code (objet, DAO, service, controller, webservice, définition) et vous expliquez le découpage de l'application.

### Etape E - Le premier ticket côté serveur

*Objectif : ajouter une fonctionnalité de bout en bout côté serveur, en sachant où agissent la transaction et les erreurs.*

Formation :

1. [Level Socle 6 - Services, DAO et webservices](../sample-vertigo-ui/LevelSocle6.md) : un service qui exécute une tâche, un webservice REST qui le publie
2. [Level Socle 7 - La transaction et les erreurs](../sample-vertigo-ui/LevelSocle7.md) : où est la transaction et ce qu'elle couvre ; les deux familles d'erreurs (l'erreur destinée à l'utilisateur, `VUserException`, et l'erreur système : tout le reste, dont `VSystemException`, `WrappedException` et les exceptions Java levées par les assertions) ; lever une erreur pour l'utilisateur

Concepts : Component, DtList, `@Transactional`, `VUserException`, `VSystemException` ; [vertigo-docs : WebServices](https://vertigo-io.github.io/vertigo-docs/#/basic/webservices).

La transaction se **lit** à ce stade : il s'agit de savoir où elle est, pas de la manipuler.

Dans votre équipe :

- [ ] Lire un service de l'application : ce qu'il reçoit par injection, sa transaction, ses erreurs pour l'utilisateur
- [ ] Repérer les webservices de l'application : ceux de l'équipe, et ceux de la plateforme (le healthcheck, par exemple)
- [ ] Traiter un premier ticket côté serveur en pull request

**Critère d'autonomie** : un service et son webservice écrits seuls, validés en code review ; vous savez dire si une erreur est pour l'utilisateur ou pour l'équipe.

### Etape F - Tester une fonction métier

*Objectif : écrire le test qui garantit une fonction métier.*

Formation : [Level Socle 8 - Tester une fonction métier](../sample-vertigo-ui/LevelSocle8.md). Le test démarre l'application, avec sa base et ses données de démonstration, appelle un service, et vérifie son résultat métier : les films d'un pays, et l'erreur prévue pour un pays sans film.

Pourquoi des tests d'intégration ? Ce qui a de la valeur, et qui reste stable quand le code évolue, c'est la fonction métier : le test l'appelle comme l'application l'appelle, avec une vraie base, plutôt que de vérifier le détail de son code.

Dans votre équipe :

- [ ] Lire le squelette de test de l'équipe : comment un test démarre le node, sur quelle base, avec quelles données
- [ ] Lancer les tests en local, et retrouver les tests générés des tâches s'il y en a
- [ ] Ajouter le test de la fonction de votre premier ticket

**Critère d'autonomie** : chaque fonction métier que vous livrez a son test, qui passe en local et dans le CI.

### Etape G - Les écrans

*Objectif : construire un écran standard de l'application.*

Formation : la série UI de l'application support, dans l'ordre. Son [Level Ui 0 - Point de départ](../sample-vertigo-ui/LevelUi0.md) renvoie au Level Socle 0, déjà fait à l'étape A :

1. [Level Ui 1 - Liste simple](../sample-vertigo-ui/LevelUi1.md) : une liste publiée dans le contexte de la vue, et la limite du nombre de lignes à y mettre
2. [Level Ui 2 - Ecran de détail](../sample-vertigo-ui/LevelUi2.md) et ses Levels 2.1 à 2.7 : listes de référence, édition, tableaux, tri côté serveur, validation, attributs et slots
3. [Level Ui 3 - Recherche](../sample-vertigo-ui/LevelUi3.md)
4. [Level Ui 4 - Tableaux éditables](../sample-vertigo-ui/LevelUi4.md)
5. [Level Ui 5 - Controllers et Context transverses](../sample-vertigo-ui/LevelUi5.md)
6. [Level Ui 6 - Modales](../sample-vertigo-ui/LevelUi6.md)

Lectures : [vertigo-docs : UI](https://vertigo-io.github.io/vertigo-docs/#/basic/ui) ; Thymeleaf et Vue.js, en vitesse (voir le [README de la formation](../sample-vertigo-ui/README.md)).

Dans votre équipe :

- [ ] Lire un écran standard de l'application (liste + détail) : son controller, sa vue, son layout
- [ ] Repérer les composants et conventions propres à l'équipe (par exemple le design system de l'État, DSFR, quand l'application est destinée au public administratif : le construire relève du parcours Autonomie)
- [ ] Traiter un ticket « liste + formulaire » en pull request

**Critère d'autonomie** : un écran « liste + formulaire » traité seul, validé en code review.

### Etape H - La sécurité et les droits

*Objectif : savoir où l'application contrôle les droits, et ce que votre code doit contrôler.*

Formation : **à compléter**. En attendant, la documentation et deux exemples à lire.

Les notions :

- **Le login** : l'application authentifie l'utilisateur, puis accorde ses droits à sa session : des **autorisations** et des **clés de périmètre** (par exemple le pays de l'utilisateur).
- **Les autorisations** se déclarent dans un fichier JSON : les autorisations **globales** (l'accès à une fonction : un écran, un bouton, un traitement), et les **opérations sur une entité** (lire, modifier…), avec des **règles** qui disent sur quelles lignes elles portent (par exemple `couId=${couId}` : les films du pays de l'utilisateur, `${couId}` étant la valeur de la clé de périmètre `couId` de sa session). Quand la génération des autorisations est activée, Studio génère les constantes correspondantes.
- **Le contrôle** se fait côté serveur, dans le code : l'accès à une fonction (`@Secured` sur un controller ou un webservice, `AuthorizationUtil.assertAuthorizations` dans un service), l'opération sur une donnée (`AuthorizationUtil.assertOperations`), et le **filtre de sécurité** d'une liste, calculé à partir des règles et appliqué par le service (`getCriteriaSecurity` pour un `findAll`, `AuthorizationUtil.authorizationCriteria` passé en paramètre d'une tâche, `getSearchSecurity` pour une recherche).

**Attention:** le filtre de sécurité n'est pas automatique : une requête à laquelle le service ne l'applique pas renvoie toutes les lignes. Et masquer un bouton à l'écran ne protège rien : le contrôle doit être fait côté serveur.

**Les webservices** demandent une protection de plus : ils peuvent être appelés directement, sans passer par un écran.

- La feature `webservices.security` de `VegaFeatures` fait exiger un utilisateur connecté par chaque webservice. Une route publique le déclare explicitement, par `@AnonymousAccessAllowed`, souvent avec `@SessionLess`, pour ne pas créer de session à chaque appel.
- `@Secured("…")`, sur la classe ou sur la méthode, exige une autorisation ; les contrôles sur les données restent dans le service, comme pour un écran.
- Le filtre `AuthorizationWebFilter`, déclaré dans le `web.xml`, protège des adresses entières par une autorisation globale : chaque paramètre du filtre porte le nom d'une autorisation, préfixé par `Atz` (par exemple `AtzVoirSwagger`), et pour valeur les adresses qu'elle protège (par exemple `/api/swagger*;/api/catalog*`). Sans cette autorisation, l'appel est refusé (erreur 403). Il protège ainsi d'un coup tout un groupe de webservices, d'administration par exemple.
- Les webservices d'outillage (Swagger, catalogue, pages de test) exposent toute l'API : ils sont placés sous un flag, actif seulement en développement et en recette (voir le [Level Socle 1.1](../sample-vertigo-ui/LevelSocle1.1.md)), ou protégés par une autorisation avec `AuthorizationWebFilter`.
- Les routes publiques se protègent des abus par la limitation de débit (`rateLimiting` et `webservices.rateLimiting`), dont les seuils viennent en général de variables d'environnement.

L'application support n'active pas `webservices.security` : ses webservices sont ouverts, ce qui convient à un exercice local, jamais à une application livrée.

À lire :

- [vertigo-docs : Sécurité](https://vertigo-io.github.io/vertigo-docs/#/basic/securite) (les principes) et [Account](https://vertigo-io.github.io/vertigo-docs/#/extensions/account) (la configuration et l'API) ; pour les webservices, [WebServices](https://vertigo-io.github.io/vertigo-docs/#/basic/webservices) (les sections « Sécurisation du WebService », « Rate Limiting » et, pour `AuthorizationWebFilter`, « Servlet Filters vs HandlerChain »)
- [sample-account](../sample-account), sur le même modèle de films : `auth-config.json` (une autorisation globale, et l'opération `read` de `Movie` avec sa règle sur le pays) ; `UserServicesImpl.login`, qui accorde les droits et la clé de périmètre `couId` ; `AccountSample.testSecurity`, qui calcule le filtre de sécurité d'une recherche ; les classes générées `GlobalAuthorizations` et `SecuredEntities`. C'est un exemple minimal : les droits y sont écrits en dur, et accordés sans tester le résultat du login ; dans une application, on vérifie d'abord le login (voir la doc Account)
- [Mars](https://github.com/vertigo-io/vertigo-mars), l'application de démonstration, en aperçu : deux fichiers de sécurité (`base-auth-config.json`, `hr-auth-config.json`), chacun chargé par la classe `Features` de son module ; des controllers annotés `@Secured` ; des services qui appliquent les contrôles et les filtres (`BaseServices`, et `EquipmentServices` pour les trois filtres)

Dans votre équipe :

- [ ] Retrouver le fichier de sécurité de l'application (depuis les classes `Features`, qui le chargent), et comment l'équipe regroupe les autorisations (profils, périmètres)
- [ ] Vous connecter avec deux profils différents, et comparer ce que chacun voit
- [ ] Repérer, dans un service, où sont appliqués les contrôles et les filtres de sécurité
- [ ] Repérer comment sont protégés les webservices de l'application : `webservices.security`, les routes publiques (`@AnonymousAccessAllowed`), les `@Secured`, les adresses protégées par `AuthorizationWebFilter` dans le `web.xml`, les flags des outils de développement, la limitation de débit

**Critère d'autonomie** : pour un nouvel écran ou un nouveau service, vous dites quels contrôles il faut, et votre ticket est validé en code review sans remarque de sécurité.

### Etape I - Les règles de l'équipe

*Objectif : que vos pull requests respectent toutes les règles de l'équipe.*

Il n'y a pas de formation : ces règles sont propres à chaque équipe.

Dans votre équipe :

- [ ] **CI** : ce que le CI exécute (build, tests, analyse), et comment se lit un échec
- [ ] **Versioning** : comment l'application communique sa version
- [ ] **Conventions** : nommage, branches, taille des pull requests, revues

**Critère d'autonomie** : une pull request qui passe le CI et les conventions sans correction demandée.

## Cas à escalader (→ [Parcours Autonomie](parcours-autonomie.md))

Cas typiques, liste non exhaustive : tout ce qui sort de l'ordinaire s'escalade.

| Cas | Chapitre du parcours Autonomie |
|---|---|
| Recherche Elasticsearch en profondeur (indexation, facettes) | S3 |
| PostgreSQL, migrations Liquibase, cache (Redis) | S2 |
| DAO personnalisés, reprise par lots, performances | S1 (complément) + S3 |
| Architecture de la sécurité (SSO, périmètres, règles avancées) | S4 |
| DSFR, accessibilité RGAA, tests UI, boot web, intercepteurs | S5 |
| Planification (agenda/créneaux), jobs batch (orchestration) | S6 |
| Communication (mail/SMS, PDF/Word), appels sortants, REST API | S7 |
| Production : stratégie de tests, paramétrage de déploiement (boot, SSL…), monitoring | S8 |

## Suite

- [sample-vertigo-ui](../sample-vertigo-ui) : les deux séries de l'application support, et leur corrigé dans [sample-vertigo-ui-full](../sample-vertigo-ui-full)
- [Parcours Autonomie](parcours-autonomie.md) : les approfondissements vers l'autonomie complète, à consulter au fil de l'eau
