# Level Socle 0 - Point de départ : démarrer l'application

Nous allons récupérer l'application de l'exercice, la démarrer et vérifier qu'elle répond.
Ce Level est le point de départ commun des deux séries de cet exercice : la série Socle et la série UI.

À la fin de ce Level, vous saurez :
- importer le module et lancer l'application ;
- lire l'adresse d'une page : le chemin de l'application, puis la route d'un controller.

## Eléments

- Classe main : `/src/main/java/io/vertigo/samples/BootSampleVui.java`
- Route : [http://localhost:18080/sample/home/](http://localhost:18080/sample/home/)
- Page d'accueil : le controller `/src/main/java/io/vertigo/samples/vui/controllers/HomeController.java` et sa vue `/src/main/resources/webapp/WEB-INF/views/vui/home.html`

### A connaitre : Une application déjà construite

L'application de cet exercice gère des films, leurs acteurs et leurs pays. Elle est déjà construite : la structure du projet, le modèle de données et les services sont en place. La série Socle vous apprend à vous y repérer et à la faire évoluer ; la série UI, à en construire les écrans.

Sa base de données H2 est en mémoire : elle est recréée à chaque démarrage, avec les données de démonstration : 20 films réels, connus en France, avec leurs acteurs et leurs rôles (les identifiants commencent à 1000). Il n'y a rien à installer.

## Etapes

1. Clonez le dépôt [vertigo-io/vertigo-university](https://github.com/vertigo-io/vertigo-university), puis importez le module `sample-vertigo-ui` comme projet Maven dans votre IDE.
*(sous Eclipse : Import > Existing Maven Projects ; le module hérite du `pom.xml` à la racine du dépôt)*
1. Lancez `BootSampleVui` *(voir le [README](./README.md#lancer-une-classe-main))*.
1. Ouvrez [http://localhost:18080/sample/home/](http://localhost:18080/sample/home/) : vous devez voir `Hello world !! It works..`.
1. **Question** : dans cette adresse, d'où vient `/sample`, et d'où vient `/home/` ? *(regardez `BootSampleVui`, puis `HomeController`)*

### Ce qu'il faut retenir

- L'application démarre par une classe main, et répond sous le chemin `/sample`.
- Sa base est en mémoire, recréée à chaque démarrage avec les données de démonstration : rien à installer.
- L'adresse d'une page se lit en deux parties : le chemin de l'application, puis la route d'un controller.

# Suite

- Série Socle : [Level Socle 1 - Le démarrage et la configuration](./LevelSocle1.md)
- Série UI : [Level Ui 1 - Liste simple](./LevelUi1.md)
