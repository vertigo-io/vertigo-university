# Level Socle 3 - Le modèle

Nous allons lire le modèle de l'application : ses objets, leurs champs, et les types de ces champs.
Puis nous ajouterons un champ à un objet, et nous suivrons ce que la génération en fait.

À la fin de ce Level, vous saurez :
- distinguer une entité, un objet de transfert (DTO) et une liste de référence statique ;
- remonter d'un champ à son domain, puis à son SmartType et à ses contraintes ;
- ajouter un champ au modèle et retrouver son effet dans le code généré et dans la base.

## Eléments

- Définitions : `/src/main/resources/definitions/model.ksp`, `modelUser.ksp`, `alter.ksp`, `domains.ksp`, et les valeurs `masterDataValues.json`
- SmartTypes : `/src/main/java/io/vertigo/samples/support/config/SampleVuiSmartTypes.java`
- Code généré : `/src/main/javagen/io/vertigo/samples/vui/domain`
- Concepts illustrés : DtObject, Entity, KeyConcept, Domain, SmartType, Constraint, Formatter *(voir le glossaire [vertigo-docs : Concepts](https://vertigo-io.github.io/vertigo-docs/#/basic/concepts))*

### A connaitre : Les objets du modèle

Un objet se déclare par `create DtDefinition`. Chaque champ a un nom, un *domain* (son type au sens Vertigo, voir plus bas), un libellé, et une cardinalité (`"1"` pour un champ obligatoire).
- Un objet qui a un identifiant (`id`) est une **entité** : il est stocké en base (une table) et Studio lui génère un DAO. Par exemple `DtMovie`, `DtCountry`.
- Un objet sans identifiant est un objet de transfert (**DTO**) : il n'est pas stocké, il transporte des données. Par exemple `DtMovieDisplay` : une ligne de liste, le film avec le nom de son pays, construite par une requête qui joint deux tables (Level Socle 4).
- Un **stéréotype** précise le rôle d'une entité :
  - `KeyConcept` marque un objet métier principal de l'application : ici `DtMovie` ;
  - `StaticMasterData` (dans `alter.ksp`) marque une liste de référence **statique** : ici `DtSexe`. Ses valeurs sont dans `masterDataValues.json`, et Studio génère l'enum `SexeEnum`.
- Une **association** relie deux objets : `AMovCou` relie un film à son pays, par le champ `couId` du film (`"*>?"` : plusieurs films pour un pays, le pays étant facultatif).

### A connaitre : Domains, SmartTypes et contraintes

Un **domain** (`domains.ksp`) déclare un type de données : son type Java (`dataType`) et son type de stockage SQL (`storeType`). À l'exécution, il correspond à un **SmartType**, déclaré dans l'enum `SampleVuiSmartTypes` : le domain `DoLabelLong` correspond à la constante `LabelLong`, et le code généré y fait référence sous le nom `STyLabelLong`.

Un SmartType porte tout ce qui caractérise la donnée :
- son type Java (`@SmartTypeDefinition`) ;
- son **formatter** (`@Formatter`), qui convertit la donnée vers le texte affiché ou saisi, et inversement ;
- ses **contraintes** (`@Constraint`), par exemple `ConstraintStringLength` : elles sont vérifiées automatiquement quand des données saisies entrent dans l'application, et leur violation produit une erreur pour l'utilisateur ;
- des propriétés, comme `storeType`, le type de la colonne en base.

## Etapes

1. Dans `model.ksp` et `modelUser.ksp`, listez les entités.
**Question** : lequel de ces objets est un `KeyConcept` ?
1. Retrouvez `DtMovieDisplay`.
**Question** : pourquoi n'a-t-il pas de DAO dans `src/main/javagen` ? *(relisez « Les objets du modèle »)*
1. Ouvrez `alter.ksp`, `masterDataValues.json` puis la classe générée `SexeEnum` : retrouvez les deux valeurs de la liste statique `DtSexe`.
1. Partez du champ `name` de `DtMovie` : son domain, puis son SmartType dans `SampleVuiSmartTypes`.
**Question** : quelle est la longueur maximale d'un titre de film ? Où est-elle contrôlée, et où est-elle appliquée en base ?
1. Ajoutez à `DtMovie`, après le champ `imdbid`, le champ `originalTitle` :
```
  field originalTitle {domain: DoLabelLong label: "Titre original" }
```
1. Régénérez le code *(voir le Level Socle 2)*, puis **observez** le résultat :
   - dans `Movie.java`, le getter `getOriginalTitle()`, annoté `@Field(smartType = "STyLabelLong", label = "Titre original")` ;
   - dans `DtDefinitions`, le champ `originalTitle` de `MovieFields` ;
   - dans `sqlgen/crebas.sql`, la colonne `ORIGINAL_TITLE VARCHAR(500)` de la table `MOVIE`.
1. Relancez l'application : elle démarre sans erreur. La base est recréée à chaque démarrage à partir de `crebas.sql` : elle a maintenant la nouvelle colonne.

### Ce qu'il faut retenir

- Une entité a un identifiant, une table et un DAO ; un DTO n'a ni identifiant ni stockage : il transporte des données.
- Un champ a un domain, qui correspond à un SmartType : le type Java, le formatter, les contraintes et le type de stockage.
- Un champ s'ajoute dans le modèle, puis on régénère : la classe, ses définitions et le SQL suivent.

# [Suite : Level Socle 4 - Les tâches](./LevelSocle4.md)
