# Level Socle 7 - La transaction et les erreurs

Nous allons voir deux mécanismes que la plateforme gère pour vous : la transaction, et le traitement des erreurs.
Il ne s'agit pas de les programmer, mais de savoir où ils agissent, pour écrire du code qui s'y inscrit.

À la fin de ce Level, vous saurez :
- dire où se trouve la transaction, et ce qu'elle couvre ;
- distinguer les deux familles d'erreurs, et la réponse que chacune produit ;
- lever une erreur destinée à l'utilisateur.

## Eléments

- Service : `/src/main/java/io/vertigo/samples/vui/services/MovieServices.java`
- Webservice : `/src/main/java/io/vertigo/samples/vui/webservices/MovieWebServices.java` (Level Socle 6)
- Concepts illustrés : `@Transactional`, `VUserException`, `VSystemException`, `WrappedException` *(voir le glossaire [vertigo-docs : Concepts](https://vertigo-io.github.io/vertigo-docs/#/basic/concepts))*
- Documentation : [vertigo-docs : Composants](https://vertigo-io.github.io/vertigo-docs/#/basic/composants) *(un exemple de service et de sa transaction)*

### A connaitre : La transaction

`@Transactional` est posée sur la classe `MovieServices` : chaque appel d'une méthode du service s'exécute dans une transaction. Elle commence à l'entrée de la méthode, elle est validée à la sortie, et elle est annulée si une exception sort de la méthode. Si une transaction est déjà en cours, elle est réutilisée.

Les DAO et les tâches ont besoin d'une transaction en cours. C'est pourquoi les controllers et les webservices passent toujours par un service : c'est lui qui porte la transaction. Vous n'avez pas à la manipuler : il suffit de savoir qu'elle est sur les services.

*(si un appel répond **500** avec `"globalErrors": ["current transaction not found"]` : un DAO ou une tâche a été appelé directement, sans passer par un service ; faites l'appel depuis une méthode du service)*

### A connaitre : Les erreurs

Dans une application Vertigo, on n'intercepte pas les exceptions : la plateforme les intercepte aux bons endroits, et produit la bonne réponse. Les erreurs se rangent en deux familles :
- **les erreurs utilisateur** : `VUserException`. Une règle métier n'est pas respectée, et son message est destiné à l'utilisateur. Un webservice répond alors **422**, avec le message dans la pile de messages (`globalErrors`) ; un écran l'affiche à l'utilisateur.
- **les erreurs système** : tout le reste, que l'utilisateur ne peut pas corriger. Ce sont les `VSystemException`, les `WrappedException`, qui enveloppent une erreur technique (comme une `SQLException` dans `DataBaseInitializer`), et les erreurs des vérifications `Assertion.check()`, qui lèvent des exceptions Java standard : `NullPointerException` pour `isNotNull`, `IllegalStateException` pour `isTrue`, `IllegalArgumentException` pour `isNotBlank`. Un webservice répond alors **500**, et le serveur trace l'erreur complète pour le développeur.

Une assertion vérifie le contrat du code : si elle échoue, c'est une erreur de programmation. Une `VUserException` signale une situation métier prévue.

## Etapes

1. Dans `MovieServices`, retrouvez `@Transactional`.
**Question** : les deux méthodes ajoutées au Level Socle 6 s'exécutent-elles dans une transaction ?
1. Complétez `getMoviesDisplayByCountry` :
   - ajoutez à la vérification du paramètre une assertion : l'identifiant doit être positif *(`Assertion.check().isNotNull(couId).isTrue(couId > 0, "the country id must be positive ({0})", couId);`)* ;
   - si la liste de films est vide, levez une erreur utilisateur *(`throw new VUserException("Aucun film pour ce pays");`)*.
1. Relancez l'application et appelez [http://localhost:18080/sample/api/movies/display/392](http://localhost:18080/sample/api/movies/display/392) (le Japon, sans film) : la réponse est **422**, avec `"globalErrors": ["Aucun film pour ce pays"]`.
1. Appelez [http://localhost:18080/sample/api/movies/display/250](http://localhost:18080/sample/api/movies/display/250) : la réponse est toujours le film allemand. L'assertion ne change rien pour un appel valide : elle protège le service contre un appel incorrect.
*(si un appel répond **500** avec `"globalErrors": ["the country id must be positive (-1)"]` : l'identifiant passé est négatif, et l'assertion a refusé l'appel ; c'est une erreur système, tracée dans la console par `ERROR ExceptionWebServiceHandlerPlugin - Internal Server Error`, suivi de `java.lang.IllegalStateException` et de la pile d'appels)*
1. **Question** : un appel pour un pays sans film, un appel avec un identifiant négatif. Lequel relève d'une erreur utilisateur, lequel d'une erreur système ? Lequel l'utilisateur peut-il corriger lui-même ?

### Ce qu'il faut retenir

- La transaction est portée par les services (`@Transactional`) : les controllers et les webservices passent par eux.
- On n'intercepte pas les exceptions : la plateforme le fait.
- `VUserException` est une erreur pour l'utilisateur (422 et son message) ; tout le reste est une erreur système (500, tracée pour le développeur), dont les erreurs d'assertion.

# Fin de la série Socle

Suite : la série UI, à partir du [Level Ui 1 - Liste simple](./LevelUi1.md).
