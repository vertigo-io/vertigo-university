# Level 2.2 - Ecran de détail - Edition

Nous allons completer l'écran de détail de film pour ajouter l'édition.
Nous allons voir la gestion des boutons, des actions et des controles de surface.

## Eléments

- Route : [http://localhost:18080/sample/movie/{movId}](http://localhost:18080/sample/movie/3678598)
- Controller : `/src/main/java/io/vertigo/samples/vui/controllers/MovieController`
- Vue : `/src/main/resources/webapp/WEB-INF/views/vui/movie.html`
- Service : `movieServices.save`

### A connaitre : Mode d'affichage

Les pages de VertigoUi possède un mode. Par défaut celui-ci est en mode readOnly, les composants graphiques proposent alors une version non modifiable.
Lorsque l'on passe en mode edit, les composants proposent les données en édition. Seuls les données en édition peuvent être dans le Post à la soumission du formulaire.

Il y a les méthodes pour changer le mode sur le controller parent : `toModeEdit` et `toModeReadOnly`

### A connaitre : Mise à jour du context

Lorsqu'un formulaire est soumis vers le serveur, celui-ci référence le context qui était utilisé (avec CTX_ID).
SpringMVC met à jour le context avec les éléments soumis, en cas d'erreur de format les messages sont conservés mais le traitement continu.
L'action du controller est appellé, il est alors possible de réceptionner un des objets du context avec le `@ViewAttribute` et la classe de l'objet.
Dans ce cas, le controle de validation est complété avec les validateurs des SmartTypes, en cas d'erreur le traitement est intérompu et une erreur utilisateur est levée.
Il est possible d'accéder aux données sans validation en référencant un `UiObjet<Movie>`.

Attention à ne pas rester sur le même context en permanence, celui-ci doit être rafraichit depuis la base de données lorsque c'est nécessaire.
Nous préconisons de le faire lors des sauvegardes, en forçant le rechargement de la page. Avec SpringMvc, il faut retourner `"redirect:"+url`.

### A connaitre : Composants

Dans les fichiers html des composants VertigoUi, on retrouve les différentes version du composant (readOnly, edit, ...).
La résolution des composants utilise le `vu:alias`et le `vu:selector`
Il est possible de *forcer* l'usage d'une version en donnant son nom principal (ex: text-field-read)

Le composant `vu:grid-cell` déclare une cellule spécifique d'une grid. Il est possible de choisir le nombre de colonne de la cellule (prend toute la ligne par défaut).

Le composant `vu:button-submit` déclare un bouton d'action, nécessite un `label` et une `action` avec l'url *(en thymeleaf : @{myurl})*

Le composant `vu:button-link` déclare un lien en forme de bouton, nécessite un `label` et une `url`. Ce bouton permet de revenir sur un controller avec un `Get`.
On l'utilise par exemple pour les boutons d'annulation.

La commande `th:if` permet de conditionner l'affichage d'un élément coté server. *(et `v-if` pour le faire coté client en vueJs)*

## Etapes

1. Dans le controller déclarez deux méthodes en Post : une pour passer en mode édit, une pour la sauvegarde.
La méthode de sauvegarde doit récupérer les données postées : utiliser le `@ViewAttribute`

```Java
  @PostMapping("/_edit")
  public void doEdit() {

  @PostMapping("/_save")
  public String doSave(@ViewAttribute("movie") final Movie movie) {
```

2. Dans la méthode de passage en mode édit, utiliser le `toModeEdit`. Il n'y a rien à retourner, à la fin d'une méthode d'action la page est rafraichie.
3. Dans la méthode de sauvegarde, utiliser le service pour sauvegarder l'objet mis à jour, puis recharger la page (`"redirect:/movie/" + movie.getMovId();`)
4. Dans la vue, ajouter une cellule qui portera les boutons d'actions
5. Ajoutez un premier bouton pour passer en mode édit. Celui-ci ne doit s'afficher qu'en mode readOnly (`th:if="${model.modeReadOnly}"`)
6. Ajoutez un second bouton pour sauvegarder. Celui-ci ne doit s'afficher qu'en mode edit (`th:if="${model.modeEdit}"`)
7. Ajoutez un troisième bouton pour annuler losrque l'on est en édition. Celui-ci recharge la page, on utilisera un `bouton-link` avec une url adaptée : `@{/movie/} + ${model.movie.getLong('movId')}`
9. Consultez la page de détail d'un film et faites quelques essais.
10. Pour éviter de modifier l'identifiant, forcez le champ `movId` en mode readOnly.
11. Retourner sur la page, notez le comportement en cas d'erreur de saisie sur les champs.
12. Consulter le contenu du `vueData` (balise script `id="vui-init-data"` ou VUiPage.vueData).

# [Suite : Level 2.3 - Ecran de détail - Affichage des tableaux](./Level2.3.md)

### Optionnel : Ajouter l'écran de creation

1. Ajouter le bouton dans la liste de Movies
2. Dans le controller de détail, ajouter un initContext dédié, et utiliser le mode Create
3. Modifier la vue pour afficher les bons composants dans le modeCreate
4. Permettre la creation d'un Movie (l'identifiant est issu d'une séquence en base de donnée)
