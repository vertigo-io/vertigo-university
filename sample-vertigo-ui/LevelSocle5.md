# Level Socle 5 - Modules et packages

Nous allons voir comment l'application est découpée : en modules, et dans chaque module en packages.
Ce découpage est le même d'une application Vertigo à l'autre : une fois compris, il permet de se repérer dans n'importe quel projet.

À la fin de ce Level, vous saurez :
- dire ce que déclare un module de l'application ;
- situer une classe d'après son package ;
- savoir où enregistrer un nouveau composant, et reconnaître l'erreur quand il manque.

## Eléments

- Modules de l'application : `/src/main/java/io/vertigo/samples/support/SupportFeatures.java` et `/src/main/java/io/vertigo/samples/vui/VuiFeatures.java`, cités dans `sample-vui.yaml`
- Configuration SpringMVC : `/src/main/java/io/vertigo/samples/support/config/VuiVSpringWebConfig.java`
- Concepts illustrés : Module, Component *(voir le glossaire [vertigo-docs : Concepts](https://vertigo-io.github.io/vertigo-docs/#/basic/concepts))*
- Documentation : [vertigo-docs : Composants](https://vertigo-io.github.io/vertigo-docs/#/basic/composants)

### A connaitre : Les modules de l'application

L'application déclare deux modules, à côté de ceux de la plateforme (Level Socle 1) :
- `support` : ce qui sert à toute l'application. Sa classe `SupportFeatures` enregistre les **définitions** : les SmartTypes et les objets générés (`DtDefinitions`), et les valeurs des listes statiques ;
- `vui` : le métier, ici les films. Sa classe `VuiFeatures` enregistre les **composants** : le service `MovieServices`, les DAO et `VuiPAO`. Sa méthode annotée `@Feature("search")` ajoute les composants de la recherche, seulement quand la feature `search` est déclarée dans le YAML (avec son flag).

Un composant n'existe dans le node que s'il est enregistré dans un module. C'est ce qui permet de l'injecter (`@Inject`) dans un autre composant : un composant manquant fait échouer le démarrage, avec un message qui dit quoi et pour qui.

Les controllers sont à part : ce ne sont pas des composants Vertigo, mais des controllers SpringMVC. `VuiVSpringWebConfig` les trouve par `@ComponentScan` : une ligne par package, sans les sous-packages.

### A connaitre : Le pattern de packages

Dans un module, chaque package a un rôle :
- `<module>.controllers` : les controllers des écrans ;
- `<module>.services` : les services, le code métier ;
- `<module>.webservices` : les webservices REST (vous créerez celui de `vui` au Level Socle 6) ;
- générés par Studio dans `src/main/javagen` : `<module>.domain` (les objets), `<module>.dao` (les DAO), `<module>.search` (la recherche), et le PAO du module (`VuiPAO`).

Le module `support` a les siens : `boot` (les initializers), `config` (les SmartTypes, la configuration SpringMVC), `mda` (les lanceurs de Studio)…

## Etapes

1. Dans `sample-vui.yaml`, retrouvez les deux modules de l'application.
1. Ouvrez `VuiFeatures`.
**Question** : quels composants enregistre-t-il toujours, et lesquels seulement avec la feature `search` ?
1. **Question** : pour chacun de ces éléments, donnez son module et son package, puis vérifiez :
   - la classe `Movie` ;
   - le service des films ;
   - le controller de la page d'accueil ;
   - l'enum des SmartTypes ;
   - l'initializer qui crée la base.
1. Retirez de `VuiFeatures` l'enregistrement `.addComponent(VuiPAO.class)`, puis relancez : le démarrage échoue, avec dans la console
`DIException: Components or params not found :vuiPAO (referenced by movieServices<MovieServices>)`.
Lisez ce message : le composant `vuiPAO` manque, et c'est `MovieServices` qui en a besoin.
1. Remettez l'enregistrement : l'application démarre de nouveau.

### Ce qu'il faut retenir

- Un module de l'application est une classe `Features`, citée dans le YAML : elle enregistre ses définitions et ses composants.
- Les packages suivent un pattern : `controllers`, `services`, `webservices`, et les packages générés `domain`, `dao`, `search`.
- Un composant non enregistré fait échouer le démarrage : le message nomme le composant manquant et celui qui l'attend.

# [Suite : Level Socle 6 - Services, DAO et webservices](./LevelSocle6.md)
