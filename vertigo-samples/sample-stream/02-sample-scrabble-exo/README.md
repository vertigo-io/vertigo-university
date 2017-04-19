#Exercice 2 : Scrabble 

Importer le projet dans Eclipse (Import Existing Maven Project)

## Prérequis 
Vérifier le fonctionnement du projet. La méthode printStarbucks doit donner le nombre de mots du texte maupassant.txt 

## Etape 1

En utilisant cette signature :
```
public String concatMot(final Path text, final String separator) throws IOException {
```
Créer une implémentation dans ScrabbleService retournant la concaténation des 10 premiers mots avec un séparateur.


## Etape 2 
En utilisant cette signature :
```
Map<String, Long> histogramFrequenceMotAvecCollectors(final Path text) throws IOException {
```

Créer une implémentation dans ScrabbleService retournant l'histogramme du nombre de mots en utilisant les Collectors.

## Etape 3
En utilisant cette signature :
```
Map<String, Long> histogramFrequenceMotSansCollectors(final Path text) throws IOException {
```

Créer une implémentation dans ScrabbleService retournant l'histogramme du nombre de mots sans utiliser les Collectors.

## Etape 4
En utilisant cette signature :
```
public int scoreScrabble(final String word)
```

Créer une implémentation dans ScrabbleService calculant le score d'un mot. La valeur de chaque lettre est stockée dans la map SCRABBLE_POINT.

Rem: On ne tiendra pas compte du nombre de lettres disponibles.

## Etape 5
En utilisant cette signature :
```
public int meilleurScoreScrabble(final Path text) throws IOException {
```

Créer une implémentation dans ScrabbleService donnant le meilleur score pour l'ensemble des mots du texte maupassant.txt

Rem: On ne tiendra pas compte du nombre de lettres disponibles.

## Etape 6
En utilisant cette signature :
```
public Map<Integer, List<String>> mapScoreMot(final Path text) throws IOException {
```

Créer une implémentation dans ScrabbleService donnant une map de score avec la liste des mots associés

Rem: On ne tiendra pas compte du nombre de lettres disponibles.



## Resultats:

Nombre de starbucks = 10843
Starbucks d'Alabama = [Target Oxford T-2153, I-20 & Hwy 21, I-85 @ Hwy 280, Target Opelika T-1499 ...]
Ville avec le plus de starbucks = New York
Ville avec le plus de starbucks au Texas = Houston
Ville avec le plus de starbucks en Californie  = San Diego
Ville avec le plus de Starbucks = [New York=202, Chicago=173, Seattle=141]
