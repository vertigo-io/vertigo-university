# vertigo-university
=================

A sample library for [Vertigo](https://vertigo.io) — the Java application development platform.
Each sample is a self-contained Maven project demonstrating a specific Vertigo topic, with hands-on exercises.

# vertigo-university

Vertigo university est une bibliothèque de samples couvrant Vertigo : chaque sample est un projet Maven autonome, ciblant un sujet précis.

Les samples ne contiennent pas de tests automatisés : chaque sample se lance par une ou plusieurs classes `main` (voir le README de chaque module).

## Prérequis

- JDK 17
- Maven 3.9+
- Eclipse (recommandé ; plugin [KSP Plugin](https://github.com/sebez/vertigo-chroma-kspplugin) en option pour l'expérience de développement)
- Les samples sont autonomes : Elasticsearch embarqué quand c'est nécessaire, bases H2 créées au démarrage ou initialisées par les scripts SQL du dépôt, aucun service externe requis (sauf les identifiants mail/Twitter/IFTTT du sample notifications).

## Build

```bash
mvn clean install -DskipTests
```

26 modules.

## Inventaire des samples

| Sample | Sujet | Démarrage | Doc |
|---|---|---|---|
| [sample-hello-world](sample-hello-world) | Premier node Vertigo + webservice REST (Vega) | `HelloWorld` (Javalin :8080) | [README](sample-hello-world/README.md) |
| [sample-component](sample-component) | Composants et injection de dépendances | `Samples` (console) | [README](sample-component/README.md) |
| [sample-dao](sample-dao) | Accès aux données SQL (DAO générés, services) | `DaoSample` | [README](sample-dao/README.md) |
| [sample-dao-full](sample-dao-full) | Idem + DAO personnalisés, projections, reprise par lots | `Level2`→`Level7`, `Reprise` | [README](sample-dao-full/README.md) |
| [sample-crystal](sample-crystal) | DataFactory Crystal (CRUD + recherche Elasticsearch) | `CrystalSample`, `CrystalSearchSample` | [README](sample-crystal/README.md) |
| [sample-crystal-full](sample-crystal-full) | Idem + niveaux, authorization, login | `Level1`→`Level7` | [README](sample-crystal-full/README.md) |
| [sample-vega](sample-vega) | Web services REST (Vega, Jetty) | `SampleVega` (:8080) | [README](sample-vega/README.md) |
| [sample-notifications](sample-notifications) | Moteur de notifications multi-canaux + AOP | `NotificationSample` | [README](sample-notifications/README.md) |
| [sample-account](sample-account) | Authentification / autorisation (security) | `AccountSample` | [README](sample-account/README.md) |
| [sample-config](sample-config) | NodeConfig programmatique (Features) | `ConfigRun` (Javalin :8080) | [README](sample-config/README.md) |
| [sample-quarto](sample-quarto) | Génération de documents Word (.docx) | `SampleQuarto` | [README](sample-quarto/README.md) |
| [sample-aop-modules](sample-aop-modules) | Aspects AOP pas à pas (exo / corrigé) | classes main des sous-modules | [README](sample-aop-modules/README.md) |
| [sample-stream](sample-stream) | Java 8 Streams (starbucks, scrabble) | `MainStarbucks`, `MainScrabble` | [README](sample-stream/README.md) |
| [sample-vertigo-ui](sample-vertigo-ui) | Application web Vertigo-UI (SpringMVC/Thymeleaf/Vue) | `BootSampleVui` (Jetty :18080) | [README](sample-vertigo-ui/README.md) |
| [sample-vertigo-ui-full](sample-vertigo-ui-full) | Idem + recherche Elasticsearch + tableaux + modales (solutions des Levels 0→6) | `BootSampleVui` (Jetty :18080) | [README](sample-vertigo-ui-full/README.md) |

## Parcours débutant

Trois parcours progressifs, du premier node à l'autonomie en équipe :

1. [Parcours Démarrage](docs/parcours-demarrage.md) — créer une application Vertigo de zéro (hello world → premier écran)
2. [Parcours Débutant](docs/parcours-debutant.md) — rejoindre une équipe expérimentée : viser l'autonomie sur le courant
3. [Parcours Autonomie](docs/parcours-autonomie.md) — les approfondissements (9 étapes S0→S8), à consulter au fil de l'eau

## Plans de formation

### 1. Fondamentaux Vertigo

Progression sur le framework, du premier node aux web services :

1. `sample-hello-world` : premier node, webservice REST (Vega)
2. `sample-component` : composants, injection de dépendances, plugins, aspects AOP
3. `sample-dao` : accès aux données SQL (DAO générés par Studio, services, supervision)
   - *Complément : `sample-dao-full` (niveaux Level2→7, DAO personnalisés, reprise par lots)*
4. `sample-crystal` : DataFactory (CRUD typé + recherche Elasticsearch)
   - *Complément : `sample-crystal-full` (Level1→7, authorization, login)*
5. `sample-vega` : web services REST (Jetty)
6. `sample-notifications` : moteur de notifications multi-canaux (mail, Twitter, IFTTT) + AOP

### 2. Application web Vertigo-UI

1. [sample-vertigo-ui](sample-vertigo-ui) : niveaux Level0→6 (préparation, listes, écran de détail, recherche, tableaux éditables, controllers, modales)
2. [sample-vertigo-ui-full](sample-vertigo-ui-full) : mêmes niveaux complétés (recherche Elasticsearch, tableaux modifiables, modales)

### 3. Sujets avancés

- [sample-aop-modules](sample-aop-modules) : les aspects AOP pas à pas (gutenberg → canaux ifttt/mail/twitter → moteur de notifications)
- [sample-stream](sample-stream) : Java 8 Streams (starbucks → scrabble), pur Java sans Vertigo
- `sample-config` : construction programmatique d'un `NodeConfig` (Features)
- `sample-account` : authentification/autorisation (login, security manager, row-level security)
- `sample-quarto` : génération de documents Word (.docx) depuis un modèle

-----
#License

            vertigo - application development platform

            Copyright (C) 2013-2026, Vertigo.io, team@vertigo.io

            Licensed under the Apache License, Version 2.0 (the "License");
            you may not use this file except in compliance with the License.
            You may obtain a copy of the License at

            http://www.apache.org/licenses/LICENSE-2.0

            Unless required by applicable law or agreed to in writing, software
            distributed under the License is distributed on an "AS IS" BASIS,
            WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
            See the License for the specific language governing permissions and
            limitations under the License.
