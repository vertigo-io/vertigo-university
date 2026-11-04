# Level 4 - DAO proxy

Nous allons déclarer une requête directement sur une interface Java annotée : la plateforme en fournit l'implémentation au démarrage, sans passer par la génération de code.

## Eléments

- Interface à créer : `src/main/java/io/vertigo/samples/crystal/dao/MovieProxyDAO.java`
- Configuration : `src/main/java/io/vertigo/samples/crystal/config/SampleConfigBuilder.java`
- Smart types : `src/main/java/io/vertigo/samples/crystal/config/SampleCrystalSmartTypes.java` (nouveau smart type `Count`)
- Classe main à créer : `src/main/java/io/vertigo/samples/crystal/run/Level4.java`

### A connaitre : Les proxies (amplifiers)

Un *amplifier* est un composant déclaré sous la forme d'une **interface** (qui étend `Amplifier`) : la plateforme crée son implémentation au démarrage, d'après les annotations portées par les méthodes. Pour les tâches :

- `@TaskProxyAnnotation(name, request, taskEngineClass)` : le nom de la tâche, la requête et le moteur d'exécution ;
- `@TaskOutput(smartType)` : le type du résultat ;
- `@TaskInput(name, smartType)` sur chaque paramètre : son nom dans la requête (`#couId#`) et son type.

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

**Attention** : l'annotation d'un proxy est `@TaskProxyAnnotation`. Le code généré (`CrystalPAO`, les DAO) utilise une autre annotation, `@TaskAnnotation` : ne la recopiez pas depuis le code généré.

Les conventions de nommage :

- le nom de la tâche commence par `Tk`, comme les tâches générées (`TkMovieCountByCountry`) ;
- les noms des paramètres (`@TaskInput(name = "couId")`, repris dans la requête par `#couId#`) sont en *lowerCamelCase* : c'est vérifié au premier appel de la méthode ;
- les smart types se nomment `STy` suivi du nom de la constante de `SampleCrystalSmartTypes` (`Id` → `STyId`).

### A connaitre : L'activation

Deux déclarations dans la configuration du node :

- dans `DataStoreFeatures`, `withTaskProxyMethod()` : active la création des méthodes de tâche des proxies ;
- un module qui déclare l'amplifier : `ModuleConfig.builder("proxies").addAmplifier(MovieProxyDAO.class).build()`.

## Etapes

1. Dans `SampleCrystalSmartTypes`, ajoutez le smart type `Count` : un `Integer` (`@SmartTypeDefinition(Integer.class)`), avec le formatter par défaut et `storeType` `NUMERIC`, sur le modèle de `Year`.
2. Créez l'interface `MovieProxyDAO` (code complet ci-dessus) : `src/main/java/io/vertigo/samples/crystal/dao/MovieProxyDAO.java`. Le dossier `dao` est à créer sous `src/main/java` : le package existe déjà, mais seulement dans `src/main/javagen` (les DAO générés), où l'on n'écrit jamais à la main.
3. Dans `SampleConfigBuilder` :
   - ajoutez `.withTaskProxyMethod()` à `DataStoreFeatures` ;
   - après le module `ressources` (et en dehors des blocs `if`), ajoutez le module `proxies` :
     `nodeConfigBuilder.addModule(ModuleConfig.builder("proxies").addAmplifier(MovieProxyDAO.class).build());`
4. Créez `Level4` en copiant `CrystalSample` (avec `ActorDAO` dans `stepDao`) :
   - injectez `MovieProxyDAO` (`@Inject private MovieProxyDAO movieProxyDAO;`) ;
   - `step1()` : lisez le pays de Forrest Gump (`movieServices.getMovieById(FORREST_GUMP_ID).getCouId()`), puis affichez `"films in the same country as Forrest Gump : " + movieProxyDAO.count(countryId)`.
5. Lancez `Level4`.
   **Vérification** : sur la ligne du logger `Level4`, la console affiche `films in the same country as Forrest Gump : 4`.

**Les erreurs que vous pouvez rencontrer** :

- **au démarrage** (le programme ne s'arrête pas de lui-même : arrêtez-le à la main) :
  - `withTaskProxyMethod()` oublié : `No way to find a proxyMethod for this method : public abstract int io.vertigo.samples.crystal.dao.MovieProxyDAO.count(java.lang.Long)` ;
  - l'amplifier déclaré deux fois (dans `SampleConfigBuilder` et dans `Level4`) : `component 'movieProxyDAO' already registered` ;
- **au premier appel de `count()`** (l'exception s'affiche tout en bas de la console, après les traces d'arrêt, et le programme s'arrête) :
  - un nom de paramètre en majuscules dans `@TaskInput(name = "COU_ID")` : `the name of the descriptor COU_ID must be in lowerCamelCase` ;
  - un smart type inconnu (par exemple `DO_INTEGER`) : `Definition 'DO_INTEGER' of type 'SmartTypeDefinition' not found` ;
  - un paramètre de la requête (`#COU_ID#`) qui ne correspond à aucun `@TaskInput` : `no data found for param COU_ID in sql ...`.

## Optionnel : pour aller plus loin

- Comparez avec le Level 1 : la même requête aurait pu être déclarée dans `dao.ksp` et générée par `Studio`. Le proxy évite la génération, mais la requête n'apparaît plus dans les définitions du modèle.

# [Suite : Level 5 - Web services REST et recherche à facettes](./Level5.md)
