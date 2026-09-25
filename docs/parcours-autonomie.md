# Parcours Autonomie - Approfondir Vertigo

Ce parcours est un **menu d'approfondissements** pour un développeur déjà autonome sur le courant (voir le [Parcours Débutant](parcours-debutant.md)). Il ne se suit pas comme une liste de courses : vous y consultez le chapitre concerné quand un cas le demande — en particulier quand un cas est escaladé par un débutant.

L'horizon : 6 à 12 mois de pratique, au rythme des tickets.

## Progression des 9 étapes

| # | Etape | Dosage cours/exo |
|---|---|---|
| S0 | Le « way of Vertigo » (le vocabulaire commun) | 60/40 |
| S1 | Modèle de données & Studio (le geste quotidien, en profondeur) | 70/30 |
| S2 | Environnement local réel (Docker, PostgreSQL/Liquibase, cache) | 30/70 |
| S3 | Accès aux données & recherche (services, Elasticsearch, DAO avancés) | 50/50 |
| S4 | Sécurité (login, authorizations, row-level, SSO) | 50/50 |
| S5 | Application web Vertigo-UI (mécanique + couche « production ») | 30/70 |
| S6 | Planification & batch (agenda, orchestration de jobs) | 40/60 |
| S7 | Communication & intégration (mail/SMS, documents, HTTP, REST, audit) | 40/60 |
| S8 | Production & fiabilité (tests, environnement, CI, monitoring) | 60/40 |

### S0 - Le « way of Vertigo »

*Dosage 60/40 — le vocabulaire et les réflexes dont tout le reste est fait.*

**Thèmes**

