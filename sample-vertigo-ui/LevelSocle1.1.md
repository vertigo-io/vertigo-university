# Level Socle 1.1 - Paramètres, variables d'environnement et flags

Nous allons adapter l'application sans toucher à son code : changer un paramètre de l'application, le faire venir d'une variable d'environnement, puis activer une partie de la configuration par un flag.
C'est ainsi qu'une même application s'adapte à chaque environnement (poste de développement, recette, production). Ce Level ne change pas l'application pour la suite : à la fin, elle se comporte comme au départ.

À la fin de ce Level, vous saurez :
- retrouver d'où vient la valeur d'un paramètre de l'application ;
- remplacer cette valeur par une variable d'environnement ;
- activer un flag au démarrage, et constater ce qu'il ajoute.

## Eléments

- Configuration : `/src/main/resources/webapp/WEB-INF/web.xml` et `/src/main/resources/webapp/META-INF/sample-vui.yaml`
- Page d'accueil : `/src/main/java/io/vertigo/samples/vui/controllers/HomeController.java` et `/src/main/resources/webapp/WEB-INF/views/vui/home.html`
- Adresses : [http://localhost:18080/sample/home/](http://localhost:18080/sample/home/) et [http://localhost:18080/sample/api/swaggerUi](http://localhost:18080/sample/api/swaggerUi)
- Concepts illustrés : Manager, Plugin *(voir le glossaire [vertigo-docs : Concepts](https://vertigo-io.github.io/vertigo-docs/#/basic/concepts))*
- Documentation : [vertigo-docs : Configuration](https://vertigo-io.github.io/vertigo-docs/#/basic/configuration) *(les sections « Configuration YAML » et « Flags »)*

### A connaitre : Les paramètres de l'application

Une application a ses propres paramètres : l'adresse d'un service externe, un répertoire de travail, un libellé… Elle les lit par le **ParamManager**, un manager que la plateforme fournit toujours : `paramManager.getParam("nom")`.

Le ParamManager interroge, dans l'ordre, les **plugins de paramètres** déclarés dans `boot.plugins` ; la première valeur trouvée l'emporte :
- `WebAppContextParamPlugin` lit les paramètres du `web.xml` (les `context-param`) ;
- `EnvParamPlugin` lit les variables d'environnement.

Pourquoi deux sources ? L'application livrée est la même dans tous les environnements : la valeur par défaut est écrite dans le `web.xml`, et un environnement qui a besoin d'une autre valeur la fournit par une variable d'environnement, sans modifier l'application. [Mars](https://github.com/vertigo-io/vertigo-mars), l'application de démonstration, lit ainsi l'adresse de son assistant conversationnel (le paramètre `chatbotUrl`).

### A connaitre : Les flags

Un élément du YAML marqué d'un flag (`__flags__: ["nom"]`) n'est pris en compte que si ce flag est actif (Level Socle 1). Les flags actifs se donnent au démarrage : par le paramètre `boot.activeFlags` du `web.xml`, ou par la variable d'environnement `VERTIGO_BOOT_ACTIVE_FLAGS` (plusieurs flags se séparent par `;`). Un flag permet ainsi d'activer, dans certains environnements seulement, une partie de la configuration : par exemple des outils réservés au développement, qui ne doivent pas être exposés en production.

### A connaitre : Lancer avec une variable d'environnement

- **Depuis l'IDE** (Eclipse) : *Run As > Run Configurations…*, la configuration de `BootSampleVui`, onglet *Environment* : ajoutez la variable, puis *Run*.
- **En ligne de commande** (Git Bash, voir le [README](./README.md#lancer-une-classe-main)) : placez la variable devant la commande ; elle ne vaut que pour ce lancement.

Dans l'IDE, la variable reste enregistrée dans la configuration de lancement : pour relancer sans elle, retirez-la de l'onglet *Environment*.
  ```
  environmentLabel=Recette java @java.args io.vertigo.samples.BootSampleVui
  ```

Le `web.xml` et le YAML sont lus depuis `target/classes` : après chaque modification, recompilez (`mvn -q compile`) avant de relancer. Avant chaque relance, arrêtez l'application (Ctrl+C, ou le bouton *Terminate* de l'IDE) : sinon le port 18080 est encore occupé.

## Etapes

1. Démarrez l'application et ouvrez [http://localhost:18080/sample/home/](http://localhost:18080/sample/home/) : sous le message d'accueil, vous devez voir `Environnement : Poste de développement`.
1. Ouvrez `HomeController` et retrouvez la lecture du paramètre `environmentLabel`.
**Question** : d'où vient sa valeur, et quel plugin de `boot.plugins` la lit ? *(cherchez `environmentLabel` dans la configuration)*
1. Dans le `web.xml`, remplacez la valeur `Poste de développement` par `Poste de formation`. Recompilez, relancez, et rechargez la page d'accueil : vous devez voir `Environnement : Poste de formation`. Remettez ensuite la valeur d'origine, `Poste de développement`.
1. Dans `sample-vui.yaml`, ajoutez **en tête** de `boot.plugins` le plugin qui lit les variables d'environnement :
   ```yaml
     plugins:
       - io.vertigo.core.plugins.param.env.EnvParamPlugin: {}
       - io.vertigo.core.plugins.resource.classpath.ClassPathResourceResolverPlugin: {}
   ```
1. Recompilez, puis relancez avec la variable d'environnement `environmentLabel=Recette` : la page d'accueil doit afficher `Environnement : Recette`.
*(si la page affiche toujours `Poste de développement` : la variable n'est pas lue ; vérifiez que `EnvParamPlugin` est en tête de `boot.plugins`, que vous avez recompilé, et que la variable est définie pour ce lancement, avec ce nom exact)*
**Question** : qu'afficherait la page si `EnvParamPlugin` était déclaré après `WebAppContextParamPlugin` ? *(relisez « Les paramètres de l'application »)*
1. Relancez **sans** la variable : la page affiche de nouveau `Environnement : Poste de développement`. Aucune variable d'environnement ne fournit le paramètre : c'est la valeur du `web.xml` qui s'applique.
1. Dans `sample-vui.yaml`, sous `featuresConfig` de `VegaFeatures`, ajoutez après `webservices.healthcheck` Swagger, l'outil qui décrit et permet d'appeler les webservices, marqué du flag `devTools` :
   ```yaml
         - webservices.healthcheck:
         - webservices.swagger:
             __flags__: ["devTools"]
   ```
1. Recompilez, puis relancez avec la variable d'environnement `VERTIGO_BOOT_ACTIVE_FLAGS=devTools`, et ouvrez dans le navigateur [http://localhost:18080/sample/api/swaggerUi](http://localhost:18080/sample/api/swaggerUi) : vous devez voir la page *MySwaggerAPI Tester*, qui liste les webservices de l'application, regroupés par classe : ici ceux de la plateforme, `HealthcheckWebServices` et `SwaggerWebServices`. Dépliez `GET /healthcheck/ping`, cliquez sur *Try it out* puis *Execute* : la réponse est `OK`. Au Level Socle 6, Swagger listera aussi vos propres webservices, et vous pourrez les appeler de la même façon.
*(si l'adresse répond `Endpoint GET /api/swaggerUi not found` : le flag n'est pas actif ; vérifiez le nom de la variable et l'écriture exacte du flag, `devTools`, qui distingue majuscules et minuscules)*
**Question** : pourquoi réserver Swagger à un flag, plutôt que de l'activer dans tous les environnements ? *(voir [vertigo-docs : WebServices](https://vertigo-io.github.io/vertigo-docs/#/basic/webservices), section « Sécurisation du WebService »)*
1. Remettez l'application dans son état de départ : relancez-la **sans `environmentLabel` ni `VERTIGO_BOOT_ACTIVE_FLAGS`**. La page d'accueil affiche `Environnement : Poste de développement`, et [http://localhost:18080/sample/api/swaggerUi](http://localhost:18080/sample/api/swaggerUi) répond `Endpoint GET /api/swaggerUi not found` : sans le flag, Swagger n'existe pas.
Le YAML garde `EnvParamPlugin` et Swagger marqué du flag : sans variable, l'application se comporte exactement comme au départ.

### Ce qu'il faut retenir

- Une application lit ses paramètres par le ParamManager, qui interroge dans l'ordre les plugins de paramètres de `boot.plugins` : le premier qui trouve le paramètre l'emporte.
- La valeur par défaut est dans l'application (le `web.xml`) ; un environnement la remplace par une variable d'environnement, lue par `EnvParamPlugin`.
- Un flag active des parties de la configuration ; les flags actifs se donnent au démarrage, par `boot.activeFlags` dans le `web.xml` ou par la variable `VERTIGO_BOOT_ACTIVE_FLAGS`.

# [Suite : Level Socle 2 - Régénérer le code](./LevelSocle2.md)

### Optionnel : Une valeur venue de l'extérieur dans le YAML

Les paramètres des features et des plugins s'écrivent dans le YAML ; leur valeur peut aussi venir de l'extérieur. Une valeur `${NOM}` est cherchée au démarrage par les mêmes plugins de paramètres, et la forme `${NOM!valeur}` donne une valeur par défaut. Mars écrit ainsi l'URL de sa base : `jdbcUrl: ${DB_URL}`.

Pour l'essayer : dans la configuration `sql.c3p0` de `DatabaseFeatures`, ajoutez après `jdbcUrl` la taille maximale du pool de connexions à la base :
```yaml
          maxPoolSize: ${DB_MAX_POOL_SIZE!15}
```
Recompilez, puis relancez avec la variable `DB_MAX_POOL_SIZE=5` : au démarrage, la ligne de la console qui commence par `Initializing c3p0 pool...` (une longue ligne) contient `maxPoolSize -> 5` ; sans la variable, `maxPoolSize -> 15`. Vous pouvez garder cette ligne dans le YAML : sans la variable, le pool garde sa taille de départ.

*Les chemins de fichiers du YAML, comme `${java.io.tmpdir}/vertigo/VViewContext`, sont un cas à part : les plugins qui gèrent des fichiers y remplacent eux-mêmes `${java.io.tmpdir}`, `${user.home}` et `${user.dir}`.*
