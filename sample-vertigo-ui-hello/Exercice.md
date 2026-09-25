# Vertigo-UI Hello — Boot web minimal

Vous allez construire, brique par brique, le boot web minimal d'un node Vertigo-UI : le node démarre, la sécurité est en place, puis une première page rendue par un controller dans un layout. Sans base de données, sans Elasticsearch, sans BerkeleyDB, sans donnée préchargée.

## Eléments

- Route : [http://localhost:18081/uihello/home/](http://localhost:18081/uihello/home/)
- Classe main : `io.vertigo.samples.uihello.BootSampleUiHello` (Jetty :18081, context `/uihello`)

**Fourni (ne pas modifier)** :

- `BootSampleUiHello` : le boot web Jetty (port, context, handler multipart)
- `support/boot/MultipartConfigInjectionHandler` et `support/config/` : l'initialisation SpringMVC
- `webapp/index.html` et `webapp/static/` : la page d'accueil statique
- `webapp/WEB-INF/views/templates/sampleLayout.html` : **le layout** (le chrome Quasar de l'application). Dans une application réelle, le layout existe et évolue avec le projet : votre travail est de créer des **pages** dedans, pas de réécrire le layout.
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
- **Le node Vertigo** est démarré par le listener `AppServletContextListener` (à ajouter dans `web.xml`) : il lit le paramètre `boot.applicationConfiguration` pour trouver la configuration du node (le fichier yaml).
- **Le contexte SpringMVC** est créé par `SampleUiHelloVSpringWebApplicationInitializer` (déjà passé à `JettyBoot`) : c'est lui qui instancie les controllers Vertigo-UI.

Sans le listener dans `web.xml`, le contexte Spring démarre avant le node : l'erreur observée est `java.lang.NullPointerException: node has not been started`.

### A connaitre : Les features du node (yaml)

La section `modules` du yaml déclare les features Vertigo utilisées par le node. Chaque feature enregistre des composants disponibles par injection (DI). Les controllers livrés dans vertigo-ui (par exemple `ListAutocompleteController`) injectent plusieurs de ces composants :

| Composant injecté | Fourni par la feature |
|---|---|
| `CodecManager` | `CommonsFeatures` |
| `SmartTypeManager` | `DataModelFeatures` (utilisé par `CollectionsManagerImpl`) |
| `JsonEngine` | `VegaFeatures` |
| `KVStoreManager` | `DataStoreFeatures` (kvStore) |
| `CollectionsManager` | `DataFactoryFeatures` |
| `vSecurityManager`, autorisation | `AccountFeatures` |

Points clés :

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

## Etapes

Après chaque modification de fichier, recompilez le projet (automatique sous Eclipse, sinon `mvn compile`) puis relancez la classe main.

1. **Démarrez le node** — complétez `web.xml` avec le listener et le paramètre de configuration (après le `<display-name>`) :

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

et complétez la section `modules` du yaml avec les 3 features de base :

```Yaml
  io.vertigo.commons.CommonsFeatures:
    features:
      - script:
    featuresConfig:
      - script.janino:
  io.vertigo.datamodel.DataModelFeatures:
  io.vertigo.vega.VegaFeatures:
```

(l'ordre des modules dans le yaml est sans importance)

1. **Testez** : lancez `BootSampleUiHello`. Jetty démarre puis l'initialisation échoue avec (attendu, c'est la brique suivante) :
`No qualifying bean of type 'io.vertigo.datastore.kvstore.KVStoreManager'`.

2. **Ajoutez la brique données** dans le yaml :

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

1. **Testez** : relancez. L'initialisation échoue avec (attendu, c'est la brique suivante) :
`component info with id 'vSecurityManager' not found`.

3. **Ajoutez la brique sécurité** — créez la session utilisateur :

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

et complétez le yaml :

```Yaml
  io.vertigo.account.AccountFeatures:
    features:
      - authorization:
      - security:
          userSessionClassName: io.vertigo.samples.uihello.support.SampleUiHelloUserSession
```

1. **Testez** : relancez. Le node démarre maintenant sans erreur.
   Vérification : `http://localhost:18081/uihello/home/` retourne **404** (l'écran n'existe pas encore).

4. **Créez l'écran** — la page (le layout `sampleLayout` est fourni) :

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

1. et le controller qui la rend :

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

1. **Testez** : `http://localhost:18081/uihello/home/` dans le navigateur (ou `curl`).
   Attendu : **200** et le contenu `Hello Vertigo-UI !!` dans la page rendue (dans le layout fourni).
1. Arrêtez le node avec Ctrl+C (le node web ne lit pas le stdin).

## Optionnel : pour aller plus loin

- **Regardez ce que Vertigo-UI injecte** : ouvrez la source de la page rendue et repérez la balise `<script id="vui-init-data">`. Dans la console du navigateur, `VUiPage.vueData` expose le context de la page en vueJs.
- **Détruisez une brique** : retirez `DataModelFeatures` du yaml et relancez. L'erreur est `Components or params not found :smartTypeManager (referenced by collectionsManager<CollectionsManagerImpl>)`. Remettez-la, puis retirez `CommonsFeatures` (`CodecManager`) puis `VegaFeatures` (`JsonEngine`) : chaque feature a son composant.
- **Deuxième page** : créez `about.html` (même pattern : decorate + fragment) et un `AboutController` (`@RequestMapping("/about")`), ajoutez un lien `<a th:href="@{/about/}">` dans le fragment `content` de `home.html`.
- **Pourquoi pas de cache, pas de lucene ?** : `CollectionsManagerImpl` déclare un `Optional<IndexPlugin>` : l'indexation Lucene est un plugin optionnel. Le cache mémoire n'est requis que si un plugin l'injecte. Un node VUI minimal n'en a pas besoin.

# [Suite : sample-vertigo-ui — Level 0](../sample-vertigo-ui/Level0.md)
