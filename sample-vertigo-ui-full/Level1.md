# Level 1 - Liste simple

Pour commencer, nous allons construire une liste simple.

## Eléments

- Route : [http://localhost:18080/sample/movies/](http://localhost:18080/sample/movies/)
- Controller : `/src/main/java/io/vertigo/samples/vui/controllers/MoviesController`
- Vue : `/src/main/resources/webapp/WEB-INF/views/vui/movies.html`
- Service : `MovieServices.getMovies`

### A connaitre : Création du controller

Vous remarquez que nous appliquons une règle de nommage pour garder une cohérence entre le nom et le package du controller, la route web et le fichier de la vue (en thymeleaf)

Pour construire le controller :
- le controller hérite de io.vertigo.ui.impl.springmvc.controller.AbstractVSpringMvcController.
- les annotations SpringMVC nécessaire : 
  - `@Controller` : pour préciser que la class est un controller et permettre le scan au démarrage
  - `@RequestMapping` : Précise le préfix de la route du controller. Par convention commence toujours par **/** et n'a pas de **/** à la fin.
  - `@GetMapping` : Précise le suffix de la route associée à une méthode, commence toujours par **/**
- le context du controller à un scope sur la page. Il est décrit par des attributs `private final ViewContextKey<...> = ViewContextKey.of("...")`. 
Le context est injecté automatiquement par Spring dans les méthodes du controller avec un attribut : `final ViewContext viewContext`
- l'annotation standard `@Inject` *(javax.inject.Inject)*, permet l'injection des services Vertigo. Par convention et par respect des bonnes pratiques, seuls les Services doivent être injectés dans les Controllers (en favorisant l'injection d'un minimum de services et seulement les services du même module).

**Rappel** : La transaction est portée par la méthode du service (avec l'annotation `@Transactional` par AOP)

Par convention, les controlleurs VertigoUi ont :
- une ou plusieurs méthodes `initContext` accessibles en `Get`. Les routes `Get` sont dans l'esprit des services REST (orientés ressources). Elles peuvent prendre des paramètres si nécessaire.
- des méthodes d'actions en `Post`, `Put` ou `Delete`. Elles sont préfixées par `do`, elles agissent sur un context de page. Les routes d'actions sont différentiables des services REST (commencent par **_** ).

### A connaitre : Création de la vue

La vue est produite coté server avec l'outil de templating Thymeleaf.

Nous préconisons l'usage des [layouts Thymeleaf](https://vertigo-io.github.io/vertigo-docs/#/extensions/ui?id=moteur-de-layout-thymeleaf-layout).
Les composants vertigo permettent une mutualisation et une simplification des composants graphiques riches de Quasar. Ils sont préfixés par `vu:` *(plus tard `vui:` pour lever l'ambiguité avec vueJs)*

Le composant *vu:table* nécessite le paramètre *list* et contient des *vu:column*.

Le composant *vu:column* nécessite le paramètre *field*.

Le body de la *column* permet d'avoir un rendu spécifique. Cela agit coté serveur comme un scope.
Le rendu de la table sera réalisé coté client en vueJs. En vueJs, dans une table, `props.row` permet d'accéder à l'objet de la vue *(il faut s'assurer que les champs sont chargés)*

## Etapes

1. Créez le controller.
1. Déclarez une clé de context "movies" de type Movie.
1. Dans le initContext chargez la liste des movies avec le service `MovieServices.getMovies`.
*(Pour créer un *DtListState* par défaut : `DtListState.defaultOf(Xxx.class)`)*
1. Publiez la liste dans le context.
1. Créez le fichier de la vue en copiant et modifiant le **home.html**
1. Ajoutez une table dans le fragment="content".
```Html
       <section layout:fragment="content" >
            <div>
            <h2>Movies</h2>
            <vu:table list="movies" componentId="movieTable" >
                <vu:column field="movId"/>
                <vu:column field="name" />
                <vu:column field="year" />
            </vu:table>
            </div>
        </section>
```
7. Testez la page affichant la liste de *Movies*.
7. Consulter la source de la page. Recherchez la balise `script id="vui-init-data"` et regardez son contenu.
8. Retirez une colonne du tableau et comparez.
9. Familiarisez vous avec la lecture des composants `vu:table` et `vu:column` ([GitHub VertigoUi Components](https://github.com/vertigo-io/vertigo-extensions/tree/vertigo-3.3.0/vertigo-ui/src/main/resources/io/vertigo/ui/components)). Notez l'usage de `vu:include-data`.

11. Modifiez la colonne movId, pour ajouter un lien vers la page de détail `/movie/{movId}`

`<a th::href="|'@{/movie/}'+props.row.movId|" >{{props.row.movId}}</a>`

12. Testez la page affichant la liste de *Movies*.

[Suite : Level 2 - Ecran de détail ](./Level2.md)
