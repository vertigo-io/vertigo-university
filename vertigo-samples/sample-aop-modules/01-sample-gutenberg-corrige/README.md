#Exercice 1 : Découverte 

Projet sample-gutenberg.

Importer le projet dans Eclipse (Import Existing Maven Project)

## Prérequis 
Vérification le fonctionnement de TextProcessorManagerImpl donnant le nombre de lignes.

## Nombre de mots
Créer une implémentation donnant le nombre de mots.

## Nombre de caractères
Créer une implémentation donnant le nombre de caractères.

## Mot le plus long
Créer une implémentation donnant le mot le plus long.


#Exercice 2 : Moteur de notifications

Groupe 1 : Projet sample-ifttt.
Groupe 2 : Projet sample-twitter.


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


