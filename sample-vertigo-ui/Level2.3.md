# Level 2.3 - Ecran de détail - Affichage des tableaux 

Nous allons completer l'écran de détail de film pour la liste des rôles.
Nous verrons le rendu particulier des tableaux.

## Eléments

- Route : [http://localhost:18080/sample/movie/{movId}](http://localhost:18080/sample/movie/3678598)
- Controller : `/src/main/java/io/vertigo/samples/vui/controllers/MovieController`
- Vue : `/src/main/resources/webapp/WEB-INF/views/vui/movie.html`
- Service : `movieServices.loadMovieWithRoles`, `movieServices.getActorsByMovie`


### A connaitre : DtObject Accessors

Nous profitons de cet exercice pour découvrir une nouveauté de Vertigo 3.
Le service `loadMovieWithRoles`, retourne bien un `Movie`, il vient donc en remplacement du `getById` utilisé dans les exercices précédent.
Le `Movie` retourné contient la liste des `Roles`, qui sont chargés et conservés dans l'association.
On les récupère à travers un objet `VAccessor` avec la méthode `get()`.

Coté service, ce `VAccessor` permet un chargement simplifié des données mais ce chargement n'est pas automatique : on garde la main sur les accès base qui sont fait.

### A connaitre : Composants

Le composant `vu:field-read` : Déclare un champ en mode lecture, il sera résolu coté client. 
Il nécessite de préciser l'`object`, le `field` et le `rowIndex` si besoin. Il peut associer une liste de mapping : avec `list`, `listKey` *(champ de la liste utilisé comme Id)* et `listDisplay` *(champ de la liste utilisé comme label)*.

*Note 1*: Il est possible de déclarer le `displayField` et le `displayKey` sur un objet pour ne pas avoir à le configurer. C'était le cas sur `Country`.

*Note 2* : pour les tables résolues coté client, le rowIndex devra pointer vers la ligne courante VueJs, et sera : `rowIndex="'props.rowIndex'"`

Le composant `vu:select`, comme les autres composants de formulaire ne peuvent pas fonctionner dans une table simple (sera vu par la suite).

## Etapes

1. Dans le Controller, déclarez une clé de context "roles" de type Role.
2. Dans le initContext charger le film avec sa liste de rôle avec le service `loadMovieWithRoles`.
3. Publiez la liste dans le context.
4. Dans la vue, ajouter une table avec les colonnes `asCharacter` et `actId`
5. Consulter la page de détail d'un film avec des Roles : [Star Wars](http://localhost:18080/sample/movie/3678598). Consulter le contenu du `vueData`.

## Etapes - Résolution du nom des acteurs
 
1. Dans le Controller, déclarez une clé de context "actors" de type Actor.
2. Dans le initContext charger la liste des acteurs du film avec le service `getActorsByMovie`.
3. Publiez la liste dans le context.
4. Dans la vue, utiliser le composant `vu:field-read` pour résoudre le actId en nom de l'acteur.
6. Consulter la page de détail. Consulter le contenu du `vueData`.
 
# [Suite : Level 2.4 - Ecran de détail - Tri spécifique coté serveur](./Level2.4.md)
