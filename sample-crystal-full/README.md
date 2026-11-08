# sample-crystal-full

Vertigo DataFactory « Crystal » : accès typé aux données (H2) et moteur de recherche Elasticsearch embarqué.

C'est le **corrigé** de [sample-crystal](../sample-crystal) : le même projet, avec les mêmes énoncés, une fois les exercices complétés (voir [Le corrigé](#le-corrigé)).

## Démarrage

- `crystal.run.CrystalSample` : CRUD (lecture d'un film)
- `crystal.run.CrystalSearchSample` : web services REST (Javalin :8081) : `GET /movies/{id}`, `GET /movies/{id}/roles`, recherche `GET|POST /movies/_search` (complétée au Level 4)
- Aucune donnée préchargée : la base H2 est créée en mémoire au démarrage (`crystal.boot.DataBaseInitializer` : `sqlgen/crebas.sql` puis les données de démo `sql/initdata.sql`) ; Elasticsearch est embarqué (données d'index sous `target/classes/search`)
- L'index de recherche est créé au premier démarrage et conservé d'un lancement à l'autre (`target/classes/search/data`, à supprimer programme arrêté pour repartir d'un index neuf). Il reste **vide** tant qu'il n'est pas réindexé : la réindexation n'est pas automatique, la route `GET /movies/search/_reindex` est ajoutée au Level 4
- Javalin démarre toujours sur le port 8081 : deux classes main ne peuvent pas tourner en même temps

## Lancer une classe main

Toujours avec la **racine du module** `sample-crystal-full` comme répertoire de travail (`Studio` lit `studio-config.yaml` en chemin relatif).

- **Depuis l'IDE** : *Run As > Java Application* sur la classe main.
- **En ligne de commande** (Git Bash, depuis la racine du module) :
  ```
  mvn -q compile dependency:build-classpath -Dmdep.outputFile=target/cp.txt
  printf -- '-cp\n"%s"\n' "target/classes;$(tr '\134' '/' < target/cp.txt)" > java.args
  java @java.args io.vertigo.samples.crystal.run.CrystalSample
  ```
  - le classpath est trop long pour une ligne de commande Windows : il passe par un fichier d'arguments (`java.args`, ignoré par git comme `target/`) ;
  - dans ce fichier, les `\` sont remplacés par des `/` (`\134` est le code octal du backslash) : le backslash y est un caractère d'échappement, et un chemin Windows non converti fait échouer le lancement (classes introuvables) ;
  - sous Linux ou macOS, le séparateur du classpath est `:` au lieu de `;` ;
  - après chaque modification de code ou régénération : `mvn -q compile` ;
  - les classes console s'arrêtent seules ; les serveurs web (`CrystalSearchSample`, `Level4`) s'arrêtent avec Ctrl+C. Après une erreur au démarrage, une classe console peut aussi rester bloquée : arrêtez-la de la même façon.
- **Le démarrage d'un serveur** est terminé quand les traces s'arrêtent sur la dernière insertion de données (`INSERT INTO ROLE ...`) : un appel lancé avant peut répondre 404 ou 500, il suffit de le relancer.

## Exercices

Les énoncés, à suivre dans l'ordre, sont les mêmes que dans [sample-crystal](../sample-crystal), le point de départ des exercices ; ce module en est la solution.

- [Level 0 - Prise en main](./Level0.md)
- [Level 1 - Tâches et listes en paramètres](./Level1.md)
- [Level 2 - Accesseurs](./Level2.md)
- [Level 3 - Liste de référence statique](./Level3.md)
- [Level 4 - Web services REST et recherche à facettes](./Level4.md)

Pour aller plus loin, hors du parcours normal :

- [Level 5 - DAO proxy](./Level5.md)

## Le corrigé

- `crystal.run.Level1` → `crystal.run.Level5` : la solution de chaque Level (`Level4` démarre les web services sur :8081 ; **l'index est vide au premier lancement** : appeler `GET /movies/search/_reindex` avant de chercher, par exemple `GET /movies/_search?q=the&top=2`)
- `crystal.run.Level6` et `crystal.run.Level7` (hors parcours, sans énoncé) : login puis authorization (entités sécurisées). Le login (`crystal.services.LoginServices`) lit les comptes dans la base H2 (`DT_USER`), mais le mot de passe est vérifié par un serveur LDAP du réseau interne : ces deux Levels ne tournent pas hors de ce réseau et ne sont pas vérifiés

## Contenu

- `crystal.services` : MovieServices + MovieSearchLoader (l'indexation), LoginServices
- `crystal.webservices` : MovieWebServices + TestUserSession
- `crystal.config` : la configuration du node
- `crystal.dao.MovieProxyDAO` : le DAO proxy (Level 5)
- `crystal.authorization` : les définitions d'authorization (Level 7)

## Code généré

`src/main/javagen` : DAO/domaine/search + authorizations générés par Vertigo Studio. Le runner `crystal.run.Studio` régénère l'ensemble à partir de `studio-config.yaml`.
