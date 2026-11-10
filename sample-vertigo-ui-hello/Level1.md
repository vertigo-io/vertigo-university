# Level 1 - Le boot web minimal

Nous allons construire le boot web minimal d'un node Vertigo-UI : le node démarre, la sécurité est en place, puis une première page est rendue par un controller dans un layout. Sans base de données, sans Elasticsearch, sans BerkeleyDB, sans donnée préchargée.

À la fin de ce Level, vous saurez :
- démarrer un node Vertigo-UI dans une webapp : le listener et le fichier YAML qu'il lit ;
- déclarer les features dont ce node a besoin ;
- créer une page dans le layout, et le controller qui la rend.

**Prérequis** : le module se compile sur place, dans le dépôt — son parent `io.vertigo:vertigo-samples`, non publié sur Maven Central, est résolu par le `pom.xml` à la racine du dépôt. Pour compiler une copie placée hors du dépôt, installez d'abord ce parent dans votre repo local Maven.

## Deux façons de faire l'exercice

- **Vous voulez démarrer vite** : suivez la [voie rapide](#etapes--voie-rapide). Vous ajoutez les quatre briques d'un coup, puis vous lancez une seule fois. C'est la voie conseillée pour un premier contact.
- **Vous êtes aventurier** : suivez la [voie brique par brique](#etapes--voie-brique-par-brique). Vous lancez le node après chaque brique : l'erreur obtenue montre ce qui manque encore, et quelle feature l'apporte.

Les deux voies construisent les mêmes fichiers et arrivent au même résultat.

## Eléments

- Route : [http://localhost:18081/uihello/home/](http://localhost:18081/uihello/home/)
- Classe main : `io.vertigo.samples.uihello.BootSampleUiHello` (Jetty :18081, context `/uihello`) *(pour la lancer en ligne de commande, depuis la racine de ce module : voir [Lancer une classe main](../sample-vertigo-ui/README.md#lancer-une-classe-main), la même procédure)*
- Concepts illustrés : Module, Component *(voir le glossaire [vertigo-docs : Concepts](https://vertigo-io.github.io/vertigo-docs/#/basic/concepts))*
- Documentation : [vertigo-docs : UI](https://vertigo-io.github.io/vertigo-docs/#/basic/ui) et [vertigo-docs : Configuration](https://vertigo-io.github.io/vertigo-docs/#/basic/configuration)

**Fourni (ne pas modifier)** :

- `BootSampleUiHello` : le boot web Jetty (port, context, handler multipart)
- `support/boot/MultipartConfigInjectionHandler` et `support/config/` : l'initialisation SpringMVC
- `webapp/index.html` et `webapp/static/` : la page d'accueil statique
- `webapp/WEB-INF/views/templates/sampleLayout.html` : **le layout** (l'habillage commun des pages : en-tête, menu, zone de contenu). Dans une application réelle, le layout existe et évolue avec le projet : votre travail est de créer des **pages** dedans, pas de réécrire le layout.
- `webapp/META-INF/sample-ui-hello.yaml` et `webapp/WEB-INF/web.xml` : les squelettes à compléter

**À construire (cet exercice)** :

- `webapp/WEB-INF/web.xml` : compléter (2 blocs)
- `webapp/META-INF/sample-ui-hello.yaml` : compléter (les features du node)
- `io/vertigo/samples/uihello/support/SampleUiHelloUserSession.java`
- `webapp/WEB-INF/views/uihello/home.html`
- `io/vertigo/samples/uihello/controllers/HomeController.java`

### A connaitre : Le node web

Un node web Vertigo-UI repose sur 3 mécanismes déjà présents dans le squelette :

- **Jetty** est démarré par `BootSampleUiHello` (classe `JettyBoot` de vertigo-ui) : port 18081, context `/uihello`.
- **Le node Vertigo** est démarré par le listener `AppServletContextListener` (à ajouter dans `web.xml`) : il lit le paramètre `boot.applicationConfiguration` pour trouver la configuration du node (le fichier YAML).
- **Le contexte SpringMVC** est créé par `SampleUiHelloVSpringWebApplicationInitializer` (déjà passé à `JettyBoot`) : c'est lui qui instancie les controllers Vertigo-UI.

### A connaitre : Les features du node (YAML)

La section `modules` du YAML déclare les features Vertigo utilisées par le node. Chaque feature enregistre des composants disponibles par injection (DI). Vertigo-UI a besoin de plusieurs de ces composants :

| Composant injecté | Fourni par la feature |
|---|---|
| `CodecManager` | `CommonsFeatures` |
| `SmartTypeManager` | `DataModelFeatures` |
| `JsonEngine` | `VegaFeatures` |
| `KVStoreManager` | `DataStoreFeatures` (kvStore) |
| `CollectionsManager` | `DataFactoryFeatures` |
| `vSecurityManager`, autorisation | `AccountFeatures` |

Points clés :

- `script.janino` (`CommonsFeatures`) : requis par Vertigo-UI (scripts/expressions).
- `kvStore.delayedMemory` stocke les **ViewContext** de Vertigo-UI (`VViewContext`, `VViewInitContext`) : c'est la mémoire par page de l'UI.
- `DataFactoryFeatures` se déclare **sans sous-feature** : le `CollectionsManager` est enregistré par le module lui-même. L'indexation Lucene est un plugin **optionnel** de ce composant : pas de dépendance lucene, pas de cache.

### A connaitre : La page dans le layout

Les pages sont des templates Thymeleaf. Le moteur de layout Thymeleaf permet à une page de se greffer sur le layout :

- `layout:decorate="~{templates/sampleLayout}"` sur la balise `<html>` : la page utilise le layout.
- `layout:fragment="content"` : le fragment que la page remplit (dans le layout, c'est la balise `<section ... layout:fragment="content">`).
- Les namespaces : `th` (Thymeleaf), `vu` (composants Vertigo-UI), `layout` (moteur de layout).

### A connaitre : Le controller

- Le controller hérite de `AbstractVSpringMvcController` (vertigo-ui).
- Annotations SpringMVC : `@Controller` (découverte par le `@ComponentScan` de `SampleUiHelloVSpringWebConfig`), `@RequestMapping("/home")` (préfixe de route), `@GetMapping("/")` (route de la page).
- La méthode `initContext(final ViewContext viewContext)` prépare le context de la page (scope de la page). Pour une page statique, son corps reste vide.

## Les briques

Les deux voies utilisent les mêmes quatre briques. L'ordre des modules dans le YAML est sans importance.

### Brique 1 - Le node et ses features de base

Complétez `web.xml` avec le listener et le paramètre de configuration (après le `<display-name>`) :

```Xml
	<listener>
		<listener-class>io.vertigo.vega.impl.webservice.servlet.AppServletContextListener
		</listener-class>
	</listener>

	<context-param>
		<param-name>boot.applicationConfiguration</param-name>
		<param-value>/webapp/META-INF/sample-ui-hello.yaml</param-value>
	</context-param>
```

et complétez la section `modules` du YAML avec les 3 features de base :

```Yaml
  io.vertigo.commons.CommonsFeatures:
    features:
      - script:
    featuresConfig:
      - script.janino:
  io.vertigo.datamodel.DataModelFeatures:
  io.vertigo.vega.VegaFeatures:
```

### Brique 2 - Les données

Ajoutez dans le YAML :

```Yaml
  io.vertigo.datastore.DataStoreFeatures:
    features:
      - kvStore:
    featuresConfig:
      - kvStore.delayedMemory:
          collections: VViewContext, VViewInitContext
          timeToLiveSeconds: 43200
  io.vertigo.datafactory.DataFactoryFeatures:
```

### Brique 3 - La sécurité

Créez la session utilisateur :

```Java
package io.vertigo.samples.uihello.support;

import java.util.Locale;

import io.vertigo.account.security.UserSession;

public class SampleUiHelloUserSession extends UserSession {

	private static final long serialVersionUID = 1L;

	/** {@inheritDoc} */
	@Override
	public Locale getLocale() {
		return Locale.FRANCE;
	}
}
```

et complétez le YAML :

```Yaml
  io.vertigo.account.AccountFeatures:
    features:
      - authorization:
      - security:
          userSessionClassName: io.vertigo.samples.uihello.support.SampleUiHelloUserSession
```

### Brique 4 - L'écran

La page (le layout `sampleLayout` est fourni), `webapp/WEB-INF/views/uihello/home.html` :

```Html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org"
  xmlns:vu="https://vertigo.io/thymeleaf/vertigo-ui"
  xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout"
  layout:decorate="~{templates/sampleLayout}"
>

<head>
	<title>UI Hello Home</title>
</head>

<body>
	<div layout:fragment="content">
		<p>Hello Vertigo-UI !!</p>
		<p>
			Votre premier écran Vertigo-UI : une page Thymeleaf rendue par le controller
			<code>HomeController</code> dans le layout <code>sampleLayout</code>.
		</p>
	</div>
</body>
</html>
```

Et le controller qui la rend :

```Java
package io.vertigo.samples.uihello.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.vertigo.ui.core.ViewContext;
import io.vertigo.ui.impl.springmvc.controller.AbstractVSpringMvcController;

@Controller
@RequestMapping("/home")
public class HomeController extends AbstractVSpringMvcController {

	@GetMapping("/")
	public void initContext(final ViewContext viewContext) {
		// dummy : l'écran de démonstration est statique
	}
}
```

## Etapes : voie rapide

1. Ajoutez les briques 1 à 4, sans lancer le node entre deux briques.
1. Recompilez le projet, puis lancez `BootSampleUiHello`.
1. Ouvrez [http://localhost:18081/uihello/home/](http://localhost:18081/uihello/home/) dans le navigateur (ou `curl`) : vous devez obtenir **200** et le contenu `Hello Vertigo-UI !!` dans la page rendue, dans le layout fourni.
*(si l'initialisation échoue avec `java.lang.NullPointerException: node has not been started` : le listener manque dans `web.xml` (brique 1) ;*
*avec `No qualifying bean of type 'io.vertigo.datastore.kvstore.KVStoreManager'` : la brique 2 manque ;*
*avec `component info with id 'vSecurityManager' not found` : la brique 3 manque ;*
*si la page répond **404** : le controller de la brique 4 manque)*
1. **Observez** la source de la page : la balise `<script id="vui-init-data">` contient le context de la page, que Vertigo-UI transmet au navigateur ; dans la console du navigateur, `VUiPage.vueData` l'expose en vueJs.
1. Arrêtez le node : Ctrl+C en ligne de commande, ou le bouton d'arrêt de la console sous Eclipse.

## Etapes : voie brique par brique

Après chaque brique, recompilez le projet puis relancez `BootSampleUiHello`. Le node ne démarre que lorsque toutes ses dépendances sont là : à chaque lancement, l'erreur obtenue montre ce qui manque encore.

1. Ajoutez la **brique 1**, puis lancez. Jetty démarre, puis l'initialisation échoue avec :
`No qualifying bean of type 'io.vertigo.datastore.kvstore.KVStoreManager'`.
Il manque le `KVStoreManager` : c'est la brique 2 qui l'apporte. *(le log peut contenir **aussi** `vSecurityManager not found` : les erreurs sont regroupées au démarrage ; c'est la brique 3)*
1. Ajoutez la **brique 2**, puis relancez. L'initialisation échoue avec :
`component info with id 'vSecurityManager' not found`.
Il manque la sécurité : c'est la brique 3.
1. Ajoutez la **brique 3**, puis relancez. Le node démarre maintenant sans erreur.
[http://localhost:18081/uihello/home/](http://localhost:18081/uihello/home/) répond **404** : l'écran n'existe pas encore.
1. Ajoutez la **brique 4**, puis relancez. [http://localhost:18081/uihello/home/](http://localhost:18081/uihello/home/) répond **200**, avec le contenu `Hello Vertigo-UI !!` dans la page rendue, dans le layout fourni.
1. **Observez** la source de la page : la balise `<script id="vui-init-data">` contient le context de la page, que Vertigo-UI transmet au navigateur ; dans la console du navigateur, `VUiPage.vueData` l'expose en vueJs.
1. Arrêtez le node : Ctrl+C en ligne de commande, ou le bouton d'arrêt de la console sous Eclipse.

### Ce qu'il faut retenir

- Le node web est démarré par le listener de `web.xml`, à partir du fichier YAML que désigne `boot.applicationConfiguration`.
- Chaque feature du YAML apporte des composants : le node ne démarre que si tous ceux dont il a besoin sont présents.
- Une page est un template Thymeleaf greffé sur le layout ; un controller SpringMVC la rend.

# [Suite : sample-vertigo-ui — Level Ui 0](../sample-vertigo-ui/LevelUi0.md)

### Optionnel : pour aller plus loin

- **Deuxième page** : créez `about.html` (même pattern : decorate + fragment) et un `AboutController` (`@RequestMapping("/about")`), ajoutez un lien `<a th:href="@{/about/}">` dans le fragment `content` de `home.html`.
- **Pourquoi pas de cache, pas de Lucene ?** : l'index Lucene est un plugin optionnel de `DataFactoryFeatures`, qui sert à rechercher dans des listes (l'autocomplete, par exemple) ; le cache mémoire n'est requis que si un plugin l'utilise. Un node Vertigo-UI minimal n'a besoin ni de l'un ni de l'autre.
