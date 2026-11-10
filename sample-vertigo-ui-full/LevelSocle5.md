# Level Socle 5 - Modules et packages

Nous allons voir comment l'application est découpée : en modules, et dans chaque module en packages.
Ce découpage est le même d'une application Vertigo à l'autre : une fois compris, il permet de se repérer dans n'importe quel projet.

À la fin de ce Level, vous saurez :
- dire ce que déclare un module de l'application ;
- situer une classe d'après son package ;
- savoir où placer et où enregistrer un nouveau composant.

## Eléments

- Modules de l'application : `/src/main/java/io/vertigo/samples/support/SupportFeatures.java` et `/src/main/java/io/vertigo/samples/vui/VuiFeatures.java`, cités dans `sample-vui.yaml`
- Configuration SpringMVC : `/src/main/java/io/vertigo/samples/support/config/VuiVSpringWebConfig.java`
- Concepts illustrés : Module, Component *(voir le glossaire [vertigo-docs : Concepts](https://vertigo-io.github.io/vertigo-docs/#/basic/concepts))*
- Documentation : [vertigo-docs : Composants](https://vertigo-io.github.io/vertigo-docs/#/basic/composants)

### A connaitre : Les modules de l'application

L'application déclare ses propres classes `Features`, à côté de celles de la plateforme (Level Socle 1), une par package de premier niveau :
- `support` : ce qui sert à toute l'application. Sa classe `SupportFeatures` enregistre les **définitions** : les SmartTypes et les objets générés (`DtDefinitions`), et les listes de référence (`Sexe`, `Country`) ;
- `vui` : le métier, ici les films. Sa classe `VuiFeatures` enregistre les **composants** : le service `MovieServices`, les DAO qu'il utilise et `VuiPAO`. Sa méthode annotée `@Feature("search")` ajoute les composants de la recherche, seulement quand la feature `search` est déclarée dans le YAML (avec son flag).

Un composant n'existe dans le node que s'il est enregistré dans un module. C'est ce qui permet de l'injecter (`@Inject`) dans un autre composant : un composant manquant fait échouer le démarrage, avec un message qui dit quoi et pour qui.

> **Dans un vrai projet : la découverte automatique.** Ce sample déclare ses composants un par un, pour que vous voyiez le mécanisme. Dans une application réelle (Mars, par exemple), la classe `Features` d'un module hérite de `DefaultUiModuleFeatures` (ou de `ModuleDiscoveryFeatures` pour un module sans écran) : elle enregistre automatiquement tous les composants de son package et de ses sous-packages, c'est-à-dire les classes qui implémentent `Component`. Un nouveau service ou webservice n'y est pas déclaré : il suffit de le créer dans le bon package. La classe `Features` ne déclare en dur que les composants optionnels, comme ceux de la recherche ici : elle les exclut de la découverte (`@NotDiscoverable`) et les ajoute dans une méthode `@Feature`, que le YAML active.

Les controllers sont à part : ce ne sont pas des composants Vertigo, mais des controllers SpringMVC. `VuiVSpringWebConfig` les trouve par `@ComponentScan("io.vertigo.samples.vui.controllers")` : SpringMVC les cherche dans ce package (sous-packages compris). Un controller placé ailleurs n'est pas trouvé.

### A connaitre : Le pattern de packages

Dans un module, chaque package a un rôle :
- `<module>.controllers` : les controllers des écrans ;
- `<module>.services` : les services, le code métier ;
- `<module>.webservices` : les webservices REST (vous créerez celui de `vui` dans ce Level) ;
- générés par Studio dans `src/main/javagen` : `<module>.domain` (les objets), `<module>.dao` (les DAO), `<module>.search` (la recherche), et le PAO du module (`VuiPAO`).

Le module `support` a les siens : `boot` (les initializers), `config` (les SmartTypes, la configuration SpringMVC), `mda` (les lanceurs de Studio)…

*`support` contient aussi `FileUploadController` et `SampleFileServices` : ils sont préparés pour un futur composant d'upload de fichiers, et ne sont pas encore branchés (le controller n'est pas dans le package scanné, le service n'est enregistré dans aucune classe `Features`).*

## Etapes

1. Dans `sample-vui.yaml`, retrouvez les deux classes `Features` de l'application.
1. Ouvrez `VuiFeatures`.
**Question** : quels composants enregistre-t-il toujours, et lesquels seulement avec la feature `search` ?
1. **Question** : pour chacun de ces éléments, donnez son module et son package, puis vérifiez :
   - la classe `Movie` ;
   - le service des films ;
   - le controller de la page d'accueil ;
   - l'enum des SmartTypes ;
   - l'initializer qui crée la base.
1. **Question** : `MovieServices` reçoit `VuiPAO` par injection. Où `VuiPAO` est-il enregistré ?
1. Créez le webservice des films, que vous compléterez au Level Socle 6 : la classe `io.vertigo.samples.vui.webservices.MovieWebServices`, avec une première route qui répond simplement `ok`. Enregistrez-la dans `VuiFeatures` (`.addComponent(MovieWebServices.class)`).
   ```Java
   @PathPrefix("/movies")
   public class MovieWebServices implements WebServices {

   	@GET("/ping")
   	public String ping() {
   		return "ok";
   	}
   }
   ```
   *(l'interface est `io.vertigo.vega.webservice.WebServices`, les annotations `PathPrefix` et `GET` sont dans le package `io.vertigo.vega.webservice.stereotype`)*
1. Relancez l'application et appelez [http://localhost:18080/sample/api/movies/ping](http://localhost:18080/sample/api/movies/ping) : vous devez obtenir `ok`. Le nouveau composant est enregistré, et sa route est servie sous le préfixe `/api`.
*(si l'adresse répond **404** : le webservice n'est pas enregistré dans `VuiFeatures`)*
*(si le démarrage échoue avec, dans la console, `DIException: Components or params not found :…` : un composant n'est enregistré dans aucun module ; le message nomme le composant manquant, puis celui qui en a besoin, par exemple `vuiPAO (referenced by movieServices<MovieServices>)` ; ajoutez-le dans la classe `Features` de son module)*

### Ce qu'il faut retenir

- Un module de l'application est une classe `Features`, citée dans le YAML : elle enregistre ses définitions et ses composants, un par un dans ce sample, par découverte automatique de son package dans un vrai projet.
- Les packages suivent un pattern : `controllers`, `services`, `webservices`, et les packages générés `domain`, `dao`, `search`.
- Dans ce sample, tout nouveau composant s'enregistre dans la classe `Features` de son module : sinon le démarrage échoue, avec un message qui nomme le composant manquant et celui qui l'attend. Dans un vrai projet, il suffit de le créer dans le package du module.

# [Suite : Level Socle 6 - Services, DAO et webservices](./LevelSocle6.md)
