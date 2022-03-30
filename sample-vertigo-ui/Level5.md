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

`vu:table-modifiable` : Déclare une table modifiable. Il n'y a ni tri, ni pagination sur cette table.
*Dans la 3.3.0, il est nécessaire de rajouter un attribut sur la table pour afficher tous les éléments : `:pagination.sync="|componentStates.${componentId}.pagination|"` *

Pour une table modifiable, il faut déclarer dans le corps des `column` le rendu souhaité.
Exemple : 
```Html
<vu:column field="title">
  <vu:text-field object="listKey" field="title"/>
</vu:column>
```

Pour que les composants d'éditions de champs fonctionnent (`textfield`, `select`, etc..), ils doivent **impérativement** être à l'intérieur d'un `vu:form`.

## Etapes

1. Créez le controller `MoviesModifiableController` à partir de `MoviesController`.
2. Retirer la méthode du tri et ajouter une méthode pour passer en mode edit
3. Ajouter une méthode de sauvegarde à l'image de celle du `MovieController` : récupère une `DtList<Movie>` et utilise le service `movieServices.saveList`
4. Créez la vue `moviesModifiable.html` à partir de `movies.html`.
5. Remplacer le composant `vu:table` par `vu:table-modifiable` *(pensez au pagination.sync)*
8. Ajouter le corps des colonnes pour les champs `name` et `year`
7. Ajouter les boutons d'action Edit, Save et Cancel à partir de `movie.html`
6. Ajouter un vu:form autour de la table et des boutons d'actions
7. Consulter la page et observer le comportement.

Pour améliorer le rendu, vous pouvez consulter la [documentation Quasar du QInput](https://v1.quasar.dev/vue-components/input#qinput-api)
Par exemple ajouter `dense hide-bottom-space` sur les `vu:text-field` 

# [Suite : Level 5 - Controllers et Context transverses](./Level5.md)
