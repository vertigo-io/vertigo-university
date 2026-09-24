# 02-sample-notifications-corrige

Corrigé de l'« Exercice 2 : Moteur de notifications » (énoncé dans [../01-sample-gutenberg/README.md](../01-sample-gutenberg/README.md)) : moteur complet — manager + plugins de canaux + aspects AOP.

## Démarrage

- **Prérequis** : comme les 3 samples de canaux (`ifttt.properties`, `twitter4j.properties`) + adresses placeholders à remplacer dans `NotificationSampleConfigBuilder`
- Lancer la classe main `io.vertigo.notifications.NotificationSample` : envoie un message sur tous les canaux, puis uniquement sur `mail` + `twitter`

## Contenu

- `notifications.NotificationManager` / `impl.NotificationManagerImpl` : le manager (`sendMessage`, `sendMessage(message, channels…)`)
- `notifications.plugins.mail` / `.twitter` / `.ifttt` : les plugins de canaux
- `notifications.aspects.trace` / `.supervision` : les aspects AOP (traces + temps de réponse)
- `notifications.config.NotificationSampleConfigBuilder` : le NodeConfig (modules + plugins + aspects)
