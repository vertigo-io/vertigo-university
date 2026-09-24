# 02-sample-ifttt

Notification IFTTT Maker via un client JAX-RS brut (sans node Vertigo).

## Démarrage

- **Prérequis** : créer `src/main/resources/ifttt.properties` avec l'url du webhook Maker (clé IFTTT)
- Le proxy HTTPS durci dans `IftttSample` (lignes 36-37) est à adapter ou supprimer selon son réseau
- Lancer la classe main `io.vertigo.ifttt.IftttSample` : poste un message JSON vers IFTTT Maker

## Contenu

- `ifttt.IftttSample` : la classe main (client JAX-RS)
- `ifttt.MakerEvent` : le payload JSON (value1/value2)
