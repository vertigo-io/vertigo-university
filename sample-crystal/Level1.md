# Level 1 - Tâches et listes en paramètres

Nous allons écrire nos premières requêtes SQL nommées : les tâches.
Nous verrons aussi comment passer une liste de valeurs en paramètre d'une requête.

## Eléments

- Classe main : `/src/main/java/io/vertigo/samples/crystal/run/Level1`
- Définitions : `/src/main/resources/dao.ksp`
- Services : `MovieServices.getActorsIdsByMovie` et `MovieServices.getMoviesInCountries`

### A connaitre : Les tâches

Dans Vertigo, une requête SQL est une **tâche** : elle a un nom, une requête et des paramètres typés. On la déclare dans les fichiers de définitions (ici `dao.ksp`), et Studio génère la méthode Java qui l'exécute.

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

- `className` : le moteur d'exécution, `TaskEngineSelect` pour une lecture.
- `request` : le SQL. Les paramètres s'écrivent `#nomDuParametre#`.
- `in` / `out` : les paramètres et le résultat, avec leur domaine et leur cardinalité (`"1"` pour une valeur, `"*"` pour une liste).

Par convention, le nom d'une tâche commence par `Tk`.

Studio range la méthode générée selon ce que retourne la tâche : dans le DAO de l'entité si elle retourne des entités (`out` en `DoDtMovie` → `MovieDAO`), sinon dans le PAO du package (`CrystalPAO`).

**Rappel** : le code généré ne se modifie jamais à la main. Si la méthode ne convient pas, on corrige la définition et on régénère.

### A connaitre : Les listes en paramètre

Un paramètre `in` de cardinalité `"*"` est une liste. Dans un `in (...)` SQL, on l'écrit `#nomDuParametre.rownum#` : chaque valeur de la liste devient un paramètre de la requête.

## Etapes

1. Dans `dao.ksp`, déclarez la tâche `TkGetActorsIdsByMovie` ci-dessus.
1. Déclarez une seconde tâche `TkGetMoviesInCountries`, qui retourne les films d'une liste de pays.
*(requête : `select * from movie where cou_id in ( #countriesIds.rownum#)` ; en entrée `countriesIds` de domaine `DoId`, en sortie `movies` de domaine `DoDtMovie`, tous deux de cardinalité `"*"`)*
1. Lancez `Studio` et retrouvez les méthodes générées : `CrystalPAO.getActorsIdsByMovie` et `MovieDAO.getMoviesInCountries`.
1. Ajoutez les deux méthodes à `MovieServices` et implémentez-les dans `MovieServicesImpl` en appelant le PAO et le DAO.
*(pensez à vérifier les paramètres avec `Assertion.check().isNotNull(...)`)*
1. Créez la classe `Level1` en copiant `CrystalSample`.
1. Déclarez les identifiants des données de démonstration utiles en constantes *(voir `sql/initdata.sql`)* : `FORREST_GUMP_ID = 1000L`, `USA_ID = 840L`, `GERMANY_ID = 250L`.
1. Dans un `step1`, affichez les identifiants des acteurs de Forrest Gump ; dans un `step2`, les films des États-Unis et d'Allemagne *(`Arrays.asList(USA_ID, GERMANY_ID)`)*.
1. Lancez `Level1` : vous devez voir les acteurs `[1000, 1002]` (Tom Hanks et Meryl Streep), puis une liste de 5 films.
1. Observez dans la console les traces des tâches exécutées *(`Finish /execute/TkGetActorsIdsByMovie ...`)* : la requête, sa durée et le nombre de lignes lues.

**Attention** : un composant ne se déclare qu'une fois. `CrystalPAO` est déjà déclaré dans le module `Sample` : le déclarer aussi dans `stepDao` fait échouer le démarrage (`Two components with the same id :'crystalPAO'`).

# [Suite : Level 2 - Accesseurs](./Level2.md)

### Optionnel : D'autres pays

Changez la liste de pays avec d'autres identifiants de `sql/initdata.sql` et vérifiez le nombre de films retournés.
