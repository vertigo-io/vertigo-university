# Level 1 - Tâches et listes en paramètres

Nous allons déclarer des requêtes SQL nommées (des tâches) et passer des listes de valeurs en paramètre.

## Eléments

- Définitions : `src/main/resources/dao.ksp`
- Code généré : `CrystalPAO.getActorsIdsByMovie(Long movId)` et `MovieDAO.getMoviesInCountries(List<Long> countriesIds)`
- Services (`src/main/java/io/vertigo/samples/crystal/services/`) :
  - `MovieServices.getActorsIdsByMovie(Long movId)` : retourne `List<Long>`
  - `MovieServices.getMoviesInCountries(List<Long> countryIds)` : retourne `DtList<Movie>`
- Classe main à créer : `src/main/java/io/vertigo/samples/crystal/run/Level1.java`

### A connaitre : Les tâches

Une tâche est une requête nommée, déclarée dans un fichier `.ksp` :

```
create Task TkGetActorsIdsByMovie {
    className : "io.vertigo.basics.task.TaskEngineSelect"
        request : "
        	select act.act_id 
			from role rol
			join actor act on rol.act_id= act.act_id 
			join movie mov on rol.mov_id = mov.mov_id 
			where mov.mov_id = #movId#
			"
	in movId	{domain : DoId 				cardinality:"1" 	}
	out actors	{domain : DoId			 	cardinality:"*" 	}
}
```

- `className` : le moteur d'exécution (`TaskEngineSelect` pour une lecture) ;
- `request` : le SQL, les paramètres s'écrivent `#nomDuParametre#` ;
- `in` / `out` : les paramètres d'entrée et le résultat, avec leur domaine et leur cardinalité (`"1"` : une valeur, `"*"` : une liste).

Le générateur place la méthode Java correspondante :

- dans le DAO de l'entité retournée quand la tâche retourne une entité (`out` de domaine `DoDtMovie` → `MovieDAO`) ;
- sinon dans le PAO du package (`CrystalPAO`).

### A connaitre : Les listes en paramètre

Avec `cardinality:"*"`, un paramètre d'entrée est une liste. Dans un `in (...)` SQL, on l'écrit `#nomDuParametre.rownum#` : chaque valeur de la liste devient un paramètre de la requête.

## Etapes

1. Dans `dao.ksp`, déclarez la tâche `TkGetActorsIdsByMovie` ci-dessus.
2. Déclarez la tâche `TkGetMoviesInCountries` :
   - requête : `select * from movie where cou_id in ( #countriesIds.rownum#)`
   - entrée : `countriesIds`, domaine `DoId`, cardinalité `"*"`
   - sortie : `movies`, domaine `DoDtMovie`, cardinalité `"*"`
3. Lancez `Studio` depuis la racine du module.
   **Vérification** : `CrystalPAO` contient `getActorsIdsByMovie` et `MovieDAO` contient `getMoviesInCountries`.
4. Ajoutez les deux méthodes à `MovieServices` et implémentez-les dans `MovieServicesImpl`, en appelant `crystalPAO.getActorsIdsByMovie(movId)` et `movieDAO.getMoviesInCountries(countryIds)` (vérifiez d'abord les paramètres avec `Assertion.check().isNotNull(...)`).
5. Créez `Level1` en copiant `CrystalSample` :
   - déclarez les constantes des données de démonstration (voir `sql/initdata.sql`) : `FORREST_GUMP_ID = 1000L`, `USA_ID = 840L`, `GERMANY_ID = 250L` ;
   - `step1()` : affichez `movieServices.getActorsIdsByMovie(FORREST_GUMP_ID)` ;
   - `step2()` : affichez `movieServices.getMoviesInCountries(Arrays.asList(USA_ID, GERMANY_ID))` ;
   - appelez `step1()` puis `step2()` dans le `main`.
6. Lancez `Level1`.
   **Vérification** : la console affiche `Level1 - [1000, 1002]` (Tom Hanks et Meryl Streep), puis une liste de taille 5 (`size=5` : 4 films américains et 1 allemand).

**Bon à savoir** : un composant ne se déclare qu'une fois. `CrystalPAO` est déjà déclaré dans le module `Sample` : le déclarer aussi dans `stepDao` fait échouer le démarrage avec `Two components with the same id :'crystalPAO'` (le programme ne s'arrête alors pas de lui-même : arrêtez-le à la main).

## Optionnel : pour aller plus loin

- Observez dans la console les traces d'exécution des tâches, par exemple pour `TkGetActorsIdsByMovie` :
  - logger `sql` : `Finish /execute/TkGetActorsIdsByMovie successfully in (... ms) measures:{..., nbSelectedRow=2.0}`, suivi de la requête exécutée (`metadatas:{statement=...}`) ;
  - logger `tasks` : `Finish /execute/TkGetActorsIdsByMovie successfully in (... ms) measures:{..., sqlRowCount=2.0}`.
  L'ordre des valeurs dans `measures` peut varier d'une exécution à l'autre.
- Remplacez la liste de pays par d'autres identifiants de `sql/initdata.sql` et vérifiez le nombre de films retournés.

# [Suite : Level 2 - Accesseurs](./Level2.md)
