# Level Socle 1 - Le démarrage et la configuration

Nous allons suivre le démarrage de l'application, jusqu'au fichier qui la configure.
Dans un vrai projet, le node Vertigo n'est pas construit en Java, comme dans le parcours Démarrage : sa configuration est déclarée dans un fichier YAML, lu au démarrage.

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
  - `featuresConfig` : la façon de les réaliser, avec ses paramètres, par exemple `sql.c3p0` et l'URL de la base. Une fonctionnalité est exposée par un **manager** ; le **plugin** choisi ici fait le travail derrière lui.
- `__flags__` : un élément marqué d'un flag n'est pris en compte que si ce flag est actif. Les flags actifs se donnent par le paramètre `boot.activeFlags`, dans `web.xml`.
- `initializers` : du code exécuté une fois le node démarré, dans l'ordre de la liste. Le premier, `DataBaseInitializer`, crée la base de données (le schéma, puis les données de démonstration).

Chaque feature enregistre des **composants** dans le node : ce sont eux que les autres composants reçoivent par injection.

## Etapes

1. Dans `web.xml`, retrouvez le listener et le paramètre `boot.applicationConfiguration` : quel fichier désigne-t-il ?
1. Dans `sample-vui.yaml`, retrouvez la configuration de la base de données.
**Question** : quelle base est utilisée, et pourquoi n'y a-t-il rien à installer ? *(lisez le paramètre `jdbcUrl`)*
1. Retrouvez le module `ElasticSearchFeatures`.
**Question** : est-il actif au démarrage de cet exercice ? *(cherchez `boot.activeFlags` dans `web.xml`)*
1. Retrouvez ce qui sert les webservices : le module `JavalinFeatures` (`standalone`), et dans `VegaFeatures` les configurations `webservices.javalin` (préfixe `/api`) et `webservices.healthcheck`. Dans `web.xml`, le filtre `VegaJavalinFilter` fait passer les adresses `/api/*` aux webservices. Ce montage est déjà prêt : vous y ajouterez votre propre webservice au Level Socle 6.
1. Démarrez l'application *(voir le [Level Socle 0](./LevelSocle0.md))* et appelez [http://localhost:18080/sample/api/healthcheck/ping](http://localhost:18080/sample/api/healthcheck/ping) : vous devez obtenir `OK`. C'est un webservice standard de la plateforme, apporté par la feature `webservices.healthcheck`.
1. Appelez [http://localhost:18080/sample/api/healthcheck/complete](http://localhost:18080/sample/api/healthcheck/complete) : la liste des contrôles de santé de l'application.
**Question** : quel contrôle vérifie la base de données, quel composant le réalise, et dans quel état est-il ? *(le contrôle `poolUtilization` des daemons peut apparaître en rouge juste après le démarrage : il n'est pas lié à l'exercice)*
1. Arrêtez l'application, retirez la ligne `- webservices.healthcheck:` du fichier YAML, puis relancez : l'adresse du ping répond maintenant **404**. Sans la feature, le composant du healthcheck n'est plus enregistré : le webservice n'existe plus.
1. Remettez la ligne.

### Ce qu'il faut retenir

- La configuration du node est un fichier YAML, désigné par `web.xml` et lu au démarrage.
- Un module est déclaré par une classe `Features` ; ses features et leur configuration enregistrent des composants (managers, plugins).
- Les flags activent ou non des parties de la configuration ; les initializers s'exécutent une fois le node démarré.

# [Suite : Level Socle 2 - Régénérer le code](./LevelSocle2.md)
