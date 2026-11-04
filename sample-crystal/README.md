# sample-crystal

Vertigo DataFactory « Crystal » : accès typé aux données (H2) et moteur de recherche Elasticsearch embarqué.

## Démarrage

- `crystal.run.CrystalSample` : CRUD (lecture d'un film)
- `crystal.run.CrystalSearchSample` : web services REST (Javalin :8081) : `GET /movies/{id}`, `GET /movies/{id}/roles`, recherche `GET|POST /movies/_search` (au départ, le `GET` renvoie au plus 5 résultats et le `POST` lit sa pagination dans les paramètres d'URL `top`, `skip` ; le Level 5 étend cette pagination au `GET`)
- Aucune donnée préchargée : la base H2 est créée en mémoire au démarrage (`crystal.boot.DataBaseInitializer` : `sqlgen/crebas.sql` puis les données de démo `sql/initdata.sql`) ; Elasticsearch est embarqué (données d'index sous `target/classes/search`)
- L'index de recherche est **vide** au premier lancement : la réindexation n'est pas automatique, la route `GET /movies/search/_reindex` est ajoutée au Level 5. Une fois réindexé, l'index est conservé d'un lancement à l'autre (`target/classes/search/data`, à supprimer programme arrêté pour repartir d'un index vide)
- Javalin démarre toujours sur le port 8081 : deux classes main ne peuvent pas tourner en même temps

## Lancer une classe main

Toujours avec la **racine du module** `sample-crystal` comme répertoire de travail (`Studio` lit `studio-config.yaml` en chemin relatif).

- **Depuis l'IDE** : *Run As > Java Application* sur la classe main.
- **En ligne de commande** (Git Bash, depuis la racine du module) :
  ```
  mvn -q compile dependency:build-classpath -Dmdep.outputFile=cp.txt
  printf -- '-cp\n"%s"\n' "target/classes;$(tr '\134' '/' < cp.txt)" > java.args
  java @java.args io.vertigo.samples.crystal.run.CrystalSample
  ```
  - le classpath est trop long pour une ligne de commande Windows : il passe par un fichier d'arguments (`java.args`) ;
  - dans ce fichier, les `\` sont remplacés par des `/` (`\134` est le code octal du backslash) : le backslash y est un caractère d'échappement, et un chemin Windows non converti fait échouer le lancement (`Could not find or load main class`) ;
  - sous Linux ou macOS, le séparateur du classpath est `:` au lieu de `;` ;
  - après chaque modification de code ou régénération : `mvn -q compile` ;
  - les classes console s'arrêtent seules ; les serveurs web (`CrystalSearchSample`, `Level5`) s'arrêtent avec Ctrl+C. Après une erreur au démarrage, une classe console peut aussi rester bloquée : arrêtez-la de la même façon.

## Exercices

Le module est le point de départ des exercices, à suivre dans l'ordre ; la solution est dans [sample-crystal-full](../sample-crystal-full).

- [Level 0 - Prise en main](./Level0.md)
- [Level 1 - Tâches et listes en paramètres](./Level1.md)
- [Level 2 - Accesseurs](./Level2.md)
- [Level 3 - Liste de référence statique](./Level3.md)
- [Level 4 - DAO proxy](./Level4.md)
- [Level 5 - Web services REST et recherche à facettes](./Level5.md)

## Contenu

- `crystal.services` : MovieServices + MovieSearchLoader (l'indexation)
- `crystal.webservices` : MovieWebServices + TestUserSession
- `crystal.config` : la configuration du node

## Code généré

`src/main/javagen` : DAO/domaine/search générés par Vertigo Studio. Le runner `crystal.run.Studio` régénère l'ensemble à partir de `studio-config.yaml`.
