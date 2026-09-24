# 02-sample-mail

Envoi d'un mail via le `MailManager` Vertigo (node Vertigo).

## Démarrage

- Lancer la classe main `io.vertigo.mail.MailSample`
- La config (`MailSampleConfigBuilder`) contient des adresses placeholders internes (host SMTP, `developmentMailTo`) à remplacer ; avec `developmentMode=true`, le mail n'est pas réellement envoyé (logué à la place)

## Contenu

- `mail.MailSample` : la classe main
- `config.MailSampleConfigBuilder` : le NodeConfig (MailFeatures + SocialFeatures)
