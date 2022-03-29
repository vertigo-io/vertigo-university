# Level 2.3 - Ecran de détail - Compléments

Nous allons completer l'écran de détail de film pour la liste des rôles.
Nous verrons le rendu particulier des tableaux, le tri coté serveur et l'ajout d'un évenement sur la ligne.

## Eléments

- Route : [http://localhost:18080/sample/movie/{movId}](http://localhost:18080/sample/movie/3678598)
- Controller : `/src/main/java/io/vertigo/samples/vui/controllers/MovieController`
- Vue : `/src/main/resources/webapp/WEB-INF/views/vui/movie.html`
- Service : `movieServices.loadMovieWithRoles`, `movieServices.getActorsByMovie`

### A connaitre : Mise à jour du vueData en Ajax

Les méthodes du controller peuvent être appelées en Ajax par le client, et retourner une mise à jour du vueData.
A la reception, le moteur réactif vueJs mettre à jour la page avec ces données.

Pour mettre en place ce type d'appel, coté client l'appel passera par une méthode Javascript : `httpPostAjax: function (url, paramsIn, options)`
Nous verrons ce mecanisme de manière plus approfondit par la suite.

Coté controller, de manière standard sur SpringMVC, on reste sur un mecanisme de méthodes avec une annotation en `@PostMapping` *(en géneral)*.
Il suffit juste que cette méthode retourne le ViewContext mis à jour.

Lors de la response server, le code javascript s'occupe de la mise à jour des éléments coté client (CTX_ID et vueData), et de traiter les erreurs le cas échéants.

### A connaitre : Composants

Le composant `vu:field-read` : Déclare un champ en mode lecture, il sera résolu coté client. 
Il nécessite de préciser l'`object`, le `field` et le `rowIndex` si besoin. Il peut associer une liste de mapping : avec `list`, `listKey` et `listDisplay`.

*Note* : pour les tables résolues coté client, le rowIndex devra pointer vers la ligne courante VueJs, et sera : `rowIndex="'props.rowIndex'"`

Le composant `vu:select`, comme les autres composants de formulaire ne peuvent pas fonctionner dans une table simple (sera vu par la suite).

## Etapes

1. Dans le Controller, déclarez une clé de context "roles" de type Role.
2. Dans le initContext charger la liste de film avec leur liste de rôle avec le service `loadMovieWithRoles`.
3. Publiez la liste dans le context.
4. Dans la vue, ajouter une table avec les colonnes `asCharacter` et `actId`
5. Consulter la page de détail d'un film avec des Roles : [Star Wars](http://localhost:18080/sample/movie/3678598). Consulter le contenu du `vueData`.

## Etapes - Résolution du nom des acteurs
 
1. Dans le Controller, déclarez une clé de context "actors" de type Actor.
2. Dans le initContext charger la liste des acteurs du film avec le service `getActorsByMovie`.
3. Publiez la liste dans le context.
4. Dans la vue, utiliser le composant `vu:field-read` pour résoudre le actId en nom de l'acteur.
6. Consulter la page de détail. Consulter le contenu du `vueData`.

## Etapes - Tri spécifique coté serveur

Nous allons commencer par le tri de la liste des films. [movies](http://localhost:18080/sample/movies/)
1. Dans le Controller `MoviesController`, ajouter une méthode qui répondra au tri Ajax du composant `vu:table`
```Java
 @PostMapping("/_sort")
	public ViewContext sort(final ViewContext viewContext, final DtListState dtListState) {
```
2. Dans cette action, utiliser le service de chargement avec le DtListState et mettez à jour le viewContext
3. Consulter le service pour voir que le DtListState est passé à la couche DAO : c'est bien la base de données qui fera le tri et le filtrage.
4. Dans la vue de la liste des films, ajouter une `sortUrl="@{/movies/_sort}"` sur la table
5. Consulter la page et observer le comportement (la base de contient que les films commençant par 'S').

Pour ajouter le tri des roles dans film, nous allons ajouter un tri spécial sur la colonne du rôle : il y a un <#> qui donne l'ordre d'importance *(selon imdb)* du role dans l'oeuvre.
1. Dans le controller `MovieController`, ajouter une méthode pour le tri. Il faudra récupérer la liste de film depuis le context car le tri ne sera pas fait par la base de données.
2. Appeler le service de tri `sortRoles` depuis l'action
3. Consulter le service pour voir que le tri est spécifique et réalisé coté Java *(cela est possible grace à la faible volumétrie de role par film)*
4. Dans la vue de l'écran de détail, sur la liste des roles ajouter le `sortUrl` sur la table
5. Consulter la page et observer le comportement

## Etapes - Ajout d'un lien de navigation sur la ligne

Sur la liste des films nous allons rajouter un lien de navigation sur l'évenement onClick de l'ensemble de la ligne


 
[Suite : Level 3 - Recherche](./Level3.md)

### Optionnel : Creation de l'écran de détail d'acteur

1. Utiliser vos connaissances pour ajouter un lien sur la liste des rôles vers l'écran de consultation/modification des acteurs.
