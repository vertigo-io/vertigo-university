# Vertigo Ui University

Vertigo-UI is built over Vue.js, Quasar and SpringMVC with Thymeleaf.


Lectures en prérequis :

- Doc Vertigo : Vertigo-docs [vertigo-io.github.io](https://vertigo-io.github.io/vertigo-docs/#/)
- Doc Thymeleaf : [Tutorial: Using Thymeleaf](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html)
- Doc Vuejs (en vitesse) pour avoir les concepts : [Introduction — Vue.js (vuejs.org)](https://v2.vuejs.org/v2/guide/?redirect=true)


Les exercices portent sur une application déjà construite : la structure de projet, les objets du modèle et les services sont présents.

*Note: Il est préférable de suivre les exercices dans l'ordre.*

Point de départ, commun aux deux séries :

- [Level Socle 0 - Point de départ : préparation de l'environnement](./LevelSocle0.md)

Série Socle (le fonctionnement de l'application : configuration, génération, modèle, tâches, modules, services, webservices, transaction et erreurs) :

- [Level Socle 1 - Le démarrage et la configuration](./LevelSocle1.md)
- [Level Socle 2 - Régénérer le code](./LevelSocle2.md)
- [Level Socle 3 - Le modèle](./LevelSocle3.md)
- [Level Socle 4 - Les tâches](./LevelSocle4.md)
- [Level Socle 5 - Modules et packages](./LevelSocle5.md)
- [Level Socle 6 - Services, DAO et webservices](./LevelSocle6.md)
- [Level Socle 7 - La transaction et les erreurs](./LevelSocle7.md)

Série UI (les écrans Vertigo-UI) :

- [Level Ui 0 - Point de départ](./LevelUi0.md)
- [Level Ui 1 - Liste simple](./LevelUi1.md)
- [Level Ui 2 - Ecran de détail](./LevelUi2.md)
  - [Level Ui 2.1 - Ecran de détail - Liste de référence des pays](./LevelUi2.1.md)
  - [Level Ui 2.2 - Ecran de détail - Edition](./LevelUi2.2.md)
  - [Level Ui 2.3 - Ecran de détail - Affichage des tableaux](./LevelUi2.3.md)
  - [Level Ui 2.4 - Ecran de détail - Tri coté serveur](./LevelUi2.4.md)
  - [Level Ui 2.5 - Ecran de détail - Validation coté serveur en Ajax](./LevelUi2.5.md)
  - [Level Ui 2.6 - Attributs complémentaires et évènements](./LevelUi2.6.md)
  - [Level Ui 2.7 - Gestion des slots](./LevelUi2.7.md)
- [Level Ui 3 - Recherche](./LevelUi3.md)
- [Level Ui 4 - Tableaux éditables](./LevelUi4.md)
- [Level Ui 5 - Controllers et Context transverses](./LevelUi5.md)
- [Level Ui 6 - Modales](./LevelUi6.md)

## Lancer une classe main

Toujours avec la **racine du module** comme répertoire de travail (`StudioGenerate` lit `studio-config.yaml` en chemin relatif).

- **Depuis l'IDE** : *Run As > Java Application* sur la classe main (`BootSampleVui` pour l'application, `io.vertigo.samples.support.mda.StudioGenerate` pour la génération).
- **En ligne de commande** (Git Bash, depuis la racine du module) :
  ```
  mvn -q compile dependency:build-classpath -Dmdep.outputFile=target/cp.txt
  printf -- '-cp\n"%s"\n' "target/classes;$(tr '\134' '/' < target/cp.txt)" > java.args
  java @java.args io.vertigo.samples.BootSampleVui
  ```
  - le classpath est trop long pour une ligne de commande Windows : il passe par un fichier d'arguments (`java.args`, ignoré par git comme `target/`) ;
  - dans ce fichier, les `\` sont remplacés par des `/` (`\134` est le code octal du backslash) : le backslash y est un caractère d'échappement, et un chemin Windows non converti fait échouer le lancement (classes introuvables) ;
  - sous Linux ou macOS, le séparateur du classpath est `:` au lieu de `;` ;
  - après chaque modification de code ou régénération : `mvn -q compile` ;
  - l'application démarre sur [http://localhost:18080/sample/home/](http://localhost:18080/sample/home/) et s'arrête avec Ctrl+C ; la génération (`StudioGenerate`) s'arrête seule.
