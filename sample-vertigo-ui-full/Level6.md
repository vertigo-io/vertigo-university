# Level 6 - Modales

Nous allons ajouter des panneaux (modales) à l'écran de détail d'un film.
Cela permettra de voir les deux façons de faire une modale en Vertigo-UI : pointer vers une page autonome, ou ouvrir un sous-élément du contexte courant.

## Eléments

- Route : [http://localhost:18080/sample/roleActor/{roleId}](http://localhost:18080/sample/roleActor/1000)
- Controller : `/src/main/java/io/vertigo/samples/vui/controllers/RoleActorController`
- Vue : `/src/main/resources/webapp/WEB-INF/views/vui/roleActor.html`
- Service : `movieServices.getRoleWithActorById`, `movieServices.save`

### A connaitre : Les deux façons de faire une modale

Le composant `vu:modal` déclare un panneau qui s'ouvre au-dessus de la page courante. Il existe deux façons de l'utiliser :

1. **Pointer vers une page autonome** : le contenu par défaut de la modale est une iframe qui charge une page complète. Cette page a son controller, son context et son layout. C'est la façon la plus utilisée lorsque l'on veut le détail d'un objet sans trop d'interaction avec la page appelante.
2. **Ouvrir un sous-élément du contexte courant** : il est possible de remplacer le contenu de la modale (l'iframe) par du contenu directement lié au contexte de la page appelante *(un `vu:form` par exemple)*. C'est la façon la plus utilisée lorsque l'on veut interagir avec le contexte courant *(éditer une ligne simple, mettre à jour un champ)*.

`openModal(componentId, url, params)` : méthode javascript du plugin vertigo-ui qui ouvre la modale. `url` est l'adresse de la page chargée dans l'iframe et `params` est ajouté à l'url sous forme de paramètres *(url?param1=valeur1&param2=valeur2)*. Sans `url`, la modale s'ouvre avec son contenu courant.

La colonne d'actions des tableaux fait partie du design system : en déclarant une `actions_slot` dans le `vu:table`, une colonne d'actions est posée automatiquement en fin de tableau. Les boutons `vu:button` avec une icône et un `title` s'y placent naturellement.

### A connaitre : Le layout modal

Les pages de modale ne passent pas par le layout principal `mmcLayout` mais par le layout modal `templates/mmcModalLayout`. Le contenu est posé dans le fragment `page-container`.

Ce layout implémente le retour vers la page appelante : lorsque la clé de context `closeSuccess` est publiée à `true`, le layout exécute dans la page parente *(window.parent)* la fonction javascript nommée par la clé de context `successCallback`. C'est ainsi que la page parente peut fermer la modale et recharger ses données.

## Etapes

Nous allons ajouter au tableau des rôles une colonne d'actions *(œil et stylo)* qui ouvre le détail d'un rôle dans une modale, en lecture ou en édition. Le clic sur la ligne naviguera directement vers la page du rôle.

1. Dans la vue du détail d'un film, ajouter la `actions_slot` au tableau des rôles avec deux boutons `vu:button` :
```Html
<vu:slot name="actions_slot">
    <vu:button flat icon="ri-eye-fill" title="Voir le rôle" th:@click.stop="|openModal('roleActorModal', '@{/roleActor/}' + props.row.rolId, {edit: false})|" />
    <vu:button flat icon="ri-edit-fill" title="Modifier le rôle" th:@click.stop="|openModal('roleActorModal', '@{/roleActor/}' + props.row.rolId, {edit: true, successCallback: 'onRoleActorSuccess'})|" />
</vu:slot>
```
2. Sur le même tableau, faire naviguer la ligne vers la page autonome du rôle *(le mécanisme `tr_@click.native` vu au Level 2.6)* :
```Html
tr_@click.native="|goTo('@{/roleActor/}' + props.row.rolId)|" tr_class="nav"
```
3. Déclarer la modale :
```Html
<vu:modal componentId="roleActorModal" title="Role Actor" width="550px" autoHeight="true" />
```
4. Dans le fragment `javascript-footer` de la vue, ajouter la fonction qui sera appelée par la page modale après enregistrement. Elle ferme la modale et recharge les rôles :
```Javascript
function onRoleActorSuccess() {
    VUiPage.$data.componentStates.roleActorModal.opened = false;
    VUiPage.httpPostAjax("[[@{_reloadRoles}]]", {});
}
```
5. Dans le `MovieController`, ajouter la méthode `_reloadRoles` qui re-publie les rôles du film dans le context.
6. Créer le controller `RoleActorController` :
   - des clés de context `role` de type `Role`, `actor` de type `Actor`, `successCallback` de type `String` et `closeSuccess` de type `Boolean`
   - un initContext `GET /{roleId}` qui prend aussi les paramètres `edit` *(boolean, non obligatoire)* et `successCallback` *(string, non obligatoire)* : charger le rôle avec son acteur via `movieServices.getRoleWithActorById`, publier les objets du context, et passer en mode édition seulement si `edit` est `true` *(par défaut la page s'ouvre en lecture)*
   - une méthode `POST /_save` qui prend les objets `role` et `actor`, sauvegarde avec le service `movieServices.save(role, actor)` et publie `closeSuccess` à `true`
7. Créer la vue `roleActor.html` en partant de `home.html` :
   - décorer le layout modal : `layout:decorate="~{templates/mmcModalLayout}"`
   - le contenu dans le fragment `page-container`
   - un `vu:form` *(masqué si `model.closeSuccess`)* avec les champs `role.asCharacter` et `actor.name`, et un bouton `vu:button-submit` `Save` *(action `@{_save}`)* posé seulement en mode édition
8. Consulter la page de détail d'un film et observer le comportement :
   - l'œil ouvre le panneau en lecture
   - le stylo ouvre le panneau en édition : modifier puis Save, le panneau se ferme tout seul et la liste des rôles est rechargée
   - le clic sur la ligne ouvre la page du rôle en plein écran *(la page est autonome : on peut aussi la charger directement dans le navigateur)*

*Note : en projet, il faudrait protéger les données en cours d'édition avant une navigation (cf. Level 5).*
*La solution complète est dans le sample-vertigo-ui-full.*

## Optionnel : Rechercher un pays en modale (sous-élément du contexte courant)

Le pays du film est une liste de référence *(Level 2.1)*. Nous allons proposer une modale qui ouvre un sous-élément du contexte courant : un objet `countrySearch` qui, une fois validé, met à jour le film dans le contexte de la page.

1. Dans le `MovieController`, publier une clé de context `countrySearch` de type `Country` *(initialisée avec `new Country()`)* dans les initContext.
2. Ajouter la méthode `POST /_selectCountry` : elle prend l'objet `countrySearch`, lit le film depuis le context, pose l'identifiant du pays choisi sur le film, re-publie le film et renvoie le `viewContext`.
3. Dans la vue, à côté du select du pays, ajouter un bouton qui ouvre la modale :
```Html
<vu:button flat icon="search" title="Rechercher un pays" th:@click="|openModal('countrySearchModal')|" />
```
4. Déclarer la modale. Cette fois le contenu remplace l'iframe par défaut : c'est un `vu:form` lié au contexte courant, posé dans un `q-card` :
```Html
<vu:modal componentId="countrySearchModal" title="Rechercher un pays">
    <q-card class="bg-white v-modal column" style="width: 400px;">
        <q-toolbar class="bg-primary">
            <vu:button flat dense v-close-popup icon="keyboard_arrow_left" color="primary" textColor="white" title="Fermer" />
            <q-toolbar-title class="text-white">Rechercher un pays</q-toolbar-title>
        </q-toolbar>
        <q-card-section>
            <vu:form>
                <vu:grid cols="1">
                    <vu:autocomplete object="countrySearch" field="couId" list="countries" minQueryLength="0" />
                    <q-btn color="primary" label="Valider" class="float-right q-mt-md" th:@click="|httpPostAjax('@{/movie/_selectCountry}', vueDataParams(['countrySearch']), { onSuccess: function() { componentStates.countrySearchModal.opened = false; } })|" />
                </vu:grid>
            </vu:form>
        </q-card-section>
    </q-card>
</vu:modal>
```
5. Consulter la page et observer le comportement : ouvrir la modale, rechercher un pays par prefix *(ex. « Fra »)* et valider : la modale se ferme et le select du film affiche le pays choisi.

*La solution est dans le sample-vertigo-ui-full.*
