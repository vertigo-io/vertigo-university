# Level 2.7 - Gestion des slots

Nous allons completer le bouton de sauvegarde de film pour ajouter une message de confirmation.
Nous verrons le mécanisme de slot.

## Eléments

- Route : [http://localhost:18080/sample/movie/{movId}](http://localhost:18080/sample/movie/3678598)
- Controller : `/src/main/java/io/vertigo/samples/vui/controllers/MovieController`
- Vue : `/src/main/resources/webapp/WEB-INF/views/vui/movie.html`
- Service :

### A connaitre : Gestion de slots

Les pages de rendu proposent plusieurs mécanisme pour généraliser des éléments tout en laissant une marge de manoeuvre pour spécialiser des sous-parties.

Au niveau des templates, nous utilisons la notion de layout dans Thymeleaf, 
vous en avez un apperçu dans toutes les pages avec le corps de page qui est à l'intérieur d'un `layout:fragment="content"`.
Le template utilisé est référencé par le `layout:decorate="~{templates/mmcLayout}"` sur la balise `html` tout en haut.
Dans la [demo Mars](https://github.com/vertigo-io/vertigo-mars/tree/master/src/main/webapp/WEB-INF/views/templates) nous avons créer des templates assez riche pour simplifier le code local à une page (header, boutons d'actions, ...).

Au niveau des composants Vertigo en Thymeleaf, nous avons aussi la notion de `slot`. 
Le body du composant est un premier niveau de paramétrage, certain composant ont un contenu par défaut mais qui peut être modifier par le contenu spécifique. 
Il peut aussi y avoir des slots nommés particuliers, dans ce cas il faut placer un `<vu:slot name="named_slot">` dans le corps du composant *(au début)*.

Au niveau des composants vueJs, il y a également une notion de `slot` avec `v-slot:named_slot`. 
La [documentation de Quasar](https://v1.quasar.dev/vue-components/input#qinput-api) est très bien faite et liste les slots proposés pour chaque composant.
C'est par exemple utilisé dans les tableaux pour spécialiser le rendu d'une ligne.

## Etapes

Pour illustrer le principe nous allons ajouter une confirmation sur l'enregistrement des modifications d'un film.

1. Dans la vue du détail d'un film. Ajouter un identifiant sur le `vu:form` avec `id="myFormId"`. Ceci est nécessaire car la fenetre de confirmation est hors du formulaire et devra poster les données.
2. Remplacer le `<vu:button-submit` par un `<vu:button-submit-confirm`, et préciser le `formId`
3. Ajouter un message et des actions spécifique, dans le corps du composant `<vu:button-submit-confirm`
```Html
<vu:slot name="actions_slot">
<q-btn flat label="Non pas vraiment" color="primary" v-close-popup />
<q-btn type="submit" th:formaction="@{_save}" form="myFormId" label="Oui tout à fait" color="primary" v-close-popup />
</vu:slot>
<span>Etes-vous certain de vouloir sauvegarder ?</span>
```
4. Etudier dans le composant `button-submit.html`, les différentes options pour ajouter une confirmation. Vous remarquerez qu'il y a beaucoup plus simple.
5. Consulter la page et observer le comportement.

# [Suite : Level 3 - Recherche](./Level3.md)
