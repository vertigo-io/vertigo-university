# Level 2 - Ecran de détail

Nous allons construire l'écran de détail d'un film.

## Eléments

- Route : [http://localhost:18080/sample/movie/{movId}](http://localhost:18080/sample/movie/3678598)
- Controller : `/src/main/java/io/vertigo/samples/vui/controllers/MovieController`
- Vue : `/src/main/resources/webapp/WEB-INF/views/vui/movie.html`
- Service : `movieServices.getById(movId)`

### A connaitre : Passage de paramètres

Les paramètres sont déclarés en ajoutant le paramètre à la méthode du controller (initContext ou actions) 
avec une annotation qui précise comment est passé le paramètre avec le protocol HTTP et le nom du paramètre.
Il existe plusieurs moyens pour passer des paramètres : Path, paramètre d'url, Body d'un post, ...

En `Get`, nous utilisons majoritairement l'extraction d'une partie de la route avec `@PathVariable` et le paramètre de requete avec *url?param1=..&param2=..* avec `@RequestParam`.

En `Post`, nous utilisons majoritairement la mise à jour d'un élément du context. Il est récupérer avec `@ViewAttribute`.

Par défaut les paramètres attendus sont considérés obligatoires, mais il est possible de les déclarer `Optional<>` si besoin.

### A connaitre : Composants

Le composant `vu:form` : Déclare un formulaire. Le contenu est utilisé.

Le composant `vu:block` : Déclare un block, c'est un élément graphique. Le contenu est utilisé.

Le composant `vu:grid` : Déclare une mise en page de grille, il possible de modifier le nombre de colonne (2 par défaut). Le contenu est utilisé.

Le composant `vu:text-field` : Pose un champ text, nécessite `object` pour le nom de l'objet dans le context et `field` pour le nom du champ. 
Ce composant a un rendu différent en fonction du mode.


## Etapes

1. Créez le controller.
1. Déclarez une clé de context "movie" de type Movie.
2. Créez un initContext récupérant l'identifiant dans le Path de la route.
3. Dans le initContext chargez le movie à partir de l'id avec le service `MovieServices.getById`.
4. Publiez l'objet `movie` dans le context.
5. Créez le fichier de la vue en copiant et modifiant le **home.html**
6. Ajoutez un formulaire `<vu:form>`.
6. Ajoutez un `<vu:block>` avec un **title="Movie"**
7. Ajouter des champs text sur movId, name, year et couId
8. Consulter la page de détail d'un film.
9. Consulter la source de la page. Recherchez la balise script id="vui-init-data" et regardez son contenu. Dans la console, consulter le vueData avec la commande `VUiPage.vueData`

# [Suite : Level 2.1 - Ecran de détail - Liste de référence des pays](./Level2.1.md)
