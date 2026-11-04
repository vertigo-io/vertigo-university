# Level 0 - Prise en main

Avant de commencer, nous allons faire tourner le point de départ de l'exercice : un node Vertigo avec une base de données, un moteur de recherche et des web services.
Il n'utilise aucune donnée préchargée : tout est créé au démarrage.

À la fin de ce Level, vous saurez :
- lancer les classes main du sample ;
- lire la configuration d'un node ;
- régénérer le code avec Studio et vérifier ce qu'il produit.

## Eléments

- Classes main : `/src/main/java/io/vertigo/samples/crystal/run/CrystalSample`, `CrystalSearchSample` et `Studio`
- Configuration : `/src/main/java/io/vertigo/samples/crystal/config/SampleConfigBuilder`
- Données de démonstration : `/src/main/resources/sql/initdata.sql`
- Route : [http://localhost:8081/movies/1000](http://localhost:8081/movies/1000)
- Documentation : [vertigo-docs : DataModel](https://vertigo-io.github.io/vertigo-docs/#/extensions/datamodel) *(génération par Studio, syntaxe KSP)*

### A connaitre : Le node du sample

Dans Vertigo, on configure d'abord, on code ensuite : le node déclare les Features qu'il active, avant toute logique métier.
`SampleConfigBuilder.createNodeConfigBuilder(withSearch, withVega)` construit cette configuration :
- une base H2 **en mémoire**, recréée à chaque démarrage par `DataBaseInitializer` : le schéma généré (`sqlgen/crebas.sql`) puis les données de démonstration (`sql/initdata.sql` : 6 films, 6 acteurs, leurs rôles, 5 pays) ;
- avec `withSearch` : un serveur Elasticsearch embarqué et le plugin de recherche ;
- avec `withVega` : les web services REST.

Le serveur web (Javalin) démarre toujours sur le port 8081 : on ne peut donc pas lancer deux classes main en même temps.

Chaque classe main ajoute ensuite deux modules : `stepDao`, vide au départ, où vous déclarerez au fil des Levels les composants dont vous aurez besoin, et `Sample`, qui porte les DAO, les services et les web services du sample.

### A connaitre : Le code généré

Le modèle est décrit dans des fichiers de définitions, écrits en KSP (`src/main/resources/*.ksp`, listés dans `application.kpr`). À partir d'eux, Studio génère le code de `src/main/javagen` : les classes des DtObject, les DAO, les tâches d'accès aux données (`CrystalPAO`), le client de recherche et le script de création de la base.

Ce que Studio génère est piloté par `studio-config.yaml` : chaque propriété active un générateur (`vertigo.domain.java` pour les classes, `vertigo.domain.sql` pour le SQL, `vertigo.task` pour les tâches…).

**Rappel** : le code généré ne se modifie jamais à la main. On modifie les définitions, puis on régénère : c'est le geste central du développement Vertigo.

### A connaitre : La console

Le démarrage est bavard : le serveur web démarre d'abord, puis Elasticsearch, puis la base est créée et remplie (les `create table`, puis les `INSERT`). Le démarrage est terminé quand les traces s'arrêtent sur la dernière insertion (`INSERT INTO ROLE ...`).
Un appel lancé trop tôt peut répondre 404 (routes pas encore publiées) ou 500 (table pas encore créée) : il suffit d'attendre et de relancer l'appel.

Le nom du logger est aligné avec des espaces (`INFO  Level1      - [1000, 1002]`) : pour retrouver une ligne, cherchez plutôt sa fin.

Attention, après une erreur au démarrage (un composant déclaré deux fois, une dépendance manquante…), le programme peut ne pas s'arrêter de lui-même : arrêtez-le à la main *(bouton Stop de l'IDE ou Ctrl+C)*.

## Etapes

1. Installez le dépôt dans votre repo Maven local : `mvn install` à la racine du dépôt.
*(le parent des samples n'est pas publié sur Maven Central)*
1. Lisez `SampleConfigBuilder` et repérez les Features activées par le node.
1. Lancez `CrystalSample` *(voir le [README](./README.md#lancer-une-classe-main) pour lancer une classe main)*. Vers la fin de la console, juste avant les traces d'arrêt, vous devez voir le film Forrest Gump : `DtMovie(couId=840, imdbid=tt0109830, movId=1000, name=Forrest Gump, year=1994)`.
1. Lancez `Studio` depuis la racine du module. Il annonce la création de 19 fichiers *(il réécrit tout le code généré)*, mais leur contenu ne change pas : `git status` reste vide.
1. Lancez `CrystalSearchSample` et attendez la fin du démarrage.
1. Consultez le film [http://localhost:8081/movies/1000](http://localhost:8081/movies/1000) et ses 2 rôles [http://localhost:8081/movies/1000/roles](http://localhost:8081/movies/1000/roles).
1. Testez la recherche [http://localhost:8081/movies/_search?q=gump](http://localhost:8081/movies/_search?q=gump) : elle ne trouve rien (`"totalCount": 0`), car l'index de recherche est encore vide. La réindexation n'est pas automatique : vous ajouterez la route qui la déclenche au Level 5 :)
1. Arrêtez `CrystalSearchSample` *(Ctrl+C)*.

### Ce qu'il faut retenir

- Un node Vertigo se configure par les Features qu'il active ; les composants s'organisent en modules.
- Le modèle est décrit dans les définitions KSP ; Studio en génère le code, qui ne se modifie jamais à la main.
- Le sample n'a besoin d'aucune donnée externe : la base est créée et remplie à chaque démarrage.

# [Suite : Level 1 - Tâches et listes en paramètres](./Level1.md)
