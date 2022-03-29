# Level 2.5 - Attributs complémentaires et évènements

Nous allons rajouter un lien de navigation sur la liste des films en récupérant l'évenement onClick de l'ensemble de la ligne.
Nous verrons comment passer des attributs spécifiques du composant Thymeleaf au composant Quasar, et comment traiter les évenements.

## Eléments

- Route : [http://localhost:18080/sample/movies/](http://localhost:18080/sample/movies/)
- Controller : `/src/main/java/io/vertigo/samples/vui/controllers/MoviesController`
- Vue : `/src/main/resources/webapp/WEB-INF/views/vui/movies.html`
- Service : 

### A connaitre : Paramètres des composants

Les composants Vertigo sont en fait des fragments Thymeleaf. Par convention, ils déclarent les paramètres qu'ils vont utiliser.
Par exemple : 
```Html
<th:block th:fragment="text-field-edit(object, field, rowIndex, label, suffix, input_attrs, label_attrs)" 
	vu:alias="text-field" vu:selector="${viewMode=='edit'}"
```
Les paramètres qui se terminent par `_attrs` permettent de passer des paramètres qui n'étaient pas prévues à la base. 
Il faut préfixer le paramètre avec le même préfix *(s'il n'y a pas de prefix, c'est le dernier attrs qui le récupère)*.
Il est aussi possible de passer des events vueJs. Et enfin, il est possible d'en passer plusieurs les `xxx_attrs` sont des listes de paramètres.

Quelques exemples : 
- `input_color="blue"` passera `color="blue"` dans le paramètre `input_attrs`
- `input_color="blue" input_readonly` passera `color="blue", readonly=true` dans le paramètre `input_attrs`
- `color="blue"` passera `color="blue"` dans le paramètre `label_attrs`
- `input_@click="todo"` passera `@click="todo"` dans le paramètre `input_attrs`

Vous pouvez voir dans les composants, comment ces paramètres sont ensuite affichés sur les composants Quasar avec des `th:attr="__${input_attrs}__"`

## Etapes 

1. Sur la page des films, ajouter une class `nav` sur le `tr` de la table, avec `tr_class="nav"` sur le composant `vu:table`
2. Ajouter l'évenement natif `onClick` sur le `tr`. Celui-ci utilisera la méthode javascript `goTo(url)`.
```Html
tr_@click.native="|goTo('@{/movie/}'+props.row.movId)|"
```
3. Consulter la page et observer le comportement

# [Suite : Level 2.6 - Gestion des slots](./Level2.6.md)

### Optionnel : Creation de l'écran de détail d'acteur

1. Utiliser vos connaissances pour ajouter un lien sur la liste des rôles vers l'écran de consultation/modification des acteurs.
