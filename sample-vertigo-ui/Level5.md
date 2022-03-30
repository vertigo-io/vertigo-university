# Level 5 - Controllers et Context transverses

Nous allons ajouter un choix de pays dans le header de toute les pages.
Cela nous montrera comment proposer des comportements transverses via des controllers et des contextes.

## Eléments

- Route : [http://localhost:18080/sample/movies/](http://localhost:18080/sample/movies/)
- Controller : `/src/main/java/io/vertigo/samples/vui/controllers/CountryChoiceController`
- Vue : `/src/main/resources/webapp/WEB-INF/views/vui/mmcLayout.html`
- Service : 


### A connaitre : Controllers transverses

Nous utilisons un mécanisme de SpringMVC pour déclarer du code qui sera posé par AOP sur l'ensemble des controllers. 
Cela permettra de l'activer sur toutes les pages.

`@ControllerAdvice(assignableTypes = { AbstractVSpringMvcController.class })` : Applique ce composant sur l'ensemble des composants de Spring héritant de `AbstractVSpringMvcController`.

`@ModelAttribute` : Execute la méthode lors du chargement du composant. Pour VertigoUI, nous l'utilisons pour le initContext des controllers transverses.
```Java
@ModelAttribute
	public void initContext(final ViewContext viewContext) {
		//must support all cases : check conditions (newContext, logged user, etc..)
    ...
}
```

**Attention:** Lorsque votre composant transverse interagit avec le serveur, les données en cours d'édition peuvent être perdues (car non postées).
Prévoyez une interaction en ajax ou une ergonomie générale adaptée.

### A connaitre : Composants

Pour cet exercice nous allons utiliser le composant autocomplete.

`vu:autocomplete` : Propose la selection d'un élément dans une liste avec une recherche par prefix. Utilise un service commun de recherche coté serveur *(utilise Lucene)*. L'api est similaire au select, necessite les paramètres `object` `field` et `list`.


## Etapes

1. Créez le controller `CountryChoiceController`, avec la route `"/country"`.
2. Ajouter l'annotation `@ControllerAdvice(assignableTypes = { AbstractVSpringMvcController.class })`
3. Ajouter une clé du context pour un objet `"user"` de type `User`
3. Ajouter une clé du context pour un objet `"countriesMdl"` de type `Country`
4. Ajouter le `initContext` qui prend le ViewContext et la Session en paramètre. Celui ci n'est pas mappé sur une route **/**, mais a l'annotation `@ModelAttribute`
```Java
@ModelAttribute
public void initContext(final ViewContext viewContext, final SampleUserSession session)
```
6. Dans le initContext, initialiser le user et publier la MasterDataList de Country. Pour initialiser le context utiliser la méthode ci-dessous :
```Java
	private User obtainUser(final SampleUserSession session) {
		User user = session.getAttribute("sessionUser");
		if (user != null) {
			return user;
		}
		user = new User();
		user.setCouId(1128L); //France
		return user;
	}
```

7. Retirer la méthode du tri et ajouter une méthode pour passer en mode edit
8. Ajouter une méthode de sauvegarde à l'image de celle du `MovieController` : récupère une `DtList<Movie>` et utilise le service `movieServices.saveList`
9. Créez la vue `moviesModifiable.html` à partir de `movies.html`.
10. Remplacer le composant `vu:table` par `vu:table-modifiable` *(pensez au pagination.sync)*
11. Ajouter le corps des colonnes pour les champs `name` et `year`
12. Ajouter les boutons d'action Edit, Save et Cancel à partir de `movie.html`
13. Ajouter un vu:form autour de la table et des boutons d'actions
14. Consulter la page et observer le comportement.

Pour améliorer le rendu, vous pouvez consulter la [documentation Quasar du QInput](https://v1.quasar.dev/vue-components/input#qinput-api)
Par exemple ajouter `dense hide-bottom-space` sur les `vu:text-field` 

# [Suite : Level 5 - Controllers et Context transverses](./Level5.md)
