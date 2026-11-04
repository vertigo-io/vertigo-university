# Level 4 - DAO proxy

Nous allons déclarer une requête directement sur une interface Java annotée.
La plateforme en fournit l'implémentation au démarrage : pas besoin de passer par Studio.

## Eléments

- Classe main : `/src/main/java/io/vertigo/samples/crystal/run/Level4`
- Interface : `/src/main/java/io/vertigo/samples/crystal/dao/MovieProxyDAO` *(à créer)*
- Configuration : `/src/main/java/io/vertigo/samples/crystal/config/SampleConfigBuilder`
- Smart types : `/src/main/java/io/vertigo/samples/crystal/config/SampleCrystalSmartTypes`

### A connaitre : Les proxies

Un *amplifier* est un composant déclaré sous la forme d'une **interface** (qui étend `Amplifier`) : la plateforme crée son implémentation au démarrage, d'après les annotations portées par ses méthodes.
Pour une tâche, on utilise :
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

Par convention, on retrouve les règles de nommage des tâches générées : le nom de la tâche commence par `Tk`, les paramètres sont en *lowerCamelCase* (`couId`) et les smart types se nomment `STy` suivi du nom de la constante de `SampleCrystalSmartTypes` (`Id` → `STyId`).

**Attention** : l'annotation d'un proxy est `@TaskProxyAnnotation`. Le code généré utilise une autre annotation, `@TaskAnnotation` : ne la recopiez pas depuis `CrystalPAO`.

### A connaitre : L'activation

Il faut deux déclarations dans la configuration du node :
- `withTaskProxyMethod()` sur les `DataStoreFeatures`, qui permet de créer les méthodes de tâche des proxies ;
- un module qui déclare l'amplifier : `ModuleConfig.builder("proxies").addAmplifier(MovieProxyDAO.class).build()`.

## Etapes

1. Dans `SampleCrystalSmartTypes`, ajoutez un smart type `Count` : un `Integer`, avec le formatter par défaut et le `storeType` `NUMERIC` *(sur le modèle de `Year`)*.
1. Créez l'interface `MovieProxyDAO` *(code ci-dessus)*. Le dossier `dao` est à créer sous `src/main/java` : le package existe déjà, mais seulement dans `src/main/javagen`, où l'on n'écrit jamais à la main.
1. Dans `SampleConfigBuilder`, ajoutez `.withTaskProxyMethod()` sur les `DataStoreFeatures`.
1. Toujours dans `SampleConfigBuilder`, après le module `ressources` et en dehors des `if`, déclarez le module `proxies` :
`nodeConfigBuilder.addModule(ModuleConfig.builder("proxies").addAmplifier(MovieProxyDAO.class).build());`
1. Créez la classe `Level4` en copiant `CrystalSample`, et injectez-y le `MovieProxyDAO`.
1. Dans un `step1`, lisez le pays de Forrest Gump *(`getMovieById(FORREST_GUMP_ID).getCouId()`)* et affichez le nombre de films de ce pays avec `movieProxyDAO.count`.
1. Lancez `Level4` : vous devez obtenir `films in the same country as Forrest Gump : 4`.

**Attention** : si `withTaskProxyMethod()` est oublié, le démarrage échoue avec `No way to find a proxyMethod for this method : ... MovieProxyDAO.count(java.lang.Long)`.
Les erreurs d'écriture du proxy, elles, n'apparaissent qu'au premier appel de `count()`, tout en bas de la console : par exemple `the name of the descriptor COU_ID must be in lowerCamelCase` pour un paramètre `@TaskInput(name = "COU_ID")`, ou `Definition 'DO_INTEGER' of type 'SmartTypeDefinition' not found` pour un smart type inconnu.

# [Suite : Level 5 - Web services REST et recherche à facettes](./Level5.md)

### Optionnel : Proxy ou tâche générée ?

Comparez avec le Level 1 : la même requête aurait pu être déclarée dans `dao.ksp` et générée par Studio. Le proxy évite la génération, mais la requête n'apparaît plus dans les définitions du modèle.
