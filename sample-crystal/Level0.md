# Level 0 - Prise en main

Nous allons faire tourner le point de départ de l'exercice : un node Vertigo avec une base de données, un moteur de recherche et des web services, sans aucune donnée préchargée.

## Eléments

- Classes main (package `io.vertigo.samples.crystal.run`) :
  - `CrystalSample` : lit un film en base et l'affiche dans la console
  - `CrystalSearchSample` : démarre les web services REST sur [http://localhost:8081](http://localhost:8081/movies/1000)
  - `Studio` : régénère le code à partir des définitions (`studio-config.yaml`)
- Configuration du node : `src/main/java/io/vertigo/samples/crystal/config/SampleConfigBuilder.java`
- Données de démonstration : `src/main/resources/sql/initdata.sql`

### A connaitre : Le node du sample

`SampleConfigBuilder.createNodeConfigBuilder(withSearch, withVega)` construit la configuration du node :

- une base H2 **en mémoire**, créée à chaque démarrage par `boot.DataBaseInitializer` : le schéma généré (`sqlgen/crebas.sql`) puis les données de démonstration (`sql/initdata.sql` : 6 films, 6 acteurs, leurs rôles, 5 pays) ;
- avec `withSearch` : un serveur Elasticsearch embarqué (données d'index sous `target/classes/search`) et le plugin de recherche ;
- avec `withVega` : les web services REST, servis par Javalin sur le port 8081.

Chaque classe main ajoute ensuite deux modules : `stepDao` (vide au départ, vous y déclarerez les composants nécessaires à chaque Level) et `Sample` (les DAO, les services et les web services du sample).

### A connaitre : Le code généré

Le modèle est décrit dans les fichiers de définitions (`src/main/resources/*.ksp`, listés par `application.kpr`). Le runner `Studio` génère à partir d'eux le code de `src/main/javagen` : les objets du domaine, les DAO, les requêtes (`CrystalPAO`), le client de recherche (`MovieIndexSearchClient`) et le SQL de création de base.

Le code généré ne se modifie jamais à la main : on modifie les définitions, puis on régénère.

## Etapes

1. Installez le dépôt dans votre repo Maven local : `mvn install` à la racine du dépôt (le parent des samples n'est pas publié sur Maven Central).
2. Lancez `CrystalSample`.
   **Vérification** : la console affiche `CrystalSample - DtMovie(couId=840, imdbid=tt0109830, movId=1000, name=Forrest Gump, year=1994)` et le programme s'arrête. Cette ligne est en **fin** de console, après les traces d'initialisation (les insertions de `initdata.sql`, le démarrage d'Elasticsearch).
3. Lancez `Studio` **depuis la racine du module** `sample-crystal` (il lit `studio-config.yaml` en chemin relatif).
   **Vérification** : Studio affiche `Création de 19 fichiers, Mise à jour de 0 fichiers, 0 fichiers identiques et 0 problemes` : il réécrit tous les fichiers générés, mais leur contenu est identique. `git status` ne montre aucune modification : le code généré correspond déjà aux définitions.
4. Lancez `CrystalSearchSample` et attendez la fin des traces de démarrage (création de la base, puis démarrage d'Elasticsearch et de Javalin), puis appelez :
   - [http://localhost:8081/movies/1000](http://localhost:8081/movies/1000) : **200**, le film Forrest Gump en JSON
   - [http://localhost:8081/movies/1000/roles](http://localhost:8081/movies/1000/roles) : **200**, 2 rôles
   - [http://localhost:8081/movies/_search?q=gump](http://localhost:8081/movies/_search?q=gump) : **200**, `"totalCount": 0`
5. Arrêtez `CrystalSearchSample` (Ctrl+C).

**Bon à savoir** :

- la recherche ne trouve rien car l'index de recherche démarre **vide** : la réindexation n'est pas automatique. Vous ajouterez la route qui la déclenche au Level 5 ;
- la base est créée juste après le démarrage du serveur web : un appel lancé trop tôt peut répondre **500** (`[SQL error]`, table `MOVIE` absente). Attendez la fin du démarrage, puis relancez l'appel ;
- après une **erreur au démarrage** (un composant déclaré deux fois, une dépendance manquante…), le message d'erreur s'affiche mais le programme peut ne pas s'arrêter de lui-même : arrêtez-le à la main (bouton Stop de l'IDE ou Ctrl+C).

# [Suite : Level 1 - Tâches et listes en paramètres](./Level1.md)
