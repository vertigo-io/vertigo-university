# Level Socle 1.1 - Paramètres, variables d'environnement et flags

Nous allons adapter l'application sans toucher à son code : activer une partie de sa configuration par un flag, changer un paramètre, puis faire venir ce paramètre d'une variable d'environnement.
C'est ainsi qu'une même application s'adapte à chaque environnement (poste de développement, recette, production). Ce Level ne change pas l'application pour la suite : à la fin, elle se comporte comme au départ.

À la fin de ce Level, vous saurez :
- activer un flag au démarrage, et constater ce qu'il ajoute ;
- changer un paramètre de la configuration et en observer l'effet ;
- faire venir un paramètre d'une variable d'environnement, avec une valeur par défaut.

## Eléments

- Configuration : `/src/main/resources/webapp/META-INF/sample-vui.yaml`
- La recherche : le module `ElasticSearchFeatures`, la feature `search` de `DataFactoryFeatures` et son plugin `RestHLClientESSearchServicesPlugin`, la feature `search` de `VuiFeatures`
- Adresses : [http://localhost:9200/_cat/indices?v](http://localhost:9200/_cat/indices?v) (la liste des index Elasticsearch) et [http://localhost:18080/sample/api/healthcheck/complete](http://localhost:18080/sample/api/healthcheck/complete)
- Concepts illustrés : Module, Plugin *(voir le glossaire [vertigo-docs : Concepts](https://vertigo-io.github.io/vertigo-docs/#/basic/concepts))*
- Documentation : [vertigo-docs : Configuration](https://vertigo-io.github.io/vertigo-docs/#/basic/configuration) *(les sections « Configuration YAML » et « Flags »)*

### A connaitre : Les flags et les paramètres

La configuration reçoit des valeurs de l'extérieur de deux façons :
- **les paramètres de démarrage**, préfixés par `boot.`, servent à démarrer le node : quel fichier YAML lire (`boot.applicationConfiguration`, Level Socle 1), quels **flags** activer (`boot.activeFlags`). Ils se donnent dans le `web.xml` ; les flags peuvent aussi se donner par la variable d'environnement `VERTIGO_BOOT_ACTIVE_FLAGS` (plusieurs flags se séparent par `;`) ;
- **les paramètres des modules** (ceux des features et des plugins) : leur valeur est écrite dans le YAML. Une valeur `${NOM}` est cherchée au démarrage par les **plugins de paramètres** déclarés dans `boot.plugins` : `WebAppContextParamPlugin` lit les paramètres du `web.xml`, `EnvParamPlugin` lit les variables d'environnement. La forme `${NOM!valeur}` donne une valeur par défaut, utilisée quand aucun plugin ne trouve `NOM`.

*Les chemins de fichiers du YAML, comme `${java.io.tmpdir}/vertigo/VViewContext`, sont un cas à part : les plugins qui gèrent des fichiers y remplacent eux-mêmes `${java.io.tmpdir}`, `${user.home}` et `${user.dir}`.*

Pourquoi des valeurs venues de l'extérieur ? Le fichier YAML est le même dans tous les environnements : seul ce qui change d'un environnement à l'autre (l'adresse de la base, les serveurs, les préfixes) est fourni par l'environnement. Dans [Mars](https://github.com/vertigo-io/vertigo-mars), l'application de démonstration, l'URL de la base s'écrit ainsi `jdbcUrl: ${DB_URL}`.

### A connaitre : Le préfixe des index de recherche

Elasticsearch, le moteur de recherche, range les données dans des **index**. Le paramètre `envIndexPrefix` du plugin de recherche préfixe le nom de ces index : plusieurs environnements peuvent ainsi partager un même serveur Elasticsearch sans mélanger leurs données. C'est typiquement un paramètre qui change d'un environnement à l'autre.

Dans cet exercice, Elasticsearch démarre avec l'application, quand le flag `search` est actif, et il répond sur le port 9200. Ses index sont conservés d'un démarrage à l'autre, dans `target/classes/search`. Le nom d'un index est construit à partir du préfixe, mis en minuscules : `sampleVui_` donne `sample_vui_`.

### A connaitre : Lancer avec une variable d'environnement

- **Depuis l'IDE** (Eclipse) : *Run As > Run Configurations…*, la configuration de `BootSampleVui`, onglet *Environment* : ajoutez la variable, puis *Run*.
- **En ligne de commande** (Git Bash, voir le [README](./README.md#lancer-une-classe-main)) : placez la variable devant la commande ; elle ne vaut que pour ce lancement.
  ```
  VERTIGO_BOOT_ACTIVE_FLAGS=search java @java.args io.vertigo.samples.BootSampleVui
  ```

Le fichier YAML est lu depuis `target/classes` : après chaque modification, recompilez (`mvn -q compile`) avant de relancer. Avant chaque relance, arrêtez l'application (Ctrl+C, ou le bouton *Terminate* de l'IDE) : sinon le port 18080 est encore occupé.

## Etapes

1. Dans `sample-vui.yaml`, retrouvez les éléments marqués du flag `search` (`__flags__: ["search"]`).
**Question** : quels éléments ce flag active-t-il, et que démarre-t-il ?
1. Démarrez l'application avec la variable d'environnement `VERTIGO_BOOT_ACTIVE_FLAGS=search`, puis appelez [http://localhost:9200/_cat/indices?v](http://localhost:9200/_cat/indices?v) : vous devez voir l'index `sample_vui___idx_movie`. Elasticsearch a démarré avec l'application, et le plugin de recherche a créé l'index des films. L'index est vide (`docs.count` à 0) : son remplissage n'est pas l'objet de ce Level.
*(si l'adresse ne répond pas : le flag n'est pas actif ; vérifiez le nom de la variable, et qu'elle est bien définie pour le lancement de `BootSampleVui`)*
1. Appelez [http://localhost:18080/sample/api/healthcheck/complete](http://localhost:18080/sample/api/healthcheck/complete) : à côté des contrôles vus au Level Socle 1, vous devez y trouver le contrôle `clusterHealth`, réalisé par `searchServicesPlugin` pour la feature `search`, dans l'état `YELLOW` *(pour le contrôle `poolUtilization`, voir la note en fin de page)*.
*(`YELLOW` est l'état normal d'un Elasticsearch seul : chaque index prévoit une copie de secours, qui ne peut être placée que sur un second serveur)*
1. Dans la configuration du plugin de recherche, remplacez `envIndexPrefix: sampleVui_` par `envIndexPrefix: dev_`. Recompilez, relancez avec le flag, et appelez de nouveau la liste des index : vous devez y trouver `dev___idx_movie`, à côté de `sample_vui___idx_movie`, l'index du démarrage précédent.
**Question** : deux environnements, la recette et la formation, partagent le même serveur Elasticsearch avec le même fichier YAML. Que se passe-t-il s'ils gardent le même préfixe, et comment l'éviter sans livrer deux fichiers YAML différents ?
1. Dans `boot.plugins`, ajoutez le plugin qui lit les variables d'environnement, après `WebAppContextParamPlugin` :
   ```yaml
       - io.vertigo.core.plugins.param.env.EnvParamPlugin: {}
   ```
   Puis faites venir le préfixe de la variable `INDEX_PREFIX`, avec la valeur d'origine par défaut :
   ```yaml
             envIndexPrefix: ${INDEX_PREFIX!sampleVui_}
   ```
1. Recompilez, puis relancez avec deux variables d'environnement : `VERTIGO_BOOT_ACTIVE_FLAGS=search` et `INDEX_PREFIX=recette_` (en ligne de commande : `VERTIGO_BOOT_ACTIVE_FLAGS=search INDEX_PREFIX=recette_ java @java.args io.vertigo.samples.BootSampleVui`). La liste des index doit maintenant contenir `recette___idx_movie`.
*(si `recette___idx_movie` n'apparaît pas : la variable n'est pas lue ; vérifiez que `EnvParamPlugin` est dans `boot.plugins`, que vous avez recompilé, et que `INDEX_PREFIX` est définie pour ce lancement)*
*(si le démarrage échoue avec, dans la console, `No component found for envIndexPrefix of type class java.lang.String` : le paramètre n'a reçu aucune valeur ; vérifiez la valeur par défaut, après le `!`)*
**Question** : sans la variable `INDEX_PREFIX`, quel préfixe l'application utilise-t-elle ? *(l'index existe déjà dans la liste ; vérifiez dans la console la ligne `set index mapping of …`)*
1. Remettez l'application dans son état de départ : relancez-la **sans aucune variable d'environnement**. Le port 9200 ne répond plus : Elasticsearch n'a pas démarré. Et [http://localhost:18080/sample/api/healthcheck/complete](http://localhost:18080/sample/api/healthcheck/complete) ne contient plus `clusterHealth`.
Le YAML garde `EnvParamPlugin` et `${INDEX_PREFIX!sampleVui_}` : sans variable, l'application se comporte exactement comme au départ.

### Ce qu'il faut retenir

- Un flag active des parties de la configuration ; les flags actifs se donnent au démarrage, par `boot.activeFlags` dans le `web.xml` ou par la variable `VERTIGO_BOOT_ACTIVE_FLAGS`.
- Une valeur `${NOM}` est cherchée par les plugins de paramètres de `boot.plugins` ; `EnvParamPlugin` lit les variables d'environnement, et `${NOM!valeur}` donne une valeur par défaut.
- Le fichier YAML est le même partout : ce qui change d'un environnement à l'autre est fourni par l'environnement.

# [Suite : Level Socle 2 - Régénérer le code](./LevelSocle2.md)

### Optionnel : Les flags dans le `web.xml`

Un flag peut aussi être activé dans le `web.xml`, par le paramètre `boot.activeFlags` : c'est ce que fait le [Level Ui 3](./LevelUi3.md) pour la recherche. La variable `VERTIGO_BOOT_ACTIVE_FLAGS` remplace alors la valeur du `web.xml` : elle permet de changer les flags d'un environnement sans modifier l'application.

---
*Note : `/healthcheck/complete` peut afficher en rouge le contrôle `poolUtilization` des daemons, et le garder en rouge. C'est un défaut connu de la plateforme, sans lien avec l'exercice.*
