# Level Socle 0 - Point de départ : préparation de l'environnement

Ce Level est le point de départ commun des deux séries de cet exercice : la série Socle et la série UI.

1. Rien à préparer pour la base de données : la base H2 est créée en mémoire au démarrage, par l'initializer `io.vertigo.samples.support.boot.DataBaseInitializer` (`src/main/javagen/sqlgen/crebas.sql` pour le schéma, puis `src/main/resources/sql/initdata.sql` pour les données de démo)
2. Récupérer les sources du github [vertigo-io/vertigo-university/sample-vertigo-ui](https://github.com/vertigo-io/vertigo-university/tree/master/sample-vertigo-ui)
3. Créer le projet Eclipse (Import Maven Project)
4. Démarrer l'application `Run Application BootSampleVui` *(dans /src/main/java/io/vertigo/samples/BootSampleVui.java)*
5. Vérifier le fonctionnement [http://localhost:18080/sample/home/](http://localhost:18080/sample/home/)


Dans le web.xml, remarquez le Listener utilisé, et le paramètre `boot.applicationConfiguration`.

Retrouvez la classe `VuiVSpringWebApplicationInitializer` : c'est le point de départ de l'initialisation de SpringMVC.
Il référence la classe `VuiVSpringWebConfig`. Elle porte les annotations de scan de SpringMVC `@ComponentScan` : une ligne par package, il n'y a pas de scan des sous packages. Elle contient les points de hook pour le paramétrage avancé de SpringMVC et VertigoUi.

Retrouver et regardez le controller `HomeController.java` et la page de la vue `home.html`.

Retrouver et regardez le fichier mermaid-io-vertigo-samples-vui.html, il est généré par Studio et propose une visualisation graphique du modele de données.

La class `io.vertigo.samples.support.mda.StudioGenerate` permet de lancer la génération de Studio. Dans la série UI, vous n'en aurez pas besoin : elle reste concentrée sur Vertigo-UI.

# [Suite : Level Ui 1 - Liste simple](./LevelUi1.md)
