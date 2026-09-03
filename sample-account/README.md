# sample-account

Vertigo Account : authentification et autorisation (login, UserSession, security manager, row-level security sur les requêtes).

## Démarrage

- `account.run.CreateDatabase` : crée la base H2
- `account.run.AccountSample` : login (`admin` / `v3rt1g0`) + test de la sécurité des requêtes

## Contenu

- `account.services` : UserServices
- `account.webservices.TestUserSession` : la session de test (Jetty)
- `src/main/resources/authentication/` : les définitions de sécurité (auth-config.json, identities.txt)

## Code généré

`src/main/javagen` : DAO/domaine (User, Role, UserGroup, Movie) + authorizations. Le runner `account.run.Studio` régénère l'ensemble à partir de `studio-config.yaml`.
