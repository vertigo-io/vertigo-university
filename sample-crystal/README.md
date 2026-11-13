# sample-crystal

Vertigo DataFactory « Crystal » : accès typé aux données (H2) et moteur de recherche Elasticsearch embarqué.

## Démarrage

- `crystal.run.CrystalSample` : CRUD (lecture d'un film)
- `crystal.run.CrystalSearchSample` : web services REST (Javalin :8081) : `GET /movies/{id}`, `GET /movies/{id}/roles`, recherche `GET|POST /movies/_search` (complétée au Level 4)
- Aucune donnée préchargée : la base H2 est créée en mémoire au démarrage (`crystal.boot.DataBaseInitializer` : `sqlgen/crebas.sql` puis les données de démo `sql/initdata.sql`) ; Elasticsearch est embarqué (données d'index sous `target/classes/search`)
- L'index de recherche est créé au premier démarrage et conservé d'un lancement à l'autre (`target/classes/search/data`, à supprimer programme arrêté pour repartir d'un index neuf). Il reste **vide** tant qu'il n'est pas réindexé : la réindexation n'est pas automatique, la route `GET /movies/search/_reindex` est ajoutée au Level 4
- Javalin démarre toujours sur le port 8081 : deux classes main ne peuvent pas tourner en même temps

## Lancer une classe main

Toujours avec la **racine du module** `sample-crystal` comme répertoire de travail (`Studio` lit `studio-config.yaml` en chemin relatif). Depuis l'IDE : *Run As > Java Application* sur la classe main.

- les classes console s'arrêtent seules ; les serveurs web (`CrystalSearchSample`, `Level4`) s'arrêtent avec Ctrl+C ou le bouton *Terminate* de l'IDE. Après une erreur au démarrage, une classe console peut aussi rester bloquée : arrêtez-la de la même façon ;
- après une régénération, rafraîchissez le projet dans l'IDE : le code généré est écrit hors de l'IDE ;
- **le démarrage d'un serveur** est terminé quand les traces s'arrêtent sur la dernière insertion de données (`INSERT INTO ROLE ...`) : un appel lancé avant peut répondre 404 ou 500, il suffit de le relancer.

## Exercices

Le module est le point de départ des exercices, à suivre dans l'ordre ; la solution est dans [sample-crystal-full](../sample-crystal-full).

- [Level 0 - Prise en main](./Level0.md)
- [Level 1 - Tâches et listes en paramètres](./Level1.md)
- [Level 2 - Accesseurs](./Level2.md)
- [Level 3 - Liste de référence statique](./Level3.md)
- [Level 4 - Web services REST et recherche à facettes](./Level4.md)

Pour aller plus loin, hors du parcours normal :

- [Level 5 - DAO proxy](./Level5.md)

## Contenu

- `crystal.services` : MovieServices + MovieSearchLoader (l'indexation)
- `crystal.webservices` : MovieWebServices + TestUserSession
- `crystal.config` : la configuration du node

## Code généré

`src/main/javagen` : DAO/domaine/search générés par Vertigo Studio. Le runner `crystal.run.Studio` régénère l'ensemble à partir de `studio-config.yaml`.
