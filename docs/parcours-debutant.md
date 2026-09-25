# Parcours Débutant - Rejoindre une équipe Vertigo expérimentée

Ce parcours prépare un développeur qui rejoint une équipe expérimentée travaillant sur une application Vertigo existante. L'objectif n'est pas d'apprendre à construire une application complète (voir le [Parcours Démarrage](parcours-demarrage.md)), mais d'**atteindre l'autonomie sur le courant** : savoir faire le travail standard de l'équipe seul, en code review.

## Qu'est-ce que « l'autonomie sur le courant » ?

| | Exemples | Posture attendue |
|---|---|---|
| **Le courant** (à traiter seul) | Un écran standard (liste + formulaire), un service sur DAO, une requête SQL nommée, un correctif, un test qui passe le CI | Autonomie, en code review |
| **Les cas complexes** (à escalader) | Les performances, les DAO personnalisés et la reprise par lots, l'architecture de la sécurité, les jobs batch, les incidents de production, tout ce qui « sort de l'ordinaire » | Montrer, demander, apprendre avec le binôme |

**Important:** escalader n'est pas un échec : c'est la bonne pratique. Le risque est de s'engager seul sur un sujet hors courant, pas de demander de l'aide.

## Prérequis

- Le [Parcours Démarrage](parcours-demarrage.md) suivi (ou un équivalent : node, javagen, services, Vertigo-UI)
- Un accès à l'application de l'équipe et à son environnement local

## Formations de référence

Chaque étape s'appuie sur des formations de ce dépôt (un sujet par formation) :

| Etape | Formations de référence |
|---|---|
| A - L'environnement tourne | — (l'application de l'équipe et son README) |
| B - Comment l'appli est faite | [sample-hello-world](../sample-hello-world), [sample-config](../sample-config), [sample-component](../sample-component) |
| C - Le geste quotidien Studio → javagen | [sample-vertigo-ui](../sample-vertigo-ui) (`definitions/`, `StudioGenerate`) |
| D - Le premier ticket | [sample-dao](../sample-dao), [sample-vertigo-ui](../sample-vertigo-ui) Levels 0→6 (boot minimal : [sample-vertigo-ui-hello](../sample-vertigo-ui-hello)), [sample-account](../sample-account) |
| E - Les règles de l'équipe | — (tests, CI et conventions de l'équipe) |

## Etapes

### Etape A - L'environnement tourne

*Objectif : l'application de l'équipe démarre en local, vous vous connectez, vous régénérez.*

À faire :

