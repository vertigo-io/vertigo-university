# Level Socle 6 - Services, DAO et webservices

Nous allons ajouter une fonctionnalité de bout en bout : un service qui exécute la tâche du Level Socle 4, et un webservice REST qui publie son résultat.

À la fin de ce Level, vous saurez :
- lire un service : ce qu'il reçoit par injection, et comment il accède aux données ;
- ajouter une méthode de service sur une tâche ;
- publier un webservice REST qui appelle un service.

## Eléments

- Service : `/src/main/java/io/vertigo/samples/vui/services/MovieServices.java`
- Webservice à créer : `/src/main/java/io/vertigo/samples/vui/webservices/MovieWebServices.java`
- Module : `/src/main/java/io/vertigo/samples/vui/VuiFeatures.java`
- Routes : `GET /api/movies/display` et `GET /api/movies/display/{couId}`
- Documentation : [vertigo-docs : Accès aux données](https://vertigo-io.github.io/vertigo-docs/#/basic/dao) et [vertigo-docs : WebServices](https://vertigo-io.github.io/vertigo-docs/#/basic/webservices)

### A connaitre : Le service

`MovieServices` est un composant (`implements Component`), enregistré dans `VuiFeatures`. Il reçoit ce dont il a besoin par injection (`@Inject`) : les DAO, `VuiPAO`, des managers de la plateforme. Ses méthodes accèdent aux données par les DAO (`get`, `save`, `findAll` avec des critères et un `DtListState`) et par les tâches.

Une méthode de service commence par vérifier ses paramètres avec `Assertion.check()`, puis une ligne `//---` sépare ces vérifications du traitement.

*Ici, les services sont des classes. Dans d'autres projets, un service est une interface et son implémentation, comme dans `sample-dao` : le principe est le même.*

Les controllers et les webservices appellent les services, jamais directement les DAO : le Level Socle 7 explique pourquoi.

### A connaitre : Le webservice

Un webservice Vega est un composant qui implémente `WebServices`. `@PathPrefix` donne le début de ses routes, et chaque méthode annotée (`@GET`, `@POST`…) devient une route ; `@PathParam` lit une partie de l'adresse. Vega convertit en JSON l'objet retourné, ici une `DtList`.

Comme tout composant, il s'enregistre dans un module. Il est servi sous le préfixe `/api` préparé dans la configuration (Level Socle 1).

## Etapes

1. Lisez `MovieServices`.
**Question** : quels DAO reçoit-il par injection ? Quelle méthode exécute une tâche ?
1. Ajoutez à `MovieServices` deux méthodes, qui appellent les tâches de `VuiPAO` :
   - `DtList<MovieDisplay> getMoviesDisplay()` ;
   - `DtList<MovieDisplay> getMoviesDisplayByCountry(final Long couId)`, qui vérifie d'abord son paramètre *(`Assertion.check().isNotNull(couId);` puis `//---`)*.
1. Créez la classe `io.vertigo.samples.vui.webservices.MovieWebServices` :
```Java
@PathPrefix("/movies")
public class MovieWebServices implements WebServices {

	@Inject
	private MovieServices movieServices;

	@GET("/display")
	public DtList<MovieDisplay> getMoviesDisplay() {
		return movieServices.getMoviesDisplay();
	}

	@GET("/display/{couId}")
	public DtList<MovieDisplay> getMoviesDisplayByCountry(@PathParam("couId") final Long couId) {
		return movieServices.getMoviesDisplayByCountry(couId);
	}
}
```
*(les annotations sont dans le package `io.vertigo.vega.webservice.stereotype`, l'interface est `io.vertigo.vega.webservice.WebServices`)*
1. Enregistrez le webservice dans `VuiFeatures` : `.addComponent(MovieWebServices.class)`.
1. Relancez l'application et appelez :
   - [http://localhost:18080/sample/api/movies/display](http://localhost:18080/sample/api/movies/display) : les 6 films, triés par titre, chacun avec son pays (`{"countryName": "Etats-Unis", "movId": 1000, "name": "Forrest Gump", "year": 1994}`…) ;
   - [http://localhost:18080/sample/api/movies/display/250](http://localhost:18080/sample/api/movies/display/250) : le seul film allemand, `The Grand Budapest Hotel` ;
   - [http://localhost:18080/sample/api/movies/display/1128](http://localhost:18080/sample/api/movies/display/1128) : `[]`, aucun film pour la France *(voir les pays dans `/src/main/resources/sql/initdata.sql`)*.
1. **Observez** la console : pour chaque appel, une trace du webservice (`Finish /Get//movies/display/{couId} ...`) et une trace de la tâche (`Finish /execute/TkGetMoviesDisplayByCountry ...`), avec leur durée.

### Ce qu'il faut retenir

- Un service est un composant : il reçoit DAO et PAO par injection, et vérifie ses paramètres avant de traiter.
- Un webservice est un composant qui implémente `WebServices` : ses méthodes annotées deviennent des routes REST, et ses résultats sont convertis en JSON.
- Le webservice appelle le service, qui appelle le DAO ou la tâche : chaque couche a son rôle.

# [Suite : Level Socle 7 - La transaction et les erreurs](./LevelSocle7.md)