1. **Le node et sa configuration** : le node est le processus qui porte l'application ; tout y est piloté par `NodeConfig`/`ModuleConfig` et les Features activées. Références : [sample-hello-world](../sample-hello-world), [sample-config](../sample-config) (Features Javalin, DataModel, Vega), l'appli de démo [Mars](https://github.com/vertigo-io/vertigo-mars) (`BootMars`).
2. **Composants, injection et plugins** : `ComponentSpace`, `@Inject`, le pattern Manager/Plugin. Référence : [sample-component](../sample-component) (steps 1→8).
3. **SmartType** : le typage fort qui relie le domaine à l'UI et aux webservices (format, conversion contrôlées dès l'entrée dans le système). Référence : [vertigo-docs : concepts](https://vertigo-io.github.io/vertigo-docs/#/), et les SmartTypes custom de Mars (`MarsSmartTypes`).
4. **Transactions** : `@Transactional` porté par les services — l'échec d'une opération en échoue tout le bloc.
5. **EventBus** : les événements de domaine (un composant en publie, d'autres les consomment) — indispensable pour comprendre le planning (S6). Référence : [vertigo-docs : commons](https://vertigo-io.github.io/vertigo-docs/#/).
6. **ParamManager** : les paramètres d'application (lecture et modification à chaud) — la configuration fine sans recompiler. Référence : [vertigo-docs : configuration](https://vertigo-io.github.io/vertigo-docs/#/).
7. **AOP, en connaissance** : les aspects traversent le node (supervision, trace). Vous les *consommez* avant de les écrire — l'écriture est couverte par [sample-aop-modules](../sample-aop-modules).

**Exo** : lancer `sample-hello-world`, ajouter un composant et un plugin dans `sample-component`, écrire un service `@Transactional` dont l'échec échoue en cascade.

**Vigilance** : ne pas écrire d'aspect avant de savoir lire la supervision d'un DAO (voir [sample-dao](../sample-dao)) : l'AOP Vertigo est d'abord un outil de lecture du système.

### S1 - Modèle de données & Studio

*Dosage 70/30 — le geste quotidien, maîtrisé cette fois en profondeur.*

**Thèmes**

1. **Les définitions** : `studio-config.yaml` + fichiers `.kpr`/`.ksp` (domaines, modèle, DAO, recherche). Références : [sample-vertigo-ui](../sample-vertigo-ui) (`src/main/resources/definitions/`), Mars.
2. **Ce que javagen produit** : classes du domaine, DAO, requêtes SQL nommées, définitions, SQL — et **les tests générés** quand l'équipe active la génération de tests (propriété `vertigo.taskTest` dans `studio-config.yaml`).
3. **Le geste** : modifier le modèle → régénérer → lire le diff. C'est le workflow, pas une option.
4. **Complément : DAO personnalisés, projections, reprise par lots** — référence : [sample-dao-full](../sample-dao-full) (Level2→Level7, `Reprise`).

**Exo** : dans un sample, modifier `model.ksp` + `dao.ksp`, régénérer avec `StudioGenerate`, lire le diff complet (domaine, DAO, requête, SQL, tests) ; puis le même exercice sur le code de l'équipe.

**Vigilance** : jamais de modification manuelle dans `src/main/javagen`. Si le code généré ne convient pas, c'est le modèle ou la configuration de génération qui doit changer.

### S2 - Environnement local réel

*Dosage 30/70 — les samples tournent sur H2 ; une application de production, non.*

**Thèmes**

1. **Les dépendances locales en docker-compose** : base de données, serveur de recherche, cache — l'environnement local reproduit la stack de production, en miniature.
2. **PostgreSQL** : Studio génère le SQL pour un SGBDR cible déclaré dans `studio-config.yaml` (paramètre `baseCible` — `H2` dans les samples, PostgreSQL dans une application de production). Référence : [vertigo-docs : database](https://vertigo-io.github.io/vertigo-docs/#/).
3. **Liquibase** : les migrations versionnées qui font évoluer la base entre deux versions de l'application, à côté du SQL généré par Studio.
4. **Le cache (Redis)** : les données calculées ou coûteuses sont cachées (typiquement un planning, S6). Connecteur Redis dans la plateforme.
5. **Le login local du back-office** : chaque équipe a un moyen de se connecter en local (comptes de test, annuaire local) — savoir le trouver et l'activer.

**Exo** : monter la stack locale en docker-compose, faire démarrer un sample university contre PostgreSQL, écrire une migration Liquibase qui ajoute une colonne et vérifier la cohérence avec le SQL généré par Studio.

**Vigilance** : deux sources de vérité SQL (génération Studio + migrations Liquibase) : tout schéma doit pouvoir se reconstruire des deux, sinon la migration d'environnement casse.

### S3 - Accès aux données & recherche

*Dosage 50/50 — le quotidien des écrans, version complète.*

**Thèmes**

1. **Services sur DAO** : `DtList`, critères, tri, pagination — la base est dans [sample-dao](../sample-dao).
2. **Elasticsearch** : indexation (pattern `SearchLoader`, voir [sample-crystal](../sample-crystal) et le [Level 3 de sample-vertigo-ui](../sample-vertigo-ui)) et recherche facettée ; plugin datafactory Elasticsearch dans la plateforme ; Mars (`basemanagement/search/`). Référence : [vertigo-docs : recherche](https://vertigo-io.github.io/vertigo-docs/#/).
3. **DAO avancés** : DAO personnalisés, projections, reprise par lots, performances — [sample-dao-full](../sample-dao-full).
4. **Master data** : les données de référence (valeurs fixes) — Mars (`MarsMasterDataDefinitionProvider`).
5. **Crystal, en option** : un autre mode d'accès typé aux données — [sample-crystal](../sample-crystal).

**Exo** : écrire dans `sample-dao-full` un service avec critères + tri + pagination ; reproduire le pattern `SearchLoader` d'un sample sur un objet du sample.

**Vigilance** : l'index de recherche est un mécanisme asynchrone : la donnée indexée peut être en retard sur la base — à connaître avant de « déboguer un résultat manquant ».

### S4 - Sécurité

*Dosage 50/50 — ce que le débutant consomme (Etape D du parcours débutant), vu de l'architecture.*

**Thèmes**

1. **Identités, login, UserSession** — [sample-account](../sample-account), Mars (`LoginController`, `LoginServices`). Référence : [vertigo-docs : sécurité](https://vertigo-io.github.io/vertigo-docs/#/).
2. **Authorizations** : `@Secured` côté service (valeurs = noms d'autorizations) + un fichier `*-auth-config.json` par domaine métier — Mars en a un par domaine (`base-auth-config.json`, `hr-auth-config.json`). Référence : [vertigo-docs : account](https://vertigo-io.github.io/vertigo-docs/#/).
3. **Row-level security, en profondeur** : les règles d'authorization **restreignent à l'exécution les requêtes émises par les DAO** (conditions injectées) — le code généré n'est pas modifié. C'est le piège à comprendre une fois pour toutes.
4. **SSO** : la plateforme fournit des connecteurs (OIDC — utilisé par Mars ; SAML2, Keycloak, LDAP). En équipe, vous les *observez* (flux de login, déconnexion, sessions) avant de les paramétrer.

**Exo** : dans `sample-account`, se connecter, créer une règle, et observer la requête exécutée avec et sans session ; puis lire les `*-auth-config.json` de Mars.

**Vigilance** : une règle d'authorization change le résultat de *toutes* les requêtes concernées : tester systématiquement avec au moins deux rôles différents.

### S5 - Application web Vertigo-UI

*Dosage 30/70 — la mécanique est dans les Levels ; ici, la couche « production » de l'UI.*

**Thèmes**

1. **La mécanique Vertigo-UI** : [Levels 0→6 de sample-vertigo-ui](../sample-vertigo-ui) (listes, écran de détail, recherche, tableaux, controllers, modales) — solutions dans [sample-vertigo-ui-full](../sample-vertigo-ui-full) — et les vues de Mars. Référence : [vertigo-docs : UI](https://vertigo-io.github.io/vertigo-docs/#/).
2. **Le boot web réel** : deux modes — `web.xml` + Listener (Level 0 de sample-vertigo-ui) ou boot programmatique `JettyBoot`/`JettyBootParams` (Mars `BootMars` : context path, SSL/keystore pilotés par variables d'environnement).
3. **L'initialisation SpringMVC** : `*VSpringWebApplicationInitializer` → `*VSpringWebConfig` (Mars, sample-vertigo-ui) — le `@ComponentScan` liste les packages explicitement, pas de scan des sous-packages.
4. **Les intercepteurs web** : le cross-cutting côté web (ex. limitation de débit sur des endpoints publics) — [vertigo-docs : UI](https://vertigo-io.github.io/vertigo-docs/#/).
5. **Gestion des erreurs et feedback** : le message stack de l'UI (voir le Level 2.5 de sample-vertigo-ui) et les pages d'erreur.
6. **Upload multipart** : Mars (`FileUploadController`, `MultipartConfigInjectionHandler`).
7. **i18n** : les bundles de messages initialisés au boot (Mars `I18nResourcesInitializer`).
8. **DSFR** : le design system de l'administration française (standard public) — une bibliothèque de composants Vertigo-UI dédiée existe (vertigo-ui-dsfr) ; s'appliquer à la checklist **RGAA** (accessibilité) quand l'application est destinée au public administratif.

**Exo** : suivre les Level 0→6, refondre un écran du sample en DSFR + passer la checklist RGAA, ajouter un test UI type (un par écran).

**Vigilance** : le `@ComponentScan` sans sous-packages : un nouveau package de controllers qui ne « se voit pas » vient d'oublier sa ligne dans `*VSpringWebConfig`.

### S6 - Planification & batch

*Dosage 40/60 — deux composants de plateforme qui structurent les applications métier.*

**Thèmes**

1. **vertigo-planning** : la bibliothèque d'agenda — plages horaires, tranches, créneaux, publication, réservation côté usager, et les événements de domaine qui propagent les changements (créneau libéré, consommé, supprimé). Référence : [vertigo-docs : planning](https://vertigo-io.github.io/vertigo-docs/#/).
2. **Le cache du planning** : un planning est lu en continu et écrit par lots — il est typiquement caché (Redis, S2), avec synchronisation base ↔ cache.
3. **vertigo-orchestra** : l'orchestration de jobs — définition de processus, activités, exécutions planifiées, rapports, déclenchement ; les jobs de Mars (`basemanagement/jobs/`, `catalog/jobs/supplier/`) en sont des exemples. Référence : [vertigo-docs : orchestra](https://vertigo-io.github.io/vertigo-docs/#/).
4. **Le pipeline de fichiers** : un batch typique = télécharger un fichier (connecteur SFTP/JSch) → import par lots → purge — avec idempotence et rollback.
5. **Le front-office sans session** : les parcours publics (usager) s'authentifient par tokens (parfois QR code) plutôt que par session — comprendre le pattern avant de toucher à un écran FO.

**Exo** : construire un mini-agenda sur vertigo-planning (plage → tranches → publication → réservation) ; créer un job orchestra de deux activités qui déclenche une requête SQL nommée ; reproduire un pipeline de fichiers contre un mock local.

**Vigilance** : un batch doit être idempotent (rejouable sans effet multiple) et sonder son échec : un job planifié ne « demandera pas s'il peut retenter ».

### S7 - Communication & intégration

*Dosage 40/60 — sortir de l'application : usagers, documents, systèmes externes.*

**Thèmes**

1. **vertigo-social** : la messagerie — mail, et SMS dans les applications de production ; Mars l'utilise, [02-sample-mail](../sample-aop-modules/02-sample-mail) l'exerce en local (yopmail). Référence : [vertigo-docs : social](https://vertigo-io.github.io/vertigo-docs/#/).
2. **vertigo-quarto** : les documents Word/PDF depuis un modèle — [sample-quarto](../sample-quarto). Référence : [vertigo-docs : quarto](https://vertigo-io.github.io/vertigo-docs/#/).
3. **Appels sortants HTTP** : le connecteur httpclient pour les webservice externes (TLS selon le contournant) ; les appels sortants se mockent en local pour tester.
4. **Vega** : la REST API de l'application (SPA, mobile, intégrations) — [sample-vega](../sample-vega), les webservices de Mars (`basemanagement/webservices/`, `command/webservices/bot/`). Référence : [vertigo-docs : vega](https://vertigo-io.github.io/vertigo-docs/#/). Les endpoints publics sont souvent dotés d'une limitation de débit.
5. **vertigo-audit** : la traçabilité métier (qui a fait quoi, sur quoi) ; Mars l'utilise. Référence : [vertigo-docs : audit](https://vertigo-io.github.io/vertigo-docs/#/).

**Exo** : `02-sample-mail` (MailManager vers yopmail), `sample-quarto` (générer un document), publier un webservice vega avec limitation de débit.

**Vigilance** : la communication ne doit jamais bloquer le parcours utilisateur : envoi asynchrone, idempotence (pas de double invitation), et journalisation de chaque envoi.

### S8 - Production & fiabilité

*Dosage 60/40 — savoir lire le pipeline, bump une version, comprendre un incident.*

**Thèmes**

1. **La stratégie de tests** : tests des requêtes SQL nommées (générés), tests de services, tests UI (souvent un par écran), tests d'intégration, tests de **rollback** ; les tests qui dépendent de systèmes externes sont souvent tagués et exclus du CI par défaut — un choix d'équipe à comprendre.
2. **Les variables d'environnement** : le paramétrage par environnement — Mars en est un exemple parlant (`BootMars` lit `CONTEXT_PATH`, `SSL_DISABLED`, `KEYSTORE_URL`…) ; chaque équipe documente ses variables.
3. **CI/CD, en lecture** : ce que le CI exécute (build, tests, analyse statique, image), le registre d'images, le déploiement (souvent Kubernetes/Helm) — lire le pipeline de bout en bout sans jamais monter de cluster.
4. **Monitoring, en connaissance** : les dashboards (Grafana), les logs centralisés (Kibana), l'analyse statique (Sonar), les CVE.
5. **Le versioning applicatif** : comment l'application communique sa version, et le rituel de release (bump, tag, note de version).

**Exo** : écrire un test de rollback type ; faire tourner la suite UI ; lire le CI de bout en bout ; effectuer le double bump de version (projet + version affichée) d'un tag.

**Vigilance** : des tests ignorés dans le build sont parfois un choix d'équipe assumé — le signaler en revue plutôt que le « corriger ».

## Hors périmètre débutant

Modules qui ne font pas partie du travail courant d'un développeur (cas d'usage spécifiques, ou pas encore prêts pour la production) : **stella** et **vortex** (pas prêts), **geo**, **easyforms**, **dashboard**, **ai-langchain4j** (démontrés dans Mars mais hors du quotidien), **mqtt**, **influxdb**, **neo4j**, **mongodb**, la partie « ledger » de **vertigo-audit**, **ui-server-ssr** et **ui-wysiwyg**.

## Références

- [vertigo-docs](https://vertigo-io.github.io/vertigo-docs/#/) : la documentation officielle (basic, extensions, modules, design-system)
- [Mars](https://github.com/vertigo-io/vertigo-mars) : l'application de démonstration publique de Vertigo
- Les samples de ce dépôt : `sample-*` (dont [sample-vertigo-ui](../sample-vertigo-ui) Levels 0→6 et sa [solution](../sample-vertigo-ui-full))
