# Level 4 - DAO proxy

Nous allons déclarer une requête directement sur une interface Java annotée.
La plateforme en fournit l'implémentation au démarrage : la requête n'est plus déclarée dans les définitions, et Studio n'intervient pas.

À la fin de ce Level, vous saurez :
- déclarer une tâche sur une interface Java, avec ses annotations ;
- activer les proxies de tâche dans la configuration du node ;
- comparer cette approche avec les tâches déclarées dans les définitions.

## Eléments

- Classe main : `/src/main/java/io/vertigo/samples/crystal/run/Level4`
- Interface : `/src/main/java/io/vertigo/samples/crystal/dao/MovieProxyDAO` *(à créer)*
- Configuration : `/src/main/java/io/vertigo/samples/crystal/config/SampleConfigBuilder`
- SmartTypes : `/src/main/java/io/vertigo/samples/crystal/config/SampleCrystalSmartTypes`
- Documentation : [vertigo-docs : DataModel](https://vertigo-io.github.io/vertigo-docs/#/extensions/datamodel) *(TaskManager, proxy annotations)*

### A connaitre : Les proxies de tâche

Au Level 1, une tâche était déclarée dans les définitions, puis Studio générait sa méthode Java. Un **proxy** prend le chemin inverse : on écrit l'interface Java, et la plateforme crée son implémentation au démarrage, d'après les annotations portées par ses méthodes.

Une telle interface étend `Amplifier` : c'est un composant dont l'implémentation est fournie par la plateforme. Pour une tâche, on utilise :
- `@TaskProxyAnnotation(name, request, taskEngineClass)` : le nom de la tâche, la requête et le moteur d'exécution ;
- `@TaskOutput(smartType)` : le type du résultat ;
- `@TaskInput(name, smartType)` sur chaque paramètre : son nom dans la requête et son type.

```java
package io.vertigo.samples.crystal.dao;

import io.vertigo.basics.task.TaskEngineSelect;
import io.vertigo.commons.transaction.Transactional;
import io.vertigo.core.node.component.Amplifier;
import io.vertigo.datamodel.task.proxy.TaskInput;
import io.vertigo.datamodel.task.proxy.TaskOutput;
import io.vertigo.datamodel.task.proxy.TaskProxyAnnotation;

@Transactional
public interface MovieProxyDAO extends Amplifier {

	@TaskProxyAnnotation(
			name = "TkMovieCountByCountry",
			request = "select count(*) from movie where cou_id = #couId#",
			taskEngineClass = TaskEngineSelect.class)
	@TaskOutput(smartType = "STyCount")
	int count(@TaskInput(name = "couId", smartType = "STyId") Long couId);

}
```

On retrouve les conventions des tâches générées : le nom commence par `Tk`, les paramètres sont en *lowerCamelCase*.
Les types sont ici des **SmartTypes**, désignés par leur nom : `STy` suivi du nom de la constante de `SampleCrystalSmartTypes` (`Id` → `STyId`). C'est le même nom que vous avez vu dans le code généré au Level 1 : le domain `DoId` des définitions y apparaît en `STyId`.

**Attention** : l'annotation d'un proxy est `@TaskProxyAnnotation`. Le code généré utilise une autre annotation, `@TaskAnnotation` : ne la recopiez pas depuis `CrystalPAO`.

### A connaitre : L'activation

Il faut deux déclarations dans la configuration du node :
- `withTaskProxyMethod()` sur les `DataStoreFeatures` : c'est cette Feature qui relie les annotations des proxies aux définitions de tâche réelles ;
- un module qui déclare l'amplifier : `ModuleConfig.builder("proxies").addAmplifier(MovieProxyDAO.class).build()`.

## Etapes

1. Dans `SampleCrystalSmartTypes`, ajoutez un SmartType `Count` : un `Integer`, avec le formatter par défaut et le `storeType` `NUMERIC` *(sur le modèle de `Year`)*.
1. Créez l'interface `MovieProxyDAO` *(code ci-dessus)*. Le dossier `dao` est à créer sous `src/main/java` : le package existe déjà, mais seulement dans `src/main/javagen`, où l'on n'écrit jamais à la main.
1. Dans `SampleConfigBuilder`, ajoutez `.withTaskProxyMethod()` sur les `DataStoreFeatures`.
1. Toujours dans `SampleConfigBuilder`, après le module `ressources` et en dehors des `if`, déclarez le module `proxies` :
`nodeConfigBuilder.addModule(ModuleConfig.builder("proxies").addAmplifier(MovieProxyDAO.class).build());`
1. Créez la classe `Level4` en copiant `CrystalSample`, et injectez-y le `MovieProxyDAO`.
1. Dans un `step1`, lisez le pays de Forrest Gump *(`getMovieById(FORREST_GUMP_ID).getCouId()`)* et affichez le nombre de films de ce pays avec `movieProxyDAO.count`.
1. Lancez `Level4` : vous devez obtenir `films in the same country as Forrest Gump : 4`.
1. **Question** : la même requête aurait pu être une tâche dans `dao.ksp`, comme au Level 1. Qu'est-ce qui change pour le reste de l'équipe ? *(où la requête est-elle visible ? qui la génère ? à quel moment une erreur dans son écriture est-elle détectée ?)*

**Attention** : si `withTaskProxyMethod()` est oublié, le démarrage échoue avec `No way to find a proxyMethod for this method : ... MovieProxyDAO.count(java.lang.Long)`.
Les erreurs d'écriture du proxy, elles, n'apparaissent qu'au premier appel de `count()`, tout en bas de la console : par exemple `the name of the descriptor COU_ID must be in lowerCamelCase` pour un paramètre `@TaskInput(name = "COU_ID")`, ou `Definition 'DO_INTEGER' of type 'SmartTypeDefinition' not found` pour un SmartType inconnu.

### Ce qu'il faut retenir

- Un proxy est une interface annotée dont la plateforme fournit l'implémentation : la requête vit dans le code Java, pas dans les définitions.
- Il faut l'activer (`withTaskProxyMethod()`) et le déclarer comme amplifier dans un module.
- Une tâche déclarée dans les définitions est lue par Studio dès la génération et reste visible dans le modèle ; les erreurs d'écriture d'un proxy n'apparaissent qu'à l'exécution, au premier appel.

# [Suite : Level 5 - Web services REST et recherche à facettes](./Level5.md)
