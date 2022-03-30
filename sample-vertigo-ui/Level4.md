# Level 4 - Tableaux éditables

Nous allons ajouter une page pour modifier les films directement depuis la liste.
Cela permettra de voir le cas des listes modifiables.

## Eléments

- Route : [http://localhost:18080/sample/moviesModifiable/](http://localhost:18080/sample/moviesModifiable/)
- Controller : `/src/main/java/io/vertigo/samples/vui/controllers/MoviesModifiableController`
- Vue : `/src/main/resources/webapp/WEB-INF/views/vui/moviesModifiable.html`
- Service : `movieServices.saveList`


### A connaitre : Publication de liste modifiable

Lorsque l'on publie une liste dans le context celle-ci est non modifiable par défaut.
Pour la rendre modifiable, il faut utiliser `publishDtListModifiable`.
Les listes modifiables sont limitées en nombre d'éléments. 
Elles permettent de conserver l'état des modifications et de récupérer les éléments modifiés, ajoutés et supprimés, 
il est ainsi possible de faire des mise à jour partielles.

*Note :* Lors de la sauvegarde d'une liste d'élément, il est préférable de favoriser le mode *Batch*. 

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
