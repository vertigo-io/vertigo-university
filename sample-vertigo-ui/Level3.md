# Level 3 - Recherche

Nous allons ajouter une page de recherche sur les Films.
Cela permettra de dépasser les limites de recherche dans la base de données
et proposer une recherche plus pratique et plus performante.

## Eléments

- Route : [http://localhost:18080/sample/moviesSearch/](http://localhost:18080/sample/moviesSearch/)
- Controller : `/src/main/java/io/vertigo/samples/vui/controllers/MoviesSearchController`
- Vue : `/src/main/resources/webapp/WEB-INF/views/vui/moviesSearch.html`
- Service : `movieServices.searchMovies`

### A connaitre : Publier un objet primitif dans le context

Il est possible de publier un objet simple *(String, Long, Integer, ...)* dans le context.

Il faut utiliser `publishRef`.

### A connaitre : FacetedQueryResult

Les services de haut niveau de recherche dans Vertigo retourne le résultat dans un objet complet : `FacetedQueryResult`.
Celui-ci contient éléments du resultat, nombre total d'élément, résultat des facettes et autre.

Il peut être publié dans le context via `publishFacetedQueryResult`. Cette méthode va *"mettre à plat"* l'objet en inserant plusieurs clés dans le contexte. 
Toutes ces clés sont préfixées par le nom de la référence passée en premier paramètre de `publishFacetedQueryResult`.
- `"_list"` : Premiers élements du résultat en mode liste
- `"_cluster"` : Premiers éléments de chaque valeurs de la facette utilisée comme cluster
- `"_facets"` : Facettes du résultat
- `"_selectedFacets"` : Facettes séléctionnées
- `"_totalcount"` : Nombre total d'élément
- `"_criteriaContextKey"` : Clé du context utilisé pour porter les critères

Pour que la recherche et le choix de facette soient réactifs, la recherche peut-être rafraichit en Ajax. Le controller porte une méthode de ce type :
```Java
@PostMapping("/_search")
	public ViewContext doSearch(
			final ViewContext viewContext,
			@ViewAttribute("criteria") final String criteria,
			@ViewAttribute("movies") final SelectedFacetValues selectedFacetValues,
			final DtListState dtListState) {
```

### A connaitre : Composants

La page de recherche est souvant une page qui propose un design particulier. VertigoUi propose des composants qui permettent de construire sa page de recherche plus simplement. 
Le projet peut composer sa page comme il le souhaite, il lui reste l'effort de mise en page.

`vu:search` : Ce composant engloble toute la page de recherche et remplace le formulaire. Il a quelques paramètres :
- `resultKey` : Nom de la référence du `FacetedQueryResult` dans le context
- `searchUrl` : Url de recherche *(en mode Ajax)*
- `collectionComponentId` : Nom du composant portant la liste. Ceci permet le rafraichissement et la conservation du tri et de la pagination.

`search('contextKeyName')` : méthode javascript qui permet de lancer un rafraichissement d'une recherche. 
On l'utilise par exemple sur l'event `@input` du champ de critère.

Sur un composant de type `vu:table`, il faut passer les éléments de manière cohérente.
Par exemple : 
- `list="contextKeyName_list"` : On référence la liste avec le suffix `_list`
- `componentId="myTableId"` : On donne le nom du composant. C'est le même que celui du `vu:search collectionComponentId`  
- `sortUrl="${searchUrl}"` : L'url de tri du tableau est celui de la recherche. Le `searchUrl` est déjà déclaré puisqu'il a été posé sur le `vu:search`

`v-model="vueData.xxx"` : Indépendant de la recherche, c'est un attribut vueJs qui permet d'associer un champ input à une donnée du model. Par exemple `v-model="vueData.criteria"`.

## Etapes

Le ElasticSearch qui sera nécessaire pour cette exercice était désactivé pour éviter des temps de démarrage trop long.
Pour le réactiver, il faut placer un `flag` dans le context de la webApp.
1. Editer le fichier `web.xml`
2. Retrouver/ajouter le paramètre `boot.activeFlags` et ajouter le `flag search`
```Xml
<context-param>
  <param-name>boot.activeFlags</param-name>
  <param-value>search</param-value>
</context-param>
```
3. Redémarrer l'application.
4. Vérifier que ElasticSearch est bien démarré : [localhost:9200/sample_vui___idx_movie/](http://localhost:9200/sample_vui___idx_movie/_search?q=*:*)

**Si besoin** : retrouver tout en bas de cette page les éléments pour faire une réindexation *(normalement vous n'en n'avez pas besoin)*

Une fois l'ElasticSearch démarré, nous pouvons commncer par le controller :

1. Créez le controller `MoviesSearchController`.
2. Déclarez une clé de context **movies** de type `FacetedQueryResult<MovieIndex, SearchQuery>`.
3. Déclarez une clé de context **criteria** de type `String`.
4. Créez un initContext avec un `@GetMapping("/")`.
5. Dans le initContext initialisez le criteria à "" 
6. Appeler le service de recherche et publier le `FacetedQueryResult`
7. Ajouter une méthode pour les recherches en Ajax
```Java
@PostMapping("/_search")
	public ViewContext doSearch(final ViewContext viewContext, @ViewAttribute("criteria") final String criteria, @ViewAttribute("movies") final SelectedFacetValues selectedFacetValues,	final DtListState dtListState) {
```
8. Utiliser ces paramètres pour faire une recherche et publier le résultat dans le context.

Nous avons préparer le Controller, passont à la vue.
1. Créez le fichier de la vue
2. Partez du modèle ci-dessous et compléter le :
```Html
<div>
	<div class="row q-col-gutter-md">
		<div class="m-title text-h6 self-center" style="flex-basis: 300px;">
		<!-- Afficher ici le nombre total de films retournés --> Movies
		</div>
		<div class="col-md col-sm-12">
			<!-- Modifier ce champs de saisie pour l'appliquer à la recherche -->
			<q-input placeholder="Name, Country, Year, ..." v-model="???" :debounce="300" outlined bg-color="white" dense >
				<template v-slot:prepend><q-icon name="search" /></template>
			</q-input>
		</div>
		<div class="col-break"></div>
		<div class="gt-md" style="flex-basis: 300px;">
			<!-- mettre ici les facettes -->
		</div>
		<div class="col">
			<!-- mettre ici le tableau des résultats -->
		</div>
	</div>
</div>
```
3. Consulter la page et observer le comportement.

# [Suite : Level 4 - Tableaux éditables](./Level4.md)

## Optionnel : Ajout un mode de rendu sélectionnable

1. Dans le Controller, ajouter une clé dans le context pour porter le mode de rendu actif `listRenderer` *(String)*
2. Modifier la méthode initContext du controller pour prendre le paramètre `renderer` et le poser dans le context `listRenderer`
3. Dans la vue, ajouter une ligne pour choisir le mode de rendu 
```Html
<div class="row justify-end items-center text-primary">
  <q-btn round icon="fas fa-table" type="a" th:href="@{?renderer=table}" th::flat="${model.listRenderer != 'table'}"> </q-btn>
  <q-btn round icon="fas fa-list" type="a" th:href="@{?renderer=list}" th::flat="${model.listRenderer != 'list'}"> </q-btn>
  </div>
```
4. Ajouter un autre rendu. Par exemple de type Liste
```Html
<th:block th:if="${model.listRenderer == 'list'}">
  <vu:list list="movies_list" componentId="moviesTable" sortUrl="${searchUrl}" rowsPerPage="8" pagination_:direction-links="true" pagination_:boundary-links="true">
  <q-item-section>
    <vu:include-data object="movies_list" field="movId" />
    <vu:include-data object="movies_list" field="name" />
    <vu:include-data object="movies_list" field="year" />
    <a class="text-primary small-caps text-bigger" th::href="|'@{/movie/}'+item.movId|">{{item.name}}</a> ({{item.year}})
  </q-item-section>
  <q-item-section side> <q-btn round color="primary" icon="arrow_forward" type="a" th::href="|'@{/movie/}'+item.movId|"></q-btn> </q-item-section>
  </vu:list>
 </th:block>
```

## Pour lancer une reindexation : 
1. Dans le controller 
```Java
@GetMapping("/_reindex")
public ViewContext indexMovies(final ViewContext viewContext) {
  movieServices.indexMovies();
  return viewContext;
}
```
2. Puis appeler le serviceWeb : http://localhost:18080/sample/moviesSearch/_reindex

