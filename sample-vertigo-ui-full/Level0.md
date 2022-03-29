# Level 0 - Préparation de l'environnement

1. Récupérer les fichiers de la base de données H2 et de l'index ElasticSearch. Et posez les en `D:/atelier/`
2. Récupérer les sources du github [vertigo-io/vertigo-university/sample-vertigo-ui](https://github.com/vertigo-io/vertigo-university/tree/master/sample-vertigo-ui)
3. Créer le projet Eclipse (Import Maven Project)
4. Démarrer l'application `Run Application BootSampleVui` *(dans /vertigo-sample-vertigo-ui-full/src/main/java/io/vertigo/samples/BootSampleVui.java)*
5. Vérifier le fonctionnement [http://localhost:18080/sample/home/](http://localhost:18080/sample/home/)

Dans le web.xml, remarquez le Listener utilisé, et le paramètre `boot.applicationConfiguration`.

Retrouvez la classe `VuiVSpringWebApplicationInitializer` : c'est le point de départ de l'initialisation de SpringMVC.
Il référence la classe `VuiVSpringWebConfig`. Elle porte les annotations de scan de SpringMVC `@ComponentScan` : une ligne par package, il n'y a pas de scan des sous packages. Elle contient les points de hook pour le paramétrage avancé de SpringMVC et VertigoUi.

Retrouver et regardez le controller `HomeController.java` et la page de la vue `home.html`.

[Suite : Level 1 - Liste simple](./Level1.md)
