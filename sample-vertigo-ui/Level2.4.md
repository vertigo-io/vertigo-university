# Level 2.4 - Ecran de détail - Tri coté serveur 

Nous allons completer la liste des rôles dans l'écran de détail de film.
Nous verrons le tri coté serveur et le cas où nous souhaitons un comportement spécifique.

## Eléments

- Route : [http://localhost:18080/sample/movie/{movId}](http://localhost:18080/sample/movie/3678598)
- Controller : `/src/main/java/io/vertigo/samples/vui/controllers/MovieController`
- Vue : `/src/main/resources/webapp/WEB-INF/views/vui/movie.html`
- Service : `movieServices.getMovies`, `movieServices.sortRoles`

**Note** Par défaut le tri sur le composant table a trois états : Inactif, Desc, Asc ce qui n'est pas très intuitif. Il est possible de désactiver ce fonctionnement avec l'attribut `binary-state-sort` sur le `vu:table`

### A connaitre : Mise à jour du vueData en Ajax

Les méthodes du controller peuvent être appelées en Ajax par le client, et retourner une mise à jour du vueData.
A la reception, le moteur réactif vueJs mettre à jour la page avec ces données.

Pour mettre en place ce type d'appel, coté client l'appel passera par une méthode Javascript : `httpPostAjax: function (url, paramsIn, options)`
Nous verrons ce mecanisme de manière plus approfondit par la suite.

Coté controller, de manière standard sur SpringMVC, on reste sur un mecanisme de méthodes avec une annotation en `@PostMapping` *(en géneral)*.
Il suffit juste que cette méthode retourne le ViewContext mis à jour.

Lorsque la response server arrive coté client, le code javascript s'occupe de la mise à jour des éléments coté client (CTX_ID et vueData), et de traiter les erreurs le cas échéants.

## Etapes - Tri en base de données

Nous allons commencer par le tri de la liste des films. [movies](http://localhost:18080/sample/movies/)
1. Commencez par observer les limites du tri coté client
2. Dans le Controller `MoviesController`, ajouter une méthode qui répondra au tri Ajax du composant `vu:table`
```Java
 @PostMapping("/_sort")
	public ViewContext sort(final ViewContext viewContext, final DtListState dtListState) {
```
2. Dans cette action, utiliser le service de chargement avec le DtListState et mettez à jour le viewContext
3. Consulter le service pour voir que le DtListState est passé à la couche DAO : c'est bien la base de données qui fera le tri et le filtrage.
4. Dans la vue de la liste des films, ajouter une `sortUrl="@{/movies/_sort}"` sur la table
5. Consulter la page et observer le comportement (la base de contient que les films commençant par 'S').

# [Suite : Level 2.5 - Attributs complémentaires et évènements](./Level2.5.md)

## Optionnel - Tri spécifique en Java

Pour ajouter le tri des roles dans film, nous allons ajouter un tri spécial sur la colonne du rôle : il y a un <#> qui donne l'ordre d'importance *(selon imdb)* du role dans l'oeuvre.
1. Dans le controller `MovieController`, ajouter une méthode pour le tri. Il faudra récupérer la liste de film depuis le context car le tri ne sera pas fait par la base de données.
2. Appeler le service de tri `sortRoles` depuis l'action
3. Consulter le service pour voir que le tri est réalisé en Java et utilise un comparator particulier *(cela est possible grace à la faible volumétrie de role par film)*
4. Dans la vue de l'écran de détail, sur la liste des roles ajouter le `sortUrl` sur la table
5. Consulter la page et observer le comportement

