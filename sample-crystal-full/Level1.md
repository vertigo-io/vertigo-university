# Level 1 - Tâches et listes en paramètres

Nous allons écrire nos premières requêtes SQL nommées : les **tâches**.
Nous verrons aussi comment passer une liste de valeurs en paramètre d'une requête.

À la fin de ce Level, vous saurez :
- choisir entre les méthodes du DAO et une tâche ;
- déclarer une tâche dans les définitions et retrouver la méthode générée par Studio ;
- passer une liste de valeurs en paramètre d'une requête.

## Eléments

- Classe main : `/src/main/java/io/vertigo/samples/crystal/run/Level1`
- Définitions : `/src/main/resources/dao.ksp`
- Services : `MovieServices.getActorsIdsByMovie` et `MovieServices.getMoviesInCountries`
- Documentation : [vertigo-docs : DAO](https://vertigo-io.github.io/vertigo-docs/#/basic/dao)

### A connaitre : Store ou tâche ?

Les DAO générés par Studio offrent déjà des méthodes d'accès aux données : création, sélection par la clé primaire, mise à jour, suppression et sélection par critères (l'API `Criteria`, utilisée dans `sample-dao` avec `findAll`). Elles couvrent une grande partie des besoins pour un effort très faible.

Ces méthodes sont volontairement limitées à **une seule entité métier** : c'est l'assurance de bonnes performances. Dès que la requête devient plus complexe, notamment lorsque plusieurs entités sont concernées (une jointure, un agrégat), on écrit une **tâche** dédiée.

**Attention** : privilégiez toujours les manipulations ensemblistes. Charger une liste puis appeler le DAO pour chacun de ses éléments multiplie les requêtes, avec un coût en performances important et non maîtrisé : une seule tâche fait le même travail en une requête.

### A connaitre : Les tâches

Une tâche (*Task*) est définie par :
- un nom, qui commence par convention par `Tk` ;
- un moteur d'exécution (`className`) : `TaskEngineSelect` pour une sélection, `TaskEngineProc` pour une mise à jour ou une suppression ;
- une requête, dans laquelle les paramètres d'entrée sont bindés par la syntaxe `#nomParametre#` ;
- des paramètres d'entrée (`in`) et un paramètre de sortie optionnel (`out`), chacun avec un *domain* (son type au sens Vertigo) et une cardinalité (`"1"`, `"?"` ou `"*"`).

Elle se déclare dans un fichier de définitions (`.ksp`). Par exemple, les identifiants des acteurs d'un film :

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

Pour un objet, le domain se nomme `Do` suivi du nom de sa définition : `DoDtMovie` pour `DtMovie` (la classe `Movie`), `DoDtActor` pour `DtActor`.

Une fois la tâche déclarée, Studio génère la méthode Java qui l'exécute. Elle est placée dans le **DAO de l'objet concerné** par les paramètres (en entrée ou en sortie) lorsqu'il n'y a pas d'ambiguïté, sinon dans le **PAO** (*Package Access Object*) du package : ici `CrystalPAO`. Une tâche dont aucun paramètre n'est un objet va donc dans le PAO.
Ce sont les **paramètres** qui décident, pas la requête : peu importe les tables jointes dans le SQL.

### A connaitre : Une liste en paramètre

Un paramètre de cardinalité `"*"` est une liste. Pour une liste de valeurs simples, on écrit `#nomParametre.rownum#` dans la requête : dans une clause `in (...)`, chaque valeur de la liste devient un paramètre bindé.

## Etapes

1. Dans `dao.ksp`, déclarez la tâche `TkGetActorsIdsByMovie` ci-dessus.
1. Déclarez une seconde tâche `TkGetMoviesInCountries`, qui retourne les films d'une liste de pays.
*(requête : `select * from movie where cou_id in ( #countriesIds.rownum#)` ; en entrée `countriesIds` de domain `DoId`, en sortie `movies` de domain `DoDtMovie`, tous deux de cardinalité `"*"`)*
1. Lancez `Studio` et retrouvez les méthodes générées : `CrystalPAO.getActorsIdsByMovie` et `MovieDAO.getMoviesInCountries`.
1. **Question** : pourquoi Studio a-t-il placé la première méthode dans `CrystalPAO` et la seconde dans `MovieDAO` ? Relisez la règle de placement ci-dessus.
1. Ajoutez les deux méthodes à `MovieServices` et implémentez-les dans `MovieServicesImpl`, en appelant le PAO et le DAO.
*(vérifiez d'abord les paramètres avec `Assertion.check().isNotNull(...)`)*
1. Créez la classe `Level1` en copiant `CrystalSample`.
1. Déclarez en constantes les identifiants utiles des données de démonstration *(voir `sql/initdata.sql`)* : `FORREST_GUMP_ID = 1000L`, `USA_ID = 840L`, `GERMANY_ID = 250L`.
1. Dans un `step1`, affichez les identifiants des acteurs de Forrest Gump ; dans un `step2`, les films des États-Unis et d'Allemagne *(`Arrays.asList(USA_ID, GERMANY_ID)`)*.
*(avec Log4j, une valeur passée en paramètre n'est affichée que si le message contient un marqueur `{}` : `info("actors : {}", actorsIds)`)*
1. Lancez `Level1` : vous devez voir les acteurs `[1000, 1001, 1002]` (Tom Hanks, Robin Wright et Gary Sinise), puis une liste de 11 films (10 américains et 1 allemand).
1. Observez dans la console les traces des tâches *(`Finish /execute/TkGetActorsIdsByMovie ...`)* : vous y trouvez la requête exécutée, sa durée et le nombre de lignes lues. C'est votre premier outil pour comprendre ce que fait l'application en base.

### Ce qu'il faut retenir

- Les méthodes du DAO suffisent pour une seule entité ; dès que plusieurs entités sont concernées, on écrit une tâche.
- Une requête se déclare comme une tâche dans les définitions, jamais dans le code Java : Studio génère la méthode.
- La méthode générée va dans le DAO de l'objet concerné, ou dans le PAO du package quand il n'y en a pas.

# [Suite : Level 2 - Accesseurs](./Level2.md)

### Optionnel : D'autres pays

Changez la liste de pays avec d'autres identifiants de `sql/initdata.sql` et vérifiez le nombre de films retournés.
