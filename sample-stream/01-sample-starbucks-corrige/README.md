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

## Etape 3 :
En utilisant cette signature :
```
public Optional<String> villeAvecLePlusDeStarbucks(final List<StarbucksDto> starbucks)
```
Créer une implémentation dans StarbucksService retournant la ville comportant le plus de Starbucks.

## Etape 4 :
En utilisant cette signature :
```
public Optional<String> villeAvecLePlusDeStarbucksFilter(final List<StarbucksDto> starbucks, final Predicate<StarbucksDto> filterPredicate)
```
Créer une implémentation dans StarbucksService retournant la ville comportant le plus de Starbucks selon un prédicat.

Utiliser cette méthode pour récuperer la ville possédant le plus de Starbucks dans l'état du Texas
Hint: getState() de StarbucksDto doit être égal à "TX"

Utiliser cette méthode pour récuperer la ville possédant le plus de Starbucks dans l'état de Californie
Hint: getState() de StarbucksDto doit être égal à "CA"


## Etape 5:
En utilisant cette signature :
```
public List<Map.Entry<String, Long>> villeNMax(final List<StarbucksDto> starbucks, final int nFirst)
```

Créer une implémentation dans StarbucksService retournant la liste des nFirst couple (ville, occurence) comportant le plus de Starbucks de toutes la liste, trié par ordre décroissant.



#Exercice 2 : Scrabble

Groupe 1 : .
Groupe 2 : .
Groupe 3 : .


Importer le projet dans Eclipse (Import Existing Maven Project)

## Prérequis 
Vérification le fonctionnement du projet en envoyant une notification (ifft ou twitter).

## Etape 1 : Back to Basic
Créer un nouveau projet.

Le but est de refactorer le code avec un manager et un plugin.

Que doit faire le manager ?
Que doit faire le plugin ?

Le manager devra comporter une méthode envoyant un message avec la signature suivante : 
void sendMessage(String message)

## Etape 2 : Broadcasting
Modifier le manager de manière à ce qu'il puisse gérer une liste de plugins.

Créer un second plugin à partir de l'implémentation du projet sample-mail.

Le manager devra maintenant envoyer le message à tous les canaux de communication.
La signature de send message doit rester inchangée : 
void sendMessage(String message)

S'il vous reste du temps, intégrer le plugin de l'autre groupe

## Etape 3 : Selecting channels
Modifier le manager de manière à créer un sélecteur de canaux.

Une nouvelle méthode dans le manager devra être ajoutée pour choisir les channels sur lesquels le message doit être envoyé.

La signature sera la suivante : 
void sendMessage(String message, String... channels);

## Etape 4 : Performance monitoring
En utilisant l'AOP, ajouter un comportement permettant de mesurer les temps de réponse lors des appels aux méthodes sendMessage.

## Etape 5 : NSA logs
Toujours en utilisant l'AOP, ajouter un comportement permettant de tracer les paramètres en entrée et les réponse de sendMessage.

Ce comportement doit pouvoir être décorrélé de l'implémentation de l'étape 4.