1. Lire le README de l'application : build, dépendances locales (base de données, serveurs — souvent en docker-compose), identifiants de test
2. Faire le build du projet et démarrer l'application localement
3. Vous connecter au back-office (l'équipe a toujours un moyen de se connecter en local : comptes de test, annuaire local, etc.)
4. Lancer une régénération Studio (le runner de l'application, de même nature que `StudioGenerate` dans les samples)

**Critère d'autonomie** : depuis un clone vierge, vous faites démarrer l'application et vous y connectez en moins d'une heure, sans demander d'aide.

### Etape B - Comment l'appli est faite (mental model + visite guidée)

*Objectif : vous y retrouver dans le code de l'équipe.*

Les concepts à maîtriser (tous couverts par les samples de ce dépôt) :

- Le node et sa configuration : `NodeConfig`/`ModuleConfig`, Features — [sample-hello-world](../sample-hello-world), [sample-config](../sample-config)
- Les composants et l'injection de dépendances (`@Inject`) — [sample-component](../sample-component)
- Le SmartType : le typage fort qui relie le domaine à l'UI et aux webservices — [vertigo-docs : concepts](https://vertigo-io.github.io/vertigo-docs/#/)
- Les transactions : `@Transactional` porté par les services
- Le ParamManager : les paramètres d'application (lecture, modification à chaud) — [vertigo-docs : configuration](https://vertigo-io.github.io/vertigo-docs/#/)

Puis la **visite guidée de l'application de l'équipe** (à faire avec un membre de l'équipe) :

- la structure par domaines métier (chaque domaine : son modèle, ses services, ses écrans)
- la séparation back-office / front-office quand elle existe
- où vivent le code généré (`javagen`), les définitions Studio (`definitions/`) et la configuration
- le boot de l'application et sa configuration (variables, environnements)

**Critère d'autonomie** : vous savez situer n'importe quel type de code (domaine, DAO, service, écran, définition) et expliquer la structure générale de l'application.

### Etape C - Le geste quotidien : Studio → javagen

*Objectif : maîtriser le geste central du développement Vertigo : modifier le modèle, régénérer, lire le diff.*

À faire :

1. Dans un sample de ce dépôt (par exemple [sample-vertigo-ui](../sample-vertigo-ui)) :
   - ajouter un champ (ou une entité) dans `src/main/resources/definitions/model.ksp`
   - ajouter ou modifier une requête SQL nommée (`dao.ksp`)
   - lancer le runner `io.vertigo.samples.support.mda.StudioGenerate`
   - lire le diff dans `src/main/javagen` : la classe du domaine, le DAO, la requête (`VuiPAO`), le SQL (`sqlgen/`)
2. Faire **la même modification en pull request sur le code de l'équipe** (reviewée par un membre senior)
3. Lire les tests générés quand l'équipe a activé la génération de tests (propriété `vertigo.taskTest` dans le `studio-config.yaml`) : un test est produit par requête SQL nommée

**Critère d'autonomie** : vous savez exactement tout ce que produit une régénération, et vous savez corriger un modèle quand le code généré ne convient pas.

**Attention:** ne jamais modifier le code de `src/main/javagen` à la main : c'est du code généré, il sera écrasé à la prochaine régénération.

### Etape D - Le premier ticket : service + écran + sécurité

*Objectif : traiter un ticket standard bout en bout.*

1. **Service sur DAO** : critères, tri, pagination — étudier [sample-dao](../sample-dao) puis écrire le service du ticket
2. **Écran Vertigo-UI** : suivre les [Levels 0→6 de sample-vertigo-ui](../sample-vertigo-ui) si ce n'est pas encore fait (listes, écran de détail, recherche, tableaux, controllers, modales) — le boot web minimal seul est dans [sample-vertigo-ui-hello](../sample-vertigo-ui-hello)
3. **Sécurité** : comprendre ce que fait l'annotation `@Secured` (côté service, valeurs = noms d'autorizations) et comment l'authorization de l'équipe (fichier `*-auth-config.json`) **restreint à l'exécution les requêtes émises par les DAO** (row-level security) — étudier [sample-account](../sample-account) (login, authorizations, `auth-config.json`) et la doc du module account dans [vertigo-docs](https://vertigo-io.github.io/vertigo-docs/#/)
4. Soumettre le ticket en code review

**Critère d'autonomie** : un ticket « liste + formulaire » traité seul, validé en code review sans remarque de sécurité.

### Etape E - Les règles de l'équipe

*Objectif : que vos pull requests respectent toutes les règles de l'équipe.*

À faire :

1. **Tests** : comprendre le squelette de test de l'équipe — comment on boot un node en test, quels tests existent (tests des tâches SQL nommées générés, tests de services, tests UI — souvent une UI test par écran)
2. **CI** : ce que le CI exécute (build, tests, analyse), comment un échec se lit
3. **Versioning** : comment l'application communique sa version
4. **Conventions** : nommage, branches, taille des pull requests, revues

**Critère d'autonomie** : une pull request qui passe le CI et les conventions sans correction demandée.

## Cas à escalader (→ [Parcours Autonomie](parcours-autonomie.md))

Cas typiques, liste non exhaustive : tout ce qui sort de l'ordinaire s'escalade.

| Cas | Chapitre du parcours autonomie |
|---|---|
| Recherche Elasticsearch en profondeur (indexation, facettes) | S3 |
| PostgreSQL, migrations Liquibase, cache (Redis) | S2 |
| DAO personnalisés, reprise par lots, performances | S1 (complément) + S3 |
| Architecture de la sécurité (SSO, row-level avancé) | S4 |
| DSFR, accessibilité RGAA, tests UI, boot web, intercepteurs | S5 |
| Planification (agenda/créneaux), jobs batch (orchestration) | S6 |
| Communication (mail/SMS, PDF/Word), appels sortants, REST API | S7 |
| Production : stratégie de tests, variables d'environnement, monitoring | S8 |

## Suite

- [sample-vertigo-ui](../sample-vertigo-ui) Levels 0→6 — le terrain d'entraînement de l'UI (et sa solution dans [sample-vertigo-ui-full](../sample-vertigo-ui-full))
- [sample-account](../sample-account) — login, authorizations, row-level security
- [Parcours Autonomie](parcours-autonomie.md) — les approfondissements vers l'autonomie complète
