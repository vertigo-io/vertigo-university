# sample-notifications

Moteur de notifications : un manager, des plugins de canaux (mail, Twitter, IFTTT) et des aspects AOP de trace/supervision.

## Démarrage

- **Prérequis** : identifiants de test pour les canaux (mail, Twitter, IFTTT) à renseigner dans la configuration
- Lancer la classe main `io.vertigo.notifications.NotificationSample` : envoie un message sur tous les canaux, puis sur des canaux sélectionnés

## Contenu

- `notifications` : NotificationManager + NotificationSample
- `notifications.impl` : l'implémentation du manager et le plugin de notification
- `notifications.plugins.mail` / `.twitter` / `.ifttt` : les canaux
- `notifications.aspects.trace` / `.supervision` : les aspects AOP
