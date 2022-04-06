# Level 2.5 - Ecran de détail - Validation coté serveur en Ajax

Nous allons ajouter une validation de la date du film l'écran de détail.
Nous verrons la réactivité aux modifications d'un objet du vueModel et la validation de données coté serveur.

## Eléments

- Route : [http://localhost:18080/sample/movie/{movId}](http://localhost:18080/sample/movie/3678598)
- Controller : `/src/main/java/io/vertigo/samples/vui/controllers/MovieController`
- Vue : `/src/main/resources/webapp/WEB-INF/views/vui/movie.html`
- Service : `movieServices.validate`

### A connaitre : UiMessageStack

VertigoUi propose un objet qui 



### A connaitre : Validations spécifiques

VertigoUi applique automatiquement une validation des DtObjects à partir des contraintes associées aux SmartTypes lors de la réception coté serveur, 
mais il est possible d'appliquer des validations spécifiques.

Ces validations peuvent s'appliquer à deux niveaux : 
- Sur la saisie utilisateur 
- Sur les entités ayant déjà passés les premiers niveaux de validation de surface

Pour être efficace, il est préférable de travailler coté serveur sur les entités déjà validées. 
Sur la saisie utilisateur, il est plus complexe de gérer les cas où les champs obligatoires sont absents et où les saisies ont des erreurs de format. 
Cette complexitée impacte fortement les autres controles, puisqu'il entrainne une forte complexité dans la combinatoire des cas rencontrés.

> Les contrôles sur la saisie utilisateur ne sont vraiment utile que lorsqu'on valide une sous partie d'un objet.

Pour la saisie utilisateur, il suffit de récupérer un UiObject<?> dans la méthode du Controller. 



### A connaitre : Reaction aux modifications sur un champ 

En vueJS, le plus simple pour réagir à quelques choses est de poser un handler d'evenement sur le composant d'édition.
Dans le cas d'une saisie sur un text-field et en consultant l'api du composante vueJs sous jacent ([QField](https://v1.quasar.dev/vue-components/field))

L'event `@input` : *Emitted when the model changes, only when used with 'clearable' or the 'control' scoped slot.*
permet de réagir à la saisie dans un text. Pour ne lancer le traitement que lorsque l'utilisateur fait a terminé sa saisie, on utilise l'attribut `debounce` (en ms).

**Note:** Quasar propose un utilitaire pour réaliser un *debounce* : `Quasar.utils.debounce(fct, timeMs)`.
Cette méthode retourne une nouvelle fonction, il faut donc s'assurer qu'elle sera évaluée.

### A connaitre : Reaction aux modifications sur un objet 

Il est souvant préférable d'agir sur les modifications d'un objet quelques soit le champ. 
Dans ce cas, on ne va pas réagir aux évenements de chaques `input`, mais plutot réagir à la modification de l'objet bindé dans le *vueData*.
Pour cela, vueJs propose la mise en place de `watcher` qui permet de réagir au modification. En générale on appliquera un `debounce`.

Exemple : 
```Javascript
VUiPage.$watch('vueData.object', 
Quasar.utils.debounce(
  (newValue, oldValue) => { ... myFunction ... },
debounceDelay), { deep: true });
```

## Etapes

Nous allons commencer par le tri de la liste des films. [movies](http://localhost:18080/sample/movies/)
1. Commencez par observer les limites du tri coté client
2. Dans le Controller `MoviesController`, ajouter une méthode qui répondra au tri Ajax du composant `vu:table`
```Java
 @PostMapping("/_sort")
	public ViewContext sort(final ViewContext viewContext, final DtListState dtListState) {
```
2. Dans cette action, utiliser le service de chargement avec le DtListState et mettez à jour le viewContext
3. Consulter le service pour voir que le DtListState est passé à la couche DAO : c'est bien la base de données qui fera le tri et le filtrage.
4. Dans la vue de la liste des films, ajouter une `sortUrl="@{/movies/_sort}"` sur la table
5. Consulter la page et observer le comportement (la base de contient que les films commençant par 'S').

# [Suite : Level 2.5 - Attributs complémentaires et évènements](./Level2.5.md)

## Optionnel - Tri spécifique en Java

Pour ajouter le tri des roles dans film, nous allons ajouter un tri spécial sur la colonne du rôle : il y a un <#> qui donne l'ordre d'importance *(selon imdb)* du role dans l'oeuvre.
1. Dans le controller `MovieController`, ajouter une méthode pour le tri. Il faudra récupérer la liste de film depuis le context car le tri ne sera pas fait par la base de données.
2. Appeler le service de tri `sortRoles` depuis l'action
3. Consulter le service pour voir que le tri est réalisé en Java et utilise un comparator particulier *(cela est possible grace à la faible volumétrie de role par film)*
4. Dans la vue de l'écran de détail, sur la liste des roles ajouter le `sortUrl` sur la table
5. Consulter la page et observer le comportement

