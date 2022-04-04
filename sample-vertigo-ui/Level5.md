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
  if (isNewContext()) { //Needded to initContext only once
    //must support all cases : check conditions (newContext, logged user, etc..)
    ...
  }
}
```

**Important:** `@ModelAttribut` est exécuté deux fois si le controller est aussi dans le scope du `@ControllerAdvice` (ce qui est souvent le cas, car il `extends AbstractVSpringMvcController`). Dans ce cas, le `if (isNewContext()) {` permet d'avoir une seule execution.

**Attention:** Lorsque votre composant transverse interagit avec le serveur, les données en cours d'édition peuvent être perdues (car non postées).
Prévoyez une interaction en ajax ou une ergonomie générale adaptée.


### A connaitre : Composants

Pour cet exercice nous allons utiliser le composant autocomplete.

`vu:autocomplete` : Propose la selection d'un élément dans une liste avec une recherche par prefix. Utilise un service commun de recherche coté serveur *(utilise Lucene)*. L'api est similaire au select, necessite les paramètres `object` `field` et `list`.


## Etapes

1. Créez le controller `CountryChoiceController`, avec la route `"/country"`.
2. Ajouter l'annotation `@ControllerAdvice(assignableTypes = { AbstractVSpringMvcController.class })`
3. Ajouter une clé du context pour un objet `"user"` de type `User`. Cet objet est un peu détourné de son usage pour récupérer le pays selectionné et actif dans la session de l'utilisateur connecté.
3. Ajouter une clé du context pour un objet `"countriesMdl"` de type `Country`
4. Ajouter le `initContext` qui prend le ViewContext en paramètre. Celui ci **n'est pas** mappé sur une route **/**, mais a l'annotation `@ModelAttribute`
Ce initContext devra vérifier qu'on est bien sur un nouveau context pour ne pas exécuter deux fois l'init.
```Java
@ModelAttribute
public void initContext(final ViewContext viewContext)
```
5. Dans le initContext il faut initialiser le user et publier la MasterDataList de Country. Pour initialiser le context utiliser la méthode ci-dessous :
```Java
private User obtainUser() {
  final SampleUserSession session = Node.getNode().getComponentSpace().resolve(VSecurityManager.class)
	.<SampleUserSession> getCurrentUserSession().orElseThrow(() -> new VSecurityException(MessageText.of("No active session found")));
  User user = session.getAttribute("sessionUser");
  if (user != null) {
    return user;
  }
  user = new User();
  user.setCouId(1128L); //France
  session.putAttribute("sessionUser", user);
  return user;
}
```
6. Ajouter une méthode mappée sur une requete `POST` en `/_changeCountry`, elle prend l'objet user qui a été envoyé, récupère l'id du pays, met à jour la session et retourne le `viewContext` mis à jour
7. Pour appliquer l'élément graphique sur toute les pages, il faut agir sur le template de page : `mmcLayout.html`
8. Ajouter le composant `vu:autocomplete` dans le dernier `div` de la `q-toolbar`. Ce composant est associé au champ `couId` de `user` et utilise la liste `countriesMdl`
9. Lors de la saisie d'une valeur sur ce composant, il faut *Poster* l'objet `user` du `vueData` vers la méthode de `CountryChoiceController`
```Javascript
th:@input="|httpPostAjax('@{/country/_changeCountry}', vueDataParams(['user']))|"
```

