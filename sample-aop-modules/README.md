# sample-aop-modules

Les aspects AOP pas à pas : back to basic → broadcasting → selecting channels → monitoring → NSA logs.
Chaque exercice existe en version **exo** (à compléter) et **corrige** (complété).

| Sous-module | Type | Sujet |
|---|---|---|
| [01-sample-gutenberg](01-sample-gutenberg) | Exo | Traitement de texte via un manager Vertigo (nb de mots, de caractères, mot le plus long) |
| [01-sample-gutenberg-corrige](01-sample-gutenberg-corrige) | Corrigé | Idem, complété |
| [02-sample-ifttt](02-sample-ifttt) | Exo | Notification IFTTT Maker (client JAX-RS brut) |
| [02-sample-mail](02-sample-mail) | Exo | Envoi d'un mail via le MailManager Vertigo (verso yopmail) |
| [02-sample-twitter](02-sample-twitter) | Exo | Envoyer un tweet via Twitter4J brut |
| [02-sample-notifications-corrige](02-sample-notifications-corrige) | Corrigé | Le moteur de notifications final (plugins mail/Twitter/IFTTT + aspects) |

Chaque sous-module se lance par sa classe main (`Gutenberg`, `IftttSample`, `MailSample`, `TwitterSample`, `NotificationSample`).
