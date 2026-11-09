# Level Socle 8 - Tester une fonction métier

Nous allons écrire les tests d'une fonction métier : les films d'un pays, du Level Socle 6, et l'erreur prévue pour un pays sans film, du Level Socle 7. Puis un test qui modifie des données, sans rien laisser derrière lui.
Le test démarre l'application, avec sa base et ses données de démonstration, et appelle le service comme l'application l'appelle.

À la fin de ce Level, vous saurez :
- dire ce que vérifie un test d'intégration, et pourquoi on le préfère pour une fonction métier ;
- écrire un test qui reçoit un service par injection et vérifie un résultat métier, y compris une erreur prévue ;
- écrire un test qui modifie des données dans une transaction annulée à la fin ;
- lancer les tests et lire leur résultat.

## Eléments

- Les classes de test fournies, dans `/src/test/java/io/vertigo/samples/support` : `SampleVuiTestExtension`, `AbstractSampleVuiTestCase` et `AbstractSampleVuiRollbackTestCase`
- Service testé : `/src/main/java/io/vertigo/samples/vui/services/MovieServices.java` (Levels Socle 6 et 7)
- Tests à créer : `MovieServicesTest` et `MovieServicesRollbackTest`, dans `/src/test/java/io/vertigo/samples/vui/services`
- Concepts illustrés : Component, `@Transactional`, `VUserException` *(voir le glossaire [vertigo-docs : Concepts](https://vertigo-io.github.io/vertigo-docs/#/basic/concepts))*
- Documentation : [JUnit 5 : User Guide](https://junit.org/junit5/docs/current/user-guide/) *(les sections « Writing Tests » et « Assertions »)*

### A connaitre : Pourquoi des tests d'intégration ?

Ce qui a de la valeur, et qui reste stable quand le code évolue, c'est la fonction métier : « les films d'un pays », « une erreur pour l'utilisateur quand le pays n'a pas de film ». Un **test d'intégration** l'appelle comme l'application l'appelle : avec la vraie configuration, la vraie base et ses données.

Il ne dépend pas de la façon dont le service est écrit : on peut remanier le service (une tâche plutôt qu'une méthode du DAO, une autre requête…), le test reste valable tant que la fonction rend le même service. C'est lui qui dit, à chaque modification, que l'application fait toujours ce qu'on attend d'elle.

### A connaitre : Les classes de test fournies

Un projet fournit à ses tests un démarrage commun et des classes parentes :
- `SampleVuiTestExtension`, une **extension JUnit 5**, démarre toute l'application, une seule fois pour tous les tests : le même serveur et la même configuration que `BootSampleVui`. Elle ajoute `noJoin()` : sans lui, le démarrage du serveur attendrait son arrêt, et les tests ne s'exécuteraient jamais. Puis elle injecte les composants dans chaque classe de test : un champ annoté `@Inject` reçoit le composant, comme dans un service ;
- `AbstractSampleVuiTestCase` est la **classe parente** de tous les tests : elle porte l'extension (`@ExtendWith`), et les aides communes aux tests (ici, vider le cache). Un test en hérite, et n'a rien d'autre à déclarer ;
- `AbstractSampleVuiRollbackTestCase` en hérite, pour les tests qui **modifient des données** (voir plus bas).

Dans une équipe, la classe parente porte aussi les autres aides communes, par exemple ouvrir une session avec des droits. La base H2 en mémoire est créée avec les données de démonstration (Level Socle 0) : les résultats attendus sont connus d'avance.

### A connaitre : Les tests qui modifient des données

Les tests partagent la même base. Un test qui enregistre ou supprime des données ne doit rien laisser derrière lui : sinon, le résultat d'un autre test dépendrait de l'ordre dans lequel ils s'exécutent.

`AbstractSampleVuiRollbackTestCase` ouvre donc une **transaction** avant chaque test, et l'**annule** (`rollback`) à la fin. Le service appelé par le test la réutilise (`@Transactional`, Level Socle 7) au lieu d'en ouvrir une : il ne valide rien lui-même. Le test voit ses propres modifications, puis l'annulation remet la base dans son état de départ.

### A connaitre : JUnit 5 en bref

- `@Test` marque une méthode de test ;
- `Assertions.assertEquals(attendu, obtenu)` vérifie une valeur ;
- `Assertions.assertThrows(VUserException.class, () -> …)` vérifie que l'appel lève cette exception, et la renvoie : on peut alors vérifier son message.

JUnit 5 est déjà fourni par le projet parent : il n'y a rien à ajouter au `pom.xml`.

## Etapes

1. Ouvrez les trois classes fournies, puis `BootSampleVui`.
**Question** : quelle méthode de `BootSampleVui` l'extension appelle-t-elle pour démarrer l'application, et qu'y ajoute-t-elle ?
1. Dans `src/test/java`, créez la classe `io.vertigo.samples.vui.services.MovieServicesTest`, qui hérite de `AbstractSampleVuiTestCase` et reçoit `MovieServices` par injection :
   ```Java
   public class MovieServicesTest extends AbstractSampleVuiTestCase {

   	@Inject
   	private MovieServices movieServices;
   }
   ```
   *(`Inject` est dans le package `javax.inject` ; `AbstractSampleVuiTestCase` dans `io.vertigo.samples.support`)*
1. Ajoutez un premier test, `testMoviesOfCountry` : les films de l'Allemagne (`couId` 250). Vérifiez qu'il y en a un seul, et que c'est `The Grand Budapest Hotel`.
*(`movieServices.getMoviesDisplayByCountry(250L)` renvoie une `DtList<MovieDisplay>` ; puis `movies.size()` et `movies.get(0).getName()` ; `Test` et `Assertions` sont dans le package `org.junit.jupiter.api`, `DtList` dans `io.vertigo.datamodel.data.model`, `MovieDisplay` dans `io.vertigo.samples.vui.domain`)*
1. Ajoutez un second test, `testCountryWithoutMovie` : pour le Japon (`couId` 392), vérifiez que le service lève une `VUserException` dont le message est `Aucun film pour ce pays`.
*(`assertThrows` renvoie l'exception : son message est `exception.getMessage()` ; `VUserException` est dans le package `io.vertigo.core.lang`)*
1. Arrêtez l'application si elle est démarrée, puis lancez les tests : depuis l'IDE, *Run As > JUnit Test* sur la classe ; en ligne de commande, `mvn test` à la racine du module. Les deux tests passent : la vue JUnit de l'IDE est verte, et Maven affiche, sous `Results:`, la ligne de total `Tests run: 2, Failures: 0, Errors: 0, Skipped: 0` *(plus haut, une ligne semblable par classe de test se termine par `-- in` suivi du nom de la classe)*.
*(si vous obtenez `Failed to bind to 0.0.0.0/0.0.0.0:18080`, puis `The forked VM terminated without properly saying goodbye` et `BUILD FAILURE` : les tests n'ont pas pu démarrer, car l'application est encore démarrée, et son port est occupé ; arrêtez-la, puis relancez les tests)*
**Attention** : en ligne de commande, un test en échec ne fait pas échouer la commande. Le projet parent est configuré pour continuer (`testFailureIgnore`) : Maven affiche `There are test failures.`, puis termine quand même par `BUILD SUCCESS`. Lisez toujours la ligne `Tests run: …, Failures: …`.
1. **Observez** ce que montre un test qui échoue : dans `testMoviesOfCountry`, attendez `2` films au lieu de `1`, et relancez les tests. Vous devez obtenir `Tests run: 2, Failures: 1`, avec le test en cause et le message `expected: <2> but was: <1>`. Remettez ensuite la valeur `1`.
**Question** : un collègue modifie la tâche `TkGetMoviesDisplayByCountry`, régénère le code, et le filtre par pays ne fonctionne plus : la tâche renvoie tous les films. Lesquels de vos tests le signalent, et que dit chacun ?
1. Créez la classe `io.vertigo.samples.vui.services.MovieServicesRollbackTest`, qui hérite de `AbstractSampleVuiRollbackTestCase` et reçoit aussi `MovieServices`. Ajoutez-lui un test, `testSaveMovieOfCountry` : enregistrez un film japonais, `Le Voyage de Chihiro`, de 2001, puis vérifiez que le Japon a maintenant un film, et que c'est celui-là.
*(créez un `Movie` (package `io.vertigo.samples.vui.domain`) avec `setName`, `setYear` et `setCouId(392L)`, enregistrez-le par `movieServices.save(movie)`, puis relisez les films du Japon comme dans le premier test)*
1. Lancez tous les tests : vous devez obtenir `Tests run: 3, Failures: 0, Errors: 0, Skipped: 0`.
**Question** : le nouveau test ajoute un film au Japon. Pourquoi `testCountryWithoutMovie` reste-t-il vert, même s'il s'exécute après lui ? Et que se passerait-il si `MovieServicesRollbackTest` héritait de `AbstractSampleVuiTestCase` ? *(pour vérifier par l'expérience, puis remettre l'héritage d'origine : `mvn test "-Dsurefire.runOrder=alphabetical"` exécute `MovieServicesRollbackTest` avant `MovieServicesTest`)*

### Ce qu'il faut retenir

- Un test d'intégration appelle la fonction métier comme l'application, avec la vraie configuration et les vraies données : il reste valable quand le code du service change.
- Les tests héritent d'une classe parente fournie par le projet, qui démarre l'application une seule fois et injecte les composants (`@Inject`).
- Un test vérifie un résultat métier précis, y compris les erreurs prévues (`assertThrows`).
- Un test qui modifie des données s'exécute dans une transaction annulée à la fin : la base reste la même pour tous les tests.
- En ligne de commande, `BUILD SUCCESS` ne suffit pas : lisez `Tests run: …, Failures: …`.

# Fin de la série Socle

Suite : la série UI, à partir du [Level Ui 1 - Liste simple](./LevelUi1.md).

### Optionnel : Les tests des tâches

Les tests des tâches peuvent être générés par Studio : avec la propriété `vertigo.taskTest: true` dans `studio-config.yaml`, chaque tâche reçoit un test qui l'exécute avec des paramètres fictifs, et vérifie ainsi que sa requête SQL fonctionne. L'équipe fournit la classe parente de ces tests (propriété `vertigo.taskTest.baseTestClass`), en général une classe qui annule elle aussi sa transaction, et leur dossier (`vertigo.taskTest.targetSubDir`).
