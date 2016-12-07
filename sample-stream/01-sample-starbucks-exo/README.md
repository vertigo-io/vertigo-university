#Exercice 1 : Starbucks 

Importer le projet dans Eclipse (Import Existing Maven Project)

## Prérequis 
Vérification le fonctionnement du projet. La méthode printStarbucks doit lister les nom des Starbucks aux USA. 

## Etape 1 : 

En utilisant cette signature :
```
public long countStarbucks(final List<StarbucksDto> starbucks)
```

Créer une implémentation dans StarbucksService retournant le nombre de starbucks.

## Etape 2 :
En utilisant cette signature :
```
public List<String> filterStarbucksState(final List<StarbucksDto> starbucks, final String stateCode)
```

Créer une implémentation dans StarbucksService retournant le nom des Starbucks de l'état d'Alabama.
Hint: getState() de StarbucksDto doit être égal à "AL"


## Etape 3 : 
En utilisant cette signature :
```
public Map<String, Long> villeHistogram(final List<StarbucksDto> starbucks)
```

Créer une implémentation dans StarbucksService retournant une map correspondant au nombre de Starbucks par ville.
ex: 
{
  "New York" : 202,
  "Falcon": 1,
  "Oklahoma City" : 15,
  ...
}

## Etape 4 :
En utilisant cette signature :
```
public Optional<String> villeAvecLePlusDeStarbucks(final List<StarbucksDto> starbucks)
```
Créer une implémentation dans StarbucksService retournant la ville comportant le plus de Starbucks.

## Etape 5 :
En utilisant cette signature :
```
public Optional<String> villeAvecLePlusDeStarbucksFilter(final List<StarbucksDto> starbucks, final Predicate<StarbucksDto> filterPredicate)
```
Créer une implémentation dans StarbucksService retournant la ville comportant le plus de Starbucks selon un prédicat.

Utiliser cette méthode pour récuperer la ville possédant le plus de Starbucks dans l'état du Texas
Hint: getState() de StarbucksDto doit être égal à "TX"

Utiliser cette méthode pour récuperer la ville possédant le plus de Starbucks dans l'état de Californie
Hint: getState() de StarbucksDto doit être égal à "CA"


## Etape 6:
En utilisant cette signature :
```
public List<Map.Entry<String, Long>> villeNMax(final List<StarbucksDto> starbucks, final int nFirst)
```

Créer une implémentation dans StarbucksService retournant la liste des nFirst couple (ville, occurence) comportant le plus de Starbucks de toutes la liste, trié par ordre décroissant.
