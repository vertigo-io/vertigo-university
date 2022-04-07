# Level 2.5 - Ecran de détail - Validation coté serveur en Ajax

Nous allons ajouter une validation de la date du film l'écran de détail.
Nous verrons la réactivité aux modifications d'un objet du vueModel et la validation de données coté serveur.

## Eléments

- Route : [http://localhost:18080/sample/movie/{movId}](http://localhost:18080/sample/movie/3678598)
- Controller : `/src/main/java/io/vertigo/samples/vui/controllers/MovieController`
- Vue : `/src/main/resources/webapp/WEB-INF/views/vui/movie.html`
- Service : `movieServices.validate`

### A connaitre : UiMessageStack

VertigoUi propose une représentation des messages qui doivent être affichés coté client.
Cela passe par l'`UiMessageStack: un objet qui contient la pile des messages de l'action : erreurs de format et de surface (validation des contraintes et du caractère non null), il peut être passé au service et complété avec des messages d'erreur, de warning, d'info ou de succès globaux, par objet ou par champs

Il peut être passé en paramètre d'une action de controller, pour être complété manuellement lors des traitements. 


### A connaitre : Validations spécifiques

VertigoUi applique automatiquement une validation des DtObjects à partir des contraintes associées aux SmartTypes lors de la réception coté serveur, 
mais il est possible d'appliquer des validations spécifiques.

**Important** : La validation coté serveur est absolument nécessaire, c'est pourquoi nous nous focalisons dessus. N'oubliez pas que les controles coté client est toujours contournable.

Ces validations peuvent s'appliquer à deux niveaux : 
- Sur la saisie utilisateur 
- Sur les entités ayant déjà passés les premiers niveaux de validation de surface

Pour être efficace, il est préférable de travailler coté serveur sur les entités déjà validées. 
Sur la saisie utilisateur, il est plus complexe de gérer les cas où les champs obligatoires sont absents et où les saisies ont des erreurs de format. 
Cette complexitée impacte fortement les autres controles, puisqu'il entrainne une forte complexité dans la combinatoire des cas rencontrés.

> Les contrôles sur la saisie utilisateur ne sont vraiment utiles que lorsqu'on valide une sous partie d'un objet.

Pour la saisie utilisateur, il suffit de récupérer un `UiObject<?>` dans la méthode du Controller, dans ce mode les valeurs de champ sont accéssibles même s'il y a des erreurs utilisateur dans l'objet.

Pour lancer une validation sur un `UiObject`, soit on utilise les méthodes de l'`UiObject`, soit on fait appliquer un `DtObjectValidator` (il existe le `DefaultDtObjectValidator` pour appliquer les contraintes des *SmartTypes*.

La méthode `mergeAndCheckInput` permet d'appliquer une Validator et de récupérer le `DtObject` typé avec les données mise à jour par la requete `Http`.

Exemple:
```Java
final Movie movie = movieUi.mergeAndCheckInput(List.of(new DefaultDtObjectValidator<>()), uiMessageStack);
```

### A connaitre : Reaction aux modifications sur un champ 

En vueJS, le plus simple pour réagir à quelques choses est de poser un handler d'evenement sur le composant d'édition.
Dans le cas d'une saisie sur un text-field et en consultant l'api du composante vueJs sous jacent ([QField](https://v1.quasar.dev/vue-components/field))

L'event `@input` : *Emitted when the model changes, only when used with 'clearable' or the 'control' scoped slot.*
permet de réagir à la saisie dans un text. Pour ne lancer le traitement que lorsque l'utilisateur fait a terminé sa saisie, on utilise l'attribut `debounce` (en ms).

Un appel ajax sur évenement ressemblera globalement à :
```Javascript
th:@input="|httpPostAjax('@{url}', vueDataParams(['obj']))|"
```

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

Nous allons ajouter une validation en Ajax sur le formulaire de saisie de film pour vérifier que l'année est supérieur à 1920. [movie](http://localhost:18080/sample/movie/3678598)


1. Dans le Controller `MovieController`, ajouter une méthode qui répondra à la validation Ajax 
```Java
 @PostMapping("/_validate")
public ViewContext doValidate(final ViewContext viewContext, @ViewAttribute("movie") final Movie movie, final UiMessageStack uiMessageStack) {
```
2. Appeller la méthode du service `validate`. Vérifier le traitement réalisé par le service.
3. Dans la vue `movie.html`, ajouter sur l'evenement `input` l'appel au service de validation
*Rappel* : utiliser la méthode `httpPostAjax: function (url, paramsIn, options)`
4. Vérifier le fonctionnement
5. Ajouter un `debounce` pour éviter de multiple appels
6. Vérifier le fonctionnement
7. Remplacer le `@input` par un *watcher* sur l'objet `movie` du `vueData` (avec `debounce` également)

# [Suite : Level 2.6 - Attributs complémentaires et évènements](./Level2.6.md)
