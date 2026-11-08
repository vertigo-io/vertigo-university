# Level Socle 1 - Le démarrage et la configuration

Nous allons suivre le démarrage de l'application, jusqu'au fichier qui la configure.
Dans le parcours Démarrage, vous avez d'abord construit le node en Java, puis rencontré le fichier YAML avec l'écran web. Dans un vrai projet, la configuration du node est toujours ce fichier YAML, lu au démarrage.

À la fin de ce Level, vous saurez :
- suivre le démarrage, de la classe main jusqu'au fichier de configuration ;
- lire le fichier YAML : les modules, leurs features, les flags et les initializers ;
- relier une feature déclarée aux composants qu'elle apporte.

## Eléments

- Classe main : `/src/main/java/io/vertigo/samples/BootSampleVui.java`
- Configuration : `/src/main/resources/webapp/WEB-INF/web.xml` et `/src/main/resources/webapp/META-INF/sample-vui.yaml`
- Concepts illustrés : Module, Component, Manager, Plugin *(voir le glossaire [vertigo-docs : Concepts](https://vertigo-io.github.io/vertigo-docs/#/basic/concepts))*
- Documentation : [vertigo-docs : Configuration](https://vertigo-io.github.io/vertigo-docs/#/basic/configuration)

### A connaitre : Le démarrage

`BootSampleVui` démarre un serveur Jetty, qui charge la webapp. Dans `web.xml`, le listener `AppServletContextListener` démarre alors le node Vertigo : il lit le paramètre `boot.applicationConfiguration`, qui désigne le fichier YAML de configuration. Le node est construit à partir de ce fichier, puis ses initializers s'exécutent.

Pourquoi un fichier plutôt que du code ? La configuration se lit d'un coup d'œil, se modifie sans recompiler, et s'adapte à chaque environnement.

### A connaitre : Le fichier YAML

- `boot` : les paramètres de démarrage (les langues) et les plugins de démarrage, par exemple ceux qui trouvent les ressources dans le classpath ou dans la webapp.
- `modules` : chaque entrée est une classe `Features`, qui déclare un **module**. Celles de la plateforme (`CommonsFeatures`, `DatabaseFeatures`, `DataStoreFeatures`, `VegaFeatures`…) et celles de l'application (`SupportFeatures`, `VuiFeatures` : nous les verrons au Level Socle 5). Sous un module :
  - `features` : les fonctionnalités activées, par exemple `sql` pour `DatabaseFeatures` ;
  - `featuresConfig` : la façon de les réaliser, avec ses paramètres, par exemple `sql.c3p0` (c3p0 : le pool de connexions à la base) et l'URL de la base. Une fonctionnalité est exposée par un **manager** ; le **plugin** choisi ici fait le travail derrière lui.
- `__flags__` : un élément marqué d'un flag n'est pris en compte que si ce flag est actif. Les flags actifs se donnent par le paramètre `boot.activeFlags`, dans `web.xml`.
- `initializers` : du code exécuté une fois le node démarré, dans l'ordre de la liste. Le premier, `DataBaseInitializer`, crée la base de données (le schéma, puis les données de démonstration).

Chaque feature enregistre des **composants** dans le node : ce sont eux que les autres composants reçoivent par injection.

## Etapes

1. Dans `web.xml`, retrouvez le listener et le paramètre `boot.applicationConfiguration` : quel fichier désigne-t-il ?
1. Dans `sample-vui.yaml`, retrouvez la configuration de la base de données.
**Question** : quelle base est utilisée, et pourquoi n'y a-t-il rien à installer ? *(lisez le paramètre `jdbcUrl`)*
1. Retrouvez le module `ElasticSearchFeatures`.
**Question** : est-il actif au démarrage de cet exercice ? *(cherchez `boot.activeFlags` dans `web.xml` : s'il est absent, et sans la variable d'environnement `VERTIGO_BOOT_ACTIVE_FLAGS`, aucun flag n'est actif)*
1. Dans `VegaFeatures`, retrouvez la configuration `webservices.javalin` et son paramètre `apiPrefix`.
**Question** : par quel préfixe commencent les adresses des webservices REST de l'application ? *(le montage qui les sert est déjà prêt : vous y ajouterez votre propre webservice au Level Socle 6)*
1. Démarrez l'application *(voir le [Level Socle 0](./LevelSocle0.md))* et appelez [http://localhost:18080/sample/api/healthcheck/ping](http://localhost:18080/sample/api/healthcheck/ping) : vous devez obtenir `OK`. C'est un webservice standard de la plateforme, apporté par la configuration `webservices.healthcheck` de `VegaFeatures`.
*(si le ping répond **404** : la configuration `webservices.healthcheck` manque dans `VegaFeatures`, ou l'adresse ne commence pas par `/sample/api` ; sans cette configuration, le composant du healthcheck n'est pas enregistré, et le webservice n'existe pas)*
1. Appelez [http://localhost:18080/sample/api/healthcheck/complete](http://localhost:18080/sample/api/healthcheck/complete) : la liste des contrôles de santé de l'application. Vous devez y trouver le contrôle `testQuery`, réalisé par `sqlConnectionProviderPlugin`, dans l'état `GREEN`.
**Question** : quel module et quelle feature de la plateforme apportent ce contrôle ? *(lisez ses champs `module` et `feature` : `vertigo-database` est le module déclaré par `DatabaseFeatures`, et `sqlDatabase` correspond à sa feature `sql`, réalisée par la configuration `sql.c3p0` ; le contrôle lui-même est fait par le plugin `sqlConnectionProviderPlugin`)*

### Ce qu'il faut retenir

- La configuration du node est un fichier YAML, désigné par `web.xml` et lu au démarrage.
- Un module est déclaré par une classe `Features` ; ses features et leur configuration enregistrent des composants (managers, plugins).
- Les flags activent ou non des parties de la configuration ; les initializers s'exécutent une fois le node démarré.

# [Suite : Level Socle 1.1 - Paramètres, variables d'environnement et flags](./LevelSocle1.1.md)

### Optionnel : Le montage des webservices

Les webservices REST passent par la webapp : dans le YAML, le module `JavalinFeatures` (`standalone`) et la configuration `webservices.javalin` de `VegaFeatures` ; dans `web.xml`, le filtre `VegaJavalinFilter`, qui transmet aux webservices les adresses `/api/*`. Ce montage se prépare une fois pour toutes dans un projet : il est détaillé dans le [Parcours Autonomie](../docs/parcours-autonomie.md).

---
*Note : `/healthcheck/complete` peut afficher en rouge le contrôle `poolUtilization` des daemons, et le garder en rouge. C'est un défaut connu de la plateforme, sans lien avec l'exercice.*
