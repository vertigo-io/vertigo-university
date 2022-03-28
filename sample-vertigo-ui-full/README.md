# Vertigo Ui University

Vertigo-UI is built over Vue.js, Quasar and SpringMVC with Thymeleaf.


Lectures en prérequis :

- Doc Vertigo : Vertigo-docs [vertigo-io.github.io](https://vertigo-io.github.io/vertigo-docs/#/)
- Doc Thymeleaf : [Tutorial: Using Thymeleaf](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html)
- Doc Vuejs (en vitesse) pour avoir les concepts : [Introduction — Vue.js (vuejs.org)](https://v2.vuejs.org/v2/guide/?redirect=true)


Les exercices ci-dessous portent sur VertigoUi, pour cela la structure de projet, les objets du mod&egrave;le et les services sont déjà présent.

*Note: Il est préférable de suivre les exercices dans l'ordre.*

# Level 0 - Préparation de l'environnement

1. Récupérer les sources du github [vertigo-io/vertigo-university/sample-vertigo-ui](https://github.com/vertigo-io/vertigo-university/tree/master/sample-vertigo-ui)
2. Créer le projet Eclipse (Import Maven Project)
3. Démarrer l'application `Run Application BootSampleVui` *(dans /vertigo-sample-vertigo-ui-full/src/main/java/io/vertigo/samples/BootSampleVui.java)*
4. Vérifier le fonctionnement [http://localhost:18080/sample/home/](http://localhost:18080/sample/home/)

Dans le web.xml, remarquez le Listener utilisé, et le paramètre `boot.applicationConfiguration`.

Retrouvez la classe `VuiVSpringWebApplicationInitializer` : c'est le point de départ de l'initialisation de SpringMVC.
Il référence la classe `VuiVSpringWebConfig`. Elle porte les annotations de scan de SpringMVC `@ComponentScan` : une ligne par package, il n'y a pas de scan des sous packages. Elle contient les points de hook pour le paramétrage avancé de SpringMVC et VertigoUi.

Retrouver et regardez le controller `HomeController.java` et la page de la vue `home.html`.

# Level 1 - Liste simple

Pour commencer, nous allons construire une liste simple.

## Eléments

- Route : [http://localhost:18080/sample/movies/](http://localhost:18080/sample/movies/)
- Controller : `/src/main/java/io/vertigo/samples/vui/controllers/MoviesController`
- Vue : `/src/main/resources/webapp/WEB-INF/views/vui/movies.html`
- Service : `MovieServices.getMovies`

### A connaitre : Création du controller

Vous remarquez que nous appliquons une règle de nommage pour garder une cohérence entre le nom du controller, la route web et le fichier de la vue (thymeleaf)

Pour construire le controller :
- le controller hérite de io.vertigo.ui.impl.springmvc.controller.AbstractVSpringMvcController.
- les annotations SpringMVC nécessaire : 
  - `@Controller` : pour préciser que la class est un controller et permettre le scan au démarrage
  - `@RequestMapping` : Précise le préfix de la route du controller. Par convention commence toujours par **/** et n'a pas de **/** à la fin.
  - `@GetMapping` : Précise le suffix de la route associée à une méthode, commence toujours par **/**
- le context du controller à un scope sur la page. Il est décrit par des attributs `private final ViewContextKey<...> = ViewContextKey.of("...")`. 
Le context est injecté automatiquement par Spring dans les méthodes du controller avec un attribut : `final ViewContext viewContext`

Par convention, les controlleurs VertigoUi ont :
- une ou plusieurs méthodes `initContext` accessibles en `Get`. Les routes `Get` sont dans l'esprit des services REST (orientés ressources). Elles peuvent prendre des paramètres si nécessaire.
- des méthodes d'actions en `Post`, `Put` ou `Delete`. Elles sont préfixées par `do`, elles agissent sur un context de page. Les routes d'actions sont différentiables des services REST (commencent par **_** ).

### A connaitre : Création de la vue

La vue est produite coté server avec l'outil de templating Thymeleaf.

Nous préconisons l'usage des [layouts Thymeleaf](https://vertigo-io.github.io/vertigo-docs/#/extensions/ui?id=moteur-de-layout-thymeleaf-layout).
Les composants vertigo permettent une mutualisation et une simplification des composants graphiques riches de Quasar. Ils sont préfixés par `vu:` *(plus tard `vui:` pour lever l'ambiguité avec vueJs)*

Le composant *vu:table* nécessite le paramètre *list* et contient des *vu:column* précisant le *field*.

Le body de la *column* permet d'avoir un rendu spécifique. Cela agit coté serveur comme un scope.
Le rendu de la table sera réalisé coté client en vueJs. En vueJs, dans une table, `props.row` permet d'accéder à l'objet de la vue *(il faut s'assurer que les champs sont chargés)*

## Etapes

1. Créez le controller.
1. Déclarez une clé de context "movie" de type Movie.
1. Dans le initContext chargez la liste des movies avec le service `MovieServices.getMovies`.
*(Pour créer un *DtListState* par défaut : `DtListState.defaultOf(Xxx.class)`)*
1. Créez le fichier de la vue en copiant et modifiant le **home.html**
1. Ajoutez une table dans le fragment="content".
```Html
       <section layout:fragment="content" >
            <div>
            <h2>Movies</h2>
            <vu:table list="movies" componentId="movieTable" >
                <vu:column field="movId" >
                    <a th::href="|'@{/movie/}'+props.row.movId|" >{{props.row.movId}}</a>
                </vu:column>
                <vu:column field="name" />
                <vu:column field="year" />
            </vu:table>
            </div>
        </section>
```
6. Testez la page affichant la liste de *Movies*.
7. Consulter la source de la page. Recherchez la balise `script id="vui-init-data"` et regardez son contenu.
8. Retirez une colonne du tableau et comparez.
9. Familiarisez vous avec la lecture des composants `vu:table` et `vu:column` ([GitHub VertigoUi Components](https://github.com/vertigo-io/vertigo-extensions/tree/vertigo-3.3.0/vertigo-ui/src/main/resources/io/vertigo/ui/components)). Notez l'usage de `vu:include-data`.


