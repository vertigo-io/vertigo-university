# Level 2.6 - Attributs complémentaires et évènements

Nous allons rajouter un lien de navigation sur la liste des films en récupérant l'évenement onClick de l'ensemble de la ligne.
Nous verrons comment passer des attributs spécifiques du composant Thymeleaf au composant Quasar, et comment traiter les évenements.

## Eléments

- Route : [http://localhost:18080/sample/movies/](http://localhost:18080/sample/movies/)
- Controller : `/src/main/java/io/vertigo/samples/vui/controllers/MoviesController`
- Vue : `/src/main/resources/webapp/WEB-INF/views/vui/movies.html`
- Service : 



### A connaitre : Peuplement du `vueData` (vueJs) à partir du `ViewContext` (SpringMVC)

Le principe de VertigoUi, est que les données nécessaires pour la construction de la page sont publiés par le controller dans le context.
L'ensemble de ces données sont accessibles lors de l'évaluation par le template Thymeleaf.

En revanche, seules les données nécessaires à vueJS coté client sont ajoutées à l'objet javascript vueData qui sera écrit dans le code source de la page. 
Il est également nécessaire de déclarer si ces données sont autorisées en modification depuis le client ou non. Cela protège les données/champs du context de recevoir des modifications interdites depuis une requete.
Cela reste relativement transparent, car les composants VertigoUi qui nécessitent des données dans le vueData le déclare déjà.

Toutefois, si le développeur souhaite utilisé des composants vueJs directement, ou utiliser une donnée coté client, il faudra le déclarer *manuellement*.
Cela se fait via le composant `include-data`. Il y a 4 modes de déclaration : (cf. [doc Vertigo](https://vertigo-io.github.io/vertigo-docs/#/extensions/ui?id=composants-vertigo-ui-utils))
- `include-data(object, field, modifiable, modifiableAllLines)` : Inclus le champ d'un objet 
- `include-data-primitive(key, modifiable)` : Inclus une donnée primitive du context
- `include-data-map(object, field, list, listKey, listDisplay)` : Inclus le champ d'un objet et applique une dénormalisation sur sa valeur (traduit un id en libellé par exemple)
- `include-data-protected(object, field)` : Inclus le champ d'un objet. La valeur posée coté client est protégée (non en clair et non modifiable), la valeur réelle reste coté serveur. Ce système est utilisé pour les identifiants de fichier par exemple.


### A connaitre : Paramètres des composants

Les composants Vertigo sont en fait des fragments Thymeleaf. Par convention, ils déclarent les paramètres qu'ils vont utiliser.
Par exemple : 
```Html
<th:block th:fragment="text-field-edit(object, field, rowIndex, label, suffix, input_attrs, label_attrs)" 
	vu:alias="text-field" vu:selector="${viewMode=='edit'}"
```
Les paramètres qui se terminant par `_attrs` permettent de passer des paramètres qui n'étaient pas prévues à la base. 
Pour passer le parametre `yyy` au *sous composant* `xxx`, il faut préfixer le paramètre avec `xxx_` (voir exemples ci-dessous), ces paramètres seront "récoltés" par le `xxx_attrs`.
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

# [Suite : Level 2.7 - Gestion des slots](./Level2.7.md)

### Optionnel : Creation de l'écran de détail d'acteur

1. Utiliser vos connaissances pour ajouter un lien sur la liste des rôles vers l'écran de consultation/modification des acteurs.
